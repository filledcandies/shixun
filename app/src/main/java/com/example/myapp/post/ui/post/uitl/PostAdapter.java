package com.example.myapp.post.ui.post.uitl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.post.ui.message.chat.util.MsgBoxAdapter;
import com.example.myapp.post.ui.post.Post;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView createTime;
        TextView context;

        public ViewHolder( View itemView) {
            super(itemView);
            createTime = (TextView) itemView.findViewById(R.id.post_add_time);
            context = (TextView)itemView.findViewById(R.id.post_context);
        }
    }
    public PostAdapter(List<Post> postList){
        this.posts = postList;

    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msgbox_item,parent,false);
        final PostAdapter.ViewHolder  holder = new PostAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        Post post = posts.get(position);
        holder.createTime.setText(post.getCreateTime().toString());
        holder.context.setText(post.getMessage());

    }

    @Override
    public int getItemCount() {
        return 0;
    }




}
