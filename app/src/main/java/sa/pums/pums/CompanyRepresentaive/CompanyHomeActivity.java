package sa.pums.pums.CompanyRepresentaive;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import sa.pums.pums.ChangeDataActivity;
import sa.pums.pums.CollegeCompanyRepresentative.CollegeCompanyRepresentativeHomeActivity;
import sa.pums.pums.HelpActivity;
import sa.pums.pums.LoginActivity;
import sa.pums.pums.R;
import sa.pums.pums.Student.StudentHomeActivity;

public class CompanyHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public  static String Company_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.getString("Uid", "");
        setContentView(R.layout.activity_company_home);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(sharedPreferences.getString("Name", ""));
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(sharedPreferences.getString("email", ""));
        TextView id = (TextView) findViewById(R.id.id);
        id.setText(sharedPreferences.getString("Id_num", ""));
        Company_ID = sharedPreferences.getString("Company_ID", "");


        findViewById(R.id.post_course_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyHomeActivity.this, PostCourseActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.post_announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyHomeActivity.this, PostAnnouncementActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.announcements_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyHomeActivity.this, AnnouncementsActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.update_company_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CompanyHomeActivity.this, UpdateCompanyInformationActivity.class);
               startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        LayoutInflater inflater = getLayoutInflater();
        View headerContainer = navigationView.getHeaderView(0); // This returns the container layout from your navigation drawer header layout file (e.g., the parent RelativeLayout/LinearLayout in your my_nav_drawer_header.xml file)
        TextView nameH = (TextView) headerContainer.findViewById(R.id.name);
        nameH.setText(sharedPreferences.getString("Name", ""));
        TextView emailH = (TextView) headerContainer.findViewById(R.id.email);
        emailH.setText(sharedPreferences.getString("email", ""));
        TextView id_num = (TextView) headerContainer.findViewById(R.id.id_num);
        id_num.setText(sharedPreferences.getString("Id_num", ""));

        navigationView.setNavigationItemSelectedListener(this);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {
            case R.id.home:
                // HomeActivityNew.this.finish();

                break;


            case R.id.help:
                Intent intenth = new Intent(CompanyHomeActivity.this, HelpActivity.class);
                startActivity(intenth);

                break;
            case R.id.edit_account:

                Intent intent = new Intent(CompanyHomeActivity.this, ChangeDataActivity.class);
                startActivity(intent);

                break;
            case R.id.logout:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(CompanyHomeActivity.this);
                builder2.setMessage("Are you exactly logged out?");
                builder2.setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                editor.clear();
                                editor.apply();

                                Intent newActivity6 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(newActivity6);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertdialog2 = builder2.create();
                alertdialog2.show();
                break;


        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}