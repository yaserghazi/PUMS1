package sa.pums.pums.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.pums.pums.Adapters.CompanyAdapter;
import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.R;

public class DeleteCompanyActivity extends AppCompatActivity {
    List<CompanyModel> resultsList;
    CompanyAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);


        findViewById(R.id.arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/")
                .getReference().child("CompanyTB");


        recyclerView = findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new CompanyAdapter(this, resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CompanyModel model = snapshot.getValue(CompanyModel.class);
                    resultsList.add(model);
                    nAdapter.notifyDataSetChanged();
                }
                if (resultsList.size() == 0) {

                    Toast.makeText(DeleteCompanyActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteCompanyActivity.this, "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}