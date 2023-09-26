package com.practice.view_system_practice.room.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.practice.view_system_practice.room.dao.SubscriberDao
import com.practice.view_system_practice.room.entity.Subscriber

@Database(
    entities = [Subscriber::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = SubscriberDatabase.Companion.Migration1To2::class),
        AutoMigration(from = 2, to = 3, spec = SubscriberDatabase.Companion.Migration2To3::class)
    ]
)
abstract class SubscriberDatabase: RoomDatabase() {
    abstract val dao: SubscriberDao

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: SubscriberDatabase? = null

        @RenameColumn(
            tableName = "subscriber_data_table",
            fromColumnName = "subscriber_id",
            toColumnName = "subscriber_id_revised"
        )
        class Migration1To2: AutoMigrationSpec

        @RenameColumn(
            tableName = "subscriber_data_table",
            fromColumnName = "subscriber_name",
            toColumnName = "subscriber_name_revised"
        )
        class Migration2To3: AutoMigrationSpec

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