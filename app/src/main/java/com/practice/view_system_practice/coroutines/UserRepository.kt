package com.practice.view_system_practice.coroutines

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUsers(): List<User> {
        delay(8000L)
        val users = listOf(
            User(1, "asjdlkf"),
            User(2, "dfhg"),
            User(3, "ncvb"),
            User(4, "iuy"),
            User(5, "kjkl"),
            User(6, "wert"),
            User(7, "yuio"),
            User(8, "dgfhd"),
            User(9, "loiuyiu"),
        )

        return users
    }
}