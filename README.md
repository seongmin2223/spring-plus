# 📝 Spring Boot 기반 할 일 관리 애플리케이션

본 프로젝트는 Spring Boot를 사용하여 할 일(Todo) 관리 기능을 구현하고, 다양한 백엔드 기술 과제를 해결하며 코드를 개선하는 과정을 담고 있습니다.  
사용자 인증부터 동적 쿼리, 성능 최적화, AOP, Spring Security 전환까지 백엔드 개발의 핵심적인 요소들을 학습하고 적용하는 것을 목표로 합니다.

---

## ✨ 주요 기능

- **사용자 인증**: JWT(JSON Web Token)를 사용한 회원가입 및 로그인 기능  
- **할 일(Todo) 관리**: 할 일 생성, 조회, 수정, 삭제 (CRUD)  
- **댓글 관리**: 특정 할 일에 대한 댓글 작성 및 조회  
- **동적 검색**: 날씨, 수정일 기간 등 여러 조합의 조건으로 할 일 목록 검색  
- **담당자 시스템**: 할 일 생성 시, 생성자가 담당자로 자동 등록  
- **관리자 기능**: 관리자가 사용자의 권한을 변경할 수 있는 기능  

---

## 🛠️ 적용 기술

- **Backend**: Java 17, Spring Boot 3.x  
- **Database**: Spring Data JPA, Hibernate, QueryDSL, H2 / MySQL  
- **Security**: Spring Security, JWT  
- **ETC**: Lombok, AOP (Aspect-Oriented Programming)  

---

## 💡 주요 개선 사항 및 학습 내용

### 1. ✅ N+1 문제 해결 (JPA 성능 최적화)

- **문제점**: 할 일이나 댓글 목록 조회 시, 연관된 User 엔티티를 조회하기 위해 목록의 개수(N)만큼 추가적인 쿼리가 발생하는 N+1 문제 발생  
- **해결**: `Fetch Join`을 사용하여 연관 엔티티를 함께 조회  
  - JPQL: `LEFT JOIN FETCH`
  - QueryDSL: `.fetchJoin()`  
- **효과**: DB 호출 횟수를 줄여 API 성능 대폭 향상

---

### 2. ⚙️ 동적 쿼리 구현 (JPQL → QueryDSL 전환)

- **문제점**: 조건이 복합적인 경우(JPQL)는 유지보수 및 타입 안정성 문제 발생  
- **해결**: QueryDSL 도입으로 타입 안전한 동적 쿼리 구현  
  - `BooleanBuilder` 또는 `where` 절 다중 조건으로 유연하게 처리  
- **효과**: 컴파일 타임에서 쿼리 오류 감지 가능, 코드의 가독성과 확장성 향상

---

### 3. 🧩 AOP를 활용한 공통 관심사 분리

- **요구사항**: 관리자 권한 변경 시 로그 기록  
- **해결**: Spring AOP를 사용하여 `AdminAccessLoggingAspect` 구현  
  - `@Before` 어드바이스 + Pointcut 설정  
- **효과**: 비즈니스 로직과 로깅 로직 완전 분리

---

### 4. 🔁 JPA Cascade를 이용한 영속성 전이

- **요구사항**: 할 일을 생성한 사용자가 자동으로 담당자로 등록되어야 함  
- **해결**: `@OneToMany(cascade = CascadeType.PERSIST)` 적용  
- **효과**: 서비스 계층 코드 변경 없이 엔티티 관계 설정만으로 문제 해결

---

### 5. 🔐 Spring Security로 인증/인가 시스템 전환

- **문제점**: 직접 구현한 인증 필터 및 커스텀 어노테이션 방식은 확장성이 떨어짐  
- **해결**: Spring Security 도입하여 전면 교체  
  - `SecurityConfig`로 요청별 권한 설정 (`permitAll`, `hasRole`, `authenticated`)  
  - JWT 검증을 위한 `JwtAuthorizationFilter` 필터 체인 등록  
  - `@AuthenticationPrincipal`로 인증 사용자 정보 주입  
- **효과**: 표준화된 보안 방식으로 유지보수성과 확장성 향상

---
