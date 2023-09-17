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