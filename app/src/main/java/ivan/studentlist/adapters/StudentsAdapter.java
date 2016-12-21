package ivan.studentlist.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
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

    private String TAG = "tag";

    private LayoutInflater inflater;
    private Context context;
    private List<Student> studentsList;



    private String queryText;



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
        String studentName = student.getName();
        String lowerCaseName = studentName.toLowerCase();
        if (queryText != null && queryText.length() > 0 && lowerCaseName.contains(queryText)) {
            SpannableString highlightedText = new SpannableString(studentName);
            int spanStart = lowerCaseName.indexOf(queryText);
            while (spanStart >= 0) {
                int spanEnd = spanStart + queryText.length();
                highlightedText.setSpan(new BackgroundColorSpan(Color.YELLOW), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanStart = lowerCaseName.indexOf(queryText, spanEnd);
            }
            holder.tvName.setText(highlightedText);
        } else {
            holder.tvName.setText(studentName);
        }
    }


    @Override
    public int getItemCount() {
        if (studentsList != null) {
            return studentsList.size();
        } else {
            return 0;
        }
    }

    public void swapData(List<Student> newData) {
        studentsList = newData;
        notifyDataSetChanged();
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText.trim();
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
            btnGit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GitProfileActivity.class);
                    intent.putExtra(MainActivity.EXTRA_GIT_LOGIN, studentsList.get(getAdapterPosition()).getGitLogin());
                    context.startActivity(intent);
                }
            });
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GoogleProfileActivity.class);
                    intent.putExtra(MainActivity.EXTRA_GOOGLE_ID, studentsList.get(getAdapterPosition()).getGoogleId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
