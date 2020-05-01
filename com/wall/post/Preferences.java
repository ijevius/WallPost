package com.wall.post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class Preferences extends PreferenceActivity {
  Account account = new Account();
  
  Preference group;
  
  Preference logout;
  
  public void logOut() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("Выход").setMessage("Вы уверены, что хотите выйти из аккаунта?");
    builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.cancel();
          }
        });
    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            Preferences.this.account.access_token = null;
            Preferences.this.account.user_id = 0L;
            Preferences.this.account.save((Context)Preferences.this);
            Intent intent = (new Intent()).setClass(Preferences.this.getApplicationContext(), MainActivity.class);
            Preferences.this.startActivity(intent);
            Preferences.this.finish();
            Preferences.this.startActivity((new Intent()).setClass(Preferences.this.getApplicationContext(), Login.class));
          }
        });
    builder.show();
  }
  
  public void onBackPressed() {
    startActivity((new Intent()).setClass((Context)this, Post.class));
    finish();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968576);
    this.account.restore((Context)this);
    setTitle("WallPost: настройки");
    this.logout = findPreference("logOut");
    this.group = findPreference("group");
    this.logout.setSummary("id" + this.account.user_id);
    this.logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
          public boolean onPreferenceClick(Preference param1Preference) {
            Preferences.this.logOut();
            return false;
          }
        });
    this.group.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
          public boolean onPreferenceClick(Preference param1Preference) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("link"));
            Preferences.this.startActivity(intent);
            return false;
          }
        });
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    switch (paramMenuItem.getItemId()) {
      default:
        return false;
      case 16908332:
        break;
    } 
    Intent intent = new Intent();
    intent.setClass(getApplicationContext(), Post.class);
    startActivity(intent);
    finish();
  }
}