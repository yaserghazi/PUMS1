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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import sa.pums.pums.Model.CompanyModel;
import sa.pums.pums.R;

public class CompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CompanyModel> list;
    Context context;

    public CompanyAdapter(Context context, List<CompanyModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_company, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CompanyModel model = list.get(position);
        holder1.name.setText(model.getName() + "");


        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Are you sure of Delete?")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                dialog.dismiss();
                                holder1.dialog1.show();
                                holder1.mdatabase.child(model.getId())
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder1.dialog1.dismiss();
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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
        TextView name, delete;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            delete = (TextView) itemView.findViewById(R.id.delete);
            mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").
                    getReference().child("CompanyTB");

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait ...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}