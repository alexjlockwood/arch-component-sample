package design.shapeshifter.interview.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import design.shapeshifter.interview.R;

class RepoViewHolder extends RecyclerView.ViewHolder {
  public final TextView textView;

  public RepoViewHolder(View itemView) {
    super(itemView);
    textView = itemView.findViewById(R.id.text_view);
  }
}
