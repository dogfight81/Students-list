package ivan.studentlist;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    private HeadsetReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        receiver = new HeadsetReceiver();

        String[] names = getResources().getStringArray(R.array.Names);
        String[] gitLogins = getResources().getStringArray(R.array.GitLogins);
        String[] googleIds = getResources().getStringArray(R.array.GoogleIDs);

        ArrayList<Student> studentsList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            studentsList.add(new Student(names[i], gitLogins[i], googleIds[i]));
        }

        RecyclerView rvStudents = (RecyclerView) findViewById(R.id.rv_students);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, studentsList);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(recyclerAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_omenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
