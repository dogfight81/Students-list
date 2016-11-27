package ivan.studentlist.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;
import ivan.studentlist.R;

public class PhotoActivity extends AppCompatActivity {

    private CircleImageView civPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        civPhoto = (CircleImageView) findViewById(R.id.civ_photo);
    }

    public void onImageClick(View view) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            civPhoto.setImageBitmap((Bitmap)data.getExtras().get("data"));
        }
    }
}
