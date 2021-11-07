package sa.pums.pums.CompanyRepresentaive;

import static sa.pums.pums.CompanyRepresentaive.CompanyHomeActivity.Company_ID;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import sa.pums.pums.CollegeCompanyRepresentative.UpdateCourseStatisticsActivity;
import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.Model.dataModel;
import sa.pums.pums.R;

public class UpdateCompanyInformationActivity extends AppCompatActivity {

    EditText name, company_id, description;
    ProgressDialog dialogM;
    CompanyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company_information);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);
        name = (EditText) findViewById(R.id.name);
        company_id = (EditText) findViewById(R.id.company_id);
        description = (EditText) findViewById(R.id.description);

        model = new CompanyModel();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("CompanyTB").child(Company_ID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model = dataSnapshot.getValue(CompanyModel.class);
                name.setText(model.getName() + "");
                company_id.setText(model.getId() + "");
                description.setText(model.getDescription()==null ? "" : model.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogM.show();

                CompanyModel companyModel = new CompanyModel(model.getUid(),
                        company_id.getText().toString(), name.getText().toString(), description.getText().toString());

                databaseReference.setValue(companyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialogM.dismiss();
                        Toast.makeText(UpdateCompanyInformationActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogM.dismiss();
                        Toast.makeText(UpdateCompanyInformationActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }


        });

    }

}