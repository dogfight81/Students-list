package ivan.studentlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleProfileActivity extends AppCompatActivity {

    public TextView tvName;
    public ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_profile);
        tvName = (TextView) findViewById(R.id.tv_google_name);
        ivAvatar = (ImageView) findViewById(R.id.iv_google_avatar);
        GoogleTask task = new GoogleTask();
        task.execute(getIntent().getStringExtra(MainActivity.EXTRA_GOOGLE_ID));
    }

    public class GoogleTask extends AsyncTask<String, Void, Object[]> {

        @Override
        protected Object[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[] resultStr = null;
            Bitmap bmp = null;

            try {
                String apiKey = "AIzaSyCBsEZKMpc5b9pDcBDphHFpmcWKEyCwrOI";
                final String BASE_URL = "https://www.googleapis.com/plus/v1/people/" + params[0];
                final String APIKEY_PARAM = "key";
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(APIKEY_PARAM, apiKey)
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                resultStr = getDataFromJson(buffer.toString());
                url = new URL(resultStr[2]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Object[] resultObj = new Object[3];
                resultObj[0] = resultStr[0];
                resultObj[1] = resultStr[1];
                resultObj[2] = bmp;
                return resultObj;
            } catch (IOException e) {
                Log.e("AsyncTask", "Error ", e);
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("AsyncTask", "Error ", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object[] resultObj) {
            super.onPostExecute(resultObj);
            tvName.setText(resultObj[0].toString());
            ivAvatar.setImageBitmap((Bitmap) resultObj[2]);
        }

        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String G_DISPLAYNAME = "displayName";
            final String G_URL = "url";
            final String G_IMAGE_OBJECT = "image";
            final String G_IMAGE_URL = "url";

            JSONObject personJson = new JSONObject(jsonStr);
            String name = personJson.getString(G_DISPLAYNAME);
            String url = personJson.getString(G_URL);
            JSONObject image = personJson.getJSONObject(G_IMAGE_OBJECT);
            String imageURL = image.getString(G_IMAGE_URL);
            String[] result = new String[3];
            result[0] = name;
            result[1] = url;
            result[2] = imageURL;
            return result;
        }
    }
}
