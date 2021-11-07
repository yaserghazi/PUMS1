package sa.pums.pums.CompanyRepresentaive;

import static sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity.Company_ID;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.pums.pums.CollegeCompanyRepresentative.AddCourseStatisticsActivity;
import sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity;
import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.Model.dataModel;
import sa.pums.pums.R;

public class PostCourseActivity extends AppCompatActivity {

    EditText about;
    ProgressDialog dialogM;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_course);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);


        database = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                child("Course");
        about = (EditText) findViewById(R.id.about);
        GetCourses();

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!about.getText().toString().trim().equals("") && !Course_id.equals("0")) {
                    dialogM.show();


                    database.child(Course_id).child("about").setValue(about.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();

                            Toast.makeText(PostCourseActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(PostCourseActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(PostCourseActivity.this, "Please see who entered the data correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    Spinner the_Courses;

    String Course_id = "", course_name = "";

    public void GetCourses() {
        the_Courses = findViewById(R.id.the_course);
        List<dataModel> listModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("Course");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listModels.clear();
                listModels.add(new dataModel("0", "Choose Course"));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CourseModel model = snapshot.getValue(CourseModel.class);
                    if (model.getCompany().equals(CompanyHomeActivity.Company_ID))
                        listModels.add(new dataModel(model.getUid(), model.getName()));
                }
                final String[] name = new String[listModels.size()];
                final String[] idList = new String[listModels.size()];

                for (int i = 0; i < listModels.size(); i++) {
                    name[i] = listModels.get(i).getName();
                    idList[i] = listModels.get(i).getId_2();
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(PostCourseActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
                the_Courses.setAdapter(adapter);
                the_Courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        try {
                            ((TextView) view).setTextColor(Color.BLACK);
                        } catch (Exception ex) {
                        }
                        if (item != null) {
                            Course_id = idList[position];
                            course_name = name[position];
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}