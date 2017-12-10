package design.shapeshifter.interview.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class GitHubServiceFactory {
  private static GitHubService gitHubService;

  public static GitHubService create() {
    if (gitHubService == null) {
      gitHubService = new Retrofit.Builder()
          .baseUrl("https://api.github.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(GitHubService.class);
    }
    return gitHubService;
  }

  private GitHubServiceFactory() {}
}
