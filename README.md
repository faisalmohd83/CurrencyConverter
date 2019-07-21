# CurrentConverter

### Prerequisites
* AndroidStudio 3.6
* Java JDK 1.8
* Kotlin 1.3.40
* Android SDK minimum API level 22

### Libraries used

* RetroFit - Retrofit is a REST Client library (Helper Library), Type-safe HTTP client for Android and Java by Square, Inc., used in Android and Koltin to create an HTTP request and also to process the HTTP response from a REST API.

* KOIN - a pragmatic lightweight dependency injection framework for Kotlin.

* OkHttp interceptor - logs HTTP request and response data.

* RxAndroid - Reactive Extensions for Android for Async programming.

* Gson - Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.

### Android components

- RecyclerView - Used to list the Albums.
- Constraintlayout - Simple, flat hierarchies in a layout.
- Synthetic Binding(Kotlin) - bind the data with UI, wonder not if **findViewById()** missing in any UI.

### [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)([Android Jetpack](https://developer.android.com/jetpack/))
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - allows data to survive configuration changes such as screen rotations.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)   - lifecycle-aware data holder class to respects the lifecycle Fragments.
- [AndroidX](https://developer.android.com/jetpack/androidx/) - Complete project implemented using AndroidX libraries.

### Workflow of the application
The app name is **CurrentConverter**. 
Once installed it can be found in the phone with a black **Currency Exchange** icon.

### Implementation

1. The App is designed to the list of exchange rates from the [network](https://revolut.duckdns.org/latest?base=EUR) by calling intended API endpoint with predefined interval(e.g., 5 seconds). The UI implemented as LinearLayout(RecycleView) to showcase the result list.
2. The network call is performed using Retrofit2 in conjunction with RxAndroid expects to convert the results to be mapped onto Kotlin data classes using Gson converter. RxJava helps to retrieve the result asynchronously and handover the result to Gson for further processing.
3. ViewModel in combination with LiveData helps the data to survive view lifecycle changes. Moreover, maintains an abstraction between the View and ViewModel.
4. Used Koin as dependency injection to have smooth integration with Kotlin, The complete code written in Kotlin.

### Design
- The application is based on MVVM pattern. 
In MVVM architecture, Views react to changes in the ViewModel without being called explicitly.
- The API requests are made using retrofit.
- The Gson is used conjunction retrofit to parser results onto Kotlin data classes.
- The ViewModel interacts with a data repository(Using Retrofit) to get the data and updates the View.
- The data source manages the data to be fetched from the network on UI scrolls using paging library.
- The ViewModel delegates all the requests from the view to the repository and vice versa.
- The RecylerView is used instead of normal listview.
- The repeated network calls are implemented using Rxjava's interval feature.

### Further enhancements
- No data/ limited network scenario to handled with progress bar.
- Data optimisation using best practices.
- Improving async by replacing the Rxjava with [Kotin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) for better performance.
- Improve the coverage in Unit, Integration and UI tests.
- furthermore, the refactoring is an endless dream.
