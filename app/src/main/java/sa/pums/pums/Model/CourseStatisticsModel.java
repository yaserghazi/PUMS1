package sa.pums.pums.Model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseStatisticsModel {
    String Uid;
    String course_id;
    String course_name;

    String number_register;
    String start_date;
    String end_date;
    String presenter;
    String success_rate;
    String failure_rate;
    String id_user;
    long createdAt;
    public CourseStatisticsModel() {
    }

    public CourseStatisticsModel(String uid, String course_id,String course_name,String number_register, String start_date, String end_date, String presenter, String success_rate, String failure_rate, String id_user) {
        Uid = uid;
        this.course_id = course_id;
        this.course_name = course_name;
        this.number_register = number_register;

        this.start_date = start_date;
        this.end_date = end_date;
        this.presenter = presenter;
        this.success_rate = success_rate;
        this.failure_rate = failure_rate;
        this.id_user = id_user;
        createdAt = new Date().getTime();

    }

    public String getCourse_name() {
        return course_name;
    }

    public String getNumber_register() {
        return number_register;
    }

    public void setNumber_register(String number_register) {
        this.number_register = number_register;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getSuccess_rate() {
        return success_rate;
    }

    public void setSuccess_rate(String success_rate) {
        this.success_rate = success_rate;
    }

    public String getFailure_rate() {
        return failure_rate;
    }

    public void setFailure_rate(String failure_rate) {
        this.failure_rate = failure_rate;
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



    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }



    @Exclude
    public String getFormattedTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(createdAt);
        return sdf.format(date);
    }
}
