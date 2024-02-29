package com.example.cmo_lms.model;

public class LoginResponseModel {
    private String User_Name;
    private String Password;
    private int RegisterID;
    private long mobileNo;
    private int is_activeUser;
    private int officer_role_id;
    private int callcenteruser_id;

    public LoginResponseModel(String user_Name, String password, int registerID, long mobileNo, int is_activeUser, int officer_role_id, int callcenteruser_id) {
        User_Name = user_Name;
        Password = password;
        RegisterID = registerID;
        this.mobileNo = mobileNo;
        this.is_activeUser = is_activeUser;
        this.officer_role_id = officer_role_id;
        this.callcenteruser_id = callcenteruser_id;
    }

    public int getCallcenteruser_id() {
        return callcenteruser_id;
    }

    public void setCallcenteruser_id(int callcenteruser_id) {
        this.callcenteruser_id = callcenteruser_id;
    }

    public int getRegisterID() {
        return RegisterID;
    }

    public void setRegisterID(int registerID) {
        RegisterID = registerID;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int is_ActiveUser() {
        return is_activeUser;
    }

    public void set_ActiveUser(int is_activeUser) {
        this.is_activeUser = is_activeUser;
    }

    public int getOfficer_role_id() {
        return officer_role_id;
    }

    public void setOfficer_role_id(int officer_role_id) {
        this.officer_role_id = officer_role_id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
