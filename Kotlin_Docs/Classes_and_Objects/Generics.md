# [Generics](https://kotlinlang.org/docs/generics.html)

* 클래스 내부에서 사용할 자료형을 나중에 인스턴스를 생성할 때 확정합니다. 
* 제네릭이 나오게 된 배경은 자료형의 객체들을 다루는 method 나 class에서 컴파일 시간에 자료형을 검사해 적당한 자료형을 선택할 수 있도록 하기 위해서 입니다. 
* **객체의 자료형을 컴파일 시에 체크하기 때문에 객체 자료형의 안정성을 높이고 형 변환의 번거로움이 줄어듭니다.**
* 앵글 브래킷(<>) 사이에 형식 매개변수를 사용해 선언합니다.
* 매개변수는 하나 이상 지정할 수 있습니다.

```kotlin
class Box<T>(t: T) { // 제네릭을 사용해 형식 매개변수를 받아 name에 저장
    var name = t
}
fun main() {
    val box1: Box<Int> = Box<Int>(1)
    val box2: Box<String> = Box<String>("Hello")

    println(box1.name)
    println(box2.name)
}
```

### 형식 매개변수의 이름 

확정된 규칙은 아니지만 마치 규칙처럼 사용된다.

형식 매개변수의 이름 | 이름
--------------------|--------------------
E         | 요소 ( element )
K         | 키 ( key )
N         | 숫자 ( number )
T         | 형식 ( type )
V         | 값 ( value )
S,U,V etc.| 두 번째, 세 번째, 네 번째 형식 ( 2nd, 3rd, 4th types )

--------------------------------

### 참고
* [공식문서](https://kotlinlang.org/docs/generics.html)
* [부스트코스](https://www.boostcourse.org/mo234/lecture/154303/)
