package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import Helper.LogSystem;
import Helper.SharedAppPrefs;
import belokonalexander.vksolution.LoginActivity;
import belokonalexander.vksolution.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 19.09.2016.
 */
public class LoginFragment extends Fragment {


    @OnClick(R.id.vk_aut_button)
    protected void onClick(){
        ((LoginActivity)getActivity()).login();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_vk_aut, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;

    }





}
