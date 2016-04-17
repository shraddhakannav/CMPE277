package com.example.shraddha.cmpe277.Utils;

import android.app.Activity;
import android.graphics.Typeface;

public class CustomFonts {

  public static Typeface setCaviarBold(Activity activity) {
    Typeface font = Typeface.createFromAsset(activity.getAssets(), "Caviar_Dreams_Bold.ttf");
    return font;
  }

  public static Typeface setCaviarSimple(Activity activity) {
    Typeface font = Typeface.createFromAsset(activity.getAssets(), "CaviarDreams.ttf");
    return font;
  }

  public static Typeface setCaviarBoldItalic(Activity activity) {
    Typeface font = Typeface.createFromAsset(activity.getAssets(), "CaviarDreams_BoldItalic.ttf");
    return font;
  }

  public static Typeface setCaviarItalic(Activity activity) {
    Typeface font = Typeface.createFromAsset(activity.getAssets(), "CaviarDreams_Italic.ttf");
    return font;
  }

}
