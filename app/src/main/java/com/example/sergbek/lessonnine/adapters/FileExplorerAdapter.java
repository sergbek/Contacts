package com.example.sergbek.lessonnine.adapters;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.activity.MainActivity;
import com.example.sergbek.lessonnine.fragments.FileDialogLongClick;
import com.example.sergbek.lessonnine.fragments.FileExplorer;
import com.example.sergbek.lessonnine.helpers.DataBase;
import com.example.sergbek.lessonnine.helpers.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class FileExplorerAdapter extends RecyclerView.Adapter<FileExplorerAdapter.MyViewHolder> {
    private ArrayList<String> mArrayList;
    private FragmentManager mFmBase;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        String s=mArrayList.get(position);
        File file=new File(s);
        if (file.isDirectory())
            holder.mImageViewContent.setImageResource(R.drawable.ic_folder);

        else
            holder.mImageViewContent.setImageResource(R.drawable.ic_file);

        holder.mTextViewContent.setText(s);
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public FileExplorerAdapter(ArrayList arrayList,FragmentManager FmMain) {
        this.mArrayList = arrayList;
        this.mFmBase= FmMain;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mTextViewContent;
        private ImageView mImageViewContent;
        private Context mContext;


        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewContent= (TextView) itemView.findViewById(R.id.textViewContent);
            mImageViewContent= (ImageView) itemView.findViewById(R.id.imageViewContent);
            mContext= MainActivity.getContext();
            mTextViewContent.setOnClickListener(this);
            mTextViewContent.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            File file = new File(mArrayList.get(getAdapterPosition()));
            if (file.isDirectory()) {
                if (file.canRead()){
                    FileHelper.getDir(mArrayList.get(getAdapterPosition()), FileExplorer.sPathTextView, mArrayList);
                    notifyDataSetChanged();

                }
                else {
                    Toast.makeText(mContext,"[" + file.getName()+ "] folder is not available!",Toast.LENGTH_SHORT).show();
                }
            } else {

                showDialog(file);
            }
        }

        private void showDialog(File file) {

            String fileInfo = "Absolute path: " + file.getAbsolutePath()
                    + "\n" + "Path: " + file.getPath() + "\n" + "Parent: "
                    + file.getParent() + "\n" + "Name: " + file.getName() + "\n"
                    + "Last Modified: " + new Date(file.lastModified());

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder
                    .setIcon(R.drawable.info)
                    .setTitle("[" + file.getName() + "]")
                    .setMessage(fileInfo)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            });
            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();
        }

        @Override
        public boolean onLongClick(View v) {
            FileDialogLongClick fileDialogLongClick=new FileDialogLongClick();
            DataBase.setPath(mArrayList.get(getAdapterPosition()));
            DataBase.setIndexFile(getAdapterPosition());
            fileDialogLongClick.show(mFmBase, "dialogOnLong");


            return true;
        }
    }


}
