package com.example.app1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "users")
public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;

    UserInfo(String email, String password){
        this.email = email;
        this.password = password;
    }

    public int getId() {

        return id;
    }
    public void setId(int id) {

        this.id = id;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}