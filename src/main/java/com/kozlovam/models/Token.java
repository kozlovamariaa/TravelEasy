package com.kozlovam.models;

import javax.persistence.*;

@Entity
@Table(name = "tokentable", schema = "public")
public class Token {

    public void Token(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenid;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "token")
    private String token;

    public int getId() {
        return tokenid;
    }

    public void setId(int tokenid) {
        this.tokenid = tokenid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
