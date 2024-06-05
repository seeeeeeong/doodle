<div align="center">
  <br>
  <h2> Doodle </h2>
  <h1> 커뮤니티 플랫폼 </h1>
  <strong>API 서버 레포지토리</strong>
</div>
<br>

### 프로젝트 아키텍처
![doodle](https://github.com/seeeeeeong/doodle/assets/136677284/90e2959a-8f2c-49ba-a509-2d16f98d9f2b)


### 기술 스택

- Java 17
- Gradle
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security
- QueryDSL
- PostgreSQL
- Redis
- Junit5, Mockito

### 프로젝트 중점사항

- Spring Security + jwt를 활용한 토큰 기반 인증 구현
- DB 조회를 최소화 하기 위헤 user 데이터는 redis 캐시를 활용
- alarm api의 경우 kafka를 활용하여 비동기적으로 처리
- 알람 데이터 조회시 인덱스를 활용하여 조회 성능 개선


### 프로젝트 위키

[WIKI](/doodle_wiki.md)




