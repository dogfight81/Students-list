package ivan.studentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ivan.studentlist.activities.ContactsActivity;


public class AddDialog extends DialogFragment implements View.OnClickListener {

    private EditText etName;
    private EditText etPhone;
    private Button btnOk;
    private Button btnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("new contact");
        View view = inflater.inflate(R.layout.dialog_add, null);
        etName = (EditText)view.findViewById(R.id.et_name);
        etPhone = (EditText)view.findViewById(R.id.et_number);
        btnCancel = (Button)view.findViewById(R.id.btn_cancel);
        btnOk = (Button)view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Name or phone field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    ((ContactsActivity)getActivity()).addNewContact(name, phone);
                    getDialog().dismiss();
                }
                break;
            case R.id.btn_cancel:
                getDialog().dismiss();
                break;
        }
        getDialog().dismiss();
    }
}
