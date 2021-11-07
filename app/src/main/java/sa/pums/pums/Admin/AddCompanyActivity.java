package sa.pums.pums.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.R;

public class AddCompanyActivity extends AppCompatActivity {

    EditText name,company_id;
    ProgressDialog dialogM;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
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
                child("CompanyTB");
        name=(EditText) findViewById(R.id.name);
        company_id=(EditText) findViewById(R.id.company_id);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    dialogM.show();
                    String key = database.push().getKey();

                    CompanyModel model = new CompanyModel(key,
                            company_id.getText().toString(), name.getText().toString());

                    database.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();
                            company_id.setText("");
                            name.setText("");
                            Toast.makeText(AddCompanyActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddCompanyActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

    public boolean validation() {
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            Toast.makeText(AddCompanyActivity.this, "Enter Company name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(company_id.getText().toString().trim())) {
            Toast.makeText(AddCompanyActivity.this, "Enter Company id", Toast.LENGTH_SHORT).show();
            company_id.requestFocus();
            return false;
        }

        return true;
    }
}