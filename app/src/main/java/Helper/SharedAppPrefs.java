package Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessToken;

/**
 * Created by admin on 19.09.2016.
 */
public class SharedAppPrefs {

    private static final String PREFS = "settings";

    private static final String _VK_ACCESS_TOKEN = "_vk_token";

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


    public static void saveVkAccessToken(String token)
    {
        VKAccessToken.currentToken().saveTokenToSharedPreferences(appContext, _VK_ACCESS_TOKEN);
    }

    public static void initVkToken(){
        VKAccessToken.tokenFromSharedPreferences(appContext, SharedAppPrefs._VK_ACCESS_TOKEN);
    }

}
