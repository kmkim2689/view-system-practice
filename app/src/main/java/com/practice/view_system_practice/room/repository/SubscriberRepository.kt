package com.practice.view_system_practice.room.repository

import com.practice.view_system_practice.room.database.SubscriberDatabase
import com.practice.view_system_practice.room.entity.Subscriber

class SubscriberRepository(private val database: SubscriberDatabase) {

    val subscribers = database.dao.getAllSubscribers()

    suspend fun insertSubscriber(subscriber: Subscriber): Long {
        // 데이터베이스 쿼리를 진행하고, 그 결과까지 리턴
        return database.dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber): Int {
        return database.dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber): Int {
        return database.dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscribers() {
        database.dao.deleteAll()
    }
}