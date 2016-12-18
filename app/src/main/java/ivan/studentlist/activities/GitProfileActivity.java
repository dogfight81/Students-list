package ivan.studentlist.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ivan.studentlist.R;
import ivan.studentlist.models.GitHubUser;
import ivan.studentlist.receivers.HeadsetReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GitProfileActivity extends AppCompatActivity {

    public TextView tvName,tvLogin, tvLocation, tvRepos, tvEmail;
    public CircleImageView ivAvatar;
    public String gitLink;
    public String userName;
    public Button btnGitHub;
    public ProgressBar pbLoading;

    private HeadsetReceiver receiver;
    private final String BASE_URL = "https://api.github.com";
    private Gson gson = new GsonBuilder().create();
    private Retrofit client = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    private GitApiInterface service = client.create(GitApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_git);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            Call<GitHubUser> call = service.getUser(userName);
            call.enqueue(new Callback<GitHubUser>() {
                @Override
                public void onResponse(Call<GitHubUser> call, retrofit2.Response<GitHubUser> response) {
                    if (response.isSuccessful()) {
                        GitHubUser user = response.body();
                        tvLogin.setText(userName);
                        tvName.setText(user.getName());
                        tvLocation. setText("Location: " + user.getLocation());
                        tvRepos.setText("repositories: " + user.getPublicRepos());
                        tvEmail.setText("email: " + user.getEmail());
                        gitLink = user.getHtmlUrl();
                        Picasso.with(GitProfileActivity.this).load(user.getAvatarUrl()).into(ivAvatar);
                        btnGitHub.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(GitProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GitHubUser> call, Throwable t) {
                    Toast.makeText(GitProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    interface GitApiInterface {
        @GET("/users/{username}")
        Call<GitHubUser> getUser(@Path("username") String username);
    }

}
