package com.practice.view_system_practice.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_data_table")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id_revised")
    val id: Int,
    @ColumnInfo(name = "subscriber_name_revised")
    var name: String,
    @ColumnInfo(name = "subscriber_email")
    var email: String
)