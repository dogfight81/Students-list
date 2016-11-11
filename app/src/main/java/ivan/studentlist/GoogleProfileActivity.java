package ivan.studentlist;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleProfileActivity extends AppCompatActivity {

    public TextView tvName, tvBirthday;
    public CircleImageView ivAvatar;
    public String googleId;
    public Button btnGoogle;
    public ProgressBar pbLoading;
    private HeadsetReceiver receiver;
    String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_profile);

        receiver = new HeadsetReceiver();

        tvName = (TextView) findViewById(R.id.tv_google_name);
        tvBirthday = (TextView) findViewById(R.id.tv_birthday);
        ivAvatar = (CircleImageView) findViewById(R.id.iv_google_avatar);
        btnGoogle = (Button) findViewById(R.id.btn_google);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_GOOGLE_ID)) {
            googleId = intent.getStringExtra(MainActivity.EXTRA_GOOGLE_ID);
        } else if (intent.getData().getHost().equals("plus.google.com")) {
            googleId = intent.getData().getLastPathSegment();
        }
        if (googleId != null) {
            FetchGoogleDataTask task = new FetchGoogleDataTask(this);
            task.execute(googleId);
        }

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

    public void onGoogleBtnClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/" + googleId)));
    }

    public class FetchGoogleDataTask extends AsyncTask<String, Void, String[]> {

        private Context context;

        FetchGoogleDataTask(Context context) {
            this.context = context;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String apiKey = "AIzaSyCBsEZKMpc5b9pDcBDphHFpmcWKEyCwrOI";
            final String BASE_URL = "https://www.googleapis.com/plus/v1/people/" + params[0];
            final String APIKEY_PARAM = "key";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(APIKEY_PARAM, apiKey)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(builtUri.toString()).build();
            try {
                Response response = client.newCall(request).execute();
                return getDataFromJson(response.body().string());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if (result != null) {
                tvName.setText(result[0]);
                tvBirthday.setText("birthday: " + result[3]);
                Picasso.with(context).load(result[2]).into(ivAvatar);
                btnGoogle.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(context, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
            }
        }

        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String G_DISPLAYNAME = "displayName";
            final String G_URL = "url";
            final String G_IMAGE_OBJECT = "image";
            final String G_IMAGE_URL = "url";
            final String G_BIRTHDAY = "birthday";
            Log.d(TAG, jsonStr);
            JSONObject personJson = new JSONObject(jsonStr);
            String name = personJson.getString(G_DISPLAYNAME);
            String url = personJson.getString(G_URL);
            String birthday;
            if (personJson.has(G_BIRTHDAY)) {
                birthday = personJson.getString(G_BIRTHDAY);
            } else {
                birthday = "unknown";
            }
            JSONObject image = personJson.getJSONObject(G_IMAGE_OBJECT);
            String imageURL = image.getString(G_IMAGE_URL);
            String[] result = new String[4];
            result[0] = name;
            result[1] = url;
            result[2] = imageURL;
            result[3] = birthday;
            return result;
        }
    }
}
