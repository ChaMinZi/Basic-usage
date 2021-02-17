# Enum classes

자료형이 동일한 상수를 나열할 수 있다. ( 다양한 자료형을 다루지는 못한다. )

* 단순 선언 
```kotlin
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
```

* 초기화
```kotlin
enum class Color(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
}
```

## [Implementing interfaces in enum classes](https://kotlinlang.org/docs/enum-classes.html#implementing-interfaces-in-enum-classes)

```kotlin
interface Score {
    fun getScore(): Int
}

enum class MemberType(var prio: String) : Score {
    NORMAL("Thrid") {
        override fun getScore(): Int = 100
    }
    SILVER("Second") {
        override fun getScore(): Int = 500
    }
    GOLD("First") {
        override fun getScore(): Int = 1000
    }
}

fun main() {
    println(MemberType.NORMAL.getScore())
    println(MemberType.GOLD)
    println(MemberType.valueOf("SILVER"))
    println(MemberType.SILVER.prio)
    
    for (grade in MemberType.values()) {  // 모든 값을 가져오기 위한 반복문 
        println("grade.name = ${grade.name}, prio = ${grade.prio}")
}
```

## [Anonymous classes ( 무명 클래스 )](https://kotlinlang.org/docs/enum-classes.html#anonymous-classes)

Enum constants는 기본 method를 재정의할 뿐만 아니라 해당 method를 사용하여 자체 익명 클래스를 선언할 수도 있습니다.

```kotlin
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}
```

## 메서드가 포함된 경우
```kotlin
enum class Color(val r: Int, val g: Int, val b: Int) {
        RED(255, 0, 0),
        ORANGE(255,165,0),
        YELLOW(255,255,0),
        GREEN(0x00FF00),
        BLUE(0x0000FF),
        INDIGO(75,0,130),
        VIOLET(238,130,238); // 메서드를 포함하는 경우 세미콜론으로 끝을 알려줘야 합니다.
        
        fun rgb() = (r * 256 + g) * 256 + b // 메서드를 포함할 수 있다.
}

fun main(args: Array) {
      println(Color.BLUE.rgb())
}
```

## [Working with enum constants](https://kotlinlang.org/docs/enum-classes.html#working-with-enum-constants)

```kotlin
EnumClass.valueOf(value: String): EnumClass
EnumClass.values(): Array<EnumClass>
```

* `valueOf()` 메소드에 지정한 enum 상수가 없는 경우 `IllegalArgumentException` 예외가 발생합니다.

* Kotlin 1.1 부터 `enumValues<T>()` 및 `enumValueOf<T>()` 함수를 사용하여 enum 클래스의 상수에 접근 가능합니다.

* 모든 Enum constant는 enum class에서의 이름과 위치를 가져오는 property를 가집니다. 

```kotlin
val name: String
val ordinal: Int
```
```kotlin
enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

printAllValues<RGB>() // prints RED, GREEN, BLUE
```

