# [Nested and inner classes](https://kotlinlang.org/docs/nested-classes.html)

## [Nested class](https://kotlinlang.org/docs/nested-classes.html)
```kotlin
class Outer {
    private val bar: Int = 1
    class Nested { // 외부의 bar에는 접근 불가능하다.
        val nv = 10
        fun foo() = 2 
    }
    
    fun outside() {
        val msg = Nested().foo()  // Outer 객체 생성 없이 nested class의 메서드에 접근 가능
        println("[Outer: $msg, ${Nested().nv]") // nested class의 property에 접근 가능
    }
}

fun main() {
    // Java의 static 처럼 Outer의 객체 생성 없이 Nested 객체를 생성 사용할 수 있음
    val demo = Outer.Nested().foo() // == 2
    
    // Outer.outside() --> Error!! Outer 객체는 static이 아니므로 객체 생성 필요
    val outer = Outer()
    outer.outside()
}
```

만약 nested class에서 외부 class로 접근하고 싶다면 **companion object**를 사용하면 됩니다.

```kotlin
class Outer {
    val ov = 5
    class Nested {
        val nv = 10
        fun greeting() = "[Nested] Hello ! $nv"
        fun accessOuter() {
            println(country)
            getSomething()
        }
    }
    
    fun outside() {
        val msg = Nested().greeting()
        println("[Outer: $msg, ${Nested().nv]")
    }
    
    companion object {
        const val country = "Korea"
        fun getSomething() = println("Get somthing...")
    }
}

fun main() {
    val output = Outer.Nested().greeting()
    println(output)
    Outer.Nested().accessOuter()
}
```
```
결과 값

[Nested] Hello ! 10
Korea
Get something....
```

아래의 코드와 같이 class 및 interface의 모든 조합이 가능합니다.

```kotlin
interface OuterInterface {
    class InnerClass
    interface InnerInterface
}

class OuterClass {
    class InnerClass
    interface InnerInterface
}
```

## [inner class](https://kotlinlang.org/docs/nested-classes.html#inner-classes)

* inner 로 지정하여 외부 클래스의 멤버가 접근할 수 있습니다.
* 외부 클래스의 private 멤버도 접근 가능합니다.
* 내부 클래스는 외부 클래스의 객체에 대한 참조를 전달합니다.

```kotlin
class Outer {
    private val bar: Int = 1
    inner class Inner { // 외부의 bar에는 접근 가능하다.
        fun foo() = bar
    }
}

val demo = Outer().Inner().foo() // == 1
```

<br>

---------------------------

# 내부 클래스

#### Kotlin의 내부 클래스 종류
1. Nested class ( 중첩 클래스 )
2. inner class  ( 이너 클래스 )

#### Java의 내부 클래스 종류

종류            | 역할
-------------|-------------
static class    | static 키워드를 가지며 외부 클래스를 인스턴스화 하지 않고 바로 사용 가능한 클래스 ( 주로 빌더 클래스에 이용된다. )
member calss    | 인스턴스 클래스로도 불리며 외부 클래스의 필드나 메서드와 연동하는 내부 클래스
local class     | 초기화 블록이나 메서드 내의 블록에서만 유효한 클래스
annoymous class | 이름이 없고 주로 일회용 객체를 인스턴스화 하면서 오버라이드 메서드를 구현하는 내부 클래스 ( 가독성이 떨어진다는 단점이 있다. )<br>Kotlin에서는 Object로 사용

### Java와 Kotlin 내부 클래스 비교
Java            | Kotlin
-------------|-------------
static class ( 정적 클래스 ) | nested class ( 중첩 클래스 )<br>: 객체 생성 없이 사용 가능
member calss ( 멤버 클래스 ) | inner class ( 이너 클래스 )<br>: field 나 method와 연동하는 내부 클래스로 inner 키워드가 필요하다.
local class ( 지역 클래스 )  | local class<br>: 클래스의 선언이 블록에 있다면 지역 클래스입니다.
anonymous class ( 익명 클래스 ) | anonymous objects ( 익명 객체 )<br>: 이름이 없고 주로 일회용 객체를 사용하기 위해 **object** 키워드를 통해 선업됩니다.

### 1. inner class

inner 키워드 유무에 차이가 있다.

* Java
```java
class A {
    ...
    class B {
        ... // 외부 클래스 A의 field에 접근 가능
    }
}
```
* Kotlin
```kotlin
class A {
    ...
    inner class B { // Java와 달리 inner 키워드 필요
        ... // 외부 클래스 A의 field에 접근 가능
    }
}
```

### 2. static class And nested class ( 정적 클래스와 중첩 클래스 )
* Java
```java
class A {
    static class B { // 정적 클래스를 위해 static 키워드를 사용
        ...
    }
}
```
* Kotlin
```kotlin
class A {
    class B { // kotlin에서는 아무 키워드가 없는 클래스는 nested class 이며 Java의 static class 처럼 사용된다.
        ...   // 외부 클래스의 A의 property, method에 접근할 수 없다.
    }
}
```

### 3. local class

* 지역 클래스는 특정 메서드의 블록이나 init 블록과 같이 블록 범위에서만 유효한 클래스입니다. 
* 블록 범위를 벗어나면 더 이상 사용되지 않습니다.
```kotlin
...
class Smartphone(val model: String) {

    private val cpu = "Exynos"
...
    fun powerOn(): String {
        class Led(val color: String) {  // 지역 클래스 선언
            fun blink(): String = "Blinking $color on $model"  // 외부의 프로퍼티는 접근 가능
        }
        val powerStatus = Led("Red") // 여기에서 지역 클래스가 사용됨
        return powerStatus.blink()
    } // powerOn() 블록 끝
}

fun main() {
...
    val myphone = Smartphone("Note9")
    myphone.ExternalStorage(128)
    println(myphone.powerOn())
}
```

### 4. anonymous object

* Java에서는 익명 이너 클래스라는 것을 제공해 일회성으로 객체를 생성해 사용합니다. 
* Kotlin에서는 이와 같은 기능으로 object 키워드를 사용하는 익명 객체로 같은 기능을 수행합니다. 
* **Java와 다른 점은 익명 객체 기법으로 앞서 살펴본 "다중의 인터페이스를 구현할 수 있다"는 것입니다.**

```kotlin
interface Switcher { // ① 인터페이스의 선언
    fun on(): String
}

class Smartphone(val model: String) {
...
    fun powerOn(): String {
        class Led(val color: String) {  
            fun blink(): String = "Blinking $color on $model" 
        }
        val powerStatus = Led("Red")
        val powerSwitch = object : Switcher {  // ② 익명 객체를 사용해 Switcher의 on()을 구현
            override fun on(): String {
                return powerStatus.blink()
            }
        } // 익명(object) 객체 블록의 끝
        return powerSwitch.on() // 익명 객체의 메서드 사용
    }
}
...
```
