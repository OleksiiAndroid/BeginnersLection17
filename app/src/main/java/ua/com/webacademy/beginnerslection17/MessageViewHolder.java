package ua.com.webacademy.beginnerslection17;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewMessage;
    public TextView mTextViewTime;

    public MessageViewHolder(View itemView) {
        super(itemView);

        mTextViewMessage = (TextView) itemView.findViewById(android.R.id.text1);
        mTextViewTime = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
