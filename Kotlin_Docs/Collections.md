# [Collection](https://kotlinlang.org/docs/collections-overview.html#collection)

* 자주 사용되는 기초적인 자료구조를 모아놓은 일종의 프레임워크로 표준 라이브러리로 제공된다. 
* Kotlin의 Collection은 Java 와는 다르게 불변형(immutable)과 가변형(mutable)로 나뉘어 colleciton을 다룰 수 있다.  

![image](https://user-images.githubusercontent.com/29828988/111117982-8f98f500-85ab-11eb-9658-c4786f23bb42.png)
###### ( 출처 : https://www.boostcourse.org/mo234/lecture/154314/?isDesc=false )

</br>
* Collection Interface의 member

| 멤버 | 설명 |
|----------|----------|
| `size` | 크기를 나타냄 |
| `isEmpty()` | 비어있는 경우 true 반환 |
| `contains(element: E)` | 특정 요소가 있다면 true를 반환 |
| `containAll(element: Collection<E>)` | 인자로 받아들인 Collection이 있다면 true를 반환 |

-----------------------------------------
# List

* 순서에 따라 정렬된 요소를 가지는 Collection
* 불변형 List = `listOf()`
* 가변형 List = `mutableListOf()`
* 원하는 만큼의 인자를 가지도록 vararg로 선언할 수 있습니다.
* List<T>는 하위에 특정한 자료구조로 구현된 LinkedList<T>, ArrayList<T> 등이 있습니다. 코틀린에서 List의 default 구현은 ArrayList로 되어 있습니다.

```kotlin
fun main() {
    var numbers: List<Int> listOf(1,2,3,4,5)
    var names: List<String> = listOf("one", "two", "three")

    println("numbers : $numbers")
  
    for (name in names) print(name)
}
```

* List 의 member method

| 멤버 | 설명 |
|----------|----------|
| `get(index: Int)` | 특정 인덱스를 인자로 받아 해당 요소를 반환한다. |
| `indexOf(element: E)` | 인자로 받은 요소가 첫 번째로 나타나는 인덱스를 반환하며, 없으면 -1을 반환한다. |
| `lastIndexOf(element: E)` | 인자로 받은 요소가 마지막으로 나타나는 인덱스를 반환하고, 없으면 -1을 반환한다. |
| `listIterator()` | 목록에 있는 iterator를 반환한다. |
| `subList(fromIndex: Int, toIndex: Int)` | 특정 인덱스의 from과 to 범위에 있는 요소 목록을 반환한다. |

</br>

### ArrayListOf()

* `arrayLIstOf()`는 가변형 List를 생성하지만 이것의 반환 자료형은 Java의 Arrayist입니다.

```kotlin
fun main() {
    /*
        * val 변수는 stringList 변수에 다른 형식의 재할당을 금지한다.
        * 이미 할당된 list의 추가 / 삭제는 무관하므로 stringList에 add, remove를 사용할 수 있다.
    */
    val stringList: ArrayList<String> = arrayListOf<String>("Hello", "Kotlin", "Wow")
    stringList.add("Java")
    stringList.remove("Hello")
    println(stringList)
}
```

-----------------------------------------
# Set

* 정해진 순서가 없는 요소들의 집합(set)
* **동일한 요소를 중복해서 가질 수 없습니다.** 동일한 요소가 여러 개 있으면 하나만 있는 것처럼 취급됩니다.
* 불변형 set = `setOf()`
* 가변형 set = `mutableSetOf()`
* 코틀린에서 Set 의 default 구현은 LinkedHashSet 입니다.

```kotlin
fun main() {
    val mixedTypesSet = setOf("Hello", 5, "world", 3.14, 'c') // 자료형 혼합 초기화
    var intSet: Set<Int> = setOf<Int>(1, 5, 5)  // 정수형만 초기화

    println(mixedTypesSet)
    println(intSet)
}
```

## Set의 여러 가지 자료구조

### 1. `hashSetOF()` 함수

* Java의 HashSet Collection을 생성
* 불변성 선언이 없기 때문에 추가, 삭제 등의 기능을 수행할 수 있습니다.

```kotlin
fun main() {
    val intsHashSet: HashSet<Int> = hashSetOF(6, 3, 5, 4)
    intsHashSet.add(1)
    intsHashSet.remove(5)
}
```

### 2. `sortedSetOf()` 함수

* Java의 TreeSet Collection을 정렬된 상태로 반환
* java.util.* 패키지를 임포트 해야 한다.
* TreeSet은 저장된 데이터의 값에 따라 정렬된다. ( binaary-search tree , Rea-Black 트리 알고리즘 이용 )
* HashSet보다 성능이 떨어지고 데이터를 추가하거나 삭제하는 시간이 걸리지만 검색과 정렬이 뛰어나다.

```kotlin
import java.util.*

fun main() {
    val intsSortedSet: TreeSet<Int> = sortedSetOf(4, 7, 1, 2)
    intsSortedSet.add(6)
    intsSortedSet.remove(1)
    intsSortedSet.clear() // 모든 요소 삭제
}
```

### 3. `linkedSetOf()` 함수

* Java의 LinkedHashSet 자료형을 반환
* HashSet, TreeSet보다 느리지만 메모리 저장 공간을 좀 더 효율적으로 사용

-----------------------------------------
# Map

* 키(key)와 값(value)로 구성된 요소를 저장 ( 키와 값 모두 객체이다. )
* 키는 중복될 수 없지만 값은 중복 저장될 수 있다. ( 동일한 키와 값을 저장할 때, 이미 있는 경우 새로운 값으로 대체된다. )
* 불변형 : mapOf()
* 가변형 : mutableMapOf()
* Map 의 default 구현은 LinkedHashMap코틀린에서 입니다.

```kotlin
fun main() {
    // 불변형 Map의 선언 및 초기화
    val langMap: Map<Int, String> = mapOf(11 to "Java", 22 to "Kotlin", 33 to "C++")
    
    for ((key, value) in langMap) { // 키와 값의 쌍을 출력
        println("key=$key, value=$value")
    }
    println("langMap[22] = ${langMap[22]}") // 키 22에 대한 요소 출력
    println("langMap.get(22) = ${langMap.get(22)}") // 위와 동일한 표현
    println("langMap.keys = ${langMap.keys}") // 맵의 모든 키 출력
}
```
</br>

* **Map 의 member method**

| 멤버 | 설명 |
|----------|----------|
| `size` | Map Collection의 크기를 반환 |
| `keys` | Set의 모든 키를 반환 |
| `values` | Set의 모든 값을 반환 |
| `isEmpty()` | 맵이 비어있는지 확인하고 비어 있으면 true를 아니면 false를 반환 |
| `containsKey(key: K)` | 인자에 해당하는 키가 있다면 true를 아니면 false를 반환 |
| `containsValue(value: V)` | 인자에 해당하는 값이 있다면 true를 아니면 false를 반환 |
| `get(key: K)` | 키에 해당하는 값을 반환하며, 없으면 null을 반환 |

</br>

* **MutableMap 의 추가, 삭제 method**

| 멤버 | 설명 |
|----------|----------|
| `put(key: K, value: V`) | 키와 값의 쌍을 맵에 추가한다. |
| `remove(key: K)` | 키에 해당하는 요소를 맵에서 제거한다. |
| `putAll(from: Map<out K, V>)` | 인자로 주어진 Map 데이터를 갱신하거나 추가한다. |
| `clear()` | 모든 요소를 지운다. |

```kotlin
fun main() {
    // 가변형 Map의 선언 및 초기화
    val captialCityMap: MutableMap<String, String> = MutableMapOf("Korea" to "Seoul", "China" to "Beijing", "Japan" to "Tokyo")
    
    captialCityMap,put("UK", "London") // 추가
    captialCityMap.remove("China") // 삭제
  
    val addData = mutableMapOf("USA" to "Washington")
    captialCityMap.puAll(addData) // 기존 Map에 새로운 Map 객체를 
}
```

-----------------------------------------
# Standard library
1. 연산자 ( Operators ) : 더하고 빼는 등의 기능
2. 집계 ( aggregators ) : 최대, 최소. 집합. 총합 등의 계산 기능
3. 검사 ( checks ) : 요소를 검사하고 순환하기 위한 기능
4. 필터 ( filtering ) : 원하는 요소를 골라내기 위한 기능
5. 변환 ( transformers ) : 뒤집기, 정렬, 자르기 등의 변환 기능

| functions | 설명 |
|-------------|-------------|
|forEach | 각 요소를 람다식을 처리한 후 컬랙션을 반환하지 않음 |
|forEachIndexed |  | 
|onEach | 각 요소를 람다식으로 처리한 후 컬렉션을 반환 받음 |
|fold | 초기값과 정해진 식에 따라 처음 요소부터 끝 요소에 적용해 값 반환|
|reduce | fold와 동일하지만 초기값을 사용하지 않는다.|
|foldRight |  |
|reduceRight | reduce의 개념과 동일하지만 요소의 마지막(오른쪽) 요소에서 처음 요소로 순서대로 적용 |
|filter |  |
|filterNot | 식 이외에 요소 |
|filterNotNull | null을 제외함 |
|filterIndexed | 두 개의 인자를 람다식에서 받아서 각각 인덱스와 값에 대해 특정 수식에 맞는 조건을 골라낼 수 있다. |
|filterIndexedTo | 여기에 컬랙션으로 반환되는 기능이 추가 |
|slice | 특정 범위 잘라내기 |
|take | n개의 요소를 반환 |
|drop |  |
|distinct | 여러 중복 요소가 있는 경우 한 개로 취급해 다시 컬렉션 List로 반환 |
|intersect | 교집합 요소만 골라내 컬렉션 List로 반환 |
|map | 컬렉션에 주어진 식을 적용해 새로운 컬렉션을 반환 |
|mapIndexed | 컬렉션에 인덱스를 포함해 주어진 식을 적용해 새로운 컬렉션 반환 |
|mapNotNull | null을 제외하고 식을 적용해 새로운 컬렉션 반환 |
|flatMap() | 각 요소에 식을 적용 후 다시 합쳐 새로운 컬렉션을 반환 |
|groupBy() | 주어진 식에 따라 요소를 그룹화하고 이것을 다시 Map으로 반환 |
|element | 인덱스와 함께 해당 요소의 값을 반환한다. 인덱스를 벗어나면 null을 반환한다.|
