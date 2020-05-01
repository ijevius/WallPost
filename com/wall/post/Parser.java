package com.wall.post;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
  public static String extractPattern(String paramString1, String paramString2) {
    Matcher matcher = Pattern.compile(paramString2).matcher(paramString1);
    return !matcher.find() ? null : matcher.toMatchResult().group(1);
  }
  
  public static String[] parseRedirectUrl(String paramString) throws Exception {
    String str1 = extractPattern(paramString, "access_token=(.*?)&");
    String str2 = extractPattern(paramString, "user_id=(\\d*)");
    if (str2 == null || str2.length() == 0 || str1 == null || str1.length() == 0)
      throw new Exception("Failed to parse redirect url " + paramString); 
    return new String[] { str1, str2 };
  }
}