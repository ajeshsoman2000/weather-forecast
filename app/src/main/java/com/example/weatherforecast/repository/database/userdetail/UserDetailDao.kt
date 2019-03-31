package com.example.weatherforecast.repository.database.userdetail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserDetailEntity)

    @Query("SELECT * from user_table WHERE email = :emailID")
    fun getUser(emailID: String): UserDetailEntity
}