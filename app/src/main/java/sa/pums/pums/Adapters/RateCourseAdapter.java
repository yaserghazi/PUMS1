package sa.pums.pums.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import sa.pums.pums.CollegeCompanyRepresentative.AddCourseToStudentActivity;
import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.R;

public class RateCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CourseModel> list;
    Context context;

    public RateCourseAdapter(Context context, List<CourseModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_rate, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CourseModel model = list.get(position);
        holder1.name.setText(model.getName() + "");
        holder1.date.setText(model.getDate_time() + "");

        FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                child("CompanyTB").child(model.getCompany()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder1.company.setText("Company Name " + snapshot.getValue()==null?"":snapshot.getValue()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder1.mdatabase.child(model.getUid()).child(holder1.sharedPreferences.getString("Uid", ""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            holder1.ratingBar.setRating((Long) snapshot.getValue());
                        } catch (Exception s) {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder1.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder1.dialog1.show();
                holder1.mdatabase.child(model.getUid()).child(holder1.sharedPreferences.getString("Uid", ""))
                        .setValue(ratingBar.getRating())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                holder1.dialog1.dismiss();
                                Toast.makeText(context, "Ratted", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView name, company, date;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;
        RatingBar ratingBar;
        SharedPreferences sharedPreferences;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            company = (TextView) itemView.findViewById(R.id.company);
            date = (TextView) itemView.findViewById(R.id.date);

            ratingBar = itemView.findViewById(R.id.rb);
            sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);

            mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                    getReference().child("CourseRate");

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}