package sa.pums.pums.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompanyModel {
    String Uid;
    String id;
    String name;
    long createdAt;

    String description;
    public CompanyModel() {
    }

    public CompanyModel(String uid, String id, String name) {
        Uid = uid;
        this.id = id;
        this.name = name;
        createdAt = new Date().getTime();

    }

    public CompanyModel(String uid, String id, String name, String description) {
        Uid = uid;
        this.id = id;
        this.name = name;
        this.description = description;
        createdAt = new Date().getTime();

    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
