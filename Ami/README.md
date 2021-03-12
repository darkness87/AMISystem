# **AMI Server System**
* SpringBoot
* REST API
* Webflux : 어노테이션 방식으로 사용하여 코드 진행
* **JPA**
* __Criteria__

<hr/>

* API Postman 문서: <https://web.postman.co/collections/2091090-d514c2a8-3ee1-56cf-fdb8-69f515104382?workspace=81c051f1-515b-48ca-84fb-dd7b238e6c6f>

<hr/>

# 패키지명
>	src/main/java
>	> com/cnu/ami/
>	>	> common 			// 공통

>	>	> controller 		// API 호출 부분

>	>	> dao 				// JPA DataBase

>	>	> models 			// VO

>	>	> scheduler			// 스케줄 관련 (검토)

>	>	> security 			// 로그인 및 보안 관련

>	>	> service 			// 서비스

>	>	> util 				// 유틸

>	src/main/resources		// 프로퍼티,설정 파일

<hr/>

# API 호출
* 공통 : /api/{화면별}/{메뉴별}/{상세}
* 기본포트 : 9099

```
** type **
전기 : electric
가스 : gas
수도 : water
온수 : hotwater
난방 : heating
```

```
** 화면별 **
현황판 : dashboard
설비 : device
검침 : metering
요금 : bill
장애 : failure
분석 : analysis
고객지원 : support
설정 : settings

```

```
** 기능별 / 상세별 예시 **
로그인/공통예시 : /api/login/,
		    	/api/login/registration
대쉬보드예시 : /api/dashboard/electric/hour
메뉴데이터예시 : /api/device/menu1/electric/hour
```

* 해당 API 호출 내용은 프로그램을 개편할 때마다 업데이트가 될 수 있습니다.
* 위 사항은 API 호출 규칙을 정하고자 작성된 내용입니다.


<hr/>


# 메서드 생성 키워드 (JPA)

* JPA 참조링크: <https://blog.jiniworld.me/34>
* CriteriaQuery 참조링크 : <http://millky.com/@origoni/post/783>

## And
```
* findByNameAndType
* select e from #{#entityName} e where e.name = ?1 and e.type = ?2
```

## Or
```
* findByNameOrType
* select e from #{#entityName} e where e.name = ?1 or e.type = ?2
```

## Is, Equals
```
* findByType
* findByTypeIs
* findByTypeEquals
* select e from #{#entityName} e where e.type = ?1
```

## Between
```
* findByOpenDateBetween
* select e from #{#entityName} e where e.openDate between ?1 and ?2
```

## LessThan
```
* findByAgeLessThan
* select e from #{#entityName} e where e.age < ?1
```

## LessThanEqual
```
* findByAgeLessThanEqual
* select e from #{#entityName} e where e.age <= ?1
```

## GreaterThan
```
* findByAgeGreaterThan
* select e from #{#entityName} e where e.age > ?1
```

## GreaterThanEqual
```
* findByAgeGreaterThanEqual
* select e from #{#entityName} e where e.age >= ?1
```

## After
```
* findByOpenDateAfter
* select e from #{#entityName} e where e.openDate > ?1
```

## Before
```
* findByOpenDateBefore
* select e from #{#entityName} e where e.openDate < ?1
```

## IsNull, Null
```
* findByAge(Is)Null
* select e from #{#entityName} e where e.age is null
```

## IsNotNull, NotNull
```
* findByAge(Is)NotNull
* select e from #{#entityName} e where e.age not null
```

## Like
```
* findByTypeLike
* select e from #{#entityName} e where e.type like ?1
```

## NotLike
```
* findByTypeNotLike
* select e from #{#entityName} e where e.type not like ?1
```

## StartingWith
```
* findByTypeStartingWith
* select e from #{#entityName} e where e.type like %?1
```

## EndingWith
```
* findByTypeEndingWith
* select e from #{#entityName} e where e.type like ?1%
```

## Containing
```
* findByTypeContaining
* select e from #{#entityName} e where e.type like %?1%
```

## OrderBy
```
* findByAgeOrderByNameDesc
* select e from #{#entityName} e where e.age = ?1 order by e.name desc
```

## Not
```
* findByNameNot
* select e from #{#entityName} e where e.name <> ?1
```

## In
```
* findByAgeIn(Collection ages)
* select e from #{#entityName} e where e.age in ?1
```

## NotIn
```
* findByAgeNotIn(Collection ages)
* select e from #{#entityName} e where e.age not in ?1
```

## True
```
* findByActiveTrue()
* select e from #{#entityName} e where e.active = true
```

## False
```
* findByActiveFalse()
* select e from #{#entityName} e where e.active = false
```

## IgnoreCase
```
* findByTypeIgnoreCase
* select e from #{#entityName} e where UPPER(e.type) = UPPER(?1)
```

<hr/>

# API status code

* 200 - ok
	-request 성공
	-ex) resource 목록/resource 상세/resource 수정/그외 대부분의 API 성공

* 201 - create
	-request 성공
	-ex) resource 생성 성공

* 204 - no content
	-request 성공
	-ex) resource 삭제 성공

* 400 - bad_request
	-request 실패
	-ex) 유효성 검사 통과 실패, 잘못된 요청

* 401 - unauthorized
	-인증 실패
	-ex) 세션 없음, 로그인 실패

* 403 - forbidden
	-인증은 성공했으나 권한이 없음
	-ex) 권한없는 자원에 접근하려함

* 404 - not_found
	-API 없음
	-ex) route 조회 실패
* 500 - internal_server_error
	-오류
	ex) 서버오류, exception
