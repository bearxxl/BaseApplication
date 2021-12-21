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

package com.utry.baselib.log.core.alert;

import android.os.AsyncTask;


import com.utry.baselib.log.ProfUseLogUtils;
import com.utry.baselib.log.core.LogConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogReaderAsyncTask extends AsyncTask<Void, LogLine, Boolean> {

    private static final String TAG = LogReaderAsyncTask.class.getName();

    private ConfigLogAlert mConfigLogAlert;


    public LogReaderAsyncTask(ConfigLogAlert uConfigLogAlert) {
        this.mConfigLogAlert = uConfigLogAlert;
    }


    public void setuConfigLogAlert(ConfigLogAlert uConfigLogAlert) {
        this.mConfigLogAlert = uConfigLogAlert;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        LogSuperUserHelper.checkRoot();

        Process process = null;
        BufferedReader reader = null;
        boolean ok = true;

        try {

            //            ConfigLogAlert mConfigLogAlert = voids[0];
            // clear buffer first
            clearLogcatBuffer();

            if (LogConstants.IS_ROOT) {
                process = LogRuntimeHelper.exec(LogConstants.COMMAND_LOGCAT_ROOT);
            } else {
                process = LogRuntimeHelper.exec(LogConstants.COMMAND_LOGCAT);
            }

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()),
                    LogConstants.COMMAND_BUFFER_TYPE);

            while (!isCancelled()) {
                final String line = reader.readLine();
                //ProfUseLogUtils.i(TAG, "doInBackground: line===" + line);
                if (line != null) {
                    if (mConfigLogAlert.isLogAutoFilterTag()) {
                        for (String tag : mConfigLogAlert.getLogFilterTags()) {
                            if (line.contains(tag)) {
                                // publish result
                                publishProgress(new LogLine(line, LogConstants.IS_ROOT));
                            }
                        }
                    } else {
                        // publish result
                        publishProgress(new LogLine(line, LogConstants.IS_ROOT));
                    }
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
            ok = false;

        } finally {

            if (process != null) {
                LogRuntimeHelper.destroy(process);
            }

            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return ok;

    }

    private void clearLogcatBuffer() {
        try {
            Process process = LogRuntimeHelper.exec(LogConstants.COMMAND_LOGCAT_CLEAR);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            ProfUseLogUtils.e(TAG, "clearLogcatBuffer: ", e);
        }
    }

}
