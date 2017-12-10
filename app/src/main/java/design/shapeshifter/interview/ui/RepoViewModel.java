package design.shapeshifter.interview.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import design.shapeshifter.interview.model.Repo;
import design.shapeshifter.interview.model.Resource;
import design.shapeshifter.interview.repository.RepoRepository;

public class RepoViewModel extends ViewModel {
  private final RepoRepository repoRepository = RepoRepository.create();
  private final MutableLiveData<String> query = new MutableLiveData<>();
  private final LiveData<Resource<List<Repo>>> repos;

  public RepoViewModel() {
    repos = Transformations.switchMap(query, search -> {
      if (search == null || search.trim().isEmpty()) {
        final MutableLiveData<Resource<List<Repo>>> results = new MutableLiveData<>();
        results.setValue(Resource.success(Collections.emptyList()));
        return results;
      } else {
        return repoRepository.loadRepos(search);
      }
    });
  }

  public void setQuery(String originalSearch) {
    final String search = originalSearch.toLowerCase(Locale.getDefault()).trim();
    if (Objects.equals(search, query.getValue())) {
      return;
    }
    query.setValue(search);
  }

  public LiveData<Resource<List<Repo>>> getRepos() {
    return repos;
  }
}
