# [Coroutines](https://kotlinlang.org/docs/coroutines-guide.html#additional-references)
-------------------------

* Non-blocking 또는 비동기 코드를 마치 일반적인 동기 코드처럼 쉽게 작성하면서도 비동기와 같은 효과를 낼 수 있다.  
 ( 스레드처럼 분리된 코드가 아니라 순차적인 코드처럼 작성된다. )
 
 ### Blocking
![image](https://user-images.githubusercontent.com/29828988/111155449-d13f9500-85d7-11eb-8701-c9ee50419d95.png)

 ### Non-Blocking
![image](https://user-images.githubusercontent.com/29828988/111155472-d56bb280-85d7-11eb-8fc5-4c8d42ac93a5.png)

기존의 작업에서 I/O 작업이 발생하면 blocking 상태로 전환되는 것이 아니라 다른 일을 처리하면서 기다리는 것

# [Thread](https://www.boostcourse.org/mo234/lecture/154328)
--------------------------
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

## 출처
-------------------------
* [공식문서](https://kotlinlang.org/docs/coroutines-guide.html#additional-references)
* [부스트코스](https://www.boostcourse.org/mo234/lecture/154327)
