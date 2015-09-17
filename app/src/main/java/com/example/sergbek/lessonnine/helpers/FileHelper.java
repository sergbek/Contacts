package com.example.sergbek.lessonnine.helpers;

import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static void getDir(String dirPath,TextView textView,ArrayList<String> arrayList) {
        String root="/";
        arrayList.clear();
        textView.setText("Sourse: " + dirPath);
        List<String> itemList = new ArrayList<>();
        File file = new File(dirPath);
        File[] filesArray = file.listFiles();

        if (!dirPath.equals(root)) {
            itemList.add(root);
            itemList.add("../");
            arrayList.add(file.getParent());
        }

        for (File aFilesArray : filesArray) {
            file = aFilesArray;
            arrayList.add(file.getPath());
            if (file.isDirectory())
                itemList.add(file.getName() + "/");
            else
                itemList.add(file.getName());
        }


    }

    public static Boolean copyFile(File source, File dest) {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            int nLength;
            byte[] buf = new byte[8000];
            while (true) {
                nLength = is.read(buf);
                if (nLength < 0) {
                    break;
                }
                os.write(buf, 0, nLength);
            }
            return true;
        } catch (IOException ex) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception ex) {
                }
            }
        }
        return false;
    }




}
