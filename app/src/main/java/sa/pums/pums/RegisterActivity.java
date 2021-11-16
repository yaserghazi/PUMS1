package sa.pums.pums;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import sa.pums.pums.Adapters.CompanyAdapter;
import sa.pums.pums.Admin.DeleteCompanyActivity;
import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.Model.UserModel;
import sa.pums.pums.Model.dataModel;


public class RegisterActivity extends AppCompatActivity {
    EditText fname, lname, id_num, email, password, confirm_password;
    Button mRegisterbtn;

    DatabaseReference mdatabase;

    String Fname, Lname, ID_num, Email, Password, CPassword, company_id;
    int Type = 0;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        id_num = (EditText) findViewById(R.id.id_num);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        mRegisterbtn = (Button) findViewById(R.id.buttonRegister);
        GetCompany();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        GetAcademicAdvisor();


        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation())
                    UserRegister();
            }
        });

    }


    public boolean validation() {
        Fname = fname.getText().toString().trim();
        Lname = lname.getText().toString().trim();
        ID_num = id_num.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        CPassword = confirm_password.getText().toString().trim();

        if (Type == 0) {
            Toast.makeText(RegisterActivity.this, "Selected Type Account", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((Type == 1 || Type == 2) && company_id.equals("0")) {
            Toast.makeText(RegisterActivity.this, "Selected Company", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(Fname)) {
            Toast.makeText(RegisterActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
            fname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Lname)) {
            Toast.makeText(RegisterActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
            lname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(ID_num)) {
            Toast.makeText(RegisterActivity.this, "Enter ID", Toast.LENGTH_SHORT).show();
            id_num.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return false;
        } else if (!isValidPassword(Password)) { //password valid
            Toast.makeText(RegisterActivity.this, "Password must be Complex 6 >> Char Upper, lower and number (Aa123)", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(CPassword)) {
            Toast.makeText(RegisterActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            confirm_password.requestFocus();
            return false;
        } else if (!CPassword.equals(Password)) {
            Toast.makeText(RegisterActivity.this, "Password and confirm not equls", Toast.LENGTH_SHORT).show();
            confirm_password.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Email)) {
            Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }
        return true;
    }


    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{6,24}");
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    private void UserRegister() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                getReference().child("Users");

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDialog.dismiss();

                    UserModel user = new UserModel(task.getResult().getUser().getUid(), fname.getText().toString() + "",
                            lname.getText().toString() + "", id_num.getText().toString() + "",
                            Email + "", Type==3?true:false, false, Type, company_id);
                    mdatabase.child(task.getResult().getUser().getUid()).setValue(user);
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(RegisterActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mDialog.dismiss();
                    email.requestFocus();
                    Toast.makeText(RegisterActivity.this, "error on creating user ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void GetAcademicAdvisor() {
        Spinner the_AA = findViewById(R.id.the_AA);
        List<dataModel> listModels = new ArrayList<>();
        listModels.add(new dataModel(0, "Choose Type User"));
        listModels.add(new dataModel(1, getString(R.string.college_company_representative)));
        listModels.add(new dataModel(2, getString(R.string.company_representative)));
        listModels.add(new dataModel(3, getString(R.string.student_account)));


        final String[] name = new String[listModels.size()];
        final int[] idList = new int[listModels.size()];

        for (int i = 0; i < listModels.size(); i++) {
            name[i] = listModels.get(i).getName();
            idList[i] = listModels.get(i).getId();
        }
        ArrayAdapter adapterPiece = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
        the_AA.setAdapter(adapterPiece);
        the_AA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                try {
                    ((TextView) view).setTextColor(Color.BLACK);
                } catch (Exception ex) {
                }
                if (item != null) {
                    Type = idList[position];
                    if (Type == 1 || Type == 2) {
                        the_company.setVisibility(View.VISIBLE);
                        the_company.setVisibility(View.VISIBLE);

                    } else {
                        the_company.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    Spinner the_company;

    public void GetCompany() {
        the_company = findViewById(R.id.the_company);
        List<dataModel> listModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("CompanyTB");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listModels.clear();
                listModels.add(new dataModel("0", "Choose Company"));

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
                ArrayAdapter adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, name);
                the_company.setAdapter(adapter);
                the_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        try {
                            ((TextView) view).setTextColor(Color.BLACK);
                        } catch (Exception ex) {
                        }
                        if (item != null) {
                            company_id = idList[position];
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
