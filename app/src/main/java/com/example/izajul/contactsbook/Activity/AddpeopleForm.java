package com.example.izajul.contactsbook.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.izajul.contactsbook.DataBase.ContactDatabaseManage;
import com.example.izajul.contactsbook.Model.ContactModel;
import com.example.izajul.contactsbook.R;

public class AddpeopleForm extends AppCompatActivity {

    private String filePath=""; //Declare This for Storinng Image Path
    private static final int PICK_IMAGE = 1; // Contstent for Picking a Image From Gallery
    ImageView userImageSet;
    EditText nameET,emailET,phoneET;
    ImageButton saveIB,cancelIB;
    ContactDatabaseManage contactDBManager;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpeople_form);

        saveIB= (ImageButton) findViewById(R.id.buttonSave);
        saveIB.getBackground().setAlpha(0);
        cancelIB= (ImageButton) findViewById(R.id.buttonCancel);
        cancelIB.getBackground().setAlpha(0);

        userImageSet= (ImageView) findViewById(R.id.userImageSet);

        nameET= (EditText) findViewById(R.id.getUserName);
        emailET= (EditText) findViewById(R.id.getEmailAddress);
        phoneET= (EditText) findViewById(R.id.getPhoneNumber);

        contactDBManager=new ContactDatabaseManage(this);

// Add New Contact To Database
        saveIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:saveIB.getBackground().setAlpha(255);break;
                    case MotionEvent.ACTION_UP:
                        saveIB.getBackground().setAlpha(0);
                        long rownumber=0;
                        if (FilepathIsEmpty() && checkIsEmpty()){
                            ContactModel contact=new ContactModel(nameET.getText().toString(),emailET.getText().toString(),phoneET.getText().toString(),filePath);
                            rownumber = contactDBManager.addContactToBD(contact);
                        }else if (!FilepathIsEmpty() && checkIsEmpty()){
                            ContactModel contact=new ContactModel(nameET.getText().toString(),emailET.getText().toString(),phoneET.getText().toString());
                            rownumber = contactDBManager.addContactToBD(contact);
                        }else {}
                        if (rownumber>0){
                            Toast.makeText(AddpeopleForm.this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddpeopleForm.this,FrontViewList.class);
                            startActivity(intent);
                            finish();
                        }
                }return false;
            }
        });
// Cancel Button
        cancelIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:cancelIB.getBackground().setAlpha(255);break;
                    case MotionEvent.ACTION_UP:cancelIB.getBackground().setAlpha(0);finish();
                }return false;
            }
        });
// Contact Image Get and Set From Gallery
        userImageSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) { openGallery(); }
        });
    }
// OpenGallery Method For Open Gallery By Request
    private void openGallery() {
        Intent openGralleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(openGralleryIntent,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            userImageSet.setImageURI(imageUri);
            userImageSet.getBackground().setAlpha(0);
            filePath = getRealPathFromURI(imageUri);
        }
    }
// Check File Path
    private boolean FilepathIsEmpty(){
        if (filePath.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
// Check The Edit Fields Are Empty Or Not
    private boolean checkIsEmpty(){
        if (nameET.getText().toString().isEmpty()){
            nameET.setError("Empty Field!");return false;
        }else if (emailET.getText().toString().isEmpty()){
            emailET.setError("Empty Field!");return false;
        }else if (phoneET.getText().toString().isEmpty()){
            phoneET.setError("Empty Field!");return false;
        }else return true;
    }
// image path
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        try {
            Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {result = contentURI.getPath();}
            else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx); cursor.close();
            }
        }catch (Exception ex) { throw ex; }
        return result;
    }

}
