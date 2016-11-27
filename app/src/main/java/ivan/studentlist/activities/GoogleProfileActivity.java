package ivan.studentlist.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    public String googleId;
    public Button btnGoogle;
    public ProgressBar pbLoading;
    private HeadsetReceiver receiver;
    String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_profile);

        final String apiKey = "AIzaSyCBsEZKMpc5b9pDcBDphHFpmcWKEyCwrOI";
        final String BASE_URL = "https://www.googleapis.com";
        Retrofit client = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        GoogleApiInteface service = client.create(GoogleApiInteface.class);

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
            Call<GoogleUser> call = service.getUser(googleId, apiKey);
            call.enqueue(new Callback<GoogleUser>() {
                @Override
                public void onResponse(Call<GoogleUser> call, retrofit2.Response<GoogleUser> response) {
                    if (response.isSuccessful()) {
                        GoogleUser user = response.body();
                        tvName.setText(user.getDisplayName());
                        tvBirthday.setText("birthday: " + user.getBirthday());
                        Picasso.with(GoogleProfileActivity.this).load(user.getImage().getUrl()).into(ivAvatar);
                        btnGoogle.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(GoogleProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GoogleUser> call, Throwable t) {
                    Toast.makeText(GoogleProfileActivity.this, R.string.error_toast_loading, Toast.LENGTH_LONG).show();
                }
            });
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

    interface GoogleApiInteface {
        @GET("/plus/v1/people/{userID}")
        Call<GoogleUser> getUser(@Path("userID") String userId, @Query("key") String apiKey);

    }

}
