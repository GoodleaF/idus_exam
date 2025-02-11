# idus_exam

**idus_exam**은 Spring Boot를 기반으로 한 사용자 관리 시스템 예제 프로젝트입니다. 이 프로젝트는 이메일 인증, JWT 기반 인증, 그리고 마스터/슬레이브 방식의 MariaDB 데이터소스 구성을 통해 확장 가능하고 안전한 RESTful API를 구현하는 방법을 보여줍니다.

## 개요

이 프로젝트는 다음과 같은 기능들을 제공합니다.

- **사용자 회원가입 및 이메일 인증:**  
  사용자가 회원가입을 하면 이메일 인증 링크가 발송되며, 해당 링크를 클릭하면 계정이 활성화됩니다.

- **JWT 기반 인증:**  
  기존의 세션 기반 로그인 방식 대신, JWT(JSON Web Token)를 사용하여 무상태(Stateless) 인증을 구현합니다. 로그인 성공 시 JWT가 HttpOnly 쿠키에 저장되며, 이후 요청에서 이 쿠키를 통해 인증을 수행합니다.

- **마스터/슬레이브 데이터소스 구성:**  
  MariaDB를 마스터와 슬레이브로 구성하여 데이터 복제 및 부하 분산 시나리오를 구현합니다.

- **RESTful API 엔드포인트 제공:**  
  회원가입, 로그인, 이메일 인증, 현재 사용자 정보 조회, 주문 관리 등의 API 엔드포인트를 제공합니다.

- **환경 변수에 의한 외부 설정:**  
  민감한 설정(데이터베이스 자격증명, 이메일 설정 등)을 환경 변수로 외부화하여 소스 코드에서 중요한 정보를 노출하지 않도록 구성합니다.

## 주요 기능

- **회원가입 (Signup):**  
  사용자가 회원가입을 진행하면, 회원가입 정보가 저장되고 이메일 인증 링크가 발송됩니다.

- **이메일 인증 (Email Verification):**  
  사용자가 이메일에 발송된 링크를 클릭하면, 해당 UUID를 통해 계정이 활성화되어 로그인할 수 있게 됩니다.

- **JWT 로그인 (JWT Login):**  
  `/auth/login` 엔드포인트를 통해 사용자가 이메일과 비밀번호로 인증하면, JWT가 생성되어 HttpOnly 쿠키(`ATOKEN`)에 저장됩니다.

- **현재 사용자 정보 조회 (My Info):**  
  인증된 사용자는 자신의 정보를 조회할 수 있습니다.

- **로그아웃 (Logout):**  
  로그아웃 시 JWT 쿠키를 삭제하거나 만료시켜 인증을 해제합니다.

- **주문 관리 (Order Management):**  
  사용자의 주문 내역을 관리하고 조회하는 기능을 제공합니다.

- **마스터/슬레이브 데이터베이스 구성:**  
  MariaDB의 마스터와 슬레이브 데이터소스를 사용하여 데이터 복제와 부하 분산을 시연합니다.

## 기술 스택

- **Java:** 17 이상
- **Spring Boot:** 3.x
- **Spring Security**
- **Spring Data JPA**
- **MariaDB**
- **JWT:** JJWT 라이브러리 사용
- **빌드 도구:** Maven (또는 Gradle)

## 사전 요구 사항

- **JDK 17** 이상 설치
- **Maven** (또는 Gradle) 설치
- **MariaDB** 설치 및 구성  
  - 마스터 DB: 예: `100.0.0.100:3306`
  - 슬레이브 DB: 예: `100.0.0.200:3306`
- **Gmail 계정:** 이메일 인증 메일 발송을 위해 필요  
  - Gmail SMTP 사용 시, 2단계 인증(2FA) 활성화 및 앱 비밀번호 생성 권장


## API 엔드포인트

### 1. 회원가입 (Signup)
- **URL:** `POST /user/signup`
- **요청 본문 예시:**
  ```json
  {
    "username": "testuser",
    "nickname": "Test User",
    "password": "password123",
    "email": "test@example.com",
    "phone": 1234567890,
    "sex": "M"
  }

### 2. 이메일 인증 (Email Verification)
- **URL:** `GET /user/verify?uuid={인증UUID}`
- 사용자가 이메일에 발송된 링크를 클릭하면, 해당 UUID를 통해 계정을 활성화합니다.

### 3. JWT 로그인 (JWT Login)
- **URL:** `POST /auth/login`
- **요청 본문 예시:**
  ```json
  {
    "email": "test@example.com",
    "password": "password123"
  }
- 설명: 사용자가 로그인하면 JWT가 생성되어 HttpOnly 쿠키(ATOKEN)에 저장됩니다.

### 4. 현재 사용자 정보 조회 (My Info)
- **URL:** `GET /user/myinfo`
- 설명: 인증된 사용자의 정보를 조회합니다.

### 5. 로그아웃 (Logout)
- **URL:** `POST /auth/logout`
- 설명: 로그아웃 처리

### 6. 사용자정보 검색 (Logout)
- **URL:** `GET /user/list`
- 설명: 유저명 또는 이메일로 유저 리스트를 조회합니다. 디폴트 값으로 ""가 설정되어 있습니다.

