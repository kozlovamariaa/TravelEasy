package com.kozlovam.models;


import javax.persistence.*;


@Entity
@Table(name = "userstable", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "userlastname")
    private String userlastname;

    @Column(name = "userlogin")
    private String userlogin;

    @Column(name = "userpassword")
    private String userpassword;

    public User(){

    }

    //constructor for login
    public User(String login, String password){
        this.userlogin = login;
        this.userpassword = password;
    }

    //constructor for registration
    public User( String name, String lastname, String login, String password){
        this.userlogin = login;
        this.userpassword = password;
        this.username = name;
        this.userlastname = lastname;
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

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
