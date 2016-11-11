package ivan.studentlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    TextView tvName;
    TextView tvLogin;
    CircleImageView ivAvatar;
    String gitLink;
    String userName;
    Button btnGitHub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_git);

        tvLogin = (TextView) findViewById(R.id.tv_git_userName);
        tvName = (TextView) findViewById(R.id.tv_git_name);
        ivAvatar = (CircleImageView) findViewById(R.id.iv_git_avatar);
        btnGitHub = (Button) findViewById(R.id.btn_github);
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

    public void onGithubBtnClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + userName)));
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
                gitLink = result[2];
                Picasso.with(context).load(result[1]).into(ivAvatar);
                btnGitHub.setVisibility(View.VISIBLE);
                } else {
                Toast.makeText(context, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
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
