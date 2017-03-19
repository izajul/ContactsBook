package com.example.izajul.contactsbook.Model;

public class ContactModel  {
    private String name,email,phone,imgsrc="";
    int id;

    public ContactModel(String name, String email, String phone,int id, String imgsrc) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imgsrc = imgsrc;
        this.id=id;
    }
    public ContactModel(String name, String email, String phone, String imgsrc) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imgsrc = imgsrc;
    }
    public ContactModel(String name, String email, String phone, int id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id=id;
    }
    public ContactModel(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImgsrc() {
        if(imgsrc==null){
            return "";
        }else {
            return imgsrc;
        }
    }

    public int getId() {
        return id;
    }
}
