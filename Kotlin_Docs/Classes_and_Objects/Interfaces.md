# Interfaces

* 기본적인 구현 내용이 포함될 수 있습니다. ( 과거 Java에서는 불가능 )
* 추상 클래스와 다른 점은 인터페이스가 상태를 저장할 수 없다는 것입니다.

## [Properties in interfaces](https://kotlinlang.org/docs/interfaces.html#properties-in-interfaces)

* Java 8 버전부터는 구현부를 가질 수 있게 되었다. 이전 버전에서는 불가능하다.
* Interface에서는 property에 값을 저장할 수 없습니다. 하지만 **val로 선언된 property는 getter를 통해 필요한 내용을 구현할 수 있습니다.**

```kotlin
interface MyInterface {
    val prop: Int // abstract

    val propertyWithImplementation: String // val 선언 시 getter 구현 가능
        get() = "foo"

    fun abMethod() // abstract

    fun foo() { // 일반 메서드
        print(prop)
    }
}

class Child : MyInterface {
    override val prop: Int = 29
}
```
## [Interfaces Inheritance](https://kotlinlang.org/docs/interfaces.html#interfaces-inheritance)

* Interface는 Interface를 상속받을 수 있습니다.

```kotlin
interface Named {
    val name: String
}

interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName $lastName"
}

data class Employee(
    // implementing 'name' is not required
    override val firstName: String,
    override val lastName: String,
    val position: Position
) : Person
```
## [Resolving overriding conflicts (오버라이딩 충돌 해결)](https://kotlinlang.org/docs/interfaces.html#resolving-overriding-conflicts)
```kotlin
interface A {
    fun foo() { print("A") }
    fun bar()
}

interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

class C : A {
    override fun bar() { print("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}
```

* class C의 경우 A에서만 파생되었기 때문에 bar()를 정의해주면 됩니다. 

* class D의 경우 A, B 모두를 구현하고 함수들을 어떻게 구현할 것인지 구체적으로 작성해야 합니다.
이 규칙은 Singel implementation(bar() 메서드)나 multiple implemntations(foo() 메서드) 모두 적용됩니다.

  -> 각각의 interface에 있다면 모호성 해결을 위해서 자식클래스에서의 오버라이딩이 강제된다. 
  이때 둘 중 하나를 선택하거나 둘 다를 쓰거나 완전 다른 방법을 쓰거나하는건 자신의 마음대로이다. ( **해당 내용이 정확히 맞는지는 모르겠다???** )
  


--------------------------------
### 참고
* [공식문서](https://kotlinlang.org/docs/classes.html)
* [Kamang's IT Blog](https://kamang-it.tistory.com/entry/Kotlin-08data-class?category=716880)
