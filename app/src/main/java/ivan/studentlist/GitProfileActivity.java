package ivan.studentlist;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class GitProfileActivity extends AppCompatActivity {

    public TextView tvName,tvLogin, tvLocation, tvRepos, tvEmail;
    public CircleImageView ivAvatar;
    public String gitLink;
    public String userName;
    public Button btnGitHub;
    public ProgressBar pbLoading;

    private HeadsetReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_git);

        receiver = new HeadsetReceiver();

        tvLogin = (TextView) findViewById(R.id.tv_git_userName);
        tvName = (TextView) findViewById(R.id.tv_git_name);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvRepos = (TextView) findViewById(R.id.tv_repos);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        ivAvatar = (CircleImageView) findViewById(R.id.iv_git_avatar);
        btnGitHub = (Button) findViewById(R.id.btn_github);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_GIT_LOGIN)) {
            userName = intent.getStringExtra(MainActivity.EXTRA_GIT_LOGIN);
        } else if (intent.getData().getHost().equals("github.com")) {
            userName = intent.getData().getLastPathSegment();
        }
        if (userName != null) {
            FetchGithubDataTask task = new FetchGithubDataTask(this);
            task.execute(userName);
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

    public void onGithubBtnClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(gitLink)));
    }

    public class FetchGithubDataTask extends AsyncTask<String, Void, String[]> {

        private Context context;

        FetchGithubDataTask(Context context) {
            this.context = context;
        }

        @Override
        protected String[] doInBackground(String... params) {

            final String BASE_URL = "https://api.github.com/users/" + params[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(BASE_URL).build();
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
            if (result != null) {
                tvLogin.setText(userName);
                tvName.setText(result[0]);
                tvLocation. setText("Location: " + result[3]);
                tvRepos.setText("repositories: " + result[4]);
                tvEmail.setText("email: " + result[5]);
                gitLink = result[2];
                Picasso.with(context).load(result[1]).into(ivAvatar);
                btnGitHub.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                } else {
                Toast.makeText(context, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
            }
            }
        }

        private String[] getDataFromJson(String jsonStr) throws JSONException {
            final String GIT_AVATAR_URL = "avatar_url";
            final String GIT_NAME = "name";
            final String GIT_HTML_URL = "html_url";
            final String GIT_LOCATION = "location";
            final String GIT_REPOS = "public_repos";
            final String GIT_EMAIL = "email";
            JSONObject personJson = new JSONObject(jsonStr);
            String name = personJson.getString(GIT_NAME);
            String link = personJson.getString(GIT_HTML_URL);
            String imageURL = personJson.getString(GIT_AVATAR_URL);
            String location = personJson.getString(GIT_LOCATION);
            String repos = Integer.toString(personJson.getInt(GIT_REPOS));
            String email = personJson.getString(GIT_EMAIL);
            String[] resultStr = new String[6];
            resultStr[0] = name;
            resultStr[1] = imageURL;
            resultStr[2] = link;
            resultStr[3] = location;
            resultStr[4] = repos;
            resultStr[5] = email;

            for (int i = 0; i < resultStr.length; i++) {
                if (resultStr[i].equals("null")){
                    resultStr[i] = "unknown";
                }
            }
            return resultStr;
        }

}
