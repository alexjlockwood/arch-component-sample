package design.shapeshifter.interview.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static design.shapeshifter.interview.model.Status.ERROR;
import static design.shapeshifter.interview.model.Status.LOADING;
import static design.shapeshifter.interview.model.Status.SUCCESS;

public final class Resource<T> {
  public static <T> Resource<T> success(@Nullable T data) {
    return new Resource<>(SUCCESS, data, null);
  }

  public static <T> Resource<T> error(@Nullable String msg) {
    return new Resource<>(ERROR, null, msg);
  }

  public static <T> Resource<T> loading() {
    return new Resource<>(LOADING, null, null);
  }

  @NonNull public final Status status;
  @Nullable public final String message;
  @Nullable public final T data;

  private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Resource<?>)) {
      return false;
    }
    final Resource<?> resource = (Resource<?>) o;
    return status == resource.status
        && (message != null ? message.equals(resource.message) : resource.message == null)
        && (data != null ? data.equals(resource.data) : resource.data == null);
  }

  @Override
  public int hashCode() {
    int result = status.hashCode();
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Resource{" +
        "status=" + status +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }
}
