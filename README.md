<div align="center">
  <br>
  <h2> Doodle </h2>
  <h1> 커뮤니티 플랫폼 </h1>
  <strong>API 서버 레포지토리</strong>
</div>
<br>

### 프로젝트 설명

본 프로젝트를 통해 아래와 같은 경험과 지식을 쌓고 있습니다.

- **kafka + SSE를 사용한 실시간 알림 전송 기능 구현**
- JPA, Hibernate를 사용한 도메인 설계 경험
- 성능 향상을 위한 고민과 도전 경험
- Test 코드 작성을 통한 안정성 확보

## 기술 스택

- Java 17
- Gradle
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security
- QueryDSL
- PostgreSQL
- Redis
- Junit5, Mockito

## 프로젝트 위키

### SSE (Server-Sent Event)

![sse](https://github.com/seeeeeeong/doodle/assets/136677284/058d1c11-62d1-4138-9c41-1444516c5471)

- 서버에서 웹브라우저로 데이터를 보내줄 수 있다.
- 웹브라우저에서는 서버쪽으로 특정 이벤트를 구독함을 알려준다.
- 서버에서는 해당 이벤트가 발생하면 웹브라우저쪽으로 이벤트를 보내준다.
- 서버에서 웹브라우저로만 데이터 전송이 가능하고 그 반대는 불가능하다.
- 최디 동시 접속 횟수가 제한되어 있다.

### Kafka

![kafka](https://github.com/seeeeeeong/doodle/assets/136677284/4adadaf2-f067-44c9-bc80-989bbc4a9284)

###### 출처: kafka.apache.org/documentation

- 프로듀서 메시지 생성 - 브로커 파일 작성 - 컨슈머 메시지 확인 - ack
- Event의 KEY를 바탕으로 파티셔닝, 컨슈머에서 메시지 확인시에 순서를 보장한다.
- Topic : 프로듀서와 컨슈머를 연결
- 파티셔닝 : 순서를 보장한다
- 메시지 = 이벤트 -> key를 설정


