package sa.pums.pums.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseModel {
    String Uid;
    String id;
    String name;
    String place;
    String date_time;
    String Counselor;
    String language;
    String company;

    String id_user;
    String price;
    String about;
    int rate;
    long createdAt;

    public CourseModel() { }

    public CourseModel(String id_user,String uid, String id, String name, String place,
                       String date_time, String counselor, String language,String company,String price,int rate) {
        this.id_user = id_user;
        Uid = uid;
        this.id = id;
        this.name = name;
        this.place = place;
        this.date_time = date_time;
        Counselor = counselor;
        this.language = language;
        this.company=company;
        createdAt = new Date().getTime();
        this.price=price;
        this.rate=rate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getCounselor() {
        return Counselor;
    }

    public void setCounselor(String counselor) {
        Counselor = counselor;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getFormattedTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(createdAt);
        return sdf.format(date);
    }
}
