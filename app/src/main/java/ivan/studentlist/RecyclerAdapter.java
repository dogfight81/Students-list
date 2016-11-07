package ivan.studentlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Student> studentsList;

    private final String TAG = "myTag";


    public RecyclerAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        studentsList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Student student = studentsList.get(position);
        holder.tvName.setText(student.getName());
        holder.btnGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: GIT " + position);
                Intent intent = new Intent(context, GitProfileActivity.class);
                intent.putExtra(MainActivity.EXTRA_GIT_LOGIN, student.getGitLogin());
                context.startActivity(intent);
            }
        });
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: item " + position);
                // TODO: 07.11.2016 add code here
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnGit;
        LinearLayout llItem;
        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            btnGit = (Button) view.findViewById(R.id.btn_git);
            llItem = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }
}