package sa.pums.pums.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sa.pums.pums.CompanyRepresentaive.AnnouncementsActivity;
import sa.pums.pums.LoginActivity;
import sa.pums.pums.MainActivity;
import sa.pums.pums.R;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.getString("Uid", "");
        setContentView(R.layout.activity_admin_home);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(sharedPreferences.getString("Name", ""));
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(sharedPreferences.getString("email", ""));
        TextView id = (TextView) findViewById(R.id.id);
        id.setText(sharedPreferences.getString("Id_num", ""));

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.add_company_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AddCompanyActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.delete_company_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, DeleteCompanyActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.company_representative_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AcceptAndVerifyActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.announcements_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AnnouncementsActivity.class);
                startActivity(intent);
            }
        });



    }
}