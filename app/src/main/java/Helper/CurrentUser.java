package Helper;

import org.json.JSONException;
import org.json.JSONObject;

import Models.VkUser;

/**
 * Created by admin on 19.09.2016.
 */
public class CurrentUser extends VkUser {

    private static CurrentUser currentUser;

    private boolean isLoadedLocal;

    public boolean isLoadedLocal() {
        return isLoadedLocal;
    }

    public CurrentUser() {

    }

    //async method
    public void init(JSONObject jsonObject){
        try {

            //TODO Glide будет кешировать данные, однако можно сохранить картинку на диск


            currentUser.setLastName(jsonObject.getString("last_name"));
            currentUser.setFirstName(jsonObject.getString("first_name"));
            currentUser.setImage(jsonObject.getString("photo_max_orig"));


            SharedAppPrefs.saveUserData(currentUser.getFirstName(),currentUser.getLastName(),currentUser.getImage());

        }catch (JSONException e) {
            LogSystem.LogThis("Error: " + e);
        }

    }

    public static synchronized CurrentUser getInstance(){
        if(currentUser==null) {
            currentUser = new CurrentUser();

            if(SharedAppPrefs.isThereUserData()){

                currentUser.setFirstName(SharedAppPrefs.getFirstName());
                currentUser.setLastName(SharedAppPrefs.getLastName());
                currentUser.setImage(SharedAppPrefs.getImage());
                currentUser.isLoadedLocal = true;

            } else currentUser.isLoadedLocal = false;

        }

        return currentUser;
    }

    public void deleteUserData() {
        SharedAppPrefs.deleteUserData();
        currentUser = null;
    }
}
