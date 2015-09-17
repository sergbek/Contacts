package com.example.sergbek.lessonnine.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.helpers.ContactsHelper;
import com.example.sergbek.lessonnine.helpers.DataBase;


public class DialogOnClick extends DialogFragment implements View.OnClickListener {
    private View mRootView;
    private ImageView mImageView;
    private TextView mHintName;
    private TextView mHintPhone;
    private Button btn_dismiss;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =
                inflater.inflate(R.layout.fragment_dialog_on_click, container);

        setAnimation();
        defineComponents();


        setDataView();

        btn_dismiss.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        removeTitleDialog();

    }

    private void removeTitleDialog(){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void setAnimation(){
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.CustomDialogAnim;
    }

    private void setDataView(){
        String image= DataBase.getNewContact().getImage();
        ContactsHelper.setImage(image, mImageView);
        mHintName.setText(String.format(mHintName.getText().toString(), DataBase.getNewContact().getName()));
        mHintPhone.setText(String.format(mHintPhone.getText().toString(), DataBase.getNewContact().getPhone()));

    }

    private void defineComponents() {

        mHintName=(TextView)mRootView.findViewById(R.id.hintName);
        mHintPhone=(TextView)mRootView.findViewById(R.id.hintPhone);
        mImageView=(ImageView)mRootView.findViewById(R.id.imageView2);
        btn_dismiss=(Button)mRootView.findViewById(R.id.btn_dismiss);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
