package design.shapeshifter.interview.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import design.shapeshifter.interview.R;
import design.shapeshifter.interview.model.Status;

public class MainActivity extends AppCompatActivity {

  private RepoViewModel repoViewModel;
  private EditText editText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

    // Setup recycler view.
    final RecyclerView rv = findViewById(R.id.recycler_view);
    rv.setLayoutManager(new LinearLayoutManager(this));
    final RepoAdapter repoAdapter = new RepoAdapter();
    rv.setAdapter(repoAdapter);

    // Setup view model and live data.
    repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
    repoViewModel.getRepos().observe(this, listResource -> {
      final Status status = listResource.status;
      if (status == Status.SUCCESS) {
        repoAdapter.setData(listResource.data);
        swipeRefreshLayout.setRefreshing(false);
      } else if (status == Status.LOADING) {
        swipeRefreshLayout.setRefreshing(true);
      } else {
        Toast.makeText(MainActivity.this, listResource.message, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    // Setup edit text.
    editText = findViewById(R.id.text_input_edit_text);
    editText.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        doSearch(v);
        return true;
      }
      return false;
    });
    editText.setOnKeyListener((v, keyCode, event) -> {
      if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
        doSearch(v);
        return true;
      }
      return false;
    });
  }

  private void doSearch(View v) {
    final String query = editText.getText().toString();
    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
        .hideSoftInputFromWindow(v.getWindowToken(), 0);
    repoViewModel.setQuery(query);
  }
}
