# Naver Document Search & Save Web Project

## 프로젝트 소개

네이버 OpenAPI를 활용하여 전문자료(문서) 검색 결과를 조회하고, 원하는 데이터를 데이터베이스에 저장할 수 있는 웹 기반 검색·저장 프로젝트입니다.
Java Servlet과 JSP를 기반으로 구현되었으며, 외부 API 연동 → 데이터 가공 → 화면 출력 → DB 저장까지 백엔드 전반 흐름을 직접 구현한 프로젝트입니다.

단순 검색 기능을 넘어,
검색 결과를 페이지 단위로 조회하고 필요한 데이터를 DB에 저장하는 기능을 통해
API 활용 능력, 서버 처리 흐름 이해, DB 연동 경험을 쌓는 것을 목표로 제작되었습니다.

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

### 1. 네이버 전문자료 검색

사용자가 검색어를 입력하면 네이버 OpenAPI를 호출하여 전문자료 검색 결과를 가져옵니다.
검색 결과는 제목, 링크, 발행처 정보를 포함합니다.

* 검색어 입력 기반 API 호출
* JSON 데이터 파싱(Gson 사용)
* 검색 결과 최대 100건 수집
* 페이지 단위 결과 출력 (10개씩)

---

### 2. 페이지네이션 처리

검색 결과를 한 번에 모두 보여주는 것이 아니라,
페이지 단위로 나누어 조회할 수 있도록 구현했습니다.

* 페이지당 10개 결과 표시
* 최대 10페이지 구성
* 페이지 이동 시 해당 페이지 데이터 출력
* 서버에서 전체 데이터를 세션에 저장 후 페이지별 분할 출력

---

### 3. 검색 결과 테이블 출력

서버에서 API 응답 데이터를 가공하여 HTML 테이블 형태로 출력합니다.

출력 정보:

* 제목(title)
* 링크(link)
* 발행처(publisher)

제목 데이터는 HTML 태그 제거 후 출력하여 가독성을 높였습니다.

---

### 4. 검색 결과 DB 저장 기능

조회된 검색 결과를 MySQL 데이터베이스에 저장할 수 있습니다.

* 저장 버튼 클릭 시 현재 결과 저장
* JDBC를 활용한 DB 연결
* PreparedStatement 사용으로 SQL 실행
* title / link / publisher 데이터 저장

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

## 프로젝트 구조

```
project
 ┣ src
 ┃ ┣ SearchServlet.java     # 검색 API 호출 및 결과 처리
 ┃ ┗ SaveServlet.java       # DB 저장 처리
 ┣ WebContent
 ┃ ┣ Search.jsp             # 검색 UI 및 결과 출력
 ┃ ┗ css / image 등 정적 리소스
 ┗ database
   ┗ MySQL 테이블
```

---

## 동작 흐름

1. 사용자가 검색어 입력
2. SearchServlet이 네이버 OpenAPI 호출
3. JSON 응답을 파싱하여 결과 생성
4. JSP에서 검색 결과 테이블 출력
5. 저장 버튼 클릭 시 SaveServlet 호출
6. 결과 데이터를 MySQL DB에 저장

---

## 구현 포인트

### API 연동 경험

외부 OpenAPI 호출 → JSON 응답 파싱 → 데이터 가공 → 화면 출력까지
실제 서비스에서 사용하는 API 처리 흐름을 직접 구현했습니다.

### 서버 중심 렌더링 구조 이해

Servlet에서 데이터 처리 후 JSP로 전달하여
서버 사이드 렌더링 구조(MVC 흐름)를 이해하고 구현했습니다.

### DB 연동 및 저장 로직 구현

JDBC를 사용하여 MySQL과 직접 연결하고
PreparedStatement를 활용한 데이터 저장 기능을 구현했습니다.

### 데이터 가공 처리

검색 결과에서 HTML 태그 제거 및 데이터 정제 후 출력하여
사용자 친화적인 UI를 구성했습니다.

---

## 개선 가능 포인트

* API 키 환경변수 분리 (보안 강화)
* 페이지네이션 API 호출 최적화
* 중복 데이터 저장 방지 로직
* MVC 패턴 구조 개선 (Service/DAO 분리)
* JSON 기반 비동기 저장 처리
* UI/UX 개선

---

## 실행 방법

### 1. 네이버 OpenAPI 발급

네이버 개발자 센터에서
검색 API Client ID / Secret 발급 후 코드에 적용

### 2. DB 설정

MySQL DB 생성 후 테이블 생성

### 3. 서버 실행

Tomcat 서버 실행 후

```
http://localhost:8080/프로젝트명/search
```

접속

---

## 프로젝트 의의

이 프로젝트는 단순 CRUD가 아닌
외부 API 연동, 데이터 처리, 페이지네이션, DB 저장까지
백엔드 개발의 핵심 흐름을 직접 구현한 실습형 프로젝트입니다.

특히,

* OpenAPI 활용 경험
* Servlet/JSP 기반 서버 처리 흐름 이해
* DB 연동 및 데이터 저장 처리 경험

을 통해 백엔드 개발자로서의 기초 역량을 강화하는 데 목적을 두었습니다.
