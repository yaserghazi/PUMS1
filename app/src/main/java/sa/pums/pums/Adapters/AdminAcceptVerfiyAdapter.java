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

import java.util.ArrayList;
import java.util.List;

import sa.pums.pums.Model.UserModel;
import sa.pums.pums.Model.dataModel;
import sa.pums.pums.R;

public class AdminAcceptVerfiyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserModel> list;
    Context context;

    public AdminAcceptVerfiyAdapter(Context context, List<UserModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user_accept, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final UserModel user = list.get(position);
        holder1.name.setText(user.getFirst_name() + " " + user.getLast_name());

        holder1.email.setText(user.getEmail() + "");
        holder1.id_num.setText(user.getId_num() + "");
        holder1.type.setText(user.getType() == 1 ? context.getString(R.string.college_company_representative)
                : context.getString(R.string.company_representative));

        holder1.delete.setText(user.isDelete() ? "UnBlock" : "Block");
        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message, title, and icon
                        .setTitle(user.isDelete() ? "UnBlock" : "Block")
                        .setMessage(user.isDelete() ? "Are you sure of UnBlock?" : "Are you sure of Block?")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                dialog.dismiss();
                                holder1.dialog1.show();
                                holder1.mdatabase.child(user.getId())
                                        .child("delete").setValue(!user.isDelete()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder1.dialog1.dismiss();
                                        Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
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
        if (user.isActive()) {
            holder1.accept.setVisibility(View.GONE);
            holder1.delete.setVisibility(View.VISIBLE);
        } else {
            holder1.accept.setVisibility(View.VISIBLE);
            holder1.delete.setVisibility(View.GONE);
        }
        holder1.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                        // set message, title, and icon
                        .setTitle("Activation")
                        .setMessage("Are you sure of Activation?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                dialog.dismiss();
                                holder1.dialog1.show();
                                holder1.mdatabase.child(user.getId())
                                        .child("active").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder1.dialog1.dismiss();
                                        Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
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
        TextView name, accept, delete, email, id_num, type;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            accept = (TextView) itemView.findViewById(R.id.accept);
            mdatabase = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().child("Users");
            email = (TextView) itemView.findViewById(R.id.email);
            id_num = (TextView) itemView.findViewById(R.id.id_num);
            type = (TextView) itemView.findViewById(R.id.type);
            delete = (TextView) itemView.findViewById(R.id.delete);

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait ...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}