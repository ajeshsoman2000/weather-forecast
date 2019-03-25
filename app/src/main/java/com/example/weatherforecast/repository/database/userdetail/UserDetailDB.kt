package com.example.weatherforecast.repository.database.userdetail

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDetailEntity::class], exportSchema = false, version = 1)
abstract class UserDetailDB: RoomDatabase() {

    abstract fun userDetailDao(): UserDetailDao

    companion object {
        var INSTANCE: UserDetailDB? = null

        fun getAppDataBase(context: Context): UserDetailDB? {
            if (INSTANCE == null){
                synchronized(UserDetailDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDetailDB::class.java, "userDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}