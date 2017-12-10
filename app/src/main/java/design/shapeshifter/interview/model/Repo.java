package design.shapeshifter.interview.model;

import com.google.gson.annotations.SerializedName;

public final class Repo {
  public final int id;
  @SerializedName("name") public final String name;
  @SerializedName("full_name") public final String fullName;
  @SerializedName("description") public final String description;
  @SerializedName("stargazers_count") public final int stars;

  public Repo(int id, String name, String fullName, String description, int stars) {
    this.id = id;
    this.name = name;
    this.fullName = fullName;
    this.description = description;
    this.stars = stars;
  }
}