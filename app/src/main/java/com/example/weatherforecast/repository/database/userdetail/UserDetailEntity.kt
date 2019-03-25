package com.example.weatherforecast.repository.database.userdetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDetailEntity(@PrimaryKey val email: String, val name: String, val password: String)