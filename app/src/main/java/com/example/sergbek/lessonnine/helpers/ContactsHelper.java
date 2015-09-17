package com.example.sergbek.lessonnine.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergbek.lessonnine.R;

public class ContactsHelper {

    public static void setImage(String image, ImageView imageView){
        if (image!=null){
            imageView.setImageURI(Uri.parse(image));
        }
        else
            imageView.setImageResource(R.drawable.ic_default);
    }

    public static void pressButtonPhone(Context context, TextView textView){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(String.format("tel:%s",textView.getText().toString())));
        context.startActivity(intent);


    }

    public static void pressButtonSMS(Context context, TextView textView){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse(String.format("sms:%s", textView.getText().toString())));
        context.startActivity(smsIntent);


    }



}
