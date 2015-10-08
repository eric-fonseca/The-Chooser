package com.example.shawn.decide;
import java.lang.reflect.Field;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by Shawn on 10/6/2015.
 */
public class FontsOverride {

    /*public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
    final Typeface regular = Typeface.createFromAsset(context.getAssets(),fontAssetName);
    replaceFont(staticTypefaceFieldName, regular);
}*/

    public static void replaceFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.e("Fonts", "Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }
}