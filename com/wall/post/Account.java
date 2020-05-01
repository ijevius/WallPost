package com.wall.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Account {
  public String access_token;
  
  public long user_id;
  
  public void restore(Context paramContext) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.access_token = sharedPreferences.getString("access_token", null);
    this.user_id = sharedPreferences.getLong("user_id", 0L);
  }
  
  public void save(Context paramContext) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    editor.putString("access_token", this.access_token);
    editor.putLong("user_id", this.user_id);
    editor.commit();
  }
}