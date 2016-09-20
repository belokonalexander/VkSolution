package belokonalexander.vksolution;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import Fragments.LoginFragment;
import Fragments.SuccessLoginFragment;
import Helper.CurrentUser;
import Helper.LogSystem;
import Helper.SharedAppPrefs;
import Models.VkUser;

public class MainActivity extends AppCompatActivity implements LoginActivity{


    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment==null){

            fragment = (VKAccessToken.currentToken()!=null) ? new SuccessLoginFragment() : new LoginFragment();
            fm.beginTransaction()
                        .add(R.id.fragmentContainer,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                SharedAppPrefs.saveVkAccessToken();

                fm.beginTransaction()
                        .replace(R.id.fragmentContainer,new SuccessLoginFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

            }
            @Override
            public void onError(VKError error) {
                //авторизация не удалась
            }
        }));
    }


    @Override
    public void logout(){
        VKSdk.logout();
        CurrentUser.getInstance().deleteUserData();
        fm.beginTransaction()
                .replace(R.id.fragmentContainer,new LoginFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }

    @Override
    public void login() {
        VKSdk.login(this,"");
    }


}
