package ivan.studentlist;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
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

    public static final String TAG = "logTag";

    public static final String[] names = {
            "Винник Владислав",
            "Теплий Михайло",
            "Рибак Богдан",
            "Лещенко Иван",
            "Сакуров Павло",
            "Воловик Руслан",
            "Кириченко Дар'я",
            "Рябко Андрій",
            "Ситник Євгеній",
            "Химич Эдгар",
            "Бочкарьова Альона",
            "Мигаль Руслан",
            "Смалько Ірина",
            "Губський Валерій",
            "Жданов Євген",
            "Сергеенко Иван",
            "Пахаренко Ігор",
            "Сторчак Олександр",
            "Піхманець Микола",
            "Лимарь Володимир" } ;

    public static final String[] gitLinks = {
            "https://github.com/vlads0n",
            "https://github.com/RedGeekPanda",
            "https://github.com/BogdanRybak1996",
            "https://github.com/ivleshch",
            "https://github.com/sakurov",
            "https://github.com/RuslanVolovyk",
            "https://github.com/dashakdsr",
            "https://github.com/RyabkoAndrew",
            "https://github.com/YevheniiSytnyk",
            "https://github.com/lyfm",
            "https://github.com/HelenCool",
            "https://github.com/rmigal",
            "https://github.com/IraSmalko",
            "https://github.com/gvv-ua",
            "https://github.com/zhdanov-ek",
            "https://github.com/dogfight81",
            "https://github.com/IhorPakharenko",
            "https://github.com/new15",
            "https://github.com/NikPikhmanets",
            "https://github.com/VovanNec"};

    public static final String[] googleLinks = {
            "https://plus.google.com/u/0/117765348335292685488",
            "https://plus.google.com/u/0/110313151428733681846",
            "https://plus.google.com/u/0/103145064185261665176",
            "https://plus.google.com/u/0/111088051831122657934",
            "https://plus.google.com/u/0/108482088578879737406",
            "https://plus.google.com/u/0/109719711261293841416",
            "https://plus.google.com/u/0/103130382244571139113",
            "https://plus.google.com/u/0/110288437168771810002",
            "https://plus.google.com/u/0/101427598085441575303",
            "https://plus.google.com/u/0/102197104589432395674",
            "https://plus.google.com/u/0/107382407687723634701",
            "https://plus.google.com/u/0/106331812587299981536",
            "https://plus.google.com/u/0/113994208318508685327",
            "https://plus.google.com/u/0/107910188078571144657",
            "https://plus.google.com/u/0/113264746064942658029",
            "https://plus.google.com/u/0/111389859649705526831",
            "https://plus.google.com/u/0/108231952557339738781",
            "https://plus.google.com/u/0/106553086375805780685",
            "https://plus.google.com/u/0/110087894894730430086",
            "https://plus.google.com/u/0/109227554979939957830"};

    private ListView lvStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudents = (ListView) findViewById(R.id.lv_students);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item,R.id.tv_name, names);
        lvStudents.setAdapter(adapter);
        lvStudents.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleLinks[position]));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_recycler) {

        }
        return super.onOptionsItemSelected(item);
    }

    public void onGitBtnClick(View view) {
        int position = lvStudents.getPositionForView((View) view.getParent());
        Log.d(TAG, "onGitBtnClick: " + position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gitLinks[position]));
        startActivity(intent);
    }


}
