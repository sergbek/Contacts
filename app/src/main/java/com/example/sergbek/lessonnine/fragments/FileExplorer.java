package com.example.sergbek.lessonnine.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.adapters.FileExplorerAdapter;
import com.example.sergbek.lessonnine.helpers.FileHelper;

import java.util.ArrayList;


public class FileExplorer extends Fragment  {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private String mRoot = "/";

    public static TextView sPathTextView;
    public static ArrayList<String> mArratList;
    public static FileExplorerAdapter mFileExplorerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =
                  inflater.inflate(R.layout.fragment_file_explorer, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.list);
        sPathTextView = (TextView) mRootView.findViewById(R.id.textViewPath);
        mArratList=new ArrayList();

        mFileExplorerAdapter=new FileExplorerAdapter(mArratList,getFragmentManager());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        FileHelper.getDir(mRoot, sPathTextView,mArratList);

        mRecyclerView.setAdapter(mFileExplorerAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);



        return mRootView;

    }


}
