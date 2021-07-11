package com.example.myapp.post.ui.notifications;

import android.os.Message;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

public class topicbox_adapter extends BaseAdapter{
    private List<Message> Datas;
    private Context mContext;

    public topicbox_adapter(List<Message> datas,Context mContext){
        Datas = datas;
        this.mContext = mContext;
    }

    public int getCount(){
        return Datas.size();
    }

    public Object getItem(int i){
        return Datas.get(i);
    }

    public  long getItemId(int i){
        return i;
    }

    public  View getView(int i,View view,ViewGroup viewgroup){
        view = LayoutInflater.from(mContext).inflate(R.layout.item_topicbox,viewgroup,false);
        //ImageView imageview = (ImageView)view.findViewById(R.id);
        TextView textView1 = (TextView)view.findViewById(R.id.text_num);
        TextView textView2 = (TextView)view.findViewById(R.id.text_time);
        TextView textView3 = (TextView)view.findViewById(R.id.text_text);
        textView1.setText("1");
        textView2.setText("2");
        textView3.setText("3");
        return view;
    }
}
