package sa.pums.pums;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sa.pums.pums.Admin.AdminHomeActivity;
import sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity;
import sa.pums.pums.CompanyRepresentaive.CompanyHomeActivity;
import sa.pums.pums.Model.UserModel;
import sa.pums.pums.Student.StudentHomeActivity;


public class LoginActivity extends AppCompatActivity {
    EditText Email, Password;
    ProgressDialog dialog;
    boolean isRemember = false;
    boolean isPressed = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        FirebaseApp.initializeApp(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in please wait ...");
        dialog.setIndeterminate(true);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSign();
            }
        });
        CheckBox remember = (CheckBox) findViewById(R.id.remember);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isRemember", false)) {
            Email.setText(sharedPreferences.getString("email", "") + "");
            Password.setText(sharedPreferences.getString("Password", "") + "");
            remember.setChecked(true);
            isRemember = true;
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isRemember = true;

                }
            }
        });


    }

    private void userSign() {
        if (TextUtils.isEmpty(Email.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "Enter the correct email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString().trim(), Password.getText().toString().trim()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();//لجلب بيانات المستخدم الذي سجل دخوله
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                                    .getReference().child("Users");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists() && isPressed)
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            UserModel user = snapshot.getValue(UserModel.class);
                                            if (user.getEmail().equalsIgnoreCase(Email.getText().toString())) {
                                                if (user.isActive()) {
                                                    dialog.dismiss();
                                                    //لتخزين البيانات لحين الحاجة اليها يتم استدعاءها بسرعة
                                                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                                    editor.putString("Name", user.getFirst_name() + " " + user.getLast_name());
                                                    editor.putString("Uid", user.getId());
                                                    editor.putString("email", Email.getText().toString());
                                                    editor.putString("Password", Password.getText().toString());
                                                    editor.putString("Id_num", user.getId_num());
                                                    editor.putString("Company_ID", user.getCompany());
                                                    editor.putBoolean("isRemember", isRemember);
                                                    editor.putInt("UserType", user.getType());
                                                    editor.apply();

                                                    if (user.getType() == 1) {
                                                        Intent intent = new Intent(LoginActivity.this, CollegeCompanyRepresentativeHomeActivity.class);
                                                        startActivity(intent);
                                                    } else if (user.getType() == 2) {
                                                        Intent intent = new Intent(LoginActivity.this, CompanyHomeActivity.class);
                                                        startActivity(intent);
                                                    } else if (user.getType() == 3) {
                                                        Intent intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
                                                        startActivity(intent);
                                                    } else if (user.getType() == 10) {
                                                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                    LoginActivity.this.finish();
                                                    isPressed = false;
                                                } else {
                                                    isPressed = false;
                                                    dialog.dismiss();
                                                        Toast.makeText(LoginActivity.this, "It needs to be activated by the admin", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "An error occurred during the login process", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    "The login did not work Make sure you connect to the Internet or email and password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}