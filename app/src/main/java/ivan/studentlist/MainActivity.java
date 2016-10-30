package ivan.studentlist;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "logTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item,R.id.tv_name, StudentsData.names);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(StudentsData.googleLinks[position]));
        startActivity(intent);
    }

    public void onGitBtnClick(View view) {
        int position = getListView().getPositionForView((View) view.getParent());
        Log.d(TAG, "onGitBtnClick: " + position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(StudentsData.gitLinks[position]));
        startActivity(intent);
    }
}
