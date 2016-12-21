package ivan.studentlist.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ivan.studentlist.R;
import ivan.studentlist.adapters.StudentsAdapter;
import ivan.studentlist.models.Student;
import ivan.studentlist.receivers.HeadsetReceiver;

public class MainActivity extends AppCompatActivity {

    private String TAG = "tag";

    public static final String EXTRA_GIT_LOGIN = "gitLogin";
    public static final String  EXTRA_GOOGLE_ID = "googleID";
    private HeadsetReceiver receiver;
    private Realm realm;
    private RealmResults<Student> studentRealmResults;
    private StudentsAdapter studentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        receiver = new HeadsetReceiver();
        RecyclerView rvStudents = (RecyclerView) findViewById(R.id.rv_students);
        studentsAdapter = new StudentsAdapter(this, null);
        rvStudents.setAdapter(studentsAdapter);


        realm = Realm.getDefaultInstance();
        studentRealmResults = realm.where(Student.class).findAllAsync();
        studentRealmResults.addChangeListener(new RealmChangeListener<RealmResults<Student>>() {
            @Override
            public void onChange(RealmResults<Student> element) {
                Log.d(TAG, "onChange: ");
                if (element.isEmpty()) {
                    String[] names = getResources().getStringArray(R.array.Names);
                    String[] gitLogins = getResources().getStringArray(R.array.GitLogins);
                    String[] googleIds = getResources().getStringArray(R.array.GoogleIDs);
                    List<Student> studentsList = new ArrayList<>();
                    for (int i = 0; i < names.length; i++) {
                        studentsList.add(new Student(i, names[i], gitLogins[i], googleIds[i]));
                    }
                    saveStudentsToRealm(studentsList);
                    studentsAdapter.swapData(studentsList);
                } else {
                    studentsAdapter.swapData(element);
                }
            }
        });




        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentsAdapter);
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
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_omenu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                studentRealmResults = realm.where(Student.class).contains("searchName", newText.toLowerCase()).findAll();
                studentsAdapter.setQueryText(newText.toLowerCase());
                studentsAdapter.swapData(studentRealmResults);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_photo:
                startActivity(new Intent(this, PhotoActivity.class));
                break;
            case R.id.item_contacts:
                startActivity(new Intent(this, ContactsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveStudentsToRealm(final List<Student> data){
        realm.beginTransaction();
        realm.commitTransaction();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(data);
            }
        });
    }

}
