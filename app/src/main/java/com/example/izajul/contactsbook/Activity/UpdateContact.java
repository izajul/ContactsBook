package com.example.izajul.contactsbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.izajul.contactsbook.DataBase.ContactDatabaseManage;
import com.example.izajul.contactsbook.Model.ContactModel;
import com.example.izajul.contactsbook.R;

public class UpdateContact extends AppCompatActivity {

    ImageButton saveBT,cancelBT;
    EditText nameET,emailET,phoneET;
    ContactDatabaseManage contactDatabaseManage;
    ContactModel contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        saveBT = (ImageButton) findViewById(R.id.updateSave);saveBT.getBackground().setAlpha(0);
        cancelBT = (ImageButton) findViewById(R.id.updateCancel);cancelBT.getBackground().setAlpha(0);

// Catch Id from Intent and Get Contact Details From DataBase
        int id = getIntent().getIntExtra("id",0);
        contactDatabaseManage = new ContactDatabaseManage(this);
        contactModel=contactDatabaseManage.getContactById(id);

        nameET = (EditText) findViewById(R.id.updateName);
        emailET = (EditText) findViewById(R.id.updateEmail);
        phoneET = (EditText) findViewById(R.id.updatePhone);

// Show Contact Details
        nameET.setText(contactModel.getName());
        emailET.setText(contactModel.getEmail());
        phoneET.setText(contactModel.getPhone());

// Action Save Button ....
        saveBT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        saveBT.getBackground().setAlpha(200);
                        break;
                    case MotionEvent.ACTION_UP:
                        saveBT.getBackground().setAlpha(0);

                        String name= nameET.getText().toString();
                        String phone= phoneET.getText().toString();
                        String email= emailET.getText().toString();
                        int id = getIntent().getIntExtra("id",0);
                        contactModel = new ContactModel(name,email,phone);
                        if (checkIsEmpty()){
                            long row = contactDatabaseManage.updateContactData(contactModel,id);
                            if (row>0){
                                Toast.makeText(UpdateContact.this, "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                Intent goIntent=new Intent(UpdateContact.this,ContactView.class);
                                goIntent.putExtra("id",id); startActivity(goIntent); finish();
                        }
                        }
                }return false;
            }
        });

// Action Cancel Button
        cancelBT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: cancelBT.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: cancelBT.getBackground().setAlpha(0);
                        Intent goIntent=new Intent(UpdateContact.this,ContactView.class);
                        int id = getIntent().getIntExtra("id",0);goIntent.putExtra("id",id);startActivity(goIntent);finish();
                }return false;
            }
        });
    }

// Checking Method For Fields Is Empty
private boolean checkIsEmpty(){
    if (nameET.getText().toString().isEmpty()){
        nameET.setError("Empty Field!");return false;
    }else if (emailET.getText().toString().isEmpty()){
        emailET.setError("Empty Field!");return false;
    }else if (phoneET.getText().toString().isEmpty()){
        phoneET.setError("Empty Field!");return false;
    }else return true;
}
}
