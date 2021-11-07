package sa.pums.pums.Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.Model.CourseStatisticsModel;
import sa.pums.pums.R;

public class CourseDetialsActivity extends AppCompatActivity {

    String Course_id;
    TextView title, the_company, start_date, end_date, Presenter, Success_Rate, Failure_Rate;
    ProgressDialog dialogM;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detials);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);
        Intent intent = getIntent();
        Course_id = intent.getStringExtra("ID");

        database = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                child("Course").child(Course_id);

        title = (TextView) findViewById(R.id.title);
        the_company = (TextView) findViewById(R.id.the_company);
        start_date = (TextView) findViewById(R.id.start_date);
        end_date = (TextView) findViewById(R.id.end_date);
        Presenter = (TextView) findViewById(R.id.Presenter);

        Success_Rate = (TextView) findViewById(R.id.Success_Rate);
        Failure_Rate = (TextView) findViewById(R.id.Failure_Rate);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CourseModel modelC = dataSnapshot.getValue(CourseModel.class);
                title.setText(modelC.getName()+"");

                FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                        child("CompanyTB").child(modelC.getCompany()).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        the_company.setText("Course Name"+snapshot.getValue()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                        child("CourseStatistics").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshotz : snapshot.getChildren()) {
                            CourseStatisticsModel model = snapshotz.getValue(CourseStatisticsModel.class);
                         if(model.getCourse_id().equals(modelC.getUid())){
                             start_date.setText("Start date"+model.getStart_date()+"");
                             end_date.setText("End date"+model.getEnd_date()+"");
                             Presenter.setText("Presenter"+model.getPresenter()+"");
                             Success_Rate.setText("Success Rate"+model.getSuccess_rate()+"");
                             Failure_Rate.setText("Failure Rate"+model.getFailure_rate()+"");
                         }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}