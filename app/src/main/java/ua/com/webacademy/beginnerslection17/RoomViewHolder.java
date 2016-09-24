package ua.com.webacademy.beginnerslection17;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RoomViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewName;
    public TextView mTextViewDescription;

    public RoomViewHolder(View itemView) {
        super(itemView);

        mTextViewName = (TextView) itemView.findViewById(android.R.id.text1);
        mTextViewDescription = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
