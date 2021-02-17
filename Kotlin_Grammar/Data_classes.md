# Data classes

* Java의 POJO(Plain Old Java Object)
* 순수한 데이터 객체를 표현합니다.
* ( property만 잘 작성하면 ) getter와 setter를 자동 생성합니다.

## 자동 생성되는 메서드들
Method 									| 설명
------------------------|------------------------
getter / setter					|
equals() 								| 두 객체의 내용이 같은지 비교 ( == 와 동일하다. ) / **고유값은 다르지만 의미는 값을 때**
hashCode()      				| **객체를 구별**하기 위한 고유한 정수값 생성
toString()      				| 데이터 객체를 읽기 편한 문자열로 반환
copy()          				| 빌더 없이 특정 property만 변경해서 **객체 복사**
componentN() functions 	| property 선언 순서에 따라 대응한다. / 객체의 선언부 구조를 분해하기 위해 property에 상응하는 메서드

## 선언하기

```kotlin
data class User(val name: String, val age: Int)
```

```kotlin
data class User(val name: String = "", val age: Int = 0)
```

### 조건
1. 주요 생성자에는 적어도 하나의 parameter가 필요합니다.
2. 모든 주요 생성자 parameter는 val 혹은 var 로 지정된 property여야 합니다.
3. 데이터 클래스는 abstract, open, sealed, inner 키워드를 사용할 수 없다.

## [Properties declared in the class body](https://kotlinlang.org/docs/data-classes.html#properties-declared-in-the-class-body)

* 필요하다면 추가로 부 생성자나 init 블록을 넣어 데이터를 위한 간단한 로직을 포함할 수 있습니다.
```kotlin
data class Person(val name: String) {
    var age: Int = 0
}
```

```kotlin
data class Person(var name: String, var age: Int) {
  var job: String = "Unknown"
  constructor(name: String, age: Int, _job: String): this(name, age) {
    job = _job
  }
  init {
    // 로직
  }
}
```

## [copy()](https://kotlinlang.org/docs/data-classes.html#copying)
```kotlin
val jack = User(name = "Jack", age = 1)
val olderJack = jack.copy(age = 2) // jact에서 age만 바꿔서 복사하기
```

## [destructuring declarations (디스트럭처링)](https://kotlinlang.org/docs/data-classes.html#data-classes-and-destructuring-declarations)

* 객체가 가지고 있는 property를 개별 변수들로 분해합니다.
* 특정 property를 가져올 필요가 없는 경우 해당 property를 _로 처리합니다.

```kotlin
val jane = User("Jane", 35)

val (name, age) = jane
println("$name, $age years of age") // prints "Jane, 35 years of age"

// 특정 property를 가져올 필요가 없는 경우
val (_, age) = jane // 첫 번째 property 제외
```

### componentN()
```kotlin
val jane = User("Jane", 35)

val name = jane.component1()
val age = jane.component2()
println("$name, $age years of age") // prints "Jane, 35 years of age"
```

### 객체 데이터가 많은 경우 for문의 활용
```kotlin
val person1 = User("Jane", 35)
val person1 = User("Sean", 21)
val bob = User("Bob", 3)
val erica = User("Erica", 16)

val people = listof(person1, person2, bob, erica)
...
for((name, age) in people) { // 반복문을 이용해 모든 객체의 property 분해
	println("$name, $age years of age")
}
```

### 함수로부터 객체가 반환되는 경우 destructuring
```kotlin
fun myFunc(): User {
	return User("Michey", 12)
}

...
val (myName, myAge) = myFunc()
```

### 람다식 함수로 Destructuring
```kotlin
val myLamda = {
	(nameLa, ageLa): User ->
	println(nameLa)
	println(ageLa)
}
myLamda(jane)
```
