package com.example.sergbek.lessonnine.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.activity.MainActivity;
import com.example.sergbek.lessonnine.adapters.RecyclerViewAdapter;
import com.example.sergbek.lessonnine.database.DataBaseContacts;
import com.example.sergbek.lessonnine.model.ContactsPhone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private  int mActionCount=0;
    private View mRootView;
    private Context mContext;

    private DataBaseContacts mDataBaseContacts;
    private SQLiteDatabase mSqLiteDatabase;

    private static List<ContactsPhone>  sContactList;
    private static RecyclerViewAdapter sAdapter;

    public static FragmentManager sFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView =
                inflater.inflate(R.layout.fragment_contacts, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mContext= MainActivity.getContext();
        sFragmentManager=getFragmentManager();
        sContactList= new ArrayList<>();

        mDataBaseContacts=new DataBaseContacts(getActivity());
        mSqLiteDatabase=mDataBaseContacts.getReadableDatabase();
        sContactList=mDataBaseContacts.getAllContacts();

        sAdapter = new RecyclerViewAdapter(sContactList);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setAdapter(sAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(itemAnimator);

        return mRootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contacts, menu);



        SearchView search =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setQueryHint("Enter your name");

        search.setOnQueryTextListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sort) {

            sortContact();
        }

        else if (id==R.id.action_add_contact){
            DialogAddContact dialogAddContact=new DialogAddContact();
            dialogAddContact.show(getFragmentManager(),"dialogAddContact");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if ( TextUtils.isEmpty(newText) ) {
            sAdapter.getFilter().filter("");
        } else {
            sAdapter.getFilter().filter(newText);
        }

        return true;
    }

    private void sortContact(){
        if (mActionCount%2==0) {
            Collections.sort(sContactList);
        }
        else
            Collections.sort(sContactList, Collections.reverseOrder());

        sAdapter.notifyDataSetChanged();
        sAdapter.getFilter().filter("");
        mActionCount++;

    }


    public static List<ContactsPhone> getContactList() {
        return sContactList;
    }

    public static RecyclerViewAdapter getAdapter() {
        return sAdapter;
    }


}
