package com.example.sergbek.lessonnine.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.activity.MainActivity;
import com.example.sergbek.lessonnine.helpers.DataBase;
import com.example.sergbek.lessonnine.helpers.FileHelper;

import java.io.File;


public class FileDialogLongClick extends DialogFragment implements View.OnClickListener {
    private View mRootView;
    private Button mRename;
    private Button mDelete;
    private Button mCopy;
    private EditText mEditRename,mEditCopy;
    private Button mBtnRename,mBtnCopy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =
                inflater.inflate(R.layout.fragment_file_dialog_long, container);

        setAnimation();
        defineComponents();
        setListeners();

        return mRootView;
    }

    private void setListeners() {
        mRename.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mCopy.setOnClickListener(this);
        mBtnRename.setOnClickListener(this);
        mBtnCopy.setOnClickListener(this);
    }

    private void defineComponents() {
        mRename= (Button) mRootView.findViewById(R.id.rename);
        mDelete= (Button) mRootView.findViewById(R.id.delete);
        mCopy= (Button) mRootView.findViewById(R.id.copy);
        mBtnRename= (Button) mRootView.findViewById(R.id.btn_rename);
        mBtnCopy= (Button) mRootView.findViewById(R.id.btn_ok_copy);
        mEditRename= (EditText) mRootView.findViewById(R.id.edit_rename);
        mEditCopy= (EditText) mRootView.findViewById(R.id.edit_copy);
    }


    private void setAnimation() {

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
        if (v==mDelete){
            String filePath= DataBase.getPath();
            File file=new File(filePath);
            if (file.delete()){
                file.delete();
                FileExplorer.mArratList.remove(DataBase.getIndexFile());
                FileExplorer.mFileExplorerAdapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(MainActivity.getContext(),"Deleting failed",Toast.LENGTH_SHORT).show();

        dismiss();
        }
        else if (v==mRename){
            mEditRename.setVisibility(View.VISIBLE);
            mBtnRename.setVisibility(View.VISIBLE);
        }

        else if (v==mBtnRename){

            String filePath= DataBase.getPath();
            File file=new File(filePath);
            String newName=mEditRename.getText().toString();
            String sourse=FileExplorer.sPathTextView.getText().toString().substring(8);
            File newFile=new File(sourse+"/"+newName);
            if (file.renameTo(newFile)){
                file.renameTo(newFile);

                FileHelper.getDir(sourse, FileExplorer.sPathTextView, FileExplorer.mArratList);
                FileExplorer.mFileExplorerAdapter.notifyDataSetChanged();

            }
            else
                Toast.makeText(MainActivity.getContext(),"Rename failed",Toast.LENGTH_SHORT).show();
            dismiss();
        }

        else if (v==mCopy){
            mEditCopy.setVisibility(View.VISIBLE);
            mBtnCopy.setVisibility(View.VISIBLE);
        }

        else if (v==mBtnCopy){
            String filePath= DataBase.getPath();
            File file=new File(filePath);
            String newName=mEditCopy.getText().toString();
            String sourse=FileExplorer.sPathTextView.getText().toString().substring(8);
            File newFile=new File(sourse+"/"+newName);
            FileHelper.copyFile(file,newFile);

            FileHelper.getDir(sourse, FileExplorer.sPathTextView, FileExplorer.mArratList);
            FileExplorer.mFileExplorerAdapter.notifyDataSetChanged();
            dismiss();
        }


    }
}
