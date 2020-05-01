package com.wall.post;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Test {
  private Context _context;
  
  public Test(Context paramContext) {
    this._context = paramContext;
  }
  
  public boolean isConnectingToInternet() {
    ConnectivityManager connectivityManager = (ConnectivityManager)this._context.getSystemService("connectivity");
    if (connectivityManager != null) {
      NetworkInfo[] arrayOfNetworkInfo = connectivityManager.getAllNetworkInfo();
      if (arrayOfNetworkInfo != null) {
        int i = 0;
        while (true) {
          if (i < arrayOfNetworkInfo.length) {
            if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
              return true; 
            i++;
            continue;
          } 
          return false;
        } 
      } 
    } 
    return false;
  }
}