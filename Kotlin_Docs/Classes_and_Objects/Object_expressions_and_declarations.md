# [Object expressions and declarations](https://kotlinlang.org/docs/object-declarations.html)

> [object와 class 키워드의 차이점](https://codechacha.com/ko/kotlin-object-vs-class/)  
> [[Kotlin] Object 키워드, 식(Expressions), 선언(Declarations)](https://shinjekim.github.io/kotlin/2019/09/04/Kotlin-Object-%ED%82%A4%EC%9B%8C%EB%93%9C,-%EC%8B%9D(Expressions),-%EC%84%A0%EC%96%B8(Declarations)/)

# Object expressions

Object expression을 이용하면 익명 클래스를 생성할 수 있습니다. 


* **익명 클래스란?**
```
클래스를 이름 없이 정의하는 것을 말합니다. 
Kotlin의 Object expressions는 Java의 anonymous inner class 대신 사용됩니다. 
```

## 익명 객체 생성 ( Creating anonymous objects )

`object` 키워드를 이용하여 생성합니다.

```kotlin
val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world" 
}
```

**아래는 특정 타입을 상속한 익명 클래스 객체를 생성하는 방법입니다.**

`:`를 이용하면 구체적인 타입을 상속 받을 수 있습니다. `implement`와 `override` 모두 아래와 같은 방법으로 사용합니다.
```kotlin
window.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { /*...*/ }

    override fun mouseEntered(e: MouseEvent) { /*...*/ }
})
```

만약 상위 타입(supertype) 이 생성자를 가진다면, 적절한 매개 변수를 전달해야 합니다. 상위 타입이 여러 개이면, 콜론(`:`) 뒤에 콤마(`,`)로 구분해서 지정할 수 있습니다.

```kotlin
open class A(x: Int) {
    public open val y: Int = x
}

interface B { /*...*/ }

val ab: A = object : A(1), B {
    override val y = 15
}
```


### 익명 객체 return 이나 value type 으로 사용하기

익명 객체는 `local` 또는 `private` 일 때만 타입으로 사용될 수 있습니다. inline 선언과는 다릅니다. 

```kotlin
class C {
    // private 함수: 리턴 타입은 익명 객체의 타입입니다.
    private fun getObject() = object {
        val x: String = "x"
    }

    fun printX() {
        println(getObject().x)
    }
}
```

public 함수의 리턴 타입이나 public 프로퍼티의 타입으로 익명 객체를 사용하면, 해당 함수/프로퍼티의 실제 타입은 익명 객체에 선언한 상위 타입(supertype)이 됩니다. 

상위 타입을 선언하지 않았을 경우에는 `Any`입니다. 

익명 객체에 추가된 멤버는 접근할 수 없습니다.

```kotlin
interface A {
    fun funFromA() {}
}
interface B

class C {
    // return type은 Any이고 x는 접근할 수 없습니다.
    fun getObject() = object {
        val x: String = "x"
    }

    // return type은 A 이고 x는 접근할 수 없습니다.
    fun getObjectA() = object: A {
        override fun funFromA() {}
        val x: String = "x"
    }

    // return type은 B 이고 funFromA() 와 x는 접근할 수 없습니다.
    fun getObjectB(): B = object: A, B { // explicit return type is required
        override fun funFromA() {}
        val x: String = "x"
    }
}
```
익명 객체는 해당 영역 안(enclosing scope)의 변수에 접근할 수 있습니다.
```kotlin
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ...
}
```

## Object delarations ( 객체 선언 )

코틀린(after Scala)에서는 싱글턴(singleton)을 쉽게 선언할 수 있도록 지원해줍니다.

```kotlin
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }

    val allDataProviders: Collection<DataProvider>
        get() = // ...
}
```
위의 예시와 같은 코드를 객체 선언(object declarations)이라고 하며, 항상 `object` 키워드 뒤에 이름이 있습니다.
변수 선언과 마찬가지로 객체 선언은 표현식(expression)이 아니며 할당문(assignment statement)의 우변(right side)에 쓰일 수 없습니다.

Object declaration의 초기화는 스레드에 안전하며 처음 액세스할 때 수행됩니다.

객체를 참조하려면 해당 객체의 이름을 바로 사용하면 됩니다. 
```kotlin
DataProviderManager.registerDataProvider(...)
```
Object는 상위 타입(supertype)을 가질 수 있습니다.
```kotlin
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
}
```

## Companion objects
