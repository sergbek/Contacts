package com.example.sergbek.lessonnine.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.activity.MainActivity;
import com.example.sergbek.lessonnine.database.DataBaseContacts;
import com.example.sergbek.lessonnine.helpers.ContactsHelper;
import com.example.sergbek.lessonnine.model.ContactsPhone;

public class DialogAddContact extends DialogFragment implements View.OnClickListener {
    private View mRootView;
    private ImageView mImageView;
    private EditText mHintName;
    private EditText mHintPhone;
    private Button mBrowse;
    private Button mOk;
    private Uri mNewPhoto;
    private ContactsPhone mContactsPhone;

    private ContactsFragment mContactsFragment;
    private DataBaseContacts mDataBaseContacts;
    private SQLiteDatabase mSqLiteDatabase;

    private static final  int CODE_GET_FROM_GALLERY = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =
                inflater.inflate(R.layout.fragment_dialog_on_long_click, container);

        setAnimation();
        defineComponents();
        setDataView();

        mBrowse.setOnClickListener(this);
        mOk.setOnClickListener(this);
        return mRootView;
    }

    private void setDataView() {
        ContactsHelper.setImage(null, mImageView);
    }

    private void defineComponents() {

        mHintName=(EditText)mRootView.findViewById(R.id.editName);
//        mHintPhone=(EditText)mRootView.findViewById(R.id.editPhone);
        mImageView=(ImageView)mRootView.findViewById(R.id.imageView3);
        mBrowse=(Button)mRootView.findViewById(R.id.browse);
        mOk=(Button)mRootView.findViewById(R.id.ok);
    }

    private void setAnimation(){
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.CustomDialogAnim;
    }

    private void removeTitleDialog(){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        removeTitleDialog();

    }

    @Override
    public void onClick(View v) {
        if (v==mBrowse){
            uploadPhotoClick();
        }
        else if (v==mOk){
            if (!mHintName.getText().toString().equals("") && !mHintPhone.getText().toString().equals("")){
                mDataBaseContacts=new DataBaseContacts(getActivity());
                mSqLiteDatabase = mDataBaseContacts.getWritableDatabase();
                String newName=mHintName.getText().toString();
                String newPhone=mHintPhone.getText().toString();

                if (mNewPhoto!=null)
                    mContactsPhone= new ContactsPhone(newName,newPhone,mNewPhoto.toString());
                else
                    mContactsPhone= new ContactsPhone(newName,newPhone,null);

                mContactsFragment =new ContactsFragment();

                mDataBaseContacts.addContact(mContactsPhone);
                mContactsFragment.getContactList().add(mContactsPhone);
                mContactsFragment.getAdapter().notifyDataSetChanged();
                dismiss();

            }
            else
                Toast.makeText(MainActivity.getContext(),"Fill in the name and phone number",Toast.LENGTH_SHORT).show();


        }

    }


    public void uploadPhotoClick() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), CODE_GET_FROM_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            mNewPhoto = data.getData();
            if (mNewPhoto != null) {
                mImageView.setImageURI(mNewPhoto);
            }
        }
    }
}
