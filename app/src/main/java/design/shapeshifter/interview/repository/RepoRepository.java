package design.shapeshifter.interview.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import design.shapeshifter.interview.api.GitHubService;
import design.shapeshifter.interview.api.GitHubServiceFactory;
import design.shapeshifter.interview.common.AppExecutors;
import design.shapeshifter.interview.model.Repo;
import design.shapeshifter.interview.model.Resource;

public final class RepoRepository {
  private static RepoRepository repoRepository;

  public static RepoRepository create() {
    if (repoRepository == null) {
      repoRepository = new RepoRepository();
    }
    return repoRepository;
  }

  private final AppExecutors appExecutors = AppExecutors.create();
  private final GitHubService gitHubService = GitHubServiceFactory.create();

  private RepoRepository() {}

  public LiveData<Resource<List<Repo>>> loadRepos(@NonNull String search) {
    return new ReposLiveData(search);
  }

  private class ReposLiveData extends MutableLiveData<Resource<List<Repo>>> {
    ReposLiveData(@NonNull String search) {
      postValue(Resource.loading());
      appExecutors.networkIO().execute(() -> {
        try {
          postValue(Resource.success(gitHubService.getRepos(search.trim()).execute().body()));
        } catch (IOException e) {
          postValue(Resource.error("Failed to query data"));
        }
      });
    }
  }
}
