package com.example.sergbek.lessonnine.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import com.example.sergbek.lessonnine.R;
import com.example.sergbek.lessonnine.customView.Card;
import com.example.sergbek.lessonnine.helpers.ContactsHelper;
import com.example.sergbek.lessonnine.helpers.DataBase;
import com.example.sergbek.lessonnine.model.ContactsPhone;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private List<ContactsPhone> mContactsList;
    private List<ContactsPhone> mArrayList;


    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    public RecyclerViewAdapter(List<ContactsPhone> ContactsList) {
        this.mContactsList = ContactsList;

    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_container, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {

        ContactsPhone contactsPhone = mContactsList.get(position);
        holder.mCard.setContactsPhone(contactsPhone);
        holder.mCard.getNameTextView().setText(contactsPhone.getName());
        holder.mCard.getPhoneTextView().setText(contactsPhone.getPhone());


        String image = contactsPhone.getImage();


        ContactsHelper.setImage(image, holder.mCard.getPhotoImageView());


        isLastContact(holder, contactsPhone);


    }

    private void isLastContact(RecyclerViewAdapter.MyViewHolder holder, ContactsPhone contactsPhone) {
        if (DataBase.getLastContact() == contactsPhone)
            holder.mCard.getRelativeLayout().setBackgroundColor(Color.GRAY);
        else
            holder.mCard.getRelativeLayout().setBackgroundColor(Color.WHITE);
    }


    @Override
    public Filter getFilter() {
        return new Filter();
    }

    public class Filter extends android.widget.Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults oReturn = new FilterResults();
            List<ContactsPhone> results = new ArrayList<>();
            if (mArrayList == null)
                mArrayList = mContactsList;
            if (constraint != null) {
                if (mArrayList != null & mArrayList.size() > 0) {
                    for (final ContactsPhone cp : mArrayList) {
                        if (cp.getName().toLowerCase().contains(constraint.toString()))
                            results.add(cp);
                    }
                }
                oReturn.values = results;
            }
            return oReturn;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mContactsList = (ArrayList<ContactsPhone>) results.values;
            notifyDataSetChanged();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

    private Card mCard;

        public MyViewHolder(View itemView) {
            super(itemView);

            mCard= (Card) itemView.findViewById(R.id.view);
        }
    }
}