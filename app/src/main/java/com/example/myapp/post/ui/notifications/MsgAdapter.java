package com.example.myapp.post.ui.notifications;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Msg> MsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout topLinearLayout;
        LinearLayout bottomLinearLayout;

        TextView numMsg;
        TextView timeMsg;
        TextView textMsg;

        Button button_shoucang;
        Button button_jubao;

        public ViewHolder(View view){
            super(view);
            topLinearLayout = (LinearLayout)view.findViewById(R.id.top_linearlayout);
            bottomLinearLayout = (LinearLayout)view.findViewById(R.id.bottom_linearlayout);
            numMsg = (TextView)view.findViewById(R.id.text_num);
            timeMsg = (TextView)view.findViewById(R.id.text_time);
            textMsg = (TextView)view.findViewById(R.id.text_text);
            button_shoucang = (Button)view.findViewById(R.id.shoucang);
            button_jubao = (Button)view.findViewById(R.id.jubao);
        }
    }
    public MsgAdapter(List<Msg> msgList){
        MsgList = msgList;
    }

    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.msg_item,parent,false);
        Log.d("onCreateViewHolder", "onCreateViewHolder: success");
        return new MsgAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.ViewHolder holder, int position) {
        Msg msg = MsgList.get(position);
        //收到的消息
        if(msg.getType()== Msg.TYPE_RECEICED){
            holder.topLinearLayout.setVisibility(View.VISIBLE);
            holder.bottomLinearLayout.setVisibility(View.GONE);
            holder.numMsg.setText(msg.getContent());
            holder.timeMsg.setText(msg.getContent());
            holder.textMsg.setText(msg.getContent());
            Log.d("received msg", "onBindViewHolder: 1");
        }else if(msg.getType()== Msg.TYPE_SEND){
            holder.topLinearLayout.setVisibility(View.GONE);
            holder.bottomLinearLayout.setVisibility(View.VISIBLE);
            holder.numMsg.setText(msg.getContent());
            holder.timeMsg.setText(msg.getContent());
            holder.textMsg.setText(msg.getContent());
            Log.d("send", "onBindViewHolder: 2");
        }
    }

    @Override
    public int getItemCount() {

        return MsgList.size();
    }
}

