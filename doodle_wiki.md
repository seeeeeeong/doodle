# 프로젝트 wiki


### 캐시


login의 경우 db에서 user테이블을 조회하고, 이후 접속시에 매번 DB에 접속하여 User조회한다.

![UserCacheRepository](https://github.com/seeeeeeong/doodle/assets/136677284/cc317d72-a9e6-4b57-b453-4d480a1a07c2)

User 데이터 조회를 매번 DB에서 조회 하는게 아니라 캐시를 활용하여 성능 개선 <br/>
- DB Connection을 맺을 때 많은 비용이 발생<br/>
- DB 조회시보다 캐시에서 데이터 조회가 더 빠르다

  

<hr>

###  인덱스

userId 기반으로 Alarm 검색 쿼리 발생시 Index를 사용하여 성능 향상

![Index](https://github.com/seeeeeeong/seong-blog/assets/136677284/848938b2-50bf-4305-b20e-d2f2b536e5fb)

Alarm 엔티티에서 User 인덱스를 설정 <br>
- 검색 : user_id 열에 인덱스를 생성하여 특정 사용자에 대해 검색할 때 데이터베이스에서 효율적으로 데이터 조회 <br>
- 조회 : @ManyToOne(fetch = FetchType.LAZY)와 함께 사용하는 user_id 인덱스는 Alarm 엔티티에서 User 정보를 필요로 하는 경우, 인덱스를 통해 조인 시 더 효율적으로 데이터를 찾아올 수 있다.
<hr>

### 비동기

작성자의 게시물에 댓글, 좋아요가 발생했을 경우 알림을 한다면
대상이 많아져 순차적으로 알림을 발송하는 방식에 한계가 온다면?

![topic](https://github.com/seeeeeeong/seong-blog/assets/136677284/30d150a5-bf6a-450a-b29e-7d9e565acfa3)
![kafaka](https://github.com/seeeeeeong/seong-blog/assets/136677284/02fb5cff-4c21-4759-aa4c-9c0d5a5ce02c)
![send](https://github.com/seeeeeeong/seong-blog/assets/136677284/dd9f4679-2e4e-4451-adf6-97fc925061c8)



alarm api를 호출해야만 갱신되는 알람을 실시간으로 전달하기 위해 Kafka를 도입하였다.


<hr>

