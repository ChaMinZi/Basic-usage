# [Generics](https://kotlinlang.org/docs/generics.html)

* 클래스 내부에서 사용할 자료형을 **나중에 인스턴스를 생성할 때 확정**합니다. ( 미리 지정도 가능하다. ) 
* 자료형의 객체들을 다루는 method 나 class에서 컴파일 시간에 자료형을 검사해 적당한 자료형을 선택합니다. 
* **객체의 자료형을 컴파일 시에 체크하기 때문에 객체 자료형의 안정성을 높이고 형 변환의 번거로움이 줄어듭니다.**
* 앵글 브래킷(<>) 사이에 형식 매개변수를 사용해 선언합니다.
* 매개변수는 하나 이상 지정할 수 있습니다.
* 일반적으로 Generic class의 instance를 생성할 때는 type을 전달해야 하지만 추론 가능한 경우에는 생략할 수 있습니다.

* ex1.
```kotlin
class Box<T>(t: T) { // 제네릭을 사용해 형식 매개변수를 받아 name에 저장
    var name = t
}
fun main() {
    val box1: Box = Box(1) // 파라미터를 추론 가능하므로 타입을 생략할 수 있다.
    val box2: Box<String> = Box<String>("Hello")

    println(box1.name)
    println(box2.name)
}
```
* ex2.
```kotlin
fun <T> find(a: Array<T>, Target: T): Int { // 인덱스 값 반환해주는 함수
    for (i in a.indices) { // indices 는 범위
        if (a[i] == Target) return i
    }
    return -1
}

fun main() {
    val arr1: Array<String> = arrayOf("Apple", "Bananan", "Cherry", "Durian")
    println(find<String>(arr1, "Cherry")) 
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

### 자료형 제한
형식 매개변수를 특정한 자료형으로 제한 가능합니다. ( 사용 범위를 좁히기 위해서 )

    1. Java에서는 extends나 super를 사용해 자료형을 제한한다.
    2. Kotlin에서는 콜론(:) 통해 자료형을 제한한다. 
    3. class, 함수 모두 가능하다.

```kotlin
class Calc<T: Number> {
    fun plus(arg1: T. arg2: T): Double {
        return arg1.toDouble() + arg2.toDouble()
    }
}

fun main(args: Array<String>) {
    val calc1 = Calc<int>()
    val calc2 = Calc<Double>()
    val calc3 = Calc<Long>()
    
    // val calc4 = Calc<String>() --> 제한된 자료형에 해당하지 않으므로 오류가 발생
}
```

--------------------------------
## 가변셩 ( Variance )

* 가변성의 3가지 유형

![image](https://user-images.githubusercontent.com/29828988/110747335-9230dd00-8281-11eb-8b41-3bfd10893a23.png)

( 출처 : [부스트코스 - 가변성의 3가지 유형](https://www.boostcourse.org/mo234/lecture/154305/?isDesc=false) )

Java의 wildcard types는 API 의 유연성을 증가시키기 위해 사용됩니다. 하지만 Java에서 다루기 힘든 것 중 하나입니다. 

Java에서는 generic type은 무변성(invariant) 입니다. 이것의 의미는 List<String>은 List<Object의 subtype이 아니라는 것입니다.  
**Why??** 만약 `List`가 invariant하지 않다면 배열보다 나을 것이 없기 때문입니다.  
이는 아래의 코드를 통해 알 수 있다. 아래의 코드가 컴파일된다면 런타임에 예외가 발생합니다.  
그래서  Java는 run-time safety를 위해서 이러한 것을 금지합니다.
                                                                             
* Java
```Java
List<String> strs = new ArrayList<String>();
List<Object> objs = strs; // !여기서 컴파일 타임 오류가 발생하면 런타임 예외에서 걸리지 않을 수 있습니다.
objs.add(1); // Put an Integer into a list of Strings
String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
```

이러한 금지로 인하여 생기는 영향이 있습니다. 아래와 같은 코드를 통해서 알아 볼 수 있습니다.

* Java
```Java
// 문제가 발생하는 코드
interface Collection<E> ... {
    void addAll(Collection<E> items);
}

void copyAll(Collection<Object> to, Collection<String> from) {
    to.addAll(from);
    // !!! addAll은 컴파일 할 수 없습니다.
    // Collection<String> is not a subtype of Collection<Object>
}
```

실제로 사용하기 위해서는 interface를 선언할 때 아래와 같이 사용해야 합니다.  
## [wildcard type argument?](https://kotlinlang.org/docs/generics.html#variance) 참고하기

```Java
interface Collection<E> ... {
    void addAll(Collection<? extends E> items);
}
```
------------------

Kotlin은 따로 지정하지 않는 경우 기본적으로 무변성입니다.  
**Java와 달리 wildcard types는 없지만 declaration-site variance 와 type projections가 있습니다.**

### 1. Declaration-site variance ( 선언 지점 변성 )

1. out

Java 에서는 파라미터를 가지지 않더라도 이를 컴파일러가 알 수 없기 때문에 

### 2. type projections


#### 1) Use-site variance: type projections


#### 2) Star-projections


* #### projection 정리
종류 | 예 | 가변성 | 제한 
--------------------|--------------------|--------------------|--------------------
out         | `Box<out Cat>` | 공변성 | 형식 매개변수는 setter를 통해 값을 설정하는 것이 제한됨
in         | `Box<in Cat>` | 반공변성 | 형식 매개변수는 getter를 통해 값을 읽거나 반환 가능
star( * )   | `Box<*>` | 모든 인스턴스는 subtype이 될 수 있음 | in , out은 사용 방법에 따라 결정됨
--------------------------------

### 참고
* [공식문서](https://kotlinlang.org/docs/generics.html)
* [부스트코스](https://www.boostcourse.org/mo234/lecture/154303/)
