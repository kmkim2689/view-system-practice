package com.practice.view_system_practice.database_migration

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)
}