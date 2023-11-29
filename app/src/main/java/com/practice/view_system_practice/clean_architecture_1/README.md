## Clean Architecture Project Example

### What is Clean Architecture?
* 클린 아키텍처는 소프트웨어를 디자인하기 위한 하나의 방법
* Martin에 의하여 처음으로 제안되었음
* 클린 아키텍처는 많은 분야의 프로그래밍/프로젝트에서 사용됨

### Advantages of Clean Architecture
* 클린 아키텍처는 프로젝트 코드의 낮은 결합성, 높은 수준의 추상성을 달성할 수 있게 해준다.
  * 다양한 계층으로 코드를 분리
  * 각 계층은 잘 정의된 책임만을 수행
* 최종적으로는 코드의 수정과 테스팅을 훨씬 용이하게 해줌

### Why Clean Architecture is needed?
* 여러 명의 개발자들이 프로젝트에 참여하며 다양한 패키지/모듈을 개발
* 클린 아키텍처를 잘 적용하면, 한 개발자가 다른 개발자가 하는 일이 무엇인지에 대해 전혀 몰라도 프로젝트 진행에 무리가 없어진다.

### Use Cases in Clean Architecture
* 안드로이드 개발에서 클린 아키텍처를 MVVM과 함께 적용하고자 할 때, `Use Case`라는 다른 컴포넌트가 필요
  * Repository와 ViewModel 사이에 존재
* UseCase : 특정 로직만을 실행하는 클래스로, SRP를 따르기 위한 것이 가장 큰 목적
  * Use Case는 `User가 행할 수 있는 동작 단위`로 정의된다.
  * 따라서, Use Case는 때때로 `Interactor`로 불리기도 한다.
* (참고)비록 UseCase가 Clean Architecture를 달성하기 위하여 필요하지만, 필수적으로 개발 시 넣어야 하는 것은 아니다.
  * 특정 태스크가 하나 이상의 ViewModel(UI Logic)에 사용되어야 할 때가 존재한다. 이 때, Use Case를 제작하면 Business Layer로부터 특정 기능 코드에 대한 재사용이 가능해진다.
  * 이는 특정 기능 구현을 위한 ViewModel에서의 중복 코드 발생을 막아준다.(UseCase로부터 같은 코드를 가져다 쓰면 되기 때문)
  * 따라서 ViewModel에서 다른 ViewModel과 중복되는 로직이 필요하면서 많은 양의 코드를 사용될 것이 예상된다면, UseCase를 활용함으로써 가독성과 테스트 가능성을 높일 수 있다.

### Principles
* 클린 아키텍처를 활용할 때 사용하는 세 가지 계층들
  * Presentation(UI 혹은 Application이라고도 함)
    * 유저와 상호작용하는 계층(유저에게 화면 표출 및 동작 인식 등)
    * View(Activities, Fragments, Adapters, DI...)와 ViewModel이 여기에 해당
    * 중요 : 클린 아키텍처에서는 ViewModel이 Repository 클래스와 직접 상호작용하지 않도록 하는 것이 중요
      * ViewModel은 UseCase와 상호작용. UseCase가 Repository와 상호작용
  * Domain
    * 애플리케이션의 비즈니스 로직을 담당
    * UseCase 및 Repository 인터페이스, Entity 클래스
      * 각 태스크 동작을 구현한 UseCase
  * Data
    * 모든 데이터 소스에 대한 정의
    * Repository 구현체 및 Data Source(Local/Remote)

-- -- --
## What to build
* TMDB를 활용한 애플리케이션

### Package Structure
* data
  * api(remote)
  * db(local)
  * model(data classes for dto)
  * repository(impl)
* domain
  * repository
  * use_case
* presentation
  * artist
  * di
  * ...

### Use Cases for the project
* 가장 먼저, 애플리케이션에서 유저가 할 수 있는 일들을 생각해보기
  * 영화에 대한 데이터 열람
  * 영화 정보 업데이트
  * TV 쇼 데이터 열람
  * TV 쇼 정보 업데이트
  * 배우 열람
  * 배우 정보 업데이트

### Project Setup
* TDMB API
  * 회원가입 후 API 키 얻어오기

## Build Application
### 1. Data Layer
* API를 활용하여 인기 영화/tv쇼/배우에 대한 정보를 얻어옴
  * Data Class로 변환하기 위한 JSON Object를 얻어오기 위함
  * API > API Reference > Movies 메뉴
  * https://developer.themoviedb.org/reference/movie-popular-list
* API 호출을 통해 얻어온 결과를 JSON to Kotlin Data Class 기능을 활용하여 Data Class로 변환
  * 이 때, Json 데이터를 붙여넣기 한다.
  * 또한, Advanced 메뉴에서, 여러 설정을 할 수 있음
    * 여기서는 GSON을 활용하여 Deserialization을 수행하므로, Annotation > Gson 선택해야 의도대로 변환됨
    * 또한, 흔하게 일어나지는 않지만 혹시 모를 오류에 대한 crash를 방지하기 위하여,
      * 무조건 있어야 하는 id 변수를 제외하고는 nullable로 해주는 것이 안전(null safe)

* Repository Implemenatations
  * repository package
    * RepositoryImpl that implements repository interface
      * 함수들을 구현
  * 고려 사항
    * 사용자가 처음으로 데이터를 열람할 때에는, 앱은 api로부터 데이터를 받아와야 함
    * 처음 데이터를 받아오는 시점에서는, 이것을 room 데이터베이스에 저장하는 과정이 필요
    * ui 계층에서, 룸 디비로부터 데이터를 받아와 rv에 표시
      * 임시 캐시를 활용하여 성능을 향상
    * 유저가 새로고침을 한다면, 데이터베이스를 비우고 다시 api로부터 데이터를 다운받아 데이터베이스에 저장하고 rv에 적재
    * **따라서 필요한 데이터 소스들은 다음과 같음**
      * remote data source
      * local data source
      * cache data source

  * 클린 아키텍처에서는, Public Interface를 활용하여 컴포넌트 간 통신을 진행한다. -> 세 가지 데이터소스에 대한 인터페이스를 만들기
    * remote data source
      * MovieRemoteDataSource => api의 응답 결과와 같은 형태를 반환하는 함수를 구현
    * local data source
      * MovieLocalDataSource => dao 함수와 같은 형태로 CRUD하는 함수를 구현
    * remote data source
      * MovieRemoteDataSource => 캐시로 데이터를 저장/획득하는 함수를 구현

### 2. domain layer
* Use Cases
* Repository Interfaces

### 3. Presentation Layer
* activity
  * tmdbclientactivity(main)
* viewmodel + viewmodel factory
  * 한 화면에 사용되는 유즈케이스 별로 뷰모델을 생성
    * 해당 유즈케이스 함수들을 viewmodel에서 invoke
  * viewmodel 클래스에서는 필요한 유즈 케이스들을 주입받아 사용
  * viewmodel 클래스에서 생성자가 필요하기 때문에, viewmodel 별로 viewmodel factory를 생성

* di using dagger2
  * 여기서 사용되는 의존성들 => 모듈로 만들어 놓기
    * ViewModel에서 사용되는 UseCase => 7
    * UseCase에서 사용되는 Repository => 6
    * Repository에서 사용되는 DataSource => 3, 4, 5
    * DataSource에서 사용되는 api service(Retrofit instance), dao => 1, 2

  1. NetworkModule(@Module) => for ~~RemoteDataSourceImpl
  * retrofit instance => baseUrl을 필요로 하므로, Module 클래스의 생성자로 baseUrl을 추가해야함.
  * TMDBService instance based on retrofit instance
  2. DatabaseModule(@Module) => for ~~LocalDataSourceImpl
  * TMDBDatabase instance
  * MovieDao instance(based on TMDBDatabase)
  * TvShowDao instance(based on TMDBDatabase)
  * ArtistDao instance(based on TMDBDatabase)
  3. RemoteDataModule(@Module) => For ~~RepositoryImpl
  * ~~RepositoryImpl 클래스의 생성자로 apiKey가 필요하기 때문에, Module의 생성자로 apiKey 추가
  * MovieRemoteDataSource instance
  * TvShowRemoteDataSource instance
  * ArtistRemoteDataSource Instance
  4. LocalDataModule(@Module) => For ~~RepositoryImpl
  * ~~RepositoryImpl 클래스의 생성자로 dao가 필요. 다만 이미 DatabaseModule에서 provide 함수를 정의했으므로 생성자는 필요 없음
  * MovieLocalDataSource instance
  * TvShowLocalDataSource instance
  * ArtistLocalDataSource Instance
  5. CacheDataModule(@Module) => For ~~RepositoryImpl
  6. RepositoryModule(@Module) => For Usecases(repository를 만드는 데 필요한 변수는 이미 앞에서 provide 함수로 정의했으므로, 생성자 필요x)
  * MovieRepository instance
  * TvShowRepository instance
  * ArtistRepository instance
  7. UseCaseModule(@Module) => For ViewModels
  8. AppModule(@Module) => applicationContext 활용을 위함 => context 주입을 위한 함수(singleton)
  9. AppComponent -> ComponentInterface => ViewModel에서 UseCase를 활용하기 위함
  * Singleton으로 선언 + 필요한 모듈들을 @Component에 모두 추가
  10. ViewModel을 공급받기 위한 모듈
  * 앱에서는 총 3개의 뷰모델을 활용(artist, movie, tvshow)
  * 만약 viewmodel이 특정 액티비티 생명주기에만 살아남기를 원한다면, subcomponent를 만들어야 한다.
  * movie, artist, tvshow의 데이터 플로우에 대한 서브컴포넌트를 만들고자 함
  * 이 과정에서, Scope Annotation class를 생성한다.
  

* view component layout
  * button layout resource file
    * 버튼에 대해서 일일이 하나하나 스타일을 적용하기 보다, 미리 하나의 테마를 만들어놓기 위함
    * btn_rounded.xml(drawable)
