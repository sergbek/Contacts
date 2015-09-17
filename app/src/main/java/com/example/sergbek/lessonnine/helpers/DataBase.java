package com.example.sergbek.lessonnine.helpers;

import com.example.sergbek.lessonnine.model.ContactsPhone;


public class DataBase {
    private static ContactsPhone sNewContact;
    private static ContactsPhone sLastContact;
    private static String sPath;
    private static int sIndexFile;


    public static int getIndexFile() {
        return sIndexFile;
    }

    public static void setIndexFile(int indexFileDelete) {
        DataBase.sIndexFile = indexFileDelete;
    }

    public static String getPath() {
        return sPath;
    }

    public static void setPath(String path) {
        DataBase.sPath = path;
    }

    public static ContactsPhone getLastContact() {
        return sLastContact;
    }

    public static void setLastContact(ContactsPhone lastContact) {
        DataBase.sLastContact = lastContact;
    }

    public static ContactsPhone getNewContact() {
        return sNewContact;
    }

    public static void setNewContact(ContactsPhone newContact) {
        DataBase.sNewContact = newContact;
    }


}
