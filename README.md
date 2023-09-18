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
  * start destination이라는 의미로, 이는 쉽게 바꿀 수 있음