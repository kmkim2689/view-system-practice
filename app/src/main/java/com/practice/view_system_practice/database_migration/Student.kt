package com.practice.view_system_practice.database_migration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    val id: Int,
    @ColumnInfo(name = "student_name")
    var name: String,
    @ColumnInfo(name = "student_email", defaultValue = "none")
    var email: String,
    @ColumnInfo(name = "student_subject", defaultValue = "none")
    var course: String?
)
