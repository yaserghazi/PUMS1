package sa.pums.pums.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnnouncementModel {
    String Uid;
    String announcement;
    String image;
    long createdAt;

    public AnnouncementModel() {
    }

    public AnnouncementModel(String uid, String announcement, String image) {
        Uid = uid;
        this.announcement = announcement;
        this.image = image;
        createdAt = new Date().getTime();

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Exclude
    public String getFormattedTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(createdAt);
        return sdf.format(date);
    }
}
