package ua.com.webacademy.beginnerslection17.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.com.webacademy.beginnerslection17.model.CommentModel;
import ua.com.webacademy.beginnerslection17.model.PostModel;

public interface APIService {
    @GET("posts")
    Call<List<PostModel>> posts();

    @GET("comments")
    Call<List<CommentModel>> comments(@Query("postId") long postId);

    @POST("posts")
    public Call<PostModel> savePost(@Body PostModel data);
}
