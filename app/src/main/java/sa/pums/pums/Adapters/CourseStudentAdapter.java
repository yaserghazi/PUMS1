package sa.pums.pums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import sa.pums.pums.CollegeCompanyRepresentative.UpdateCourseStatisticsActivity;
import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.R;
import sa.pums.pums.Student.CourseDetialsActivity;

public class CourseStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CourseModel> list;
    Context context;

    public CourseStudentAdapter(Context context, List<CourseModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_course_student, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CourseModel model = list.get(position);
        holder1.name.setText(model.getName() + "");
        holder1.date.setText(model.getDate_time() + "");
        holder1.mdatabase.child(model.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long total_rate= Long.valueOf(0);
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            total_rate+=(Long) dataSnapshot.getValue();
                        }
                        try {
                            holder1.rb.setRating(total_rate);
                        } catch (Exception s) {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseDetialsActivity.class);
                intent.putExtra("ID", model.getUid());
                context.startActivity(intent);

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
        TextView name, date;
        RatingBar rb;
        DatabaseReference mdatabase;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            date= (TextView) itemView.findViewById(R.id.date);
            rb=(RatingBar) itemView.findViewById(R.id.rb);
            mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                    getReference().child("CourseRate");


        }
    }
}