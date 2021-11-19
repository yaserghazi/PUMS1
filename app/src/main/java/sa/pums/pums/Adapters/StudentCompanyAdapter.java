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

public class StudentCompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CompanyModel> list;
    Context context;

    public StudentCompanyAdapter(Context context, List<CompanyModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_company2, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CompanyModel model = list.get(position);
        holder1.name.setText(model.getName() + "");
        try {
            holder1.description.setText(model.getDescription() + "");
        }catch (Exception e){

        }



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
        TextView name,description;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description= (TextView) itemView.findViewById(R.id.description);
        }
    }
}