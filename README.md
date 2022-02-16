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
