package belokonalexander.vksolution;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import Helper.LogSystem;
import Helper.SharedAppPrefs;

/**
 * Created by admin on 19.09.2016.
 */
public class AppStarter  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();


        VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {

                    LogSystem.LogThis("Токен не валидный");
                    /*Intent intent = new Intent();
                    intent.setClass(getBaseContext(),Intro.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);*/
                }
            }
        };

        vkAccessTokenTracker.startTracking();

        SharedAppPrefs.InitPrefs(getBaseContext());

        VKSdk.initialize(getApplicationContext());

        SharedAppPrefs.initVkToken();

    }
}
