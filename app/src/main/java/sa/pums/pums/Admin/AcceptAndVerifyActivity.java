package sa.pums.pums.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.pums.pums.Adapters.AdminAcceptVerfiyAdapter;
import sa.pums.pums.Model.UserModel;
import sa.pums.pums.R;

public class AcceptAndVerifyActivity extends AppCompatActivity {
    List<UserModel> resultsList;
    AdminAcceptVerfiyAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_user);


        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("Users");


        recyclerView = findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new AdminAcceptVerfiyAdapter(this, resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);

                    if (user.getType() == 1 || user.getType() == 2) {//للتأكد من ان المستخدمCompany representative او College/company representative
                        resultsList.add(user);
                    }

                    nAdapter.notifyDataSetChanged();
                }
                if (resultsList.size() == 0) {

                    Toast.makeText(AcceptAndVerifyActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AcceptAndVerifyActivity.this, "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}