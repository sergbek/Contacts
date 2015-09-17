package com.example.sergbek.lessonnine.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.sergbek.lessonnine.fragments.BrowserFragment;
import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.fragments.ContactsFragment;
import com.example.sergbek.lessonnine.fragments.FileExplorer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context context;
    private Button mButtonBrowser;
    private Button mButtonContacts;
    private Button mButtonFileExplorer;
    private Toolbar mToolbar;
    private FragmentTransaction mFTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();
        defineComponents();
        setSupportActionBar(mToolbar);
        setListeners();


    }

    private void setListeners() {
        mButtonBrowser.setOnClickListener(this);
        mButtonContacts.setOnClickListener(this);
        mButtonFileExplorer.setOnClickListener(this);
    }

    public static Context getContext() {
        return context;
    }


    private void defineComponents() {
        mButtonBrowser= (Button) findViewById(R.id.browser);
        mButtonContacts= (Button) findViewById(R.id.contacts);
        mButtonFileExplorer= (Button) findViewById(R.id.file);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }


    @Override
    public void onClick(View v) {
        if (v==mButtonBrowser){
            mFTrans=getFragmentManager().beginTransaction();
            BrowserFragment browserFragment=new BrowserFragment();
            mFTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            mFTrans.replace(R.id.containerView, browserFragment, "browserFragment");
            mFTrans.commit();
        }
        else if (v==mButtonContacts){
            mFTrans=getFragmentManager().beginTransaction();
            ContactsFragment contactsFragment=new ContactsFragment();
            mFTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            mFTrans.replace(R.id.containerView, contactsFragment, "contactsFragment");
            mFTrans.commit();

        }
        else if(v==mButtonFileExplorer){
            mFTrans=getFragmentManager().beginTransaction();
            FileExplorer fileExplorer=new FileExplorer();
            mFTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            mFTrans.replace(R.id.containerView, fileExplorer, "fileFragment");
            mFTrans.commit();

        }
    }
}
