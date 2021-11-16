package sa.pums.pums.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CourseModel> list;
    Context context;

    public AddCourseAdapter(Context context, List<CourseModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_course_add, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CourseModel model = list.get(position);
        holder1.name.setText(model.getName() + "");

        holder1.mdatabase.child(model.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CourseModel model = snapshot.getValue(CourseModel.class);
                if(model!=null){
                    holder1.add.setVisibility(View.GONE);
                    holder1.added.setVisibility(View.VISIBLE);

                }else {
                    holder1.add.setVisibility(View.VISIBLE);
                    holder1.added.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder1.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message, title, and icon
                        .setTitle("Add")
                        .setMessage("Are you sure of Register?")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                dialog.dismiss();
                                holder1.dialog1.show();
                                holder1.mdatabase.child(model.getUid()).setValue(model)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder1.dialog1.dismiss();
                                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    }
                                });



                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                myQuittingDialogBox.show();
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
        TextView name, add,added;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            add = (TextView) itemView.findViewById(R.id.add);
            added = (TextView) itemView.findViewById(R.id.added);
            mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                    getReference().child("CourseRegister").child(AddCourseToStudentActivity.ID);

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}