package ua.com.webacademy.beginnerslection17.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ua.com.webacademy.beginnerslection17.R;
import ua.com.webacademy.beginnerslection17.adapter.CommentsAdapter;
import ua.com.webacademy.beginnerslection17.model.CommentModel;
import ua.com.webacademy.beginnerslection17.service.APIService;

public class CommentsFragment extends APIFragment {

    private static final String EXTRA_POST_ID = "ua.com.webacademy.beginnerslection17.extra.POST_ID";

    private long postId;

    private LoadTask task;

    private RecyclerView recyclerView;
    private CommentsAdapter adapter;
    private List<CommentModel> comments = new ArrayList<>();


    private CommentsFragment(APIService service) {
        super(service);
    }

    public static CommentsFragment getInstance(APIService service, long postId) {
        CommentsFragment fragment = new CommentsFragment(service);

        Bundle args = new Bundle();
        args.putLong(EXTRA_POST_ID, postId);

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postId = getArguments().getLong(EXTRA_POST_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new CommentsAdapter(getContext(), comments);
        recyclerView.setAdapter(adapter);

        task = new LoadTask(service);
        task.execute(postId);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (task != null) {
            task.cancel(true);
        }

        adapter = null;
        comments.clear();
    }


    //Async
    class LoadTask extends AsyncTask<Long, Void, List<CommentModel>> {

        private APIService service;

        public LoadTask(APIService service) {
            this.service = service;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }

        @Override
        protected List<CommentModel> doInBackground(Long... values) {
            List<CommentModel> comments = new ArrayList<>();

            try {
                Response<List<CommentModel>> response = service.comments(values[0]).execute();
                if (response.isSuccessful()) {
                    comments.addAll(response.body());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return comments;
        }

        @Override
        protected void onPostExecute(List<CommentModel> commentModels) {
            super.onPostExecute(commentModels);

            comments.clear();
            comments.addAll(commentModels);

            adapter.notifyDataSetChanged();

            hideProgress();
        }
    }
}
