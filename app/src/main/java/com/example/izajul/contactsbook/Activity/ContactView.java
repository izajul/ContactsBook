package com.example.izajul.contactsbook.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.izajul.contactsbook.DataBase.ContactDatabaseManage;
import com.example.izajul.contactsbook.Model.ContactModel;
import com.example.izajul.contactsbook.R;

import java.io.File;

public class ContactView extends AppCompatActivity {

    TextView nameTV, phoneTV, emailTV;
    ImageView contactImageView, mfevoretImgButton;
    ImageButton call,sms,email,delete,edit;
    boolean check = true;
    ContactDatabaseManage contactDatabaseManage;
    ContactModel mContact;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onBackPressed();}
        });

        call = (ImageButton) findViewById(R.id.callbtn);
        sms = (ImageButton) findViewById(R.id.smsbtn);
        email = (ImageButton) findViewById(R.id.emailbtn);
        delete = (ImageButton) findViewById(R.id.deletebtn);
        edit = (ImageButton) findViewById(R.id.editbtn);
        call.getBackground().setAlpha(0);sms.getBackground().setAlpha(0);email.getBackground().setAlpha(0);delete.getBackground().setAlpha(0);edit.getBackground().setAlpha(0); // Firstly Set All Button Background Alpha(0)

//  Set Button Background Alpha When On Touch
        call.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: call.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: call.getBackground().setAlpha(0);
                }return false;
            }
        });
        sms.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: sms.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: sms.getBackground().setAlpha(0);
                }return false;
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: email.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: email.getBackground().setAlpha(0);
                }return false;
            }
        });
        delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: delete.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: delete.getBackground().setAlpha(0);
                }return false;
            }
        });
        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: edit.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP: edit.getBackground().setAlpha(0);
                }return false;
            }
        });

        nameTV = (TextView) findViewById(R.id.contactName);
        phoneTV = (TextView) findViewById(R.id.contactPhone);
        emailTV = (TextView) findViewById(R.id.contactEmail);

        contactImageView = (ImageView) findViewById(R.id.contactImage);
        mfevoretImgButton = (ImageView) findViewById(R.id.fevoretImgButton);

// Set Fevoret Button Active DisActive
        mfevoretImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    mfevoretImgButton.setImageResource(R.drawable.fevoret);
                    check = false;
                } else {
                    mfevoretImgButton.setImageResource(R.drawable.fevoretblack);
                    check = true;
                }
            }
        });
// Set Contact Details In Contact View
        int id = getIntent().getIntExtra("id",0);
        contactDatabaseManage = new ContactDatabaseManage(this);
        mContact = contactDatabaseManage.getContactById(id);
        nameTV.setText(mContact.getName());
        phoneTV.setText(mContact.getPhone());
        emailTV.setText(mContact.getEmail());
        if (mContact.getImgsrc().isEmpty()) {
        } else {
            File imgFile = new File(mContact.getImgsrc());
            if (imgFile.exists()) {Glide.with(this).load(imgFile).into(contactImageView);}
        }
    }
// Action On click Call Button
    public void callTheContactPeople(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mContact.getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }startActivity(intent);
    }
// Action On Click SMS Button
    public void smsTheContactPeople(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("sms:" + mContact.getPhone()));
        startActivity(intent);
    }
// Action On Click Email Button
    public void emailContact(View view) {
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+mContact.getEmail()));
        startActivity(intent);
    }
// Action On Click Delete Button
    public void deleteContact(View view) {
        int id = getIntent().getIntExtra("id",0);
        contactDatabaseManage = new ContactDatabaseManage(this);
        if (contactDatabaseManage.deleteContact(id)){
            Toast.makeText(this, " The Contact Delete Success !!  ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ContactView.this,FrontViewList.class);
            startActivity(intent);
            finish();
        }
    }
// Action On click Edit Button
    public void editContact(View view) {
        int id = getIntent().getIntExtra("id",0);
        Intent intent= new Intent(ContactView.this,UpdateContact.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }


}
