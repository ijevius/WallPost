package com.wall.post;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends Activity {
  WebView webview;
  
  private void parseUrl(String paramString) {
    if (paramString != null)
      try {
        if (paramString.startsWith("https://oauth.vk.com/blank.html#access_token")) {
          String[] arrayOfString;
          if (!paramString.contains("error=")) {
            arrayOfString = Parser.parseRedirectUrl(paramString);
            Intent intent = new Intent();
            intent.putExtra("token", arrayOfString[0]);
            intent.putExtra("uid", Long.parseLong(arrayOfString[1]));
            setResult(-1, intent);
          } else if (arrayOfString.contains("error?err")) {
            setResult(0);
            finish();
          } 
          finish();
          return;
        } 
        return;
      } catch (Exception exception) {
        exception.printStackTrace();
        return;
      }  
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130903041);
    this.webview = (WebView)findViewById(2131296258);
    this.webview.getSettings().setJavaScriptEnabled(true);
    this.webview.clearCache(true);
    this.webview.setWebViewClient(new VkWebViewClient());
    CookieSyncManager.createInstance((Context)this);
    CookieManager.getInstance().removeAllCookie();
    this.webview.loadUrl("https://oauth.vk.com/authorize?client_id=3682744&scope=wall,offline&redirect_uri=https://oauth.vk.com/blank.html&display=touch&revoke=1&v=5.21&response_type=token");
  }
  
  class VkWebViewClient extends WebViewClient {
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
      LoginActivity.this.parseUrl(param1String);
    }
  }
}