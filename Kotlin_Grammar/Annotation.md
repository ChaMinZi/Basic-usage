# [Annotation](https://kotlinlang.org/docs/annotations.html)

* 코드에 부가 정보를 추가하는 기능
* @ 기호와 함께 나타내는 표기법으로 주로 컴파일러나 프로그램 실행 시간에서 사전 처리를 위해 사용합니다.

```kotlin
annotation class Fancy
```

## 속성
Name | Describe
------|-------
@Target | Annotation이 지정되어 사용할 종류를 정의 ( classes, functions, properties, expressions 등 )
@Retention    | Annotation을 컴파일된 class 파일에 저장할 것인지 실행 시간에 반영할 것인지 결정
@Repeatable    | Annotatino을 같은 요소에 여러 번 사용 가능하게 할지를 결정
@MustBeDocumented   | Annotation이 API의 일부분으로 문서화하기 위해 사용

<br>

* example
```kotlin
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)  // SOURCE : 컴파일 시간에 제거된다.
@MustBeDocumented
annotation class Fancy
```

## [Usage](https://kotlinlang.org/docs/annotations.html#usage)
```kotlin
@Fancy class Foo {
    @Fancy fun baz(@Fancy foo: Int): Int {
        return (@Fancy 1)
    }
}
```


# !!!!추가 해야 함!!!!
