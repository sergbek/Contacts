package com.example.sergbek.lessonnine.customView;


import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.fragments.ContactsFragment;
import com.example.sergbek.lessonnine.fragments.DialogOnClick;
import com.example.sergbek.lessonnine.fragments.DialogOnLongClick;
import com.example.sergbek.lessonnine.helpers.ContactsHelper;
import com.example.sergbek.lessonnine.helpers.DataBase;
import com.example.sergbek.lessonnine.model.ContactsPhone;

public class Card  extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {

    private ContactsPhone mContactsPhone;
    private CardView mCardView;
    private TextView mNameTextView;
    private TextView mPhoneTextView;
    private ImageView mPhotoImageView;
    private Button mButtonPhone;
    private Button mButtonSms;
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private FragmentManager mFragmentManager;

    public Card(Context context) {
        super(context);
        this.mContext = context;
        init();
        setListeners();

    }


    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        setListeners();
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        setListeners();
    }

    private void init() {
        inflate(getContext(), R.layout.card, this);
        mNameTextView = (TextView) findViewById(R.id.textViewLarge);
        mPhoneTextView = (TextView) findViewById(R.id.textPhone);
        mPhotoImageView = (ImageView) findViewById(R.id.image);
        mButtonPhone = (Button) findViewById(R.id.buttonPhone);
        mButtonSms = (Button) findViewById(R.id.buttonSms);
        mCardView = (CardView) findViewById(R.id.cardView);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        this.mFragmentManager= ContactsFragment.sFragmentManager;

    }

    private void setListeners() {
        mButtonPhone.setOnClickListener(this);
        mButtonSms.setOnClickListener(this);
        mCardView.setOnClickListener(this);
        mCardView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == mButtonPhone) {
            ContactsHelper.pressButtonPhone(mContext, mPhoneTextView);


        } else if (v == mButtonSms) {

            ContactsHelper.pressButtonSMS(mContext, mPhoneTextView);

        } else if (v == mCardView) {

            DialogOnClick dialogOnClick = new DialogOnClick();
            DataBase.setNewContact(mContactsPhone);
            dialogOnClick.show(mFragmentManager, "dialog");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        DialogOnLongClick dialogOnLongClick = new DialogOnLongClick();
        DataBase.setNewContact(mContactsPhone);
        dialogOnLongClick.show(mFragmentManager, "dialogOnLong");

        return true;
    }

    public ContactsPhone getContactsPhone() {
        return mContactsPhone;
    }

    public void setContactsPhone(ContactsPhone mContactsPhone) {
        this.mContactsPhone = mContactsPhone;
    }

    public CardView getCardView() {
        return mCardView;
    }

    public void setCardView(CardView cardView) {
        this.mCardView = cardView;
    }

    public TextView getNameTextView() {
        return mNameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.mNameTextView = nameTextView;
    }

    public TextView getPhoneTextView() {
        return mPhoneTextView;
    }

    public void setPhoneTextView(TextView phoneTextView) {
        this.mPhoneTextView = phoneTextView;
    }

    public ImageView getPhotoImageView() {
        return mPhotoImageView;
    }

    public void setPhotoImageView(ImageView photoImageView) {
        this.mPhotoImageView = photoImageView;
    }

    public Button getButtonPhone() {
        return mButtonPhone;
    }

    public void setButtonPhone(Button buttonPhone) {
        this.mButtonPhone = buttonPhone;
    }

    public Button getButtonSms() {
        return mButtonSms;
    }

    public void setButtonSms(Button buttonSms) {
        this.mButtonSms = buttonSms;
    }


    public RelativeLayout getRelativeLayout() {
        return mRelativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.mRelativeLayout = relativeLayout;
    }



}
