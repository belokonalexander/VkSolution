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


import Helper.GlideTransformers.GlideCircleTransformation;
import Helper.CurrentUser;
import belokonalexander.vksolution.LoginActivity;
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
        ((LoginActivity)getActivity()).logout();
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

                                final VKResponse finalResponse = response;

                                new AsyncTask<Void, Void, Boolean>() {

                                    @Override
                                    protected Boolean doInBackground(Void... params) {
                                        try {
                                            return CurrentUser.getInstance().init(finalResponse.json.getJSONArray("response").getJSONObject(0));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Boolean aVoid) {
                                        super.onPostExecute(aVoid);
                                        if(aVoid)
                                            bindUser();
                                    }
                                }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);


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

        // Хранение картинки можно было организовать проще - с помощью Glide кеширования
        // картинка хранилась бы просто в виде url'a
        // для демонстрации метода загрузки и сохранения файла по url во внутреннее хранилище, отключил кеширование

        try {
            userName.setText(CurrentUser.getInstance().getFullName());
            Glide.with(getContext()).load(CurrentUser.getInstance().getImage())
                    .crossFade()
                    .error(R.drawable.vk_gray_transparent_shape)
                    .transform(new GlideCircleTransformation(getContext()))
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userAvatar);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
