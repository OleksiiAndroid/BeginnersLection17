package ua.com.webacademy.beginnerslection17.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ua.com.webacademy.beginnerslection17.R;
import ua.com.webacademy.beginnerslection17.activity.APIActivity;
import ua.com.webacademy.beginnerslection17.adapter.PostsAdapter;
import ua.com.webacademy.beginnerslection17.model.PostModel;
import ua.com.webacademy.beginnerslection17.service.APIService;

public class PostsFragment extends APIFragment {

    private IPost listener;
    private LoadTask loadTask;
    private SaveTask saveTask;

    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private List<PostModel> posts = new ArrayList<>();

    private EditText editTextTitle, editTextBody;

    private PostsFragment(APIService service) {
        super(service);
    }


    public static PostsFragment getInstance(APIService service) {
        return new PostsFragment(service);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextBody = view.findViewById(R.id.editTextBody);

        view.findViewById(R.id.buttonSave).setOnClickListener(saveListener);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (APIActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new PostsAdapter(getContext(), posts, clickListener);
        recyclerView.setAdapter(adapter);

        loadTask = new LoadTask(service);
        loadTask.execute();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (loadTask != null) {
            loadTask.cancel(true);
        }
        if (saveTask != null) {
            saveTask.cancel(true);
        }

        adapter = null;
        posts.clear();
    }


    //Click
    private PostsAdapter.IPostList clickListener = new PostsAdapter.IPostList() {
        @Override
        public void onClick(int position, PostModel post) {
            if (listener != null) {
                listener.onClick(post.id);
            }
        }
    };

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = editTextTitle.getText().toString();
            String body = editTextBody.getText().toString();

            if (!title.isEmpty() && !body.isEmpty()) {
                PostModel post = new PostModel();
                post.title = title;
                post.body = body;

                saveTask = new SaveTask(service);
                saveTask.execute(post);
            } else {
                Toast.makeText(getContext(), "Empty fields", Toast.LENGTH_LONG).show();
            }
        }
    };


    //Listener
    public interface IPost {
        void onClick(long id);
    }


    //Async
    class LoadTask extends AsyncTask<Void, Void, List<PostModel>> {

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
        protected List<PostModel> doInBackground(Void... voids) {
            List<PostModel> posts = new ArrayList<>();

            try {
                Response<List<PostModel>> response = service.posts().execute();
                if (response.isSuccessful()) {
                    posts.addAll(response.body());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return posts;
        }

        @Override
        protected void onPostExecute(List<PostModel> postModels) {
            super.onPostExecute(postModels);

            posts.clear();
            posts.addAll(postModels);

            adapter.notifyDataSetChanged();

            hideProgress();
        }
    }

    class SaveTask extends AsyncTask<PostModel, Void, PostModel> {

        private APIService service;

        public SaveTask(APIService service) {
            this.service = service;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }

        @Override
        protected PostModel doInBackground(PostModel... values) {
            try {
                PostModel post = values[0];

                Response<PostModel> response = service.savePost(post).execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(PostModel post) {
            super.onPostExecute(post);

            hideProgress();

            if (post == null) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), String.format("SUCCESS: %d", post.id), Toast.LENGTH_SHORT).show();

                editTextTitle.setText(null);
                editTextBody.setText(null);
            }
        }
    }
}
