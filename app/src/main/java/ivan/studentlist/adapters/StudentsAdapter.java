package ivan.studentlist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ivan.studentlist.R;
import ivan.studentlist.activities.GitProfileActivity;
import ivan.studentlist.activities.GoogleProfileActivity;
import ivan.studentlist.activities.MainActivity;
import ivan.studentlist.models.Student;


public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Student> studentsList;



    public StudentsAdapter(Context context, List<Student> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (list != null) {
            studentsList = list;
        }
    }


    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, final int position) {
        final Student student = studentsList.get(position);
        holder.tvName.setText(student.getName());
        holder.btnGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GitProfileActivity.class);
                intent.putExtra(MainActivity.EXTRA_GIT_LOGIN, student.getGitLogin());
                context.startActivity(intent);
            }
        });
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoogleProfileActivity.class);
                intent.putExtra(MainActivity.EXTRA_GOOGLE_ID, student.getGoogleId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public void swapData(List<Student> newData) {
        studentsList = newData;
        notifyDataSetChanged();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnGit;
        LinearLayout llItem;
        StudentViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            btnGit = (Button) view.findViewById(R.id.btn_git);
            llItem = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }
}
