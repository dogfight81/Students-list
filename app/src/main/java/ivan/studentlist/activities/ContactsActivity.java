package ivan.studentlist.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import ivan.studentlist.AddDialog;
import ivan.studentlist.R;

public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvContacts;
    private SimpleCursorAdapter contactsAdapter;

    private final static int REQUEST_CODE_ADD_CONTACT = 101;
    private final static int REQUEST_CODE_PERMISSION_READ_CONTACTS = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        String[] from = new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        int[] to = {R.id.tv_contact_name, R.id.tv_contact_number};

        lvContacts = (ListView)findViewById(R.id.lv_contacts);
        contactsAdapter = new SimpleCursorAdapter(this, R.layout.item_contact, null, from, to, 0);
        lvContacts.setAdapter(contactsAdapter);
        ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_CODE_PERMISSION_READ_CONTACTS);



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

    public void onFabClick(View view) {
        DialogFragment addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void addNewContact(String name, String phone) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra("finishActivityOnSaveCompleted", true);
        startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK) {
            Toast.makeText(this, "contact has been added", Toast.LENGTH_SHORT).show();
        }
    }

    static class ContactsLoader extends CursorLoader {

        private Context ctx;

        ContactsLoader(Context context) {
            super(context);
            ctx = context;
        }

        @Override
        public Cursor loadInBackground() {
            return ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_CONTACTS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportLoaderManager().initLoader(0, null, this);
                } else {
                    Toast.makeText(this, "Permission denied to read your contacts", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
