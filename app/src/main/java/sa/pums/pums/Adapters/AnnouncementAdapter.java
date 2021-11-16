package sa.pums.pums.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sa.pums.pums.Model.AnnouncementModel;
import sa.pums.pums.R;

public class AnnouncementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AnnouncementModel> list;
    Context context;

    public AnnouncementAdapter(Context context, List<AnnouncementModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final AnnouncementModel model = list.get(position);
        holder1.name.setText(model.getAnnouncement() + "");
        holder1.date.setText(model.getFormattedTime() + "");
        holder1.company.setText("Company Name " + model.getCompany_name() + "");
      //  holder1.company.setText("Company Name " + model.getCompany_name()==null?"":model.getCompany_name()+"");

        try {
            if (!model.getImage().equals(""))
                Picasso.get().load(model.getImage()).into(holder1.imageView);
            else holder1.imageView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            holder1.imageView.setVisibility(View.GONE);
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
        TextView name,date,company;
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            company= (TextView) itemView.findViewById(R.id.company);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}