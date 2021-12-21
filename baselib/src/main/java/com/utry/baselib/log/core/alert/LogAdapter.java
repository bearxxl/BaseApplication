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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.utry.baselib.R;

import java.util.List;

public class LogAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<LogLine> mData;
    private ConfigLogAlert mConfigLogAlert;


    public LogAdapter(Context context, List<LogLine> objects, ConfigLogAlert mConfigLogAlert) {
        this.mContext = context.getApplicationContext();
        this.mData = objects;
        this.mConfigLogAlert = mConfigLogAlert;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(List<LogLine> objects) {
        mData = objects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public LogLine getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.log_list_item, parent, false);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.log_time);
            holder.tag = (TextView) convertView.findViewById(R.id.log_tag);
            holder.msg = (TextView) convertView.findViewById(R.id.log_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LogLine line = getItem(position);
        final int color = mConfigLogAlert.getColor(line.getLevel());

        holder.time.setText(line.getDateTime());
        holder.tag.setText(line.getTagLevel());
        holder.msg.setText(line.getMessage());

        holder.time.setTextColor(color);
        holder.tag.setTextColor(color);
        holder.msg.setTextColor(color);

        holder.time.setTextSize(mConfigLogAlert.getLogTextSize());
        holder.tag.setTextSize(mConfigLogAlert.getLogTextSize());
        holder.msg.setTextSize(mConfigLogAlert.getLogTextSize());

        holder.time.setAlpha(mConfigLogAlert.getLogTextAlpha());
        holder.tag.setAlpha(mConfigLogAlert.getLogTextAlpha());
        holder.msg.setAlpha(mConfigLogAlert.getLogTextAlpha());

        //convertView.setAlpha(mConfigLogAlert.getLogBackgroundAlpha());

        return convertView;
    }

    private class ViewHolder {
        public TextView time;
        public TextView tag;
        public TextView msg;
    }

}
