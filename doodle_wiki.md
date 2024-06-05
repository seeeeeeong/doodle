# 프로젝트 wiki


## redis 캐싱

token인증시에 db에서 user테이블을 조회하고, 그 이후 API 요청 마다 user 테이블을 반복해서 조회해야 하는 문제점이 있었다.
redis 캐싱을 도입하여 db 호출수를 최소화하였다.

![UserCacheRepository](https://github.com/seeeeeeong/doodle/assets/136677284/ffc16b55-b5a8-4dcb-a80a-39b9b44a53d9)

<hr>

## Kafka + SSE


실시간 알림같은 경우는 양방향 통신이 필요 없고 일방적으로 서버에서 클라이언트에게 데이터를 보내주기만 하면 되기 때문에 
websockets 대신 SSE를 사용하는것이 더 적합하다고 판단하며 SSE로 실시간 알림을 구현하게 되었다.

alarm api를 호출해야만 갱신되는 알람을 실시간으로 전달하기 위해 Kafka를 도입하였다.
<br/>
![like](https://github.com/seeeeeeong/doodle/assets/136677284/35bdc060-c061-4a25-9883-ff973b7f33b9)
![comment](https://github.com/seeeeeeong/doodle/assets/136677284/6465b2ab-0499-428f-b2b6-50df7f80cc77)

알람을 구독한 유저의 게시물에 댓글이나 좋아요 이벤트가 발생했을 경우 
실시간으로 알람을 전송한다.

## Query 최적화

데이터베이스로 날아가는 Query들은 최적화가 되었을까?
<br/>

![fetch](https://github.com/seeeeeeong/doodle/assets/136677284/58cb570f-6d0e-4f87-8888-37f5d92ae7db)

지연로딩의 경우 해당 연결 entity에 대해서 프록시로 걸어두고, 사용할 때 쿼리문을 날리기 때문에 <br/>
처음 find할 경우 N+1이 발생하지 않지만 추가로 find시에 N+1 문제가 발생한다.

![query](https://github.com/seeeeeeong/doodle/assets/136677284/79cbd954-dc63-40da-ad5c-14f22cd978e3)

실제 join native query를 작성하여 fetch가 된 query를 사용하여 실질적으로 N+1 문제를 해결하였다.


![delete](https://github.com/seeeeeeong/doodle/assets/136677284/194098c6-2ecb-4d12-a4f0-c2bd1fe77c8e)

데이터베이스에서 데이터를 가지고 올 때 데이터의 변화를 영속성 관리를 통해 체크한다.
영속성을 관리하기 위해서는 데이터베이스에서 데이터를 가지고 와야한다.

JPA의 deleteAllByPost의 경우 삭제할 데이터들을 불러와 삭제하게 된다.
데이터의 조회가 필요하지 않기 때문에 JPA에서 delete를 할 경우 직접 쿼리를 작성하여 삭제하는것이 효율적이다. 






