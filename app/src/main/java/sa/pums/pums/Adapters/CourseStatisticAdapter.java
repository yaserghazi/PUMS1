package sa.pums.pums.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import sa.pums.pums.CollegeCompanyRepresentative.UpdateCourseStatisticsActivity;
import sa.pums.pums.Model.CourseStatisticsModel;
import sa.pums.pums.R;

public class CourseStatisticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CourseStatisticsModel> list;
    Context context;

    public CourseStatisticAdapter(Context context, List<CourseStatisticsModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_course_statistic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final CourseStatisticsModel model = list.get(position);
        holder1.course_name.setText(model.getCourse_name() + "");
        holder1.date.setText(model.getFormattedTime() + "");


        holder1.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateCourseStatisticsActivity.class);
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
        TextView date, course_name, update;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;

        public Holder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            course_name = (TextView) itemView.findViewById(R.id.course_name);

            update = (TextView) itemView.findViewById(R.id.update);

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}