package com.example.izajul.contactsbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.izajul.contactsbook.Model.ContactModel;
import com.example.izajul.contactsbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by izajul on 1/14/2017.
 */

public class AllPeoplesListAdapter extends ArrayAdapter<ContactModel>{

    ArrayList<ContactModel>mContacts;
    Context mContext;

    public AllPeoplesListAdapter(Context context, ArrayList<ContactModel>contacts) {
        super(context, R.layout.people_row, contacts);
        mContacts=contacts;
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactModel contact=mContacts.get(position);
        convertView= LayoutInflater.from(mContext).inflate(R.layout.people_row,parent,false);

        TextView name= (TextView) convertView.findViewById(R.id.showContactName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.showContactImage);

        name.setText(contact.getName());
        String src = contact.getImgsrc();
        if(src.isEmpty()){} // Set Image If Have Image Src
        else {
            File imgFile = new File(src);
            if (imgFile.exists()) { Glide.with(mContext).load(imgFile).into(imageView); }
        }return convertView;
    }
}
