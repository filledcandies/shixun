package com.example.myapp.post.ui.dashboard;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

public class notice_adapter extends BaseAdapter {
    private List<Message> Datas;
    private Context mContext;

    public notice_adapter(List<Message> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }

    public int getCount() {
        return Datas.size();
    }

    public Object getItem(int i) {
        return Datas.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_notice, viewgroup, false);
        TextView textView1 = (TextView) view.findViewById(R.id.text_notice);
        textView1.setText("1");
        return view;
    }

}