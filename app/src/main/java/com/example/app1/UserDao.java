package com.example.app1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Insert
    void insertAll(UserInfo user);
}
