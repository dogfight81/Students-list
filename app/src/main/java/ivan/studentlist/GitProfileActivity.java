package ivan.studentlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class GitProfileActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvLogin;
    ImageView ivAvatar;
    String gitLink;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_git);

        tvLogin = (TextView) findViewById(R.id.tv_git_userName);
        tvName = (TextView) findViewById(R.id.tv_git_name);
        ivAvatar = (ImageView) findViewById(R.id.iv_git_avatar);

        userName = getIntent().getStringExtra(MainActivity.EXTRA_GIT_LOGIN);
        GitTask personTask = new GitTask();
        personTask.execute(userName);
    }

    public class GitTask extends AsyncTask<String, Void, Object[]> {



        @Override
        protected Object[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[] resultStr = null;
            Bitmap bmp = null;

            try {
                final String BASE_URL = "https://api.github.com/users/" + params[0];
                Uri builtUri = Uri.parse(BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                resultStr = getDataFromJson(buffer.toString());
                url = new URL(resultStr[1]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Object[] resultObj = new Object[3];
                resultObj[0] = resultStr[0];
                resultObj[1] = bmp;
                resultObj[2] = resultStr[2];
                return resultObj;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object[] resultObj) {
            if (resultObj != null) {
                tvLogin.setText(userName);
                tvName.setText(resultObj[0].toString());
                gitLink = resultObj[2].toString();
                ivAvatar.setImageBitmap((Bitmap) resultObj[1]);
                }
            }
        }

        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String GIT_AVATAR_URL = "avatar_url";
            final String GIT_NAME = "name";
            final String GIT_HTML_URL = "html_url";

            JSONObject personJson = new JSONObject(jsonStr);
            String name = personJson.getString(GIT_NAME);
            String link = personJson.getString(GIT_HTML_URL);
            String imageURL = personJson.getString(GIT_AVATAR_URL);
            String[] resultStr = new String[3];
            resultStr[0] = name;
            resultStr[1] = imageURL;
            resultStr[2] = link;
            return resultStr;
        }

}
