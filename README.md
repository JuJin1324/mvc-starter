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
