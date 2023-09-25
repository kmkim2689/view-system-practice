package com.practice.view_system_practice.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.practice.view_system_practice.room.entity.Subscriber

@Dao
interface SubscriberDao {
    // onConflict : 같은 id가 존재하는 경우 어떻게 할지 결정
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber): Long

/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscribers(subscribers: List<Subscriber>): List<Long>*/

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

/*    @Update
    suspend fun updateSubscribers(subscribers: List<Subscriber>)*/

    // 특정 레코드 삭제 시, @Delete로 충분
    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    // query는 컴파일 타임에 검증 -> 잘 동작할 수 있는 쿼리인지 컴파일 타임에 검사
    // 런타임에 쿼리 자체로 인한 오류는 발생하지 않으므로 room의 장점이라고 볼 수 있음
    // 모든 레코드를 삭제하고자 할 때에는, 직접 쿼리를 작성해야 한다.
    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    // 모든 레코드 가져오기
    // 단, 바로 LiveData 형태로 가져오고자 한다. - Room 라이브러리를 활용하면 별도의 코루틴을 개발자가 두지 않아도 됨(room에서 다 해줌)
    // 따라서 이 함수는 background thread에서 수행될 필요가 없고, 그에 따라 suspend 키워드는 생략
    // return 형태가 LiveData/Flow라면, Room 라이브러리가 background thread에서 해당 작업을 수행해주기 때문
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
    // 만약 Flow를 사용하고자 한다면, Flow<List<Subscriber>>
}