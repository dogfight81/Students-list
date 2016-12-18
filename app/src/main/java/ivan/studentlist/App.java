package ivan.studentlist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ivan on 18.12.2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("users").schemaVersion(1).build();
        Realm.setDefaultConfiguration(configuration);
    }
}
