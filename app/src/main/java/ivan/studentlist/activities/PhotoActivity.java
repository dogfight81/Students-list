package ivan.studentlist.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.hdodenhof.circleimageview.CircleImageView;
import ivan.studentlist.ProcessPhotoTask;
import ivan.studentlist.R;

public class PhotoActivity extends AppCompatActivity implements ProcessPhotoTask.OnPhotoProcessedListener {

    private static final String TAG = "tag";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 101;
    private static final int REQUEST_CODE_IMAGE_OPEN = 102;
    private static final int REQUEST_CODE_SELECT_PHOTO = 103;


    private CircleImageView civPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        civPhoto = (CircleImageView) findViewById(R.id.civ_photo);
    }

    public void onImageClick(View view) {
        ListView lvChoice = new ListView(this);
        String[] choice = new String[] {"capture photo(camera)", "select photo(gallery)"};
        lvChoice.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, choice));
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(lvChoice);

        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        lvChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_IMAGE_CAPTURE);
                        break;
                    case 1:
                        getPhotoFromGallery();
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_CODE_IMAGE_CAPTURE:
                    civPhoto.setImageBitmap((Bitmap)data.getExtras().get("data"));
                    break;
                case REQUEST_CODE_SELECT_PHOTO:
                    Uri photoUri = getRealPathFromURI(data.getData());
                    ProcessPhotoTask photoTask = new ProcessPhotoTask(this);
                    photoTask.setOnPhotoProcessedListener(this);
                    photoTask.execute(photoUri);
                    break;
            }
        }

    }

    @Override
    public void onBitmapReady(@Nullable Bitmap bitmap) {
        civPhoto.setImageBitmap(bitmap);
    }

    private void getPhotoFromGallery() {
        Intent photoIntent = new Intent();
        photoIntent.setType("image/*");
        photoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoIntent, "select photo"), REQUEST_CODE_SELECT_PHOTO);
    }

    public Uri getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return Uri.parse("file://" + res);
    }

}
