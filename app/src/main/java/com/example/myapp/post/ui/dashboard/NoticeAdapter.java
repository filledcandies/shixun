package com.example.myapp.post.ui.dashboard;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.entity.Notice;
import com.example.myapp.util.RelativeDateHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    private Context mContext;

    private List<Notice> notices;

    public NoticeAdapter(Context context, List<Notice> notices) {
        mContext = context;
        this.notices = notices;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeHolder holder, int position) {
        holder.mTitleNotice.setText("通知");
        holder.mTextNotice.setText(notices.get(position).getMessage());
        holder.mTimeNotice.setText(RelativeDateHandler.format(notices.get(position).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    class NoticeHolder extends RecyclerView.ViewHolder{

        private TextView mTitleNotice;
        private TextView mTextNotice;
        private TextView mTimeNotice;


        public NoticeHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mTitleNotice = itemView.findViewById(R.id.title_notice);
            mTextNotice = itemView.findViewById(R.id.text_notice);
            mTimeNotice = itemView.findViewById(R.id.time_notice);
        }
    }



}