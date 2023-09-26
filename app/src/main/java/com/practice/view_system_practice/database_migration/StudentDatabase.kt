package com.practice.view_system_practice.database_migration

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.practice.view_system_practice.room.dao.SubscriberDao
import com.practice.view_system_practice.room.database.SubscriberDatabase


@Database(
    entities = [Student::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4, spec = StudentDatabase.Migration3To4::class)
    ]
)
abstract class StudentDatabase: RoomDatabase() {
    abstract val dao: StudentDao

    @RenameColumn(
        tableName = "student_table",
        fromColumnName = "student_course",
        toColumnName = "student_subject"
    )
    class Migration3To4: AutoMigrationSpec

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        StudentDatabase::class.java,
                        "student_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}



/*@Database(
    entities = [Student::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2), AutoMigration(from = 2, to = 3)]
)
abstract class StudentDatabase: RoomDatabase() {
    abstract val dao: StudentDao

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        StudentDatabase::class.java,
                        "student_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}*/

/*
@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDatabase: RoomDatabase() {
    abstract val dao: StudentDao

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        StudentDatabase::class.java,
                        "student_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}*/
/*
@Database(
    entities = [Student::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class StudentDatabase: RoomDatabase() {
    abstract val dao: StudentDao

    // 하나의 인스턴스로 Database 객체를 관리하는 것이 best practice - 예상치 못한 에러를 피하기 위함
    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        StudentDatabase::class.java,
                        "student_db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}*/
