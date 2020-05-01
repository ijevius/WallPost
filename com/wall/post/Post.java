package com.wall.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class Post extends Activity {
  private static final String FILENAME = "post.txt";
  
  private static final String URL = "https://api.vk.com/method/";
  
  private static final String wallpost = "wall.post";
  
  private final int IDM_SETTINGS = 100;
  
  Account account = new Account();
  
  String attachs;
  
  Test cd;
  
  ProgressDialog dialog;
  
  int error_code;
  
  Boolean isInternetPresent = Boolean.valueOf(false);
  
  EditText messageEditText;
  
  CheckBox only_friends;
  
  Button postButton;
  
  private View.OnClickListener postClick = new View.OnClickListener() {
      public void onClick(View param1View) {
        try {
          Post.this.postToWall();
          return;
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
          unsupportedEncodingException.printStackTrace();
          return;
        } 
      }
    };
  
  private void openFile(String paramString) {
    try {
      FileInputStream fileInputStream = openFileInput("post.txt");
      if (fileInputStream != null) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
          String str = bufferedReader.readLine();
          if (str == null) {
            fileInputStream.close();
            this.messageEditText.setText(stringBuffer.toString());
            if (!stringBuffer.toString().equals("")) {
              this.messageEditText.setText(stringBuffer.toString());
              return;
            } 
            break;
          } 
          stringBuffer.append(String.valueOf(str) + "\n");
        } 
      } 
    } catch (Throwable throwable) {
      Toast.makeText(getApplicationContext(), "Exception: " + throwable.toString(), 1).show();
    } 
  }
  
  private void params() {
    this.postButton = (Button)findViewById(2131296260);
    this.messageEditText = (EditText)findViewById(2131296261);
  }
  
  private void saveFile(String paramString) {
    try {
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("post.txt", 0));
      outputStreamWriter.write(this.messageEditText.getText().toString());
      outputStreamWriter.close();
      return;
    } catch (Throwable throwable) {
      Toast.makeText(getApplicationContext(), "Exception: " + throwable.toString(), 1).show();
      return;
    } 
  }
  
  public void onBackPressed() {
    if (this.messageEditText.getText().toString().length() == 0) {
      AlertDialog.Builder builder1 = new AlertDialog.Builder((Context)this);
      builder1.setMessage("Вы уверены, что хотите выйти?");
      builder1.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              Post.this.saveFile("post.txt");
              Post.this.finish();
            }
          });
      builder1.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              param1DialogInterface.cancel();
            }
          });
      builder1.setCancelable(true).show();
      return;
    } 
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setMessage("Если вы выйдете, введеный Вами текст будет сохранен. Вы сможете вернуться и дописать в любой момент. Выйти?");
    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            if (Post.this.messageEditText.getText().toString().length() == 0) {
              Post.this.finish();
              return;
            } 
            Post.this.saveFile("post.txt");
            Post.this.finish();
          }
        });
    builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.cancel();
          }
        });
    builder.setNeutralButton("Выйти и не сохранять", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            Post.this.finish();
            try {
              OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Post.this.openFileOutput("post.txt", 0));
              outputStreamWriter.write("");
              outputStreamWriter.close();
              return;
            } catch (Throwable throwable) {
              Toast.makeText(Post.this.getApplicationContext(), "Exception: " + throwable.toString(), 1).show();
              return;
            } 
          }
        });
    builder.setCancelable(true).show();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    this.account.restore((Context)this);
    this.cd = new Test(getApplicationContext());
    params();
    setupUI();
    this.postButton.setEnabled(false);
    if (this.account.access_token != null) {
      if (this.account.access_token == null) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), Login.class);
        startActivity(intent);
        return;
      } 
    } else {
      return;
    } 
    ((EditText)findViewById(2131296261)).addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) {
            if (param1Editable.toString().length() > 0) {
              Post.this.postButton.setEnabled(true);
              return;
            } 
            Post.this.postButton.setEnabled(false);
          }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            Post.this.postButton.setEnabled(false);
          }
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            Post.this.postButton.setEnabled(false);
            Post.this.saveFile("post.txt");
          }
        });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    paramMenu.add(0, 100, 0, "Настройки");
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    switch (paramMenuItem.getItemId()) {
      default:
        return false;
      case 100:
        break;
    } 
    Intent intent = new Intent();
    intent.setClass((Context)this, Preferences.class);
    startActivity(intent);
    finish();
  }
  
  public void onResume() {
    super.onResume();
    openFile("post.txt");
  }
  
  public void postToWall() throws UnsupportedEncodingException {
    this.isInternetPresent = Boolean.valueOf(this.cd.isConnectingToInternet());
    if (this.isInternetPresent.booleanValue()) {
      boolean bool;
      this.only_friends = (CheckBox)findViewById(2131296262);
      if (this.only_friends.isChecked()) {
        bool = true;
      } else {
        bool = false;
      } 
      String str = this.messageEditText.getText().toString();
      (new poster()).execute((Object[])new String[] { "https://api.vk.com/method/wall.post?owner_id=" + this.account.user_id + "&message=" + URLEncoder.encode(str, "UTF-8") + "&friends_only=" + bool + "&v=" + 5.21D + "&access_token=" + URLEncoder.encode(this.account.access_token, "UTF-8") });
      return;
    } 
    Toast.makeText(getApplicationContext(), "Отсутствует интернет-соединение", 1).show();
  }
  
  void setupUI() {
    this.postButton = (Button)findViewById(2131296260);
    this.postButton.setOnClickListener(this.postClick);
  }
  
  class FloatKeyListener extends NumberKeyListener {
    private static final String CHARS = "0123456789-.";
    
    protected char[] getAcceptedChars() {
      return "0123456789-.".toCharArray();
    }
    
    public int getInputType() {
      return 0;
    }
  }
  
  class poster extends AsyncTask<String, String, String> {
    int code;
    
    int post_id;
    
    protected String doInBackground(String... param1VarArgs) {
      try {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
        String str = (String)defaultHttpClient.execute((HttpUriRequest)new HttpPost(param1VarArgs[0]), (ResponseHandler)basicResponseHandler);
        try {
          this.post_id = (new JSONObject(str)).getJSONObject("response").getInt("post_id");
        } catch (JSONException jSONException) {}
      } catch (Exception exception) {}
      return null;
    }
    
    protected void onPostExecute(String param1String) {
      super.onPostExecute(param1String);
      if (this.post_id != 0) {
        Toast.makeText(Post.this.getApplicationContext(), "Запись добавлена", 1).show();
        Post.this.messageEditText.setText(null);
        Post.this.dialog.dismiss();
        return;
      } 
      Post.this.dialog.dismiss();
      if (this.code == 5) {
        Toast.makeText(Post.this.getApplicationContext(), "Пожалуйста, авторизуйтесь", 1).show();
        Post.this.account.access_token = null;
        Post.this.account.user_id = 0L;
        Post.this.account.save((Context)Post.this);
        Post.this.finish();
        return;
      } 
      Toast.makeText(Post.this.getApplicationContext(), "Ошибка! " + Long.toString(this.code), 1).show();
    }
    
    protected void onPreExecute() {
      super.onPreExecute();
      Post.this.dialog = new ProgressDialog((Context)Post.this);
      Post.this.dialog.setMessage("Запись отправляется...");
      Post.this.dialog.setIndeterminate(true);
      Post.this.dialog.setCancelable(false);
      Post.this.dialog.show();
    }
  }
}