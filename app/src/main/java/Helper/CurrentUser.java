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

    //async only method
    synchronized public Boolean init(JSONObject jsonObject){
        try {

            String lastName = jsonObject.getString("last_name");
            String firstName = jsonObject.getString("first_name");
            String image = jsonObject.getString("photo_max_orig");

            currentUser.setLastName(lastName);
            currentUser.setFirstName(firstName);

            LogSystem.LogThis("Сохраняем файл");


            if(GlobalShell.saveFileFromUrl(image, getPhotoFileName(), SharedAppPrefs.appContext))
                currentUser.setImage(SharedAppPrefs.appContext.getFilesDir()+"/"+ getPhotoFileName());
            else currentUser.setImage(image);

            SharedAppPrefs.saveUserData(currentUser.getFirstName(),currentUser.getLastName(),currentUser.getImage());

        } catch (JSONException e) {

            return false;

        } catch (NullPointerException e) {
            //пользователь уже вышел, т.е во время загрузки фото был logout

            return false;
        }

        return true;
    }

    public static synchronized CurrentUser getInstance(){
        if(currentUser==null) {
            currentUser = new CurrentUser();

            if(SharedAppPrefs.isThereUserData()){

                currentUser.setFirstName(SharedAppPrefs.getFirstName());
                currentUser.setLastName(SharedAppPrefs.getLastName());
                currentUser.setImage(SharedAppPrefs.getImage());
                currentUser.isLoadedLocal = true;

            } else {
                currentUser.isLoadedLocal = false;

            }

        }

        return currentUser;
    }

    public void deleteUserData() {
        SharedAppPrefs.deleteUserData();
        currentUser = null;
    }



}
