# Naver Document Search & Save Web Project

## 프로젝트 소개

네이버 OpenAPI를 활용하여 전문자료(문서) 검색 결과를 조회하고, 원하는 데이터를 데이터베이스에 저장할 수 있는 웹 기반 검색·저장 프로젝트입니다.
Java Servlet과 JSP를 기반으로 구현되었으며, 외부 API 연동 → 데이터 가공 → 화면 출력 → DB 저장까지 백엔드 전반 흐름을 경험하기 위해 제작되었습니다.

해당 프로젝트는 개발 초기 단계에서 4인 팀으로 진행되었으며,
웹 서비스의 기본 구조와 서버-데이터 흐름을 이해하는 것을 목표로 진행된 실습형 프로젝트입니다.

---

## 기술 스택

* Language: Java
* Backend: Java Servlet, JSP
* Database: MySQL
* API: Naver OpenAPI (전문자료 검색 API)
* Library: Gson
* Server: Apache Tomcat
* Frontend: HTML, CSS, JavaScript

---

## 주요 기능

### 1. 네이버 전문자료 검색 기능

사용자가 검색어를 입력하면 네이버 OpenAPI를 호출하여 전문자료 검색 결과를 가져옵니다.

* 검색어 기반 API 요청 처리
* JSON 데이터 파싱(Gson)
* 검색 결과 제목, 링크, 발행처 추출
* 검색 결과 최대 100건 수집 및 가공

---

### 2. 페이지네이션 처리

대량의 검색 데이터를 효율적으로 확인할 수 있도록
페이지 단위 조회 기능을 구현했습니다.

* 페이지당 10개 데이터 출력
* 최대 10페이지 구성
* 현재 페이지 기준 데이터 분할 처리
* 세션을 활용한 전체 검색 데이터 관리

---

### 3. 검색 결과 테이블 출력

검색 결과를 JSP 화면에서 테이블 형태로 출력합니다.

출력 항목:

* 제목(title)
* 링크(link)
* 발행처(publisher)

검색 결과의 HTML 태그를 제거하여 가독성을 개선했습니다.

---

### 4. 검색 결과 DB 저장 기능

조회된 검색 데이터를 MySQL DB에 저장할 수 있는 기능을 구현했습니다.

* 저장 버튼 클릭 시 DB 저장 요청
* JDBC 기반 DB 연결
* PreparedStatement 사용
* 검색 결과 데이터 DB INSERT 처리

테이블 예시:

```
CREATE TABLE naver_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title TEXT,
    link TEXT,
    publisher VARCHAR(255)
);
```

---

## 담당 역할 (Backend - API 연동 및 데이터 처리)

프로젝트에서 **백엔드 데이터 처리 및 API 연동 중심 역할**을 담당했습니다.

### 1. 네이버 OpenAPI 연동 구현

* 검색 API 요청 URL 구성
* HttpURLConnection을 활용한 API 호출
* JSON 응답 데이터 수신 및 처리
* Gson을 활용한 데이터 파싱 로직 구현

### 2. 검색 데이터 가공 및 서버 처리

* 검색 결과(title, link, publisher) 추출
* HTML 태그 제거 및 문자열 정제
* 페이지별 데이터 분할 처리 로직 구현
* 세션을 활용한 검색 데이터 관리

### 3. Servlet 기반 서버 흐름 구현

* SearchServlet: 검색 요청 처리 및 결과 반환
* SaveServlet: DB 저장 요청 처리
* JSP와 Servlet 간 데이터 전달 구조 구현
* request / session 객체 활용 경험

### 4. DB 저장 로직 구현

* MySQL DB 연결 설정
* JDBC 기반 INSERT 처리
* PreparedStatement 사용
* 검색 결과 데이터 저장 기능 구현

### 5. 협업 기여

* 기능 구조 설계 및 흐름 정리 참여
* API 응답 데이터 구조 분석
* 팀원 기능 통합 과정에서 데이터 흐름 정리

---

## 프로젝트 구조

```
project
 ┣ src
 ┃ ┣ SearchServlet.java     # 네이버 API 호출 및 검색 처리
 ┃ ┗ SaveServlet.java       # DB 저장 처리
 ┣ WebContent
 ┃ ┣ Search.jsp             # 검색 UI 및 결과 출력
 ┗ database
   ┗ MySQL 테이블
```

---

## 동작 흐름

1. 검색어 입력
2. SearchServlet → 네이버 OpenAPI 호출
3. JSON 데이터 파싱 및 가공
4. JSP 페이지에 결과 테이블 출력
5. 저장 버튼 클릭
6. SaveServlet → MySQL DB 저장

---

## 프로젝트를 통해 배운 점

### 외부 API 연동 경험

OpenAPI 호출 → JSON 파싱 → 데이터 가공 → 화면 출력까지
실제 서비스에서 사용하는 API 처리 흐름을 직접 경험했습니다.

### 서버-데이터 흐름 이해

Servlet과 JSP 구조를 활용하여
클라이언트 요청 → 서버 처리 → DB 저장 흐름을 이해했습니다.

### DB 연동 및 백엔드 기초 역량 강화

JDBC 기반 DB 연결 및 SQL 실행을 통해
백엔드 개발의 기본적인 데이터 처리 구조를 경험했습니다.

### 협업 기반 개발 경험

초기 팀 프로젝트로
기능 분담, 코드 통합, 역할 조율 등
협업 개발 프로세스를 경험했습니다.

---

## 개선 가능 포인트 (리팩토링 방향)

* API 키 환경변수 분리
* MVC 패턴 구조 개선
* 중복 데이터 저장 방지
* 비동기 처리(Ajax)
* UI/UX 개선
* 코드 모듈화(Service/DAO 분리)

---

## 실행 방법

1. 네이버 개발자 센터에서 OpenAPI 발급
2. MySQL DB 생성 및 테이블 생성
3. Tomcat 서버 실행
4. `/search` 경로 접속 후 검색 사용

---

## 프로젝트 의의

개발 초기 단계에서 진행한 프로젝트로,
외부 API 활용, 서버 처리 흐름, DB 연동까지
백엔드 개발의 핵심 구조를 직접 구현해 본 경험입니다.

이 프로젝트를 통해 단순 이론이 아닌
실제 동작하는 웹 서비스의 전체 흐름을 이해할 수 있었으며,
이후 Spring 기반 백엔드 학습의 기초가 된 프로젝트입니다.
