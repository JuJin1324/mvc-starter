# mvc-starter

## 스펙
### Platform
> Framework: SpringBoot 2.5  
> Java: 11  

### DB
> DB: H2  
> Framework: JPA  

## 구성
### 데이터 객체 흐름
> RequestBody -> DTO -> Entity -> DTO -> ResponseBody   

## 테스트
### Response Body 객체
> 테스트에서 TestRestTemplate 으로 Response Body 객체를 가져오기 위해서는 해당 Response Body 객체에 @NoArgsConstructor 필요.  

### JPA
> [jpa-starter](https://github.com/JuJin1324/jpa-starter.git)

### Exception 처리
> [exception-starter](https://github.com/JuJin1324/exception-starter.git)

### Validation 처리
> [validation-starter](https://github.com/JuJin1324/validation-starter.git)

## 구조
### 도메인형 패키지 구조
> [Spring Guide - Directory 패키지 구조 가이드](https://cheese10yun.github.io/spring-guide-directory/)

## RequestBody 로깅시 주의사항
> 컨트롤러 이전 단(AOP, Interceptor, Filter)에서 RequestBody 를 로그로 남기기 위해서 HttpServletRequest 의 InputStream 을
> 통해서 RequestBody 를 읽어낸다. 하지만 HttpServletRequest의 InputStream 은 한번 읽으면 다시 못읽기 때문에 컨트롤러 이전 단에서 
> Request 를 읽은 다음 컨트롤러로 HttpServletRequest 를 넘기게 되면 빈 객체를 넘기게 됨으로 Exception 이 발생하게 된다.   
> 참조사이트: https://stuffdrawers.tistory.com/9   
