# Sealed classes

> !! Sealed interfaces 실험적입니다. 언제든지 삭제하거나 변경할 수 있습니다. 평가 목적으로만 사용해야 합니다.

* Java 에는 없는 Kotlin에만 있는 문법입니다.
* Sealed classes 그 자체로는 추상 클래스와 같기 때문에 객체를 만들 수 없습니다.
* 생성자도 기본적으로 private이며 private이 아닌 생성자는 허용하지 않습니다.
* 실드 클래스는 캍은 파일 안에서는 상속이 가능합니다. ( 다른 파일에서는 상속이 불가능 )
* 블록{} 안에 선언되는 클래스는 상속이 필요한 경우 `open` 키워드로 선언

## 선언 

* 주로 상태를 정의하고 관리하는데 사용합니다.

> 모든 것은 sealed 클래스 선언 내에 있어야한다
```kotlin
sealed classes {
    open class Success(val message: String): Result()
    class Error(val code: Int, val message: String): Result()
}

class Status: Result()                  // 실드 클래스 상속은 같은 파일에서만 가능
class Inside: Result.Success("Status")  // 내부 클래스 상속
```
#### {} 없이 선언하는 방법
```kotlin
sealed class Result

open class Success(val message: String): Result()
class Error(val code: Int, val message: String): Result()

class Status: Result()
class Inside: Success("Status)
```

## 사용

* Sealed class는 조건을 제한할 수 있기 때문에 when 문을 사용할 때 유용하다. ( else가 필요 없어진다.)

```kotlin
fun eval(result: Result): String = when(result) {
    is Status -> "in progress"
    is Result.Success -> result.message
    is Result.Error -> result.message
    // 모든 조건을 가지므로 else 필요 없음
}
```
