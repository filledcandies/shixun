package com.example.myapp.post.ui.message.chat.util;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.post.ui.message.chat.entity.Msg;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> MsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLinearLayout;
        LinearLayout rightLinearLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View view){
            super(view);
            leftLinearLayout = (LinearLayout)view.findViewById(R.id.chat_left_layout_1);
            rightLinearLayout = (LinearLayout)view.findViewById(R.id.chat_right_layout_1);
            leftMsg = (TextView)view.findViewById(R.id.chat_left_msg_1);
            rightMsg = (TextView)view.findViewById(R.id.chat_right_msg_1);
        }
    }
    public MsgAdapter(List<Msg> msgList){
        MsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.msg_item,parent,false);
        Log.d("onCreateViewHolder", "onCreateViewHolder: success");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = MsgList.get(position);
        //收到的消息
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLinearLayout.setVisibility(View.VISIBLE);
            holder.rightLinearLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            Log.d("received msg", "onBindViewHolder: 1");
        }else if(msg.getType()==Msg.TYPE_SENT){
            holder.leftLinearLayout.setVisibility(View.GONE);
            holder.rightLinearLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
            Log.d("send", "onBindViewHolder: 2");
        }
    }

    @Override
    public int getItemCount() {

        return MsgList.size();
    }
}
