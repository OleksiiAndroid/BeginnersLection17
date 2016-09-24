package ua.com.webacademy.beginnerslection17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mListView;
    private EditText mEditTextName;
    private EditText mEditTextDescription;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Room, RoomViewHolder> mFirebaseAdapter;

    private static final String FIREBASE_CHILD_ROOMS = "rooms";
    public static final String EXTRA_KEY = "ua.com.webacademy.beginnerslection17.extra.KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (RecyclerView) findViewById(R.id.listView);
        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextDescription = (EditText) findViewById(R.id.editTextDescription);

        mListView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Room room = new Room(mEditTextDescription.getText().toString());

                mDatabaseReference.child(FIREBASE_CHILD_ROOMS + "/" + mEditTextName.getText().toString()).setValue(room);

                mEditTextName.setText(null);
                mEditTextDescription.setText(null);
            }
        });

        initRooms();
    }

    private void initRooms() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Room, RoomViewHolder>(
                Room.class,
                android.R.layout.simple_list_item_2,
                RoomViewHolder.class,
                mDatabaseReference.child(FIREBASE_CHILD_ROOMS).orderByKey()) {

            @Override
            protected void populateViewHolder(RoomViewHolder holder, Room room, int position) {
                final String key = mFirebaseAdapter.getRef(position).getKey();

                holder.mTextViewName.setText(key);
                holder.mTextViewDescription.setText(room.getDescription());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Activity2.class);
                        intent.putExtra(EXTRA_KEY, key);

                        startActivity(intent);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mDatabaseReference.child(FIREBASE_CHILD_ROOMS + "/" + key).removeValue();

                        return true;
                    }
                });
            }
        };

        mListView.setAdapter(mFirebaseAdapter);
    }
}
