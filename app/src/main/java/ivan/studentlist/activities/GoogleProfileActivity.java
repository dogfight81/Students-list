package ivan.studentlist.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ivan.studentlist.models.GoogleUser;
import ivan.studentlist.receivers.HeadsetReceiver;
import ivan.studentlist.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class GoogleProfileActivity extends AppCompatActivity {

    public TextView tvName, tvBirthday;
    public CircleImageView ivAvatar;
    private LinearLayout llRetry;

    public String googleId;
    public Button btnGoogle;
    public ProgressBar pbLoading;
    private HeadsetReceiver receiver;
    String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





        receiver = new HeadsetReceiver();

        llRetry = (LinearLayout)findViewById(R.id.ll_retry);
        llRetry.setVisibility(View.INVISIBLE);
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
            loadData();
        }

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

    public void onRetryBtnClick(View view) {
        llRetry.setVisibility(View.INVISIBLE);
        loadData();
    }

    interface GoogleApiInteface {
        @GET("/plus/v1/people/{userID}")
        Call<GoogleUser> getUser(@Path("userID") String userId, @Query("key") String apiKey);

    }

    private void loadData() {

        pbLoading.setVisibility(View.VISIBLE);

        final String BASE_URL = "https://www.googleapis.com";
        Retrofit client = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        final String apiKey = "AIzaSyBC0oTTVSVQeC9_tNlTD6k8QuU6c2Hg9nI";
        GoogleApiInteface service = client.create(GoogleApiInteface.class);

        Call<GoogleUser> call = service.getUser(googleId, apiKey);
        call.enqueue(new Callback<GoogleUser>() {
            @Override
            public void onResponse(Call<GoogleUser> call, retrofit2.Response<GoogleUser> response) {
                Log.d(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    GoogleUser user = response.body();
                    tvName.setText(user.getDisplayName());
                    tvBirthday.setText("birthday: " + user.getBirthday());
                    Picasso.with(GoogleProfileActivity.this).load(user.getImage().getUrl()).into(ivAvatar);
                    btnGoogle.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(GoogleProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: " + response.message());
                    pbLoading.setVisibility(View.INVISIBLE);
                    llRetry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GoogleUser> call, Throwable t) {
                Toast.makeText(GoogleProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
                pbLoading.setVisibility(View.INVISIBLE);
                llRetry.setVisibility(View.VISIBLE);
            }
        });
    }

}
