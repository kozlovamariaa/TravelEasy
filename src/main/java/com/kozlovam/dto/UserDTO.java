package com.kozlovam.dto;

public class UserDTO {
    private int id;
    private String username;
    private String userlastname;
    private String userlogin;

    public  UserDTO(){}

    public UserDTO(int id, String username, String userlastname, String userlogin) {
        this.id = id;
        this.username = username;
        this.userlastname = userlastname;
        this.userlogin = userlogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
}