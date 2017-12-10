package design.shapeshifter.interview.common;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AppExecutors {
  private static AppExecutors appExecutors;

  public static AppExecutors create() {
    if (appExecutors == null) {
      appExecutors = new AppExecutors();
    }
    return appExecutors;
  }

  private final Executor networkIO;
  private final Executor mainThread;

  private AppExecutors() {
    this.networkIO = Executors.newFixedThreadPool(3);
    this.mainThread = new MainThreadExecutor();
  }

  public Executor networkIO() {
    return networkIO;
  }

  public Executor mainThread() {
    return mainThread;
  }

  private static class MainThreadExecutor implements Executor {
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
      mainThreadHandler.post(command);
    }
  }
}