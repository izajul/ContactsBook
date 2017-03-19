package com.example.izajul.contactsbook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.izajul.contactsbook.Activity.ContactView;
import com.example.izajul.contactsbook.Adapter.AllPeoplesListAdapter;
import com.example.izajul.contactsbook.DataBase.ContactDatabaseManage;
import com.example.izajul.contactsbook.Model.ContactModel;
import com.example.izajul.contactsbook.R;

import java.util.ArrayList;

public class PeoplesFragment extends Fragment {
    ListView peopleLV;
    ContactDatabaseManage mContactDBManage;
    AllPeoplesListAdapter adapter;
    ArrayList<ContactModel>contacts=new ArrayList<>();
    ImageButton searchBT;

    public PeoplesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
// Get People From DataBase And Set List View
        mContactDBManage=new ContactDatabaseManage(inflater.getContext());
        View view= inflater.inflate(R.layout.fragment_peoples,container,false);
        peopleLV = (ListView) view.findViewById(R.id.peopleListView);
        final ArrayList<ContactModel>contacts=mContactDBManage.getContacts();
        if (contacts.size()==0){} // checking If Database Is Empty
        else{
            adapter=new AllPeoplesListAdapter(getActivity(),contacts);
            peopleLV.setAdapter(adapter);
        }

// List View Item Click Action
        peopleLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ContactModel>contactModels=contacts;
                clickListItem(position,contactModels);
            }
        });return view;
    }

// Helping Method For Click List item
    public void clickListItem(int position, ArrayList<ContactModel>contacts){
        ContactModel contactModel= contacts.get(position);
        Intent newIntent=new Intent(getActivity(),ContactView.class);
        int id = contactModel.getId();
        newIntent.putExtra("id",id);
        startActivity(newIntent);
    }

}
