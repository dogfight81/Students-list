package ivan.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "myTag";
    public static final String EXTRA_GIT_LOGIN = "gitLogin";
    public static final String  EXTRA_GOOGLE_ID = "googleID";

    private ListView lvStudents;
    private String[] names;
    private String[] gitLinks;
    private String[] googleLinks;
    private String[] gitLogins;
    private String[] googleIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudents = (ListView) findViewById(R.id.lv_students);
        names = getResources().getStringArray(R.array.Names);
        gitLinks = getResources().getStringArray(R.array.GitLinks);
        googleLinks = getResources().getStringArray(R.array.GoogleLinks);
        gitLogins = getResources().getStringArray(R.array.GitLogins);
        googleIds = getResources().getStringArray(R.array.GoogleIDs);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item,R.id.tv_name, names);
        lvStudents.setAdapter(adapter);
        lvStudents.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
        Intent intent = new Intent(this, GoogleProfileActivity.class);
        intent.putExtra(EXTRA_GOOGLE_ID, googleIds[position]);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_omenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_recycler) {
            startActivity(new Intent(this, RecyclerActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onGitBtnClick(View view) {
        int position = lvStudents.getPositionForView((View) view.getParent());
        Log.d(TAG, "onGitBtnClick: " + position);
        Intent intent = new Intent(this, GitProfileActivity.class);
        intent.putExtra(EXTRA_GIT_LOGIN, gitLogins[position]);
        startActivity(intent);
    }


}
