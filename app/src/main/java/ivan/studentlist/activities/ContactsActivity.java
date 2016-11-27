package ivan.studentlist.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ivan.studentlist.R;

public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvContacts;
    private SimpleCursorAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        String[] from = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER};
        int[] to = {R.id.tv_contact_name, R.id.tv_contact_number};

        lvContacts = (ListView)findViewById(R.id.lv_contacts);
        //Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        contactsAdapter = new SimpleCursorAdapter(this, R.layout.item_contact, null, from, to, 0);
        lvContacts.setAdapter(contactsAdapter);

        getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        contactsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class ContactsLoader extends CursorLoader {

        private Context ctx;

        public ContactsLoader(Context context) {
            super(context);
            ctx = context;
        }

        @Override
        public Cursor loadInBackground() {
            return ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        }
    }

}
