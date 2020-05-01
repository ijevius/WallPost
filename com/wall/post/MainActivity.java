package com.wall.post;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
  Account account = new Account();
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.account.restore((Context)this);
    if (this.account.access_token != null) {
      Intent intent1 = new Intent();
      intent1.setClass(getApplicationContext(), Post.class);
      startActivity(intent1);
      finish();
      return;
    } 
    Intent intent = new Intent();
    intent.setClass(getApplicationContext(), Login.class);
    startActivity(intent);
    finish();
  }
}