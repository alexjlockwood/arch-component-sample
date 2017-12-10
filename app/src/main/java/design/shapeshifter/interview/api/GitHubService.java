package design.shapeshifter.interview.api;

import java.util.List;

import design.shapeshifter.interview.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

  @GET("users/{user}/repos")
  Call<List<Repo>> getRepos(@Path("user") String user);
}