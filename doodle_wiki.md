# 프로젝트 wiki


### User

#### Login

- userName, Password를 통해 로그인
![image](https://github.com/seeeeeeong/doodle/assets/136677284/7e9d886c-6b7d-4f10-8989-fe9d8113169f)

- Cache에 User 정보가 있다면 UserCacheRepository에서 정보를 가져온다.
- Cache에 User 정보가 없다면 UserRepository에서 정보를 가져오고, Cache에 유저 정보를 저장한다.
![image](https://github.com/seeeeeeong/doodle/assets/136677284/400e10ee-ceea-42c8-8320-07f963910890)


- 로그인 성공시 JwtTokenProvider를 통해 accessToken, refreshToken을 발급한다.

<hr>

#### ArgumentResolver를 통한 인증처리 중복 제거

- 주어진 요청을 처리할 때, 메서드 파라미터를 인자값들에 주입
- HandlerMethodArgumentResolver를 이용하여 Custom Annotation을 통해 User 정보를 가져오도록 구현

**webConfig** 
![image](https://github.com/seeeeeeong/doodle/assets/136677284/43be8333-5460-4b30-aed8-c885b9e28325)


**AuthUser**
![image](https://github.com/seeeeeeong/doodle/assets/136677284/ac86d9ca-887a-4a78-89bb-ddfe0c40030d)


- Retention(RetentionPolicy.RUNTIME) : 런타임까지 Annotation 정보 유지
- Target(ElementType.PARAMETER) : 파라미터에 해당 Annotation을 사용 가능

**AuthUserArgumentResolver**
![image](https://github.com/seeeeeeong/doodle/assets/136677284/ab7343f3-fff7-42b9-8409-b5a86740dac0)


- HandlerInterceptorAdapter를 이용하여 사용자 정보 인증

**Controller**
![image](https://github.com/seeeeeeong/doodle/assets/136677284/e8cbb839-cbed-4593-9c9a-2bbe3b231f22)


- User 정보가 필요한 api를 요청할 때 @AuthUser를 인식하고 인증처리를 위한 ArgumentResolver 실행

<hr>

### Page

![image](https://github.com/seeeeeeong/doodle/assets/136677284/96fa0aac-9f3f-479a-9c4e-72b6a1f09a43)

- QueryDsl 을 사용하여 Page처리, content, countQuery 분리
- PageableExecutionUtils를 사용하여 countQuery를 필요한 시점(전체 페이지 수, 마지막 페이지 계산)에만 실행하여 쿼리 실행을 최소화하고 성능 최적화
<hr>

  
### Alarm

#### Alarm Entity

- userId 컬럼에 index
![image](https://github.com/seeeeeeong/doodle/assets/136677284/32ccfcb6-ab0b-41f6-bc88-004bd41777db)


- userId를 기준으로 알림을 조회할 때, 인덱스가 없으면 데이터베이스는 모든 행을 스캔
- 인덱스 사용시 효율적으로 userId를 가진 행을 찾을 수 있다.
<hr>


#### kafka

**Producer**

![image](https://github.com/seeeeeeong/doodle/assets/136677284/efe22c91-8558-4047-95d8-e4a664867f8c)

![image](https://github.com/seeeeeeong/doodle/assets/136677284/cfee0cc3-a6a0-40d2-9d72-4ab8ff515794)

- like, comment시에 userId 및 알람 정보를 담은 AlarmEvent를 Topic과 함께 kafkaTemplate.send()

**Consumer**

![image](https://github.com/seeeeeeong/doodle/assets/136677284/5987d1eb-9094-4638-8da4-408bf4019f3c)

- Alarm Topic의 파티션에서 메시지를 가져와 alarmService.send()
- ack.acknowledge() 함수로 Producer에게 메시지를 받았다는 응답
<hr>

#### SSE

- 이벤트가 서버에서 클라이언트 방향으로 흐르는 단방향 통신
- 서버에서 클라이언트로 실시간 데이터 전송

**사용자 subscribe 요청시 SseEmitter 생성**

![image](https://github.com/seeeeeeong/doodle/assets/136677284/92b8fbf0-4169-41aa-bf64-48dc0a47146f)


- SseEmitter의 완료 및 시간초과시 SseEmitter 제거
- 연결 성공시 알림 전송

**Emitter 객체 관리**
![image](https://github.com/seeeeeeong/doodle/assets/136677284/82517919-8c39-49a5-8257-cdf8975cb940)


- 일반 HashMap은 Thread-safe하지 않으므로 ConcurrentHashMap을 사용하도록 변경 필요

**subscribe중인 User의 게시물에 like, comment시 메시지 전달

![image](https://github.com/seeeeeeong/doodle/assets/136677284/55524b80-510e-452a-9d87-ec688608d8c5)
