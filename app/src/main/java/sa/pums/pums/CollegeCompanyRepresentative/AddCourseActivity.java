package sa.pums.pums.CollegeCompanyRepresentative;

import static sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity.Company_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.R;

public class AddCourseActivity extends AppCompatActivity {

    EditText name,course_id,place,date_time,Counselor,language;
    ProgressDialog dialogM;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
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
        name=(EditText) findViewById(R.id.name);
        course_id=(EditText) findViewById(R.id.course_id);
        place=(EditText) findViewById(R.id.place);

        date_time=(EditText) findViewById(R.id.date_time);
        Counselor=(EditText) findViewById(R.id.Counselor);
        language=(EditText) findViewById(R.id.language);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    dialogM.show();
                    String key = database.push().getKey();

                    CourseModel model = new CourseModel(CollegeCompanyRepresentativeHomeActivity.Uid,key,
                            course_id.getText().toString(), name.getText().toString(),place.getText().toString(),
                            date_time.getText().toString(),Counselor.getText().toString(),language.getText().toString(),Company_ID,"",0);

                    database.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();
                            course_id.setText("");
                            name.setText("");
                            place.setText("");
                            date_time.setText("");
                            Counselor.setText("");
                            language.setText("");
                            Toast.makeText(AddCourseActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddCourseActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

    public boolean validation() {
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(course_id.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter id", Toast.LENGTH_SHORT).show();
            course_id.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(place.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter place", Toast.LENGTH_SHORT).show();
            place.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(date_time.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter date-time", Toast.LENGTH_SHORT).show();
            date_time.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Counselor.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter Counselor", Toast.LENGTH_SHORT).show();
            Counselor.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(language.getText().toString().trim())) {
            Toast.makeText(AddCourseActivity.this, "Enter language", Toast.LENGTH_SHORT).show();
            language.requestFocus();
            return false;
        }

        return true;
    }
}