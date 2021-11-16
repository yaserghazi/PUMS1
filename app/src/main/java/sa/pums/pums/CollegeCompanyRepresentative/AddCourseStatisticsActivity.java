package sa.pums.pums.CollegeCompanyRepresentative;

import static sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity.Company_ID;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.Model.CourseStatisticsModel;
import sa.pums.pums.Model.dataModel;
import sa.pums.pums.R;

public class AddCourseStatisticsActivity extends AppCompatActivity {

    EditText number_register,Start_Date, End_Date, Presenter, Success_Rate, Failure_Rate;
    ProgressDialog dialogM;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_statistics);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);
        GetCourses();

        database = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                child("CourseStatistics");
        number_register= (EditText) findViewById(R.id.number_register);
        Start_Date = (EditText) findViewById(R.id.Start_Date);
        End_Date = (EditText) findViewById(R.id.End_Date);
        getDate(Start_Date);
        getDate(End_Date);
        Presenter = (EditText) findViewById(R.id.Presenter);

        Success_Rate = (EditText) findViewById(R.id.Success_Rate);
        Failure_Rate = (EditText) findViewById(R.id.Failure_Rate);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    dialogM.show();
                    String key = database.push().getKey();

                    CourseStatisticsModel model = new CourseStatisticsModel(Company_ID,key,
                            Course_id,course_name,number_register.getText().toString(), Start_Date.getText().toString(), End_Date.getText().toString(),
                            Presenter.getText().toString(), Success_Rate.getText().toString(), Failure_Rate.getText().toString(), CollegeCompanyRepresentativeHomeActivity.Uid);

                    database.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();
                            Start_Date.setText("");
                            number_register.setText("");
                            End_Date.setText("");
                            Presenter.setText("");
                            Success_Rate.setText("");
                            Failure_Rate.setText("");
                            Toast.makeText(AddCourseStatisticsActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddCourseStatisticsActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

    public boolean validation() {
        if (Course_id.equals("0")) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Choose Course", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(number_register.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter Number Register Student", Toast.LENGTH_SHORT).show();
            number_register.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Start_Date.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter Start Date", Toast.LENGTH_SHORT).show();
            Start_Date.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(End_Date.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter End Date", Toast.LENGTH_SHORT).show();
            End_Date.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Presenter.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter Presenter", Toast.LENGTH_SHORT).show();
            Presenter.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Success_Rate.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter Success Rate", Toast.LENGTH_SHORT).show();
            Success_Rate.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Failure_Rate.getText().toString().trim())) {
            Toast.makeText(AddCourseStatisticsActivity.this, "Enter Failure Rate", Toast.LENGTH_SHORT).show();
            Failure_Rate.requestFocus();
            return false;
        }

        return true;
    }

    Spinner the_Courses;

    String Course_id = "",course_name="";
    public void getDate(final EditText editText) {

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddCourseStatisticsActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                String myFormat = "dd-MM-yyyy";// HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(sdf.format(date.getTime()));
                editText.setError(null);

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePickerDialog.show();
            }
        });
        datePickerDialog.getDatePicker();

    }

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
                    CompanyModel model = snapshot.getValue(CompanyModel.class);
                    listModels.add(new dataModel(model.getUid(), model.getName()));
                }
                final String[] name = new String[listModels.size()];
                final String[] idList = new String[listModels.size()];

                for (int i = 0; i < listModels.size(); i++) {
                    name[i] = listModels.get(i).getName();
                    idList[i] = listModels.get(i).getId_2();
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(AddCourseStatisticsActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
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