package sa.pums.pums.CollegeCompanyRepresentative;

import static sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity.Company_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.pums.pums.Adapters.AddCourseAdapter;
import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.R;

public class AddCourseToStudentActivity extends AppCompatActivity {
    List<CourseModel> resultsList;
    AddCourseAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;
    TextView student_name;
    public static String ID, nameStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_student);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        nameStudent = intent.getStringExtra("name");
        student_name = findViewById(R.id.student_name);
        student_name.setText(nameStudent + "");
        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("Course");


        recyclerView = findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new AddCourseAdapter(this, resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CourseModel model = snapshot.getValue(CourseModel.class);
                    if (Company_ID.equals(model.getCompany()))
                        resultsList.add(model);
                    nAdapter.notifyDataSetChanged();
                }
                if (resultsList.size() == 0) {

                    Toast.makeText(AddCourseToStudentActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddCourseToStudentActivity.this, "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}