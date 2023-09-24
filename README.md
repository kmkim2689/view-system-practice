# Android View System Practice

## 1. DataBinding

### 1.1. Databinding을 활용한 간단한 예시

* TextView, EditText, Button으로 구성

  * EditText에 이름을 입력하고 Button을 클릭 시, TextView에 인사를 띄우는 작업

* findViewById()란?
  
  * xml에서 정의한 View를 Activity/Fragment에서 참조하기 위하여 사용하는 메소드
  
  * xml에서 정의한 View의 Id를 활용하여 접근
    `val button = findViewById<Button>(R.id.submit_button)`
  
  * 동작 원리
  
    * findViewById() 함수를 활용하여 View에 대한 참조를 실시할 때
    
      * 안드로이드 시스템은 런타임에 View들의 계층구조 속에서 해당 View를 찾아낸다
      
      * 문제점
        * 애플리케이션의 규모가 커질수록 무수히 많은 Layout과 View가 존재하게 되는데, 런타임동안 View의 거대한 계층구조 속에서 원하는 View를 찾는 것은 비효율적
        * 즉, 애플리케이션의 규모가 커질수록 해당 View를 찾는 데 많은 시간과 자원이 소모되는 만큼 성능 저하가 필연적
        
* DataBinding이란?

  * 동작 원리

    * "레이아웃" 단위로, 각 View들에 대한 참조를 담고 있는 Binding Object를 생성

    * Binding Object가 생성되면
    
      * 애플리케이션의 모든 컴포넌트들은 Binding Object를 통해 View와 Data에 접근 가능
      
    * 이러한 방식은 안드로이드 시스템이 View를 찾기 위하여 계층구조를 살펴볼 필요가 없도록 한다.
    
      * 따라서, findViewById를 사용할 때에 비하여 애플리케이션의 성능을 대폭 향상시킬 수 있게 된다.
      * 뿐만 아니라, findViewById에 비하여 더욱 코드를 간결하게 만들며, 읽기 쉽고 유지보수성을 높인다.
      * DataBinding 방식의 Data Binding Object 생성은 compile time에 이뤄진다는 것이 큰 장점이다.
      
  * 사용 방식
    
  1. gradle에서 dataBinding을 enable시킨다.
  
    ```
    android {
      // ...
      dataBinding {
        enabled = true
      }
    }
    ```
  
  2. xml 레이아웃에 대한 Binding Class를 생성하기 위하여, 레이아웃의 모든 요소들을 <layout> 태그로 감싸야 한다.

    * 이 과정에서, namespace declaration은 가장 바깥의 태그에 존재해야 함에 유의  

    ```
    <?xml version="1.0" encoding="utf-8"?>
    <layout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools">
  
      <androidx.constraintlayout.widget.ConstraintLayout
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          tools:context=".MainActivity">
  
          <TextView
              android:id="@+id/tv_greeting"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              app:layout_constraintStart_toStartOf="parent"
              app:layout_constraintEnd_toEndOf="parent"
              app:layout_constraintTop_toTopOf="parent"
              app:layout_constraintBottom_toTopOf="@id/edit_name"/>
  
          <EditText
              android:id="@+id/edit_name"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:layout_marginStart="50dp"
              android:layout_marginEnd="50dp"
              app:layout_constraintBottom_toTopOf="@id/btn_submit"
              android:layout_marginBottom="20dp"
              android:autofillHints="fill here"
              android:inputType="text" />
  
          <Button
              android:id="@+id/btn_submit"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              app:layout_constraintTop_toTopOf="parent"
              app:layout_constraintBottom_toBottomOf="parent"
              app:layout_constraintStart_toStartOf="parent"
              app:layout_constraintEnd_toEndOf="parent"
              android:text="@string/submit"/>
  
      </androidx.constraintlayout.widget.ConstraintLayout>
    </layout>
  
    ```
  
    * layout 태그로 감싼 레이아웃의 경우, 안드로이드 DataBinding 라이브러리는 Binding Object를 생성한다.
  
      * 이 과정에서 Binding Object의 이름은 다음과 같이 생성된다.
      
        * xml 파일의 이름 : activity_main -> Binding Object 이름 : ActivityMainBinding
        * underscore를 기준으로 각 단어의 첫 글자를 대문자로 만들고, underscore를 제거한 후 뒤에 Binding을 붙임

  3. Activity 클래스에서 databinding object 생성하여 사용

  * binding object에 대한 Reference Variable 생성하기
  
    `private lateinit var binding: ActivityMainBinding`

  * setContentView()란?
  
    * 스크린에 레이아웃을 렌더링하기 위한 함수
    
    * 개발자가 매개변수로 삽입한 xml 레이아웃 파일에 기반하여, 레이아웃 내부의 View들은 inflate되고 렌더링될 것
    
    * databinding에서는 이 함수를 사용하지 않고, binding object를 활용하여 같은 작업을 효율적으로 수행
    
      * DataBindingUtil의 setContentView() 함수를 사용
  
  * Databinding Object를 생성(앞에서 정의한 lateinit 변수를 초기화)
  
    * 매개변수 : activity, layout
  
      `binding = DataBindingUtil.setContentView(this, R.layout.activity_main)`
  
  * 이렇게 되면, DataBinding Object는 해당 xml(여기서는 activity_main)의 모든 View들에 대해 '속성'으로 참조 가능
  
    * dot operator로 참조 가능 => `binding.viewId`
    
    * property name이 만들어지는 원리는 DataBinding Object의 이름의 원리와 동일하지만, 첫 글자는 소문자라는 차이점이 있음
    
    * 주의점 : xml 파일에서 id를 camal case로 정의한다면, DataBinding Object는 해당 뷰를 속성으로 받아들이지 못한다. 따라서 반드시 snake case로 지정
    
* 최종 코드

    ```
    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    
            val button = binding.btnSubmit
    
            button.setOnClickListener {
                displayGreeting(binding.editName.text)
            }
        }
    
        private fun displayGreeting(text: Editable) {
            binding.apply {
                tvGreeting.text = text
            }
        }
    }
    ```
  
### 1.2. Databinding을 활용한 객체 바인딩
* 많은 경우에 UI에 특정 정보를 띄우기 위하여 다음과 같은 방법을 사용

  * 로컬 데이터베이스
  * 원격 API

* 이 두 경우의 공통점은, Object 형태로 데이터를 받는다는 것이다.

* DataBinding을 활용한다면, 이렇게 받은 정보들을 직접적으로 레이아웃(xml)으로 전달시킬 수 있다.

* 그렇게 함으로써 코드량을 줄일 수 있을 뿐만 아니라, 코드의 가독성도 높일 수 있다.

* ObjectBindingActivity.kt

  * 서버로부터 Student라는 객체 형태로 데이터를 GET 한다고 가정 => getStudent()
  
  * 1.1.에서 알아보았던 대로라면, binding 객체에서 직접 view들을 참조하여 각 view들의 값을 설정해주는 작업을 수행해야 한다.
  
  ```
  class ObjectBindingActivity : AppCompatActivity() {
      private lateinit var binding: ActivityObjectBindingBinding
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = DataBindingUtil.setContentView(this, R.layout.activity_object_binding)
          
          val student = getStudent()
          
          binding.apply { 
              tvName.text = student.name
              tvEmail.text = student.email
          }
      }
  
      private fun getStudent(): Student {
          return Student(1, "Alex", "alex@gmail.com")
      }
  }

  data class Student(
      val id: Int,
      val name: String,
      val email: String
  )
  ```
  
  * 하지만, 가져온 객체의 property의 값을 하나하나 이용하여 binding 객체의 property에 일일이 값을 설정해주는 것은 매우 귀찮은 일
  
  * DataBinding을 활용한다면, Student 객체를 직접적으로 xml에 바인딩할 수 있음 => viewBinding 대신 dataBinding을 활용하는 것의 가장 큰 강점
  
* Data class를 비롯한 객체를 직접 xml에 바인딩 하는 방법

1. 레이아웃에서 특정 객체에 대한 참조를 추가

* xml 파일에서 <layout> 태그 안에 <data> 태그를 추가

  * 여기 안에서 레이아웃에서 사용할 객체들을 정의 => <variable>
  
  * variable -> 특정 객체를 변수로 사용하기 위함
  
    * name -> 변수명에 비유 가능
    
    * type -> 자료형에 비유 가능
      * package name을 포함한 경로
  
  ```
  <data>
        <variable
            name="student"
            type="com.practice.view_system_practice.Student" />
  </data>
  ```

2. 이를 활용하여 Student의 property를 직접적으로 View에 넣을 값으로 할당 가능

* name을 통하여 참조

  ```
  <layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="student"
            type="com.practice.view_system_practice.Student" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ObjectBindingActivity">

        <TextView
            android:id="@+id/tv_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{student.name}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tv_email"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{student.email}"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_name" />

    </androidx.constraintlayout.widget.ConstraintLayout>
  </layout>
  ```
  
3. Activity 파일에서 DB/API로부터 받은 student 객체를 xml 레이아웃으로 보내기 위한 작업을 수행

* BindingObject.xml에서 정의한 variable의 name 형태로
  ```
  class ObjectBindingActivity : AppCompatActivity() {
      private lateinit var binding: ActivityObjectBindingBinding
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = DataBindingUtil.setContentView(this, R.layout.activity_object_binding)
          binding.student = getStudent()
  
      }
  
      private fun getStudent(): Student {
          return Student(1, "Alex", "alex@gmail.com")
      }
  }
  
  data class Student(
      val id: Int,
      val name: String,
      val email: String
  )
  ```
  
-- -- --

## 2. ViewModel

### 2.1. ViewModel을 왜 사용해야 하는가?

```
class VmBasicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmBasicBinding
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_basic)

        binding.apply {
            tvCount.text = count.toString()
            btnCount.setOnClickListener {
                count++
                tvCount.text = count.toString()
            }
        }
    }
}
```

* 이 코드의 문제점 => User Experience를 크게 해친다.

  * Configuration Change를 고려하지 않았다.
  
    * 화면을 회전하거나, 키보드 변경, 언어를 변경하거나, 멀티윈도우를 실행시키거나, 다크모드로 바꾸는 등의 동작
    
    * 액티비티는 Destroy 되고 다시 Create된다.
    
    * 이 과정에서, Configuration Change가 발생하기 이전의 값을 온전히 보전하지 못하고 초기 상태로 돌아가는 문제가 발생
    
  * 따라서 이러한 Configuration Change가 발생하여도 Activity의 생명주기에 관계 없이 독립적으로 동작하는 곳이 필요


* 이를 해결하기 위하여, ViewModel을 사용한다.

  * A Model class for View - Activity/Fragment에 속하는 값들을 보관하기 위한 목적으로 사용
  
  * UI와 관련된 데이터들을 두고 관리하기 위한 목적으로 사용
  
  * 주로 하나의 Activity/Fragment 당 하나의 ViewModel을 활용. 다만 하나의 ViewModel를 여러 개의 Activity/Fragment들과 공유하기도 함
  
  * Activity가 Create되고 나서 메모리로부터 Clear(ViewModel의 onClear())될 때까지 유지된다.
  
    * Activity가 Destroy 되어도 메모리에 유지된다.

* cf) AAC의 ViewModel과 MVVM ViewModel의 차이점은?

  * AAC ViewModel은 Activity/Fragment 클래스의 생명주기에 관계 없이(ViewModel이 onClear되지 않는 한) 지속되어야 할 데이터를 담기 위해 사용되는 추상 클래스
    * configuration change에도 Activity/Fragment에서 사용할 데이터를 유지하기 위한 목적이 큼
    * AAC의 ViewModel은 하나의 View에 대해서 종속(싱글톤)
      * 그렇다고 이것이 하나의 View에 한 종류의 ViewModel만 있어야 한다는 의미는 아님
      * 한 종류의 ViewModel이 하나의 액티비티에 존재할 때, 적어도 한 액티비티 내에서는 Singleton으로 존재해야 한다는 의미.
      
  * MVVM의 ViewModel은 Model과 View 사이에서 데이터를 관리하고 바인딩 하는 역할
    * 비즈니스 로직을 담당
      * viewmodel은 view와 model을 소유. model을 소유하면서 갱신도 가능
        * 비즈니스 로직과 UI 로직을 분리하기 위한 목적이 가장 크다.
        * 데이터 처리에 관한 로직들은 ViewModel에서 담당하고,
        * Ui 계층에서는 Data의 변화를 ViewModel로부터 통지받아(observe) 통지받은 대로 값을 바꿀 수 있도록 한다.
        * 이렇게 해서 UI에서는 데이터 처리에는 관심을 가지지 않고 UI 작업에만 관심을 가질 수 있도록 함
    * ViewModel에서 특정 View에 대한 참조가 이뤄져서는 안된다.
      * 하나의 ViewModel은 여러 View에 대해서 사용될 수 있어야 하기 때문
      
  * AAC의 ViewModel을 MVVM의 ViewModel처럼 사용하려면?
    * DataBinding과 LiveData를 사용하는 것이 가장 일반적인 방법
      * UI에서 사용할 ViewModel 내부의 Data의 변화에 대한 사항을 observe하여, 그 값이 바뀌면 그에 따라 UI에 반영할 수 있게 됨

### 2.2. ViewModel의 사용법

1. dependency 추가
```
    def lifecycle_version = "2.5.1"

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
```

2. ViewModel을 확장하는 클래스를 만든다.

cf) ViewModel이 아닌 AndroidViewModel을 확장하는 경우가 있음

* AndroidViewModel의 경우, Application 인스턴스를 생성자에 추가

* ViewModel 코드에서 context를 사용해야 하는 경우 유용하다.

```
import androidx.lifecycle.ViewModel

class VmBasicViewModel: ViewModel() {
    private var count = 0

    // 현재의 값을 리턴하기 위한 function
    fun getCurrentCount(): Int {
        return count
    }

    // 현재의 값에서 1을 추가하고 그 값을 return하는 function
    fun getUpdatedCount(): Int {
        return ++count
    }
}
```

3. Activity 코드에 viewModel 인스턴스를 초기화 시킨다.

* ViewModelProvider(context).get(ViewModel클래스명)를 이용

```
viewModel = ViewModelProvider(this).get(클래스명::class.java)
```

* 다른 방법 : ktx를 이용한다.

  * 액티비티와 프래그먼트를 위한 dependency 추가
  
  ```
  implementation 'androidx.activity:activity-ktx:1.3.1'
  implementation 'androidx.fragment:fragment-ktx:1.3.1'
  ```
  
  * 액티비티에서 인스턴스 초기화
  
  ```
  private val viewModel: MainActivityViewModel by viewModels()
  ```

* 최종 코드

```
class VmBasicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmBasicBinding
    private lateinit var viewModel: VmBasicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_basic)
        viewModel = ViewModelProvider(this).get(VmBasicViewModel::class.java)
    
        binding.apply { 
            tvCount.text = viewModel.getCurrentCount().toString()
            btnCount.setOnClickListener { 
                tvCount.text = viewModel.getUpdatedCount().toString()
            }
        }

    }
}

```

* ViewModel에 Constructor를 넣어야 하는 경우라면?

  * ViewModelFactory 클래스를 이용해야 함
  
  * Factory 클래스를 하나 만들고, 이 클래스가 ViewModelProvider.Factory를 구현하도록 해야 한다.
  
  ```
  class VmAccViewModelFactory: ViewModelProvider.Factory {
  
  }
  ```
  
  * 이렇게 하면, viewmodel에 생성자를 추가 가능
  * 
  ```
  class VmAccViewModel(startingTotal: Int): ViewModel() {
    private var count = 0

    init {
        count = startingTotal
    }
    
    fun getCountData(): Int {
        return count
    }

    fun getUpdatedData(num: Int) {
        count += num
    }
  }
  ```

  * ViewModel에서 추가한 생성자를 사용하기 위하여, Factory 클래스의 생성자로 ViewModel과 같은 생성자를 추가하고, create 메소드를 override 해야 한다.
  * 
  ```
  class VmAccViewModelFactory(private val startingTotal: Int): ViewModelProvider.Factory {

    // create function to return the viewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VmAccViewModel::class.java)) {
            return VmAccViewModel(startingTotal) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
  }  
  ```
  
  * Activity에서 Factory Instance를 만들어 ViewModel을 사용한다.
  ```
  viewModelFactory = VmAccViewModelFactory(15)
  viewModel = ViewModelProvider(this, viewModelFactory).get(VmAccViewModel::class.java)
  ```

### 2.3. ViewModel의 onCleared()는 언제 호출되는가?
* ViewModel이 더 이상 필요해지지 않는 시점에 호출된다.
  * 앱이 백그라운드 상태가 되고 시스템 메모리 관리 상의 이유로 앱 프로세스가 kill될 때
  * 또한 유저가 명백하게 액티비티 상에서 finish() 코드를 호출하거나 뒤로가기 버튼을 누를 때 때에도 onCleared()가 호출

-- -- --
## 3. LiveData

### 3.1. LiveData의 용도

* LiveData란?

  * Lifecycle을 인식하는 Observable한 Data Holder 클래스
  * 생명주기를 인식한다는 것이 특징 : 생명주기 동안에만 observer에게 변경되는 값을 알려줌
    * 이것의 큰 장점으로 작용. 생명주기를 인식하기 때문에 RxJava와 같은 다른 반응형 프로그래밍을 위한 라이브러리를 사용할 때 activity/fragment/service의 observer를 별도로 처리 할 필요가 없어진다는 것
  * 안드로이드에서 Lifecycle과 관련된 세 가지의 클래스들 => 아래의 클래스들은 LiveData Object에 대한 관찰자가 될 수 있음
    * Activity
    * Fragment
    * Service

* Activity/Fragment에서 ViewModel 상의 데이터를 관찰하기 위한 코드를 작성할 수 있는데, 바로 LiveData를 이용하는 것이다.

* 만약 ViewModel 상의 데이터에 변화가 발생하였다면, LiveData를 활용하면 Activity나 Fragment의 UI를 자동으로 업데이트 할 수 있다.

  * 수동으로 특정 view를 참고하여 직접 값을 설정할 필요가 없어짐 : up-to-date data 유지

* 생명주기를 다루기 위한 코드들을 수동으로 작성할 필요가 없다.
  * 관련된 생명주기를 가진 클래스가 destroy된다면, 스스로 clean up 작업을 수행
  * 따라서, 메모리 누수 여부를 고려하지 않아도 됨
  
### 3.2. LiveData 사용하기

1. ViewModel에서는 Activity/Fragment에서 관찰할 변수를 MutableLiveData와 LiveData로 정의한다.

cf) LiveData vs MutableLiveData
* LiveData 객체 내부에 있는 데이터는 읽기만 가능하며, 수정이 불가능하다.
* MutableLiveData는 LiveData의 하위 클래스로, 데이터를 바꾸는 것이 가능하다.

2. MutableLiveData에 값을 할당해준다.

* 방법
  * mutableLiveData명.value = 값

* 그 결과, viewModel에서는 더 이상 데이터를 얻어오기 위한 함수가 필요 없어진다.

```
class VmAccViewModel(startingTotal: Int): ViewModel() {
    private var _count = MutableLiveData<Int>()
    val count = _count.value

    init {
        _count.value = startingTotal
    }

/*    fun getCountData(): Int {
        return count
    }*/

    fun updatedData(num: Int) {
        _count.value = _count.value?.plus(num)
    }
}
```

3. 해당 LiveData를 사용할 Activity/Fragment/Service에서, LiveData를 observe 하기 위한 코드를 작성한다.

```
viewModel.count.observe(this, Observer { 
    binding.tvCount.text = it.toString()
})
```
여기서...
* 첫 번째 parameter : lifecycleOwner -> 액티비티에 대한 라이프사이클을 감지해야 하므로 액티비티 context를 집어넣음
* 두 번째 parameter : Observer 클래스 정의 -> 값이 바뀌는 것을 관찰한 순간 해야 할 일을 onChanged block 안에 정의


### 3.3. viewModel을 xml layout에 직접 연결하기

* 버튼을 클릭하였을 때의 동작을 Activity 코드에서 정의할 필요가 없고, xml에서 정의

  * Activity에서 button에 대한 onClickListener를 설정할 필요가 없다.
  * databinding을 활용하면 된다.

1. activity layout에서 viewModel을 활용하기 위하여 data를 정의
```
    <data>
        <variable
            name="viewModel"
            type="com.practice.view_system_practice.VmAccViewModel" />
    </data>
```

2. activity에서 xml에서 사용할 viewModel을 할당해주기
```
binding.viewModel = viewModel
```

3. layout xml 파일에서 특정 뷰(여기서는 버튼)에 대한 onClick 속성을 정의한다.

  * 방법

    * Button에 onClick 속성을 추가한다.
    * @와 {}를 활용
    * {} 안에는 익명의 함수를 arrow function으로 정의한다.
    ```
    <Button
            android:id="@+id/btn_count"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintStart_toStartOf="@id/tv_count"
            app:layout_constraintTop_toBottomOf="@id/tv_count"
            android:onClick="@{() -> viewModel.updatedData()}"/>
    ```
    
* 이렇게 함으로써, 최종 코드는 다음과 같이 button에 대한 onClickListener를 제거할 수 있다.
```
class VmAccActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmAccBinding
    private lateinit var viewModel: VmAccViewModel
    private lateinit var viewModelFactory: VmAccViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_acc)

        viewModelFactory = VmAccViewModelFactory(15)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VmAccViewModel::class.java)
        binding.viewModel = viewModel

        // live data
        viewModel.count.observe(this) {
            binding.tvCount.text = it.toString()
        }
    }
}
```
    
* 주의 : 이것을 사용하기 위해서는 gradle 버전이 2.0 이상이어야 한다.

### 3.4. LiveData Object를 layout xml에 직접 연결하기

* 이것을 활용하면, activity 단에서 특정 LiveData를 Observe할 필요가 없어진다.
* xml에서 viewModel의 LiveData를 직접 읽어오는 작업을 진행하면 된다.
* 만약 자료형을 바꾸고 싶다면, 바꾸고 싶은 자료형.valueOf(이전 자료형)을 넣으면 된다.

```
<TextView
    android:id="@+id/tv_count"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="36dp"
    android:layout_marginTop="200dp"
    android:text="@{String.valueOf(viewModel.count)}"
    android:textSize="52sp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/et_count" />
```

* 이 작업은 viewModel의 livedata를 직접 사용한 것으로, activity 코드에서 진행하였던 observe() 함수를 사용하는 과정이 포함되어 있지 않는다.
  * 즉, lifecycle owner를 지정해줌으로써 livedata가 lifecycle을 인식할 수 있도록 하는 과정이 생략되어있음.
  * LiveData는 항상 생명주기와 관련되어있기 때문에, viewModel object에 대한 lifecycle owner를 설정해주는 것이 반드시 필요

```
class VmAccActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVmAccBinding
    private lateinit var viewModel: VmAccViewModel
    private lateinit var viewModelFactory: VmAccViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vm_acc)
        // viewmodel 생성자가 없는 경우
        // viewModel = ViewModelProvider(this).get(VmAccViewModel::class.java)

        // viewmodel 생성자가 있는 경우
        viewModelFactory = VmAccViewModelFactory(15)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VmAccViewModel::class.java)
        binding.viewModel = viewModel
        // *** viewmodel에서 사용할 livedata들이 생명주기를 인식할 수 있도록 하는 작업
        binding.lifecycleOwner = this
    }
}
```

### 3.5. 양방향 DataBinding

* 지금까지의 내용을 바탕으로, LiveData를 활용하면 실시간으로 변경되는 값을 자동적으로 UI에 값을 변경할 수 있음을 알게 되었다.

* 양방향 DataBinding?

  * 앞에서는 Object의 값이 변경될 때 UI의 값이 변경되는 것을 다루었음(단방향 데이터 바인딩)
  * 양방향 DataBinding은 여기에 더하여 UI의 값이 변경될 때, 연관된 Object의 값을 변경하기 위해 사용
  
* 양방향 데이터 바인딩을 구현하기 위하여, EditText를 하나 구현

  * 주의 : viewModel에서 livedata를 정의할 때 MutableLiveData로 정의해야 하며, 해당 MutableLiveData 변수가 private이 되어서는 안된다.
  `val userName = MutableLiveData<String>()`

  * EditText의 값에 따라 자동적으로 viewModel의 LiveData값이 변경되도록 하는 방법
  ```
  <EditText
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:textSize="36sp"
      app:layout_constraintBottom_toTopOf="@id/tv_count"
      android:text="@{viewModel.userName}" />
  ```
  
  * 이러한 방식으로 단방향 데이터 바인딩과 같은 방법을 사용하면, edittext의 변화가 livedata에 반영되지 않는다.

  * 입력 사항을 livedata에 반영한다는 의미에서, **@={}** 사인을 사용한다.

  ```
  <EditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="36sp"
    app:layout_constraintBottom_toTopOf="@id/tv_count"
    android:text="@{viewModel.userName}" />
  ```
  
-- -- --

## 4. Lifecycle

### 4.1. Activity Lifecycle Transition Example

* 어떤 Activity를 띄울 때(메모리에 올라갈 때),
  * onCreate -> onStart -> onResume 순으로 빠르게 호출
  * 액티비티가 Foreground에서 아무런 제약없이 화면이 보이고 동작하는 동안은 Activity Running 상태
  * 그러다가, 다른 Activity가 띄워지게 되면, 이전 Activity는 일부분 이상이(여기서는 전체) 시야에서 사라지게 되므로 onPause가 호출
  * 그 후 다른 Activity의 onCreate -> onStart -> onResume 순으로 호출되고 나서야 완전히 이전 액티비티는 Foreground에서 사라진 것을 확신할 수 있어 이 시점에서 onDestroy가 호출
    * **onStop() 상태에서는 UI가 보이지 않을 뿐, 여전히 액티비티는 메모리에 남아있다는 것에 주의해야 한다.**
    
  * 액티비티에서 onDestroy()가 호출되는 경우
    * 뒤로가기 버튼을 누르거나
    * finish()를 호출하거나
    * Configuration Change가 발생하였을 경우
      * 특히, Configuration Change가 발생하는 경우, onDestroy()까지 호출된 후 다시 onCreate()부터 호출된다.
    
  * 따라서, 새로 호출한 Activity에서 뒤로가기 버튼을 누르는 경우, 두 번째로 호출한 Activity는 onPause() 상태로 전이 되었다가,
    * 이전 Activity가 onStop() 상태에서 다시 호출되었으므로 onRestart -> onStart -> onResume으로 다시 Running 상태로 들어가고,
    * 그때 이후에 두번째로 호출한 Activity는 onStop -> onDestroy까지 호출됨으로써 메모리로부터 해제된다.

### 4.2. Fragment Lifecycle

* fragment : 액티비티(UI)에서 일부분 재사용될 수 있는 장소 => sub activity component
* 액티비티에서처럼, 별도의 레이아웃과 생명주기를 가지며 사용자와 상호작용도 가능
  * 하지만, Fragment는 독립적으로 존재할 수 없다는 점이 가장 큰 특징
  * 반드시 Host Activity 혹은 다른 Fragment에 속해 있어야 한다.

* Fragment의 lifecycle 중, onCreateView에서 ui 관련 객체들을 초기화하는 작업들(binding, viewmodel)과 클릭 이벤트 등을 정의한다.
  * onCreate()에서는 Fragment가 생성되는 때이지, Fragment에 속해있는 View들이 생성되는 때가 아니기 때문
  * Fragment와 Fragment의 View들의 lifecycle은 별도임을 유의

-- -- --

## 5. Navigation

### 5.1. Jetpack Navigation

* jetpack navigation architecture components library
  * 앱에서 다뤄야 할 navigation을 위해 필요한 모든 것들을 다룰 수 잇는 도구들이 마련되어 있어 종전의 방식보다 쉽게 navigation 구현 가능

* 최근의 안드로이드 앱개발의 트렌드 : **Single Activity, Multiple Fragments**
  * Navigation Architecture Component를 사용함으로써, 하나의 빈 Activity를 Basement로 사용
  * 다른 화면들은 모두 Fragment로 구현

* Navigation 3대 구성요소
  * NavigationGraph
    * navigation과 관련된 모든 정보들을 담고 있는 xml 파일
    * Navigation Graph를 활용하여, 한 xml 파일에서 navigation 관련 task들을 관리 가능
  * NavHost(Fragments)
    * Navigation Graph를 가지고 있는 Container로, Activity에서 관리.
    * 일종의 비어있는 Container 역할
    * Container 내부에 Navigation Graph 경로의 설정에 따라 Fragment를 갈아끼우거나 추가하거나 제거하는 식으로 화면을 전환
  * NavController
    * Navigation Graph에 추가한 destination들 간 navigation을 관리하기 위한 클래스
    * navigate() 등의 함수를 활용하여 화면 이동을 지시

### 5.2. Navigation Component 사용하기

1. dependency로 다음과 같이 추가

```
  def nav_version = "2.5.3"

  // Kotlin
  implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
  implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
```

2. Fragment 간 Data를 주고받고자 할 때, safe args를 사용해야 함. 따라서 아래의 dependency도 추가

* project 수준 build.gradle
```
buildscript {
    ext {
        nav_version = '2.5.3'
    }
    dependencies {
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
    }
}
```

* module 수준
```
plugins {
    id 'androidx.navigation.safeargs.kotlin'
}
```

3. Navigation Graph 생성

* Navigation Graph는 애플리케이션에서 표출해야 할 모든 화면들(Fragment)을 표출하며
* 어떻게 유저가 각 스크린에 도달하는지에 대한 정보를 담고 있음
* 한 파일을 활용하여 navigation 관련 action들을 관리할 수 있도록 한다.

* Navigation Editor에서 생성 가능(xml 코드로도 확인 가능하며, 시각적으로도 확인 가능)
  * Android Studio 좌측의 Resource Manager > Navigation 탭 > 좌측 상단의 + 버튼
  * Android Studio가 res 폴더 내부에 navigation 폴더를 생성하여 그 안에 navigation graph xml 파일을 생성한다.
  * 파일명은 nav_graph로 한다.

4. Navigation Host Fragment 생성

> navigation graph를 생성하고, 디자인 모드에서 좌측 상단의 Host 탭을 보면 다음과 같은 문구를 확인 가능
> "No NavHostFragments Found"

* Navigation Graph를 Host하고 화면들을 담기 위한 Fragment
* Navigation Graph만을 생성해서 화면들의 이동을 구현할 수는 없다. 어떻게 이동할 지만 정해놓았기 때문.
  * Navigation Graph는 Host Fragment를 통해 다른 스크린과 상호작용
* 이동할 화면들을 Host, 즉 Container 역할을 할 Fragment가 별도로 필요한데 이것이 바로 Navigation Host Fragment
  * Host Fragment의 고정된 크기 안에서 여러 화면들을 전환

* Basement로 삼을 Activity xml 파일로 이동하여, NavHostFragment를 추가한다.
  * FragmentContainerView 태그를 활용
  * activity 내부에 fragment를 생성하고, 그 fragment를 navGraph와 연결(navGraph 속성 활용)
  ```
  <androidx.fragment.app.FragmentContainerView
       android:id="@+id/nav_host_fragment"
       android:name="androidx.navigation.fragment.NavHostFragment"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       app:defaultNavHost="true"
       app:navGraph="@navigation/nav_graph" />
  ```
  * 그 결과, nav_graph.xml로 이동하면 Host로 activity가 추가된 것을 확인 가능

5. Nav Graph에 화면(Fragment)들을 추가

* 좌측 상단의 + 버튼 > fragment 추가
* 첫 번째 화면을 추가하면, graph에 화면이 나타나고 fragment명 좌측에는 홈 모양의 아이콘이 함께 나타남
  * start destination이라는 의미로, 이는 쉽게 바꿀 수 있음(위의 홈 모양 버튼을 통해)
  * 첫번째 Fragment인 HomeFragment에 EditText와 Button을 넣고, Databinding을 위하여 다음과 같이 설정
    * fragment_home.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"        
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
    
        </data>
        <androidx.constraintlayout.widget.ConstraintLayout 
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".navigation.HomeFragment">
    
            <EditText
                android:id="@+id/et_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintBottom_toTopOf="@id/btn_submit"
                android:layout_marginHorizontal="36dp"
                android:textSize="36sp" />
    
            <Button
                android:id="@+id/btn_submit"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@id/et_text"
                app:layout_constraintBottom_toBottomOf="parent"
                android:text="@string/submit"/>
    
    
    
        </androidx.constraintlayout.widget.ConstraintLayout>
    </layout>
    ```
    
    * HomeFragment.kt
    ```
    class HomeFragment : Fragment() {
        private lateinit var binding: FragmentHomeBinding
            override fun onCreateView(
                inflater: LayoutInflater, 
                container: ViewGroup?,savedInstanceState: Bundle?
            ): View? {
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
                return binding.root
            } 
    }
    ```
    
  * 같은 방식으로, 다른 fragment들도 만든다.

6. Fragment 간 이동을 구현한다.

* Fragment간 이동은 <action>으로 구현한다!

* navigation graph xml 파일을 text mode로 전환 시,
  * **<navigation>** 태그가 가장 상위에 존재
    * 여기서 startDestination이 정의됨을 알 수 있음
      * 하위 <fragment>의 id로 정의
  * **<navigation>** 태그 내부에는, **<fragment>** 태그들이 존재
    * base activity xml 파일에서 정의한 <FragmentContainerView> 내부에 나타날 수 있는 화면들을 정의한 것
    
* 하지만, 내부에 **<fragment>**만 정의한다고 해서, 화면 간 이동을 구현할 수는 없다.
  * 화면에서 다른 화면으로 이동시키는 것은 <action>으로 정의한다.

* 방법

  * design 모드로 이동하여,
    * 시작 fragment의 오른쪽 circle을 끌어당겨 도착 fragment에 놓는다.
    * 이렇게 하면, 시작 화면에서 도착 화면으로 이동이 가능함을 정의한 것이다.
    * 그 결과로, text 모드로 보면 action이 생성되었고, 그에 대한 action id 역시 생성됨을 알 수 있다.
      * "시작" fragment 태그 하위에 action 태그가 발생하고 속성으로 id와 destination(도착 fragment id)이 지정됨을 알 수 있다.
    
  * Fragment Class 파일에서 Fragment의 이벤트와 만들어 놓은 action을 연결함으로써 화면 이동을 구현한다.
    * view를 통하여 navController 객체를 가져와, navController 객체의 navigate(action id) 함수를 활용하여 화면 이동을 구현한다.
  
  ```
  binding.btnSubmit.setOnClickListener { 
      // nav graph에서 정의한 action을 연결하기 위하여, navController 객체를 이용해야 한다.
      // 매개변수인 view(it)를 통해 navController를 찾아올 수 있음
      // navController의 navigate()함수를 통하여 화면 이동을 구현
      // findNavController() 메소드는 fragment가 navHostFragment 내부에 있을 때에만 호출되어야 하며, 만약 그렇지 않는다면 예외 발생 - IllegalStateException
      // 앞의 과정에서 activity에 <FragmentContainerView> 태그를 통하여 navHostFragment를 정의
      // 거기에 navGraph 속성을 통해 navGraph를 연결. 그 안에 fragment들이 소속되어 있으므로 호출 가능 조건 만족
      it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
  }
  ```
  
  * 그 결과, 화면 이동이 잘 이뤄짐.

7. 화면 이동 시 넘겨줄 데이터를 정의한다.

* 방법 1. navigate() 함수의 두 번째 매개변수인 args를 활용하여 bundle 형태로 도착 fragment에 데이터 전달하기

  * 하지만, 이것은 권장되지 않는 방법이다.

  * 시작 fragment에서 데이터 번들로 만들어 전달하기

  ```
  class HomeFragment : Fragment() {
      private lateinit var binding: FragmentHomeBinding
      override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
          binding.btnSubmit.setOnClickListener { 
              
              // null safety를 위하여 edittext에 text 존재 여부 검사
              if (!TextUtils.isEmpty(binding.etText.text.toString())) {
                  // 다른 화면으로 넘겨줄 데이터
                  // edittext의 content를 보내고자 함 -> bundle 형태로 만드는 것이 포인트
                  // 하지만, 권장되지 않는 방법임.
                  // viewModel을 사용하는 것이 best practice. 클릭 시 뷰모델에 데이터를 저장하고, 도착 fragment에서 viewmodel의 데이터를 사용
                  val bundle = bundleOf(
                      "user_input" to binding.etText.text.toString()
                  )
  
                  // nav graph에서 정의한 action을 연결하기 위하여, navController 객체를 이용해야 한다.
                  // 매개변수인 view(it)를 통해 navController를 찾아올 수 있음
                  // navController의 navigate()함수를 통하여 화면 이동을 구현
  
                  // 데이터를 넘겨주고자 한다면, navigate()의 두 번째 인자로 bundle을 넣는다.
                  // 권장되지 않는 방법
                  it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
                  // it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
              } else {
                  Toast.makeText(requireActivity(), "please insert text", LENGTH_SHORT).apply { 
                      show()
                  }
              }
          }
          return binding.root
      }
  
  }
  ```
  
  * 도착 fragment에서 전달받은 데이터 활용하기

  ```
  class SecondFragment : Fragment() {
      private lateinit var binding: FragmentSecondBinding
      override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
          // Inflate the layout for this fragment
          
          var input = requireArguments().getString("user_input")
          binding.tvResult.text = input.toString()
          
          return binding.root
      }
  }
  ```

* 방법 2. viewModel을 활용하기(권장)

8. Fragment 이동에 대한 animation 구현하기

* navigation graph xml 파일에서, 화면의 이동을 나타내는 화살표(action)를 클릭하면,
* design mode의 우측의 설정 탭에서 Animation 관련하여 네 가지 속성을 설정할 수 있음을 알 수 있다.
  * enter : 한 화면에서 다른 화면을 append 하는 방식으로 이동하는 경우, 다음 화면이 나타나는 애니메이션을 정의 
  * exit : 한 화면에서 다른 화면을 append 하는 방식으로 이동하는 경우, 이전 화면이 사라지는 애니메이션을 정의
  * popEnter : 한 화면을 종료(pop)하는 방식으로 다른 화면으로 이동하는 경우, 이전 화면이 나타나는 애니메이션을 정의
  * popExit : 한 화면을 종료(pop)하는 방식으로 다른 화면으로 이동하는 경우, 현재 화면이 사라지는 애니메이션을 정의
* xml 형태로 만들어진 animation xml 파일명을 활용하여 설정

-- -- --
## 5. RecyclerView

### 5.1. RecyclerView의 사용 목적
* Android 개발을 위해, 이전에는 ListView와 GridView를 활용하여 데이터의 리스트를 화면에 보여주었음

* 하지만, 여기에는 몇몇 문제점들이 존재

  * 가장 큰 문제점 : 메모리 비효율성
  
    * 그 이외의 문제점들 : 복잡하고 에러가 발생하기 쉬운 코드를 작성

* 이를 해결하기 위하여 나온 대안이 RecyclerView

  * 메모리 효율이 더 좋고, 더 진보된 방식으로 데이터의 리스트를 보여줄 수 있음


### 5.2. RecyclerView를 활용하는 방법

1. Activity 혹은 Fragment 클래스 파일과 그에 상응하는 Layout xml 파일을 만든다.

2. xml 파일에 <RecyclerView>를 추가한다.

   * 아이디를 추가하여, 클래스에서 사용할 수 있도록 한다.
   
3. Activity/Fragment 파일로 이동하여, RecyclerView를 초기화한다.

4. 초기화한 RecyclerView를 통하여 rv의 layoutManager와 adapter를 설정한다.

   * 가능한 세 가지 layoutManager
   
     * LinearLayoutManager
     * GridLayoutManager
     * StaggeredGridLayoutManager : https://abhiandroid.com/materialdesign/recyclerview-as-staggered-grid-example.html
     * RecyclerView 라이브러리는 layoutManager를 커스텀 할 수도 있도록 한다.
     
   * adapter 인스턴스를 적용 -> 아이템의 레이아웃과 바인딩되는 adapter 클래스를 만들어주어야 함.
   
5. Adapter 클래스를 만들어준다.

  * getItemCount

    * adapter에 의하여 보유되고 관리될 아이템의 총 개수를 리턴
    * 해당 개수에 근거하여, RecyclerView 라이브러리는 리스트 아이템들에 대한 숫자만큼 아이템을 보여줌(재활용하여)
    
  * onCreateViewHolder

    * 리스트의 각 아이템의 뷰를 create -> 리스트 아이템의 레이아웃을 가져와서 생성(binding 객체를 여기서 활용)
    * 초기에 몇 번 호출됨으로써 일정 개수를 만들어두고, 이것들을 스크롤 시 재사용
    * 이를 위하여, 6.과 같이 아이템을 위한 레이아웃을 생성
    * 해당 레이아웃을 이용하여 DataBinding 객체를 만들고, 해당 Binding 객체를 생성자로 가지는 ViewHolder 클래스를 반환하도록 한다.
      * 필요한 것 : LayoutInflater
    * 클릭 시의 이벤트 등을 여기서 정의하는 것이 좋음(also 스코프 함수 활용)
    
  * onBindViewHolder

    * 레이아웃의 각 view에 activity/fragment로부터 넘겨받은 데이터를 할당해준다.
    
      * 초기에 보여질 값을 '설정'하는 작업은 ViewHolder 클래스에서 이뤄지도록 한다.
      * ViewHolder 내부의 bind 메소드를 호출하도록 하는 것이 best practice
      
6. 아이템을 위한 Layout을 생성한다.

   * databinding/viewBinding 등으로 정의
   
7. 아이템 layout의 view와 대응되는 데이터들을 담은 data class를 생성한다.

8. View Controller(Activity/Fragment) 파일에서 item에 들어갈 데이터들의 리스트를 생성한다.(convention)

9. 4.에서 adapter를 설정한다.


### 5.3. Adapter에서 list item을 사용하여 작업을 수행하는 방법

* 방법 1. ViewHolder 클래스 내부에 정의한 bind() 함수에서 정의
  * 장점 : 손쉽게 설정 가능
  * 단점 : 제약 사항이 존재 -> 선택된 아이템에 대응하는 객체 데이터만 다룰 수 있음

* 방법 2 : 고차함수를 활용하여 controller view에서 클릭 시의 이벤트를 정의하여 adapter로 넘겨주는 방법
  * view controller로 선택된 아이템(객체)를 넘겨주어야 할 때 유용하다.


-- -- --
## 6. Coroutines

### 6.1. About Coroutines

* Cooperative Multitasking

  * Thread가 스스로 그들의 행동을 제어

* Coroutine이란?

  * 소프트웨어 구성요소로서, Cooperative Multitasking을 위하여 만들어지는 하위 routines

* 역사

  * 1958년 어셈블리어에서 처음으로 활용
  * Python, C#, JS 등에서 사용

* Kotlin의 Coroutines

  * sequence of well managed **sub tasks** : 하위의 여러 태스크들의 일치(동시다발적 실행)
  * Coroutine은 경량 Thread로 간주되기도 함(단, Coroutine이 Thread라는 것을 의미하는 것은 아님)
  * 하나의 Thread 내부에 수많은 Coroutine 실행 가능
  * Coroutine은 여러 Thread를 오가며 작업할 수 있음
  * 특정 Coroutine은 한 Thread에서 동작 중지된 상태에서, 다른 Thread에서 resume될 수 있다.

* 왜 안드로이드 개발에 Coroutine이 필요한가?

  * 최근 안드로이드 디바이스에서는 1초 동안 60Hz / 최대 120Hz의 refresh 주파수를 가지고 있다.
    * https://source.android.com/docs/core/graphics/multiple-refresh-rate?hl=ko
    * 즉, 1초에 특정 Hz만큼의 횟수로 앱의 refresh가 진행된다는 것이다.
    * 그렇다면, 한 번 refresh가 진행되는 데에는 
      * 60Hz의 경우 약 17ms동안 1번의 refresh가 진행
      * **즉, Main Thread에서 1번 refresh를 진행하는데 17ms를 사용하게 됨**
      * 하지만, 최근 스마트폰에서는 Hz가 커짐에 따라 refresh 횟수가 많아져, refresh에 주어지는 시간이 점점 짧아지고 있다.
        * 120Hz의 경우 약 8ms동안 1번의 refresh

  * 매 refresh마다, 안드로이드의 Main Thread에는 주기적으로 해야 할 일들(책임)이 부여됨

    * parsing XML
    * inflating view components
    * drawing the screen
    * listening to users -> 유저의 동작 처리(클릭 이벤트 등)
    
  * 이렇게 할 일이 많은 만큼, 만약 Main Thread에 많은 task를 부여한다면
    * refresh 간 시간(위의 예시대로 라면 16ms)이 부족하게 될 수 있음(refresh 시 부여된 수많은 작업들을 하느라 refresh 간 시간을 초과)
    * 이것이 누적된다면, 성능 상의 문제가 발생하며, 스크린이 멈춘다.
    * 이 현상으로 인하여 최악의 경우 ANR 발생 가능
    * 최근 안드로이드 디바이스의 refresh 횟수가 계속 커짐에 따라 이러한 오류의 가능성도 높아짐

  * 따라서, 오랜 시간이 걸리는 작업의 경우 비동기적으로, 다른 Thread에서 실행시켜야 한다.
    * 이것을 달성할 수 있는 가장 효율적인 기술이 Coroutines


### 6.2. Coroutines Tutorial

* Coroutine을 사용하지 않는다면?

  * 아래의 예시로 시간이 오래 걸리는 작업을 Main Thread에서 수행하고 있는 동안, 다른 UI 관련 작업을 수행했을 때의 양상을 확인할 수 있음
  * 먼저 다운로드 버튼을 누르고, count 버튼을 수 차례 눌렀을 때 어떠한 일이 발생하는가?
    * count 버튼을 누를 때마다 즉각 count가 증가하지 못하며 시간이 지나고 나서야 가장 마지막 count 값으로 업데이트 됨을 알 수 있음
    * 이러한 UI 이슈는 Main Thread에서 시간이 오래 걸리는 task를 수행한 탓

```
class CoroutineActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var binding: ActivityCoroutineBinding
    companion object {
        const val TAG = "CoroutineActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)
        
        // initial project : 모든 작업을 main thread에서 실행
        binding.btnCount.setOnClickListener { 
            binding.tvCount.text = count++.toString()
        }
        
        binding.btnDownload.setOnClickListener {
            // long runnign task    
            downloadUserData()
        }
        
    }

    // 시간이 오래 걸리는 작업
    private fun downloadUserData() {
        for (i in 1..200_000) {
            Log.i(TAG, "downloading $i in ${Thread.currentThread().name}")
        }
    }
}
```

* 이를 방지하기 위하여, 다른 Thread에서 long running task를 수행시키고자 coroutine을 활용

  * 특정 Thread에서 같은 시간 동안 여러 개의 Coroutine이 실행될 수 있음.
  * Coroutine은 Thread가 아니라, Thread의 위에서 동작하는 별도의 프로세서라고 간주하는 것이 좋음
  * 앞의 예시에서 발생한 문제가 해결되었음을 알 수 있다.

```
class CoroutineActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var binding: ActivityCoroutineBinding
    companion object {
        const val TAG = "CoroutineActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutine)
        
        binding.btnCount.setOnClickListener { 
            binding.tvCount.text = count++.toString()
        }
        
        // coroutine 사용
        binding.btnDownload.setOnClickListener {
            // run on background thread
            CoroutineScope(Dispatchers.IO).launch {
                // long runnign task    
                downloadUserData()
            }
        }
        
    }

    // 시간이 오래 걸리는 작업
    private fun downloadUserData() {
        for (i in 1..200_000) {
            Log.i(TAG, "downloading $i in ${Thread.currentThread().name}")
        }
    }
}
```