package com.example.sergbek.lessonnine.model;

public class ContactsPhone implements Comparable<ContactsPhone>{
    private String mId;
    private String mName;
    private String mPhone;
    private String mImage;



    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }



    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }



    public ContactsPhone(String id,String name, String phone,String image) {
        this.mId=id;
        this.mName = name;
        this.mPhone = phone;
        this.mImage=image;
    }

    public ContactsPhone(String name, String phone,String image) {
        this.mName = name;
        this.mPhone = phone;
        this.mImage=image;
    }

    public ContactsPhone(){

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    @Override
    public int compareTo(ContactsPhone o) {
        return this.mName.compareToIgnoreCase(o.getName());
    }
}