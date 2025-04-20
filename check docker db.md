PostgreSQL 컨테이너 상태 확인: 도커 컨테이너의 상태를 확인하려면 다음 명령어를 사용하세요:
```sh
docker ps
```
이 명령어는 현재 실행 중인 모든 도커 컨테이너를 나열합니다. PostgreSQL 컨테이너가 실행 중인지 확인할 수 있습니다.  PostgreSQL 컨테이너에 접속: PostgreSQL 컨테이너에 접속하려면 다음 명령어를 사용하세요:
```
docker exec -it <컨테이너_ID> psql -U <사용자명> -d <데이터베이스명>
```

예를 들어, 사용자명이 myuser이고 데이터베이스명이 postgres인 경우:
```
docker exec -it 780b46a02263 psql -U myuser -d postgres
```

데이터베이스 상태 및 구조 확인: PostgreSQL 셸에 접속한 후, 다음 SQL 명령어를 사용하여 데이터베이스 상태와 구조를 확인할 수 있습니다:
-- 데이터베이스 목록 확인
```
\l
```

-- 현재 데이터베이스의 테이블 목록 확인
```
\dt
```
-- 특정 테이블의 구조 확인
```
\d <테이블명>
```

예를 들어, books 테이블의 구조를 확인하려면:
```
\d books
```