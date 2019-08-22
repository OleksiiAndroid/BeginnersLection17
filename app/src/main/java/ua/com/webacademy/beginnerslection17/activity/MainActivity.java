package ua.com.webacademy.beginnerslection17.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.com.webacademy.beginnerslection17.R;
import ua.com.webacademy.beginnerslection17.fragment.CommentsFragment;
import ua.com.webacademy.beginnerslection17.fragment.PostsFragment;
import ua.com.webacademy.beginnerslection17.service.APIService;


public class MainActivity extends APIActivity {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);

        init();
    }


    private void init() {
        Fragment fragment = PostsFragment.getInstance(service);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


    //Posts
    @Override
    public void onClick(long id) {
        Fragment fragment = CommentsFragment.getInstance(service, id);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("CommentsFragment").commit();
    }
}
