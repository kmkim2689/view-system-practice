package com.practice.view_system_practice.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.practice.view_system_practice.R
import org.w3c.dom.Text
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitActivity : AppCompatActivity() {

    // retrofit 객체를 액티비티의 모든 function이 쓸 수 있도록, lateinit으로
    private lateinit var retrofitService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val textView = findViewById<TextView>(R.id.text_view)

        retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        getAlbums(textView)

    }

    private fun getAlbums(textView: TextView) {
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this) {
            // it : retrofit response object we requested
            // able to get an response object. 이 변환 작업은 GSONConverter로부터 이뤄짐
            val albums = it.body()?.listIterator() // to get elements one by one - listIterator
            albums?.let {
                while (albums.hasNext()) {
                    val albumsItem = albums.next()
                    Log.i("RetrofitActivity", albumsItem.title)
                    val result = " album id : ${albumsItem.id}\n" +
                            " album title : ${albumsItem.title}" +
                            " user id : ${albumsItem.userId}\n\n\n"

                    // text가 아닌 append 메소드 활용. text로 설정하면 값 하나하나 가져올 때마다 추가가 아닌 변경이 이뤄짐
                    textView.append(result)
                }
            }
        }
    }
}