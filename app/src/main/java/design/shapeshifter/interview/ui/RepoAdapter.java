package design.shapeshifter.interview.ui;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import design.shapeshifter.interview.R;
import design.shapeshifter.interview.model.Repo;

class RepoAdapter extends RecyclerView.Adapter<RepoViewHolder> {
  private final List<Repo> repos = new ArrayList<>();

  public void setData(List<Repo> newRepos) {
    final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
      @Override
      public int getOldListSize() {
        return repos.size();
      }

      @Override
      public int getNewListSize() {
        return newRepos.size();
      }

      @Override
      public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final int oldId = repos.get(oldItemPosition).id;
        final int newId = newRepos.get(newItemPosition).id;
        return Objects.equals(oldId, newId);
      }

      @Override
      public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Repo oldRepo = repos.get(oldItemPosition);
        final Repo newRepo = newRepos.get(newItemPosition);
        return Objects.equals(oldRepo.name, newRepo.name);
      }
    });
    repos.clear();
    repos.addAll(newRepos);
    result.dispatchUpdatesTo(this);
  }

  @Override
  public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new RepoViewHolder(inflater.inflate(R.layout.view_holder_item, parent, false));
  }

  @Override
  public void onBindViewHolder(RepoViewHolder holder, int position) {
    holder.textView.setText(repos.get(position).name);
  }

  @Override
  public int getItemCount() {
    return repos.size();
  }
}