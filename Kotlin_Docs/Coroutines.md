# [Coroutines](https://kotlinlang.org/docs/coroutines-guide.html#additional-references)

* Light-Thread 라고도 불린다
* Coroutine 은 Stackless 이므로 생성 오버헤드가 줄어듭니다.
* 문맥 교환 없이 해당 루틴을 일시 중단 ( suspended )을 통해 제어합니다.
* Non-blocking 또는 비동기 코드를 마치 일반적인 동기 코드처럼 쉽게 작성하면서도 비동기와 같은 효과를 낼 수 있다.  
 ( 스레드처럼 분리된 코드가 아니라 순차적인 코드처럼 작성된다. )
 
 ### Blocking
![image](https://user-images.githubusercontent.com/29828988/111155449-d13f9500-85d7-11eb-8701-c9ee50419d95.png)

 ### Non-Blocking
![image](https://user-images.githubusercontent.com/29828988/111155472-d56bb280-85d7-11eb-8fc5-4c8d42ac93a5.png)

기존의 작업에서 I/O 작업이 발생하면 blocking 상태로 전환되는 것이 아니라 다른 일을 처리하면서 기다리는 것

</br>

* **Kotlin.coroutines의 commom 패키지**

| 기능 | 설명 |
|--------------------|--------------------|
| launch / async | 코루틴 빌더</br>둘의 차이는 실행 결과 반환 여부이다. |
| Job / Deferred | cancellation 지원을 위한 기능</br> ( Job = 생명주기를 가지고 동작하는 작업의 단위 ) |
| Dispatchers | 스케줄러</br>Default는 백그라운드 코루틴을 위한 것이고 Main은 Android 나 Swing, JavaFx를 위해 사용 |
| delay / yield | 상위 레벨 지연(suspending) 함수 |
| Channel / Mutex | 통신과 동기화를 위한 기능 |
| coroutineScope / supervisorScope | 범위 빌더 |
| select | 표현식 지원 |

### [launch](https://kotlinlang.org/docs/coroutines-basics.html#your-first-coroutine)
1. 일단 실행하고 잊어버지는(fire and forget) 형태의 코루틴 ( = 메인 프로그램과 독립적으로 실행할 수 있습니다. )
2. 기본적으로 즉시 실행하며 블록 내의 **실행 결과는 반환하지 않습니다.**
3. 상위 코드를 블록시키지 않고(= Non blocking) 관리를 위한 Job 객체를 즉시 반환합니다.
4. join을 통해 상위 코드가 종료되지 않고 완료를 기다리게 할 수 있습니다.</br>( = 메인 코드에 launch를 실행하고 곧바로 메인 프로그램이 중단되면 launch가 사라져버립니다. 하지만 launch를 사용하는 경우 main은 종료되지 않고 launch가 종료될 때 까지 기다립니다.

```kotlin
import kotlinx.coroutines.*

fun main() { // 메인 스레드 문맥
    GlobalScope.launch { // 새로운 코루틴을 백그라운에 실행
        delay(1000L) // 1 초의 넌블로킹 지연 ( 시간의 기본 단위는 ms )
        println("World!") // 지연 후 출력
    }
    println("Hello ") // main 스레드가 코루틴이 지연되는 동안 계속 실행
    Thread.sleep(2000L) // main 스레드가 JVM에서 바로 종료되지 않게 2초를 기다린다.
}
```
```kotlin
import kotlinx.coroutines.*

fun main() { 
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    } 
}
```

### async
 1. 비동기 호출을 위해 만든 코루틴으로 결과나 예외를 반환한다.
 2. 실행 결과는 Deffered<T>를 통해 반환하며 await을 통해 받을 수 있다.
 3. await은 작업이 완료될 때까지 기다리게 된다.


</br>

* **core 패키지**

| 기능 | 설명 |
|--------------------|--------------------|
| CommonPool | 코루틴 컨텍스트 |
| produce / actor | 코루틴 빌더 |

## 라이브러리 추가하기

![image](https://user-images.githubusercontent.com/29828988/111326560-fef80d00-86af-11eb-804b-c167a5fb70e6.png)


-------------------------

# [Thread](https://www.boostcourse.org/mo234/lecture/154328)

* Thread 생성 4가지 방법
```kotlin
// 1. 클래스
class SimpleThread : Thread() {
   override fun run() {
       println("Class Thread ${Thread.currentThread()}")
   }
}

// 2. 인터페이스
class SimpleRunnable : Runnable {
   override fun run() {
       println("Interface Thread ${Thread.currentThread()}")
   }
}

fun main() {
    val thread = SimpleThread()
    thread.start()
    
    val runnable = SimpleFunnable()
    val thread2 = Thread(runnable)
    thread2.start()
    
    // 3. 익명객체
    object : Thread() {
        override fun run() {
            println("object Thread ${Thread.currentThread()}")
        }
    }
    
    // 4. 람다식
    Thread {
        println("Lambda Thread ${Thread.currentThread()}")
    }
}
```
--------------------------

## 출처
-------------------------
* [공식문서](https://kotlinlang.org/docs/coroutines-guide.html#additional-references)
* [부스트코스](https://www.boostcourse.org/mo234/lecture/154327)
