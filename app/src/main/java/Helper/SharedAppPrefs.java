package Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessToken;

import java.util.HashMap;

/**
 * Created by admin on 19.09.2016.
 */
public class SharedAppPrefs {

    private static final String PREFS = "settings";

    private static final String _VK_ACCESS_TOKEN = "_vk_token";

    private static final String _USER_FIRST_NAME = "_user_first_name";
    private static final String _USER_LAST_NAME = "_user_last_name";
    private static final String _USER_IMAGE = "_user_image";

    private  static SharedPreferences Settings;

    private static Context appContext;


    public static void InitPrefs(Context context)
    {
        appContext = context;

        if(Settings==null && context!=null)
        {
            Settings = context.getSharedPreferences(PREFS,Context.MODE_PRIVATE);
        }
    }


    public static void saveVkAccessToken()
    {
        VKAccessToken.currentToken().saveTokenToSharedPreferences(appContext, _VK_ACCESS_TOKEN);
    }

    public static void initVkToken(){
        VKAccessToken.tokenFromSharedPreferences(appContext, SharedAppPrefs._VK_ACCESS_TOKEN);
    }

    protected static void saveUserData(String firstName, String lastName, String image){
        SharedPreferences.Editor editor = Settings.edit();
        editor.putString(_USER_FIRST_NAME, firstName);
        editor.putString(_USER_LAST_NAME, lastName);
        editor.putString(_USER_IMAGE, image);
        editor.apply();
    }

    protected static void deleteUserData(){
        SharedPreferences.Editor editor = Settings.edit();
        editor.remove(_USER_FIRST_NAME);
        editor.remove(_USER_LAST_NAME);
        editor.remove(_USER_IMAGE);
        editor.apply();
    }


    protected static String getFirstName(){
        return Settings.getString(_USER_FIRST_NAME,"");
    }

    protected static String getLastName(){
        return Settings.getString(_USER_LAST_NAME,"");
    }

    protected static String getImage(){
        return Settings.getString(_USER_IMAGE,"");
    }


    protected static boolean isThereUserData(){
        return Settings.contains(_USER_LAST_NAME) && Settings.contains(_USER_FIRST_NAME) && Settings.contains(_USER_IMAGE);
    }


}
