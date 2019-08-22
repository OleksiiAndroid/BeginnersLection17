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
import ua.com.webacademy.beginnerslection17.model.CommentModel;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private LayoutInflater inflater;
    private List<CommentModel> posts;

    public CommentsAdapter(Context context, List<CommentModel> values) {
        inflater = LayoutInflater.from(context);
        posts = values;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(inflater.inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {
        final CommentModel comment = posts.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    //ViewHolder
    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewEmail, textViewBody;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewBody = itemView.findViewById(R.id.textViewBody);
        }

        public void bind(CommentModel comment) {
            textViewName.setText(comment.name);
            textViewEmail.setText(comment.email);
            textViewBody.setText(comment.body);
        }
    }
}
