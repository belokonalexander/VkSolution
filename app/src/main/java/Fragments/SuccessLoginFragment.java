package Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Helper.GlideTransformers.GlideCircleTransformation;
import Helper.LogSystem;
import Helper.SharedAppPrefs;
import Helper.CurrentUser;
import Models.VkUser;
import belokonalexander.vksolution.MainActivity;
import belokonalexander.vksolution.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 19.09.2016.
 */
public class SuccessLoginFragment  extends Fragment{

    @OnClick (R.id.vk_logout_button)
    protected void logoutButtonClick(){
        ((MainActivity)getActivity()).logout();
    }

    @BindView(R.id.user_avatar)
    ImageView userAvatar;

    @BindView(R.id.user_name)
    TextView userName;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_vk_success_aut, container, false);
        ButterKnife.bind(this, rootView);


        //данные локально не сохранены?
        if(!CurrentUser.getInstance().isLoadedLocal()){

                    //region Запрашиваем у вк данные пользователя и сохраняем в CurrentUser и SharedPrefs
                    VKRequest getUserData = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_max_orig"));

                    VKRequest.VKRequestListener vkRequestListener = new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);

                            try {
                                CurrentUser.getInstance().init(response.json.getJSONArray("response").getJSONObject(0));
                                bindUser();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                            super.attemptFailed(request, attemptNumber, totalAttempts);
                        }

                        @Override
                        public void onError(VKError error) {
                            super.onError(error);
                        }

                        @Override
                        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                            super.onProgress(progressType, bytesLoaded, bytesTotal);
                        }
                    };

                    getUserData.executeWithListener(vkRequestListener);

                    //endregion

        } else {
            bindUser();
        }


        return rootView;
    }


    void bindUser(){
        LogSystem.LogThis("Биндим данные");
        userName.setText(CurrentUser.getInstance().getFullName());

        Glide.with(getContext()).load(CurrentUser.getInstance().getImage()).crossFade().error(R.drawable.vk_gray_transparent_shape).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(userAvatar);
    }

}
