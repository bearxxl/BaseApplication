/*
 * Copyright (C) 2013 readyState Software Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utry.baselib.log;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;


import com.utry.baselib.R;
import com.utry.baselib.log.core.LogConstants;
import com.utry.baselib.log.core.LogLevel;
import com.utry.baselib.log.core.alert.ConfigLogAlert;
import com.utry.baselib.log.core.alert.LogAdapter;
import com.utry.baselib.log.core.alert.LogLine;
import com.utry.baselib.log.core.alert.LogReaderAsyncTask;

import java.util.Calendar;
import java.util.LinkedList;

public class ProfUseLogAlertService extends Service implements View.OnTouchListener {

    private static final String TAG = ProfUseLogAlertService.class.getName();

    private static boolean sIsRunning = false;
    private boolean mIsLogPaused = false;

    private LogReaderAsyncTask mLogReaderTask;
    private Handler mLogBufferUpdateHandler = new Handler();

    private Context context;
    private LayoutInflater inflator;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wParamsTop;
    //上下两个布局
    private RelativeLayout layoutTop;
    private ListView mLogListView;
    private LogAdapter mAdapter;
    private LinkedList<LogLine> mLogBuffer;

    private ConfigLogAlert mConfigLogAlert = new ConfigLogAlert();

    //最小滑动距离
    private int mTouchSlop;
    //屏幕宽高
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private int downX, downY;
    //down 时的时间戳
    private int downTime;
    //top xy坐标, bottom y坐标
    private int topY, topX;
    //top 的宽高
    private int topWidth, topHeight;

    public static boolean isRunning() {
        return sIsRunning;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ProfUseLogUtils.d(TAG, "onStartCommand: ");

        if (null != intent && intent.hasExtra(LogConstants.LOG_KEY_PARAMS_ALERT)) {
            mConfigLogAlert = intent.getParcelableExtra(LogConstants.LOG_KEY_PARAMS_ALERT);
        }

        sIsRunning = true;

        createSystemWindow();

        startLogReader();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sIsRunning = false;

        mLogBuffer.clear();

        stopLogReader();

        removeSystemWindow();

        ProfUseLogUtils.d(TAG, "onDestroy: ");

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createSystemWindow() {

        this.context = getApplicationContext();
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);

        //需要减去状态栏高度
        //screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        //screenHeight = mWindowManager.getDefaultDisplay().getHeight() - statusBarHeight;
        screenWidth = mWindowManager.getDefaultDisplay().getWidth();
        screenHeight = mWindowManager.getDefaultDisplay().getHeight();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        layoutTop = (RelativeLayout) inflator.inflate(R.layout.log_window_debug, null);
        mLogListView = (ListView) layoutTop.findViewById(R.id.log_list);
        Button logMoveBtn = (Button) layoutTop.findViewById(R.id.log_move_btn);
        logMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfUseLogUtils.i(TAG, "onClick: 移动查看开关被点击");
                if (mIsLogPaused) {
                    mIsLogPaused = false;
                    mLogListView.setOnTouchListener(ProfUseLogAlertService.this);
                    logMoveBtn.setText("移动");
                } else {
                    mIsLogPaused = true;
                    mLogListView.setOnTouchListener(null);
                    logMoveBtn.setText("查看");
                }
            }
        });
        Button logCloseBtn = (Button) layoutTop.findViewById(R.id.log_close_btn);
        logCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });

        EditText editText = (EditText) layoutTop.findViewById(R.id.log_tag);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        //editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tagString = editable.toString().trim();
                if (tagString.isEmpty()) {
                    mConfigLogAlert.setLogAutoFilterTag(false);
                } else {
                    mConfigLogAlert.setLogAutoFilterTag(true);
                    mConfigLogAlert.setLogFilterTags(new String[]{tagString});
                }
                if (mLogReaderTask != null) mLogReaderTask.setuConfigLogAlert(mConfigLogAlert);
            }
        });

        String[] log_letter = context.getResources().getStringArray(R.array.log_letter);

        Spinner spinner = (Spinner) layoutTop.findViewById(R.id.log_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mConfigLogAlert.setLogAlertLevel(i);
                if (mLogReaderTask != null) mLogReaderTask.setuConfigLogAlert(mConfigLogAlert);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        layoutTop.setBackgroundColor(Color.parseColor(mConfigLogAlert.getLogBackground()));

        mLogBuffer = new LinkedList<>();
        LogLine logLine = new LogLine();
        logLine.setLevel(LogLevel.TYPE_LEVELS_VALUE.get(mConfigLogAlert.getLogAlertLevel()));
        logLine.setDate(Calendar.getInstance().getTime().toLocaleString());
        logLine.setMessage("开启日志打印，等待日志出现后，点击日志可以移动窗口");
        mLogBuffer.add(logLine);

        mAdapter = new LogAdapter(this, mLogBuffer, mConfigLogAlert);
        mLogListView.setAdapter(mAdapter);

        mLogListView.setOnTouchListener(this);

        topWidth = 40;
        topHeight = 40;

        topX = getCoordinateX(context);
        topY = getCoordinateY(context);

        wParamsTop = new WindowManager.LayoutParams();
        wParamsTop.width = (screenWidth / 2) + (screenWidth / 3);
        wParamsTop.height = (screenHeight / 2);
        //初始化坐标
        wParamsTop.x = topX;
        wParamsTop.y = topY;
        //弹窗类型
        //wParamsTop.type = WindowManager.LayoutParams.TYPE_PHONE;
        wParamsTop.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //wParamsTop.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //以左上角为基准
        wParamsTop.gravity = Gravity.START | Gravity.TOP;
        //        wParamsTop.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //如果不加,背景会是一片黑色。
        wParamsTop.format = PixelFormat.RGBA_8888;
        wParamsTop.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //伪全屏
        wParamsTop.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        mWindowManager.addView(layoutTop, wParamsTop);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LogConstants.LOG_ALERT_STATUS_REFRESH:
                    mWindowManager.updateViewLayout(layoutTop, wParamsTop);
                    break;
                case LogConstants.LOG_ALERT_STATUS_CLICK:
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downTime = (int) System.currentTimeMillis();

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                //保留相对距离，后面可以通过绝对坐标算出真实坐标
                downX = (int) event.getX();
                downY = (int) event.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(event.getRawX() - lastX) > 0 || Math.abs(event.getRawY() - lastY) > 0) {
                    topX = (int) (event.getRawX() - downX);
                    //需要减去状态栏高度
                    topY = (int) (event.getRawY() - downY);

                    //top左右不能越界
                    if (topX < 0) {
                        topX = 0;
                    } else if ((topX + topWidth) > screenWidth) {
                        topX = screenWidth - topWidth;
                    }
                    wParamsTop.x = topX;

                    //top上下不能越界
                    if (topY < 0) {
                        topY = 0;
                    } else if ((topY + topHeight) > screenHeight) {
                        topY = screenHeight - topHeight;
                    }
                    wParamsTop.y = topY;

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();

                    downTime = (int) System.currentTimeMillis();

                    mHandler.sendEmptyMessage(LogConstants.LOG_ALERT_STATUS_REFRESH);
                }

                break;
            case MotionEvent.ACTION_UP:
                mLogListView.performClick();

                int currentTime = (int) System.currentTimeMillis();
                if (currentTime - downTime > 200 && Math.abs(event.getRawX() - lastX)
                        < mTouchSlop && Math.abs(event.getRawY() - lastY) < mTouchSlop) {
                    mHandler.sendEmptyMessage(LogConstants.LOG_ALERT_STATUS_CLICK);
                }

                //保留坐标
                setCoordinateX(context, topX);
                setCoordinateY(context, topY);
                break;
        }
        return true;
    }

    private void removeSystemWindow() {
        if (layoutTop != null && layoutTop.getParent() != null) {
            mWindowManager.removeView(layoutTop);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void startLogReader() {
        mLogReaderTask = new LogReaderAsyncTask(mConfigLogAlert) {
            @Override
            protected void onProgressUpdate(LogLine... values) {
                // process the latest logcat lines
                for (LogLine line : values) {
                    if (LogLevel.TYPE_LEVELS_VALUE.indexOf(line.getLevel()) >= (mConfigLogAlert.getLogAlertLevel())) {
                        updateBuffer(line);
                    }
                }
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                if (!ok) {
                    // not root - notify activity
                    ProfUseLogUtils.d(TAG, "onPostExecute: startLogReader is not root");
                }
            }
        };
        mLogReaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        ProfUseLogUtils.d(TAG, "startLogReader: task started");

    }

    private void stopLogReader() {

        if (mLogReaderTask != null) {
            mLogReaderTask.cancel(true);
        }
        mLogReaderTask = null;

    }

    private void updateBuffer(final LogLine line) {
        mLogBufferUpdateHandler.post(new Runnable() {
            @Override
            public void run() {

                // update adapter
                if (!mIsLogPaused) {
                    if (line != null) {
                        mLogBuffer.add(line);
                    }

                    // purge old entries
                    while (mLogBuffer.size() > LogConstants.LOG_BUFFER_LIMIT) {
                        mLogBuffer.removeFirst();
                    }
                    mAdapter.setData(mLogBuffer);
                }

            }
        });
    }

    /**
     * top的x、y坐标
     * @param context
     * @param x
     */
    public static void setCoordinateX(Context context, int x) {
        SharedPreferences.Editor editor = context.getSharedPreferences("window_sp", Context.MODE_PRIVATE).edit();
        editor.putInt("windowX", x);
        editor.apply();
    }

    public static int getCoordinateX(Context context) {
        return context.getSharedPreferences("window_sp", Context.MODE_PRIVATE).getInt("windowX", 100);
    }

    public static void setCoordinateY(Context context, int y) {
        SharedPreferences.Editor editor = context.getSharedPreferences("window_sp", Context.MODE_PRIVATE).edit();
        editor.putInt("windowY", y);
        editor.apply();
    }

    public static int getCoordinateY(Context context) {
        return context.getSharedPreferences("window_sp", Context.MODE_PRIVATE).getInt("windowY", 100);
    }

}
