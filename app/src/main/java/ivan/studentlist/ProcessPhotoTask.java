package ivan.studentlist;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class ProcessPhotoTask extends AsyncTask<Uri, Void, Bitmap> {

    private String TAG = "tag";

    private Context context;
    private OnPhotoProcessedListener onPhotoProcessedListener;

    public ProcessPhotoTask(Context context) {
        this.context = context;
    }




    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri photoUri = uris[0];

        ContentResolver resolver = context.getContentResolver();
        try {
            final InputStream photoStream = resolver.openInputStream(photoUri);
            Log.d(TAG, "doInBackground: " + photoUri.toString());
            Bitmap bitmap = BitmapFactory.decodeStream(photoStream);
            if (photoStream != null) {
                photoStream.close();
            }
            if (bitmap.getByteCount() > 41107008) {
                return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.75), (int)(bitmap.getHeight()*0.75), true);
            }
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "exception" + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        onPhotoProcessedListener.onBitmapReady(bitmap);
        Log.d(TAG, "onPostExecute: " + (bitmap != null));
    }

    public void setOnPhotoProcessedListener(OnPhotoProcessedListener listener) {
        onPhotoProcessedListener = listener;
    }

    public interface OnPhotoProcessedListener {
        void onBitmapReady(@Nullable Bitmap bitmap);
    }

}
