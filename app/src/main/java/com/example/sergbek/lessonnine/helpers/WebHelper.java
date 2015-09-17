package com.example.sergbek.lessonnine.helpers;



import android.widget.EditText;

public class WebHelper {

    public   static String valid(EditText mEditText){
        String http="http://";
        String https="https://";

        String textEdit=mEditText.getText().toString();
        if (textEdit.contains(http)&&textEdit.contains(https)) {
            return textEdit;
        }

        else
            return http+textEdit;


    }


}