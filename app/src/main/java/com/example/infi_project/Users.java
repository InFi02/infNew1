package com.example.infi_project;

import java.util.Vector;

public class Users {



    String userName;
    String userPhone;
    String userEmail;
    String dateofBirth;
    String rollno;
    String pass;
    boolean choiceSelected;
    public Vector userInterest=new Vector();
    public  int totalNoOfInterest;
    public String url;

    public Users() {
    }

    public Users(String userName, String userPhone, String userEmail, String dateofBirth, String rollno, String pass, boolean choiceSelected, Vector userInterest,int totalNoOfInterest,String url) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.dateofBirth = dateofBirth;
        this.rollno = rollno;
        this.pass = pass;
        this.choiceSelected=choiceSelected;
        this.userInterest=userInterest;
        this.totalNoOfInterest=totalNoOfInterest;
        this.url=url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isChoiceSelected() {
        return choiceSelected;
    }

    public void setChoiceSelected(boolean choiceSelected) {
        this.choiceSelected = choiceSelected;
    }

    public Vector getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(Vector userInterest) {
        this.userInterest = userInterest;
    }

    public int getTotalNoOfInterest() {
        return totalNoOfInterest;
    }

    public void setTotalNoOfInterest(int totalNoOfInterest) {
        this.totalNoOfInterest = totalNoOfInterest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
