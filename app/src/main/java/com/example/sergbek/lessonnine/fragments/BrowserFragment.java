package com.example.sergbek.lessonnine.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.activity.MainActivity;
import com.example.sergbek.lessonnine.helpers.WebHelper;

public class BrowserFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private WebView mWebView;
    private ImageButton mImageButtonGoogle;
    private ImageButton mImageButtonYandex;
    private ImageButton mImageButtonYoutube;
    private ImageButton mImageButtonBing;
    private ImageButton mImageButtonBack;
    private ImageButton mImageButtonForward;
    private EditText mEditText;
    private Button mButton;
    private Context mContext;
    private SharedPreferences mSettings;

    public  String homeAddress="http://www.google.com";

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_ENTERED_ADDRESS = "address";
    public static final String APP_PREFERENCES_HOME_ADDRESS = "home_address";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =
                inflater.inflate(R.layout.fragment_browser, container, false);
        defineComponents();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



        mWebView.setWebViewClient(new MWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("http://ukr.net");


        setComponentsClickListener();


        return mRootView;
    }

    private void setComponentsClickListener() {
        mImageButtonGoogle.setOnClickListener(this);
        mImageButtonYandex.setOnClickListener(this);
        mImageButtonYoutube.setOnClickListener(this);
        mImageButtonBing.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mImageButtonBack.setOnClickListener(this);
        mImageButtonForward.setOnClickListener(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }




    private void defineComponents(){
        mWebView = (WebView) mRootView.findViewById(R.id.webView);
        mButton = (Button) mRootView.findViewById(R.id.button);
        mEditText = (EditText) mRootView.findViewById(R.id.editText);
        mImageButtonGoogle=(ImageButton)mRootView.findViewById(R.id.imageButtonGoogle);
        mImageButtonBack=(ImageButton)mRootView.findViewById(R.id.imageButtonBack);
        mImageButtonForward=(ImageButton)mRootView.findViewById(R.id.imageButtonForward);
        mImageButtonYandex=(ImageButton)mRootView.findViewById(R.id.imageButtonYandex);
        mImageButtonYoutube=(ImageButton)mRootView.findViewById(R.id.imageButtonYoutube);
        mImageButtonBing=(ImageButton)mRootView.findViewById(R.id.imageButtonBing);
        mContext= MainActivity.getContext();

    }

    @Override
    public void onClick(View view){

        if (view==mImageButtonGoogle) {
            mWebView.loadUrl("http://google.com");
        }
        else if (view==mImageButtonYandex){
            mWebView.loadUrl("http://ya.ru");
        }
        else if (view==mImageButtonYoutube){
            mWebView.loadUrl("http://youtube.com");
        }
        else if (view==mImageButtonBing) {
            mWebView.loadUrl("http://bing.com");
        }
        else if (view==mButton){
            mWebView.loadUrl(WebHelper.valid(mEditText));
        }
        else if (view==mImageButtonBack){
            mWebView.goBack();
        }
        else if (view==mImageButtonForward){
            mWebView.goForward();
        }




    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_set_home) {

            final EditText editText=new EditText(mContext);
            editText.setHint("Enter the address of the home page");
            showDialog(editText);


            return true;
        }

        else if (id == R.id.action_home){
            mWebView.loadUrl(homeAddress);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showDialog(final EditText editText){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Enter the address of the home page")
                .setIcon(R.drawable.ic_home)
                .setCancelable(false)
                .setView(editText)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                homeAddress = WebHelper.valid(editText);
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();

    }




    private class MWebViewClient extends WebViewClient
    {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.indexOf("tel:") > -1) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            } else {
                view.loadUrl(url);

            }




            return true;
        }





    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_ENTERED_ADDRESS, mWebView.getUrl());
        editor.putString(APP_PREFERENCES_HOME_ADDRESS,homeAddress);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_ENTERED_ADDRESS)) {
            String address = mSettings.getString(APP_PREFERENCES_ENTERED_ADDRESS, "not");
            mWebView.loadUrl(address);

        }
        if (mSettings.contains(APP_PREFERENCES_HOME_ADDRESS)){
            String home_address = mSettings.getString(APP_PREFERENCES_HOME_ADDRESS, "404");
            homeAddress=home_address;
        }
    }
}
