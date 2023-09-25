package com.practice.view_system_practice.room.repository

import com.practice.view_system_practice.room.database.SubscriberDatabase
import com.practice.view_system_practice.room.entity.Subscriber

class SubscriberRepository(private val database: SubscriberDatabase) {

    val subscribers = database.dao.getAllSubscribers()

    suspend fun insertSubscriber(subscriber: Subscriber) {
        database.dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber) {
        database.dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber) {
        database.dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscribers() {
        database.dao.deleteAll()
    }
}