package sa.pums.pums.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sa.pums.pums.Model.UserModel;
import sa.pums.pums.R;

public class VerfiyStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserModel> list;
    Context context;

    public VerfiyStudentAdapter(Context context, List<UserModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final UserModel user = list.get(position);
        holder1.name.setText(user.getFirst_name() + " " + user.getLast_name());

        holder1.email.setText(user.getEmail() + "");
        holder1.id_num.setText(user.getId_num() + "");
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        TextView name, email, id_num;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);

            email = (TextView) itemView.findViewById(R.id.email);
            id_num = (TextView) itemView.findViewById(R.id.id_num);

        }
    }
}