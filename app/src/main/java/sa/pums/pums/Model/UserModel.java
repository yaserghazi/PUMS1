package sa.pums.pums.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserModel {
    String id;
    String first_name;
    String last_name;
    String id_num;
    String email;
    long createdAt;
    int Type;
    String company;

    boolean Active;
    boolean delete;
    public UserModel(String id, String first_name, String last_name,String id_num, String email, boolean active,
                     boolean delete, int Type,String company) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.id_num=id_num;
        this.email = email;
        this.Type=Type;
        this.company=company;
        Active = active;
        this.delete=delete;
        createdAt = new Date().getTime();

    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    @Exclude
    public String getFormattedTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(createdAt);
        return sdf.format(date);
    }
}
