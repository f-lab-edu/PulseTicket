-- PulseTicket 데이터베이스 테이블 생성
-- ============================================================================

-- USERS 테이블
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    login_id      VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP           NOT NULL,
    updated_at    TIMESTAMP           NOT NULL
);

-- EVENTS 테이블
CREATE TABLE events
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    total_seats INT          NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL
);

-- SEATS 테이블
CREATE TABLE seats
(
    id             BIGSERIAL PRIMARY KEY,
    event_id       BIGINT      NOT NULL, -- FK -> events.id
    seat_number    INT         NOT NULL,
    status         VARCHAR(20) NOT NULL, -- AVAILABLE, RESERVED, SOLD
    reserved_until TIMESTAMP   NULL,
    created_at     TIMESTAMP   NOT NULL,
    updated_at     TIMESTAMP   NOT NULL,
    CONSTRAINT uk_seats_event_seat_number UNIQUE (event_id, seat_number)
);

-- RESERVATIONS 테이블
CREATE TABLE reservations
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL, -- FK -> users.id
    seat_id      BIGINT      NOT NULL, -- FK -> seats.id
    event_id     BIGINT      NOT NULL, -- FK -> events.id
    status       VARCHAR(20) NOT NULL, -- PENDING, CONFIRMED, CANCELLED, EXPIRED
    expires_at   TIMESTAMP   NOT NULL,
    confirmed_at TIMESTAMP   NULL,
    cancelled_at TIMESTAMP   NULL,
    created_at   TIMESTAMP   NOT NULL,
    updated_at   TIMESTAMP   NOT NULL
);
