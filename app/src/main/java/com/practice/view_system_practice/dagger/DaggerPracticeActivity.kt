package com.practice.view_system_practice.dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.view_system_practice.R
import javax.inject.Inject

class DaggerPracticeActivity : AppCompatActivity() {

    @Inject
    lateinit var smartPhone: SmartPhone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_practice)

        // 재래식 의존성 주입
/*        val battery = Battery()
        val memoryCard = MemoryCard()
        val serviceProvider = ServiceProvider()
        val simCard = SIMCard(serviceProvider)
        val smartPhone = SmartPhone(
            battery,
            simCard,
            memoryCard
        )
        smartPhone.makeACallWithRecording()

        */


        // dagger의 constructor injection
        /*DaggerSmartPhoneComponent
            .create()
            .getSmartPhone() // SmartPhone 객체 가져옴
            .makeACallWithRecording() // SmartPhone의 멤버 메소드 수행
*/

        // field injection
        // DaggerSmartPhoneComponent.create().inject(this)
        // 활용
        // smartPhone.makeACallWithRecording()

        // dynamic injection
        DaggerSmartPhoneComponent.builder()
            .memoryCardModule(MemoryCardModule(1000)) // 생성자가 있는 모듈
            .build() // build
            .inject(this)
        smartPhone.makeACallWithRecording()
    }
}