package Models;

/**
 * Created by admin on 19.09.2016.
 */
public class VkUser {


    private String firstName;
    private String lastName;
    private String image;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


    @Override
    public String toString() {
        return firstName + " " + lastName + " " + image;

    }

    public String getPhotoFileName(){
        return lastName+"_"+firstName+"_photo";
    }

}
