package com.practice.view_system_practice.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.view_system_practice.room.dao.SubscriberDao
import com.practice.view_system_practice.room.entity.Subscriber

@Database(
    entities = [Subscriber::class],
    version = 1
)
abstract class SubscriberDatabase: RoomDatabase() {
    abstract val dao: SubscriberDao

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: SubscriberDatabase? = null
        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        SubscriberDatabase::class.java,
                        "subscriber_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}