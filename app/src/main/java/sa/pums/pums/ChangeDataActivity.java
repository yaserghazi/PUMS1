package sa.pums.pums;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sa.pums.pums.Model.UserModel;

import java.util.regex.Pattern;

public class ChangeDataActivity extends AppCompatActivity {
    EditText name;
    EditText email, id_num, first_name, last_name;
    String ID_s;
    UserModel UserModel;
    DatabaseReference mdatabase;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_data);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        ID_s = sharedPreferences.getString("Uid", "");

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        id_num = (EditText) findViewById(R.id.id_num);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);


        mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                getReference().child("Users").child(ID_s);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel = dataSnapshot.getValue(UserModel.class);
                name.setText(UserModel.getFirst_name() + " " + UserModel.getLast_name());
                email.setText("" + UserModel.getEmail());
                id_num.setText("" + UserModel.getId_num());
                first_name.setText("" + UserModel.getFirst_name());
                last_name.setText("" + UserModel.getLast_name());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSave();
            }
        });


    }


    private void UserSave() {

        if (TextUtils.isEmpty(id_num.getText().toString().trim())) {
            Toast.makeText(ChangeDataActivity.this, "Enter id num", Toast.LENGTH_SHORT).show();
            id_num.requestFocus();

            return;
        } else if (TextUtils.isEmpty(first_name.getText().toString().trim())) {
            Toast.makeText(ChangeDataActivity.this, "Enter  first name", Toast.LENGTH_SHORT).show();
            first_name.requestFocus();
            return;
        } else if (TextUtils.isEmpty(last_name.getText().toString().trim())) {
            Toast.makeText(ChangeDataActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
            last_name.requestFocus();
            return;
        }

        mDialog = new ProgressDialog(this);

        mDialog.setMessage("Updating UserModel please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        UserModel user = new UserModel(UserModel.getId(), first_name.getText().toString() + "",
                last_name.getText().toString() + "", id_num.getText().toString() + "",
                UserModel.getEmail() + "", UserModel.isActive(), UserModel.isDelete(), UserModel.getType(), UserModel.getCompany());

        mdatabase.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.dismiss();
                Toast.makeText(ChangeDataActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public static boolean isValidPhone(String s) {
        Pattern Phone
                = Pattern.compile(
                "^((?:[+?0?0?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$");

        return !TextUtils.isEmpty(s) && Phone.matcher(s).matches();
    }

}