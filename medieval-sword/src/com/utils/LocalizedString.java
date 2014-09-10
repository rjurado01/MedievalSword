package com.utils;

import java.util.HashMap;
import java.util.Map;

public class LocalizedString {
  Map<String, String> localized_string = new HashMap<String, String>();

  public void addString( String language, String msg ) {
    localized_string.put( language, msg );
  }

  public String getString( String language ) {
    return localized_string.get( language );
  }
}
