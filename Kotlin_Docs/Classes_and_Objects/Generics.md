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

------------------

Java에서는 generic type은 무변성(invariant) 입니다. 이것의 의미는 List<String>은 List<Object의 subtype이 아니라는 것입니다.  
**Why??** 만약 `List`가 invariant 하지 않다면 배열보다 나을 것이 없기 때문입니다.  
이는 아래의 코드를 통해 알 수 있습니다. 아래의 코드가 컴파일된다면 런타임에 예외가 발생합니다.  
그래서 Java는 run-time safety를 위해서 이러한 것을 금지합니다.
 
```Java
// Java
List<String> strs = new ArrayList<String>();
List<Object> objs = strs; // !여기서 컴파일 타임 오류가 발생하면 런타임 예외에서 걸리지 않을 수 있습니다.
objs.add(1); // Put an Integer into a list of Strings
String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
```

이러한 금지로 인하여 생기는 영향이 있습니다. 아래와 같은 코드를 통해서 알아 볼 수 있습니다.

```Java
// Java
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

```Java
interface Collection<E> ... {
    void addAll(Collection<? extends E> items);
}
```

## [wildcard type argument?](https://kotlinlang.org/docs/generics.html#variance) 참고하기

Java의 wildcard types는 API 의 유연성을 증가시키기 위해 사용됩니다. 하지만 Java에서 다루기 힘든 것 중 하나입니다. 

------------------

Kotlin은 따로 지정하지 않는 경우 기본적으로 무변성입니다.  
**Java와 달리 wildcard types는 없지만 declaration-site variance 와 type projections가 있습니다.**

------------------

## 1. [ Declaration-site variance ( 선언 지점 변성 ) ](https://kotlinlang.org/docs/generics.html#declaration-site-variance)

### 1) out

Java 에서는 파라미터로 T를 가지는 메서드가 없고 오직 T를 반환하는 메서드만 있어도 이를 컴파일러가 알 수 없기 때문에 의미 없는 코드여도 ```Source<? extends Object>```와 같이 선언해야 합니다.

* Java 예시
```Java
// 이렇게 선언하면 Java에서는 문제가 발생
interface Source<T> {
  T nextT();
}

void demo(Source<String> strs) {
  Source<Object> objects = strs; // !!! Not allowed in Java
  // ...
}
```
아래와 같이 선언해줘야 한다.
```Java
// Java
interface Source<? extends Object> {
  T nextT();
}
```

**하지만 Kotlin은 다릅니다!! Kotlin은 이러한 경우를 컴파일러에게 알려줄 수 있는 방법이 있습니다.**  
Source의 타입 파라미터 T에 대해 Source<T> 의 멤버에서 T를 리턴 (제공)만 하고 사용하지 않는다는 것을 명시할 수 있습니다.

```kotlin
// kotlin
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}
```

### 2) in

* 타입 파라미터를 반공변 으로 만듭니다. 반공변 파라미터는 오직 소비만 할 수 있고 생산할 수는 없습니다. 반공변의 좋은 예는 Comparable 입니다.

```kotlin
// Kotlin
interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0은 Double 타입인데 이는 Number의 하위타입이다.
    // 따라서 Comparable<Double> 타입의 변수에 x를 할당할 수 있다.
    val y: Comparable<Double> = x // OK!
}
```

## 2. type projections

: 메서드 매개변수에서 또는 제네릭 클래스를 생성할 때 가변성을 지정하는 방식

### 1) Use-site variance: type projections

type parameter T를 out으로 선언하면 사용 위치에서 하위 타입의 문제를 피할 수도 있고 편리하다. 하지만 어떤 클래스는 T 만 return 하도록 제한할 수 없다.  
예를 들어 배열을 보자!! 

```kotlin
class Array<T>(val size: Int) { // 이 클래스는 T에 대하여 공변, 반공변으로 사용할 수 없다. 이는 배열을 유연하지 못하게 합니다.
    fun get(index: Int): T { ... }
    fun set(index: Int, value: T) { ... }
}

// 배열을 다른 배열로 copy 하는 함수
fun copy(from: Array<Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}

val ints: Array<Int> = arrayOf(1, 2, 3)
val any = Array<Any>(3) { "" }
copy(ints, any)
//    ^ Array<Int> 가 아닌 Array<Any> 를 기대하여 Error 발생 = type mismatch
```

위의 코드에서 Array<T> 는 무공변(invariant)이므로 Array<Int>와 Array<Any>는 상관이 없다. ( 서로의 하위 타입이 아니다. )  

copy 라는 함수는 우리가 기대하지 않은 것( 예를 들면 ClassCastException )을 발생시키지 않는다는 것을 컴파일러에게 전달할 필요가 있습니다.

이 때 copy에 out을 추가하면 컴파일 에러가 발생하는 것을 막을 수 있다. 

```kotlin
// kotlin
fun copy(from: Array<out Any>, to: Array<Any>) { ... }
```

위의 선언을 통해 `from`은 producer로서만 사용될 수 있다는 것을 명시하였으므로 copy 내에서는 consume 하는 메서드 ( set )들은 사용이 제한됩니다.

### 2) Star-projections

in/out을 정하지 않고 추후에 결정합니다.

**어떤 자료형이라도 들어올 수 있으나 구체적으로 자료형이 결정되고 난 후에는 그 자료형과 하위 자료형의 요소만 담을 수 있도록 제한합니다.**

    * in으로 정의되어 있는 type parameter를 *로 받으면 in Nothing 인 것으로 간주한다.
    * out으로 정의되어 있는 type parameter를 *로 받으면 out Any? 인 것으로 간주한다.

```
interface Function<in IN, out OUT> 와 같이 정의한 경우

    Function<*, String> -> 실제 의도 : Function<in Nothing, String>
    Function<Int, *> -> 실제 의도 : Function<Int, out Any?>
    Function<*, *> -> 실제 의도 : Function<in Nothing, out Any?>
```

```kotlin
// Kotlin
class InOutTest<in T, out U>(t: T, u: U) {
    val propT: T = t // !!!오류 발생 / T는 in 위치이기 때문에 out 위치에서는 사용이 불가능
    val propU: U = u // U는 out 위치 이므로 사용 가능하다.
    
    fun func1(u: U) // !!!오류 발생 / U는 out 위치이기 때문에 in 위치에서는 사용이 불가능
    fun func2(t: T) { // T는 in 위치에 사용 가능하다.
        print(t)
    }
}

fun starTestFunc(v: InOutTest<*, *>) {
    v.func2(1) // 오류: Nothing으로 인자를 처리한다.
    print(v.propU)
}
```

--------------------------------

* ### projection 정리
종류 | 예 | 가변성 | 제한 
--------------------|--------------------|--------------------|--------------------
out         | `Box<out Cat>` | 공변성 | 형식 매개변수는 setter를 통해 값을 설정하는 것이 제한됨
in         | `Box<in Cat>` | 반공변성 | 형식 매개변수는 getter를 통해 값을 읽거나 반환 가능
star( * )   | `Box<*>` | 모든 인스턴스는 subtype이 될 수 있음 | in , out은 사용 방법에 따라 결정됨

--------------------------------


### 참고
* [공식문서](https://kotlinlang.org/docs/generics.html)
* [부스트코스](https://www.boostcourse.org/mo234/lecture/154303/)
* https://medium.com/mj-studio/%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%A0%9C%EB%84%A4%EB%A6%AD-in-out-3b809869610e
