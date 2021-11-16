package sa.pums.pums;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget_password);

        Button buttonforget = (Button) findViewById(R.id.buttonforget);

        EditText Email = (EditText) findViewById(R.id.Email);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("please wait...");
        dialog.setIndeterminate(true);


        buttonforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Email.getText().toString().trim().equals("")) {
                    dialog.show();
                    //ارسال ايميل لتغيير كلمة المرور
                    FirebaseAuth.getInstance().sendPasswordResetEmail(Email.getText().toString() + "")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(ForgetPasswordActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Plz enter Email", Toast.LENGTH_SHORT).show();
                    Email.requestFocus();
                    Email.setError("needed*");
                }
            }
        });
    }

}