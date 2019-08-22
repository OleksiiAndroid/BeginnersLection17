package ua.com.webacademy.beginnerslection17.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.com.webacademy.beginnerslection17.R;
import ua.com.webacademy.beginnerslection17.model.PostModel;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private LayoutInflater inflater;
    private List<PostModel> posts;
    private IPostList listener;


    public PostsAdapter(Context context, List<PostModel> values, IPostList listener) {
        inflater = LayoutInflater.from(context);
        posts = values;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(inflater.inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        final PostModel post = posts.get(position);
        holder.bind(post);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position, post);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    //ViewHolder
    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewBody;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewBody = itemView.findViewById(R.id.textViewBody);
        }

        public void bind(PostModel post) {
            textViewTitle.setText(post.title);
            textViewBody.setText(post.body);
        }
    }


    //Interface
    public interface IPostList {
        void onClick(int position, PostModel post);
    }
}
