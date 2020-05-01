package com.wall.post;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {
  private final int REQUEST_LOGIN = 1;
  
  Account account = new Account();
  
  Button authorizeButton;
  
  private View.OnClickListener click = new View.OnClickListener() {
      public void onClick(View param1View) {
        Login.this.r();
      }
    };
  
  private void setupUI() {
    this.authorizeButton = (Button)findViewById(2131296256);
    this.authorizeButton.setOnClickListener(this.click);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 1 && paramInt2 == -1) {
      this.account.access_token = paramIntent.getStringExtra("token");
      this.account.user_id = paramIntent.getLongExtra("uid", 0L);
      this.account.save((Context)this);
      startActivity((new Intent()).setClass(getApplicationContext(), Post.class));
      finish();
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    setupUI();
    this.account.restore((Context)this);
  }
  
  void r() {
    Intent intent = new Intent();
    intent.setClass((Context)this, LoginActivity.class);
    startActivityForResult(intent, 1);
  }
}