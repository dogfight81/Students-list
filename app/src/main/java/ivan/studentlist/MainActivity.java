package ivan.studentlist;

import android.content.Intent;
import android.content.IntentFilter;
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
    private String[] gitLogins;
    private String[] googleIds;

    private HeadsetReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new HeadsetReceiver();

        String[] names = getResources().getStringArray(R.array.Names);

        lvStudents = (ListView) findViewById(R.id.lv_students);

        gitLogins = getResources().getStringArray(R.array.GitLogins);
        googleIds = getResources().getStringArray(R.array.GoogleIDs);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, R.id.tv_name, names);
        lvStudents.setAdapter(adapter);
        lvStudents.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
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

        switch (item.getItemId()) {
            case R.id.item_recycler:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            case R.id.item_photo:
                startActivity(new Intent(this, PhotoActivity.class));
                break;
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
