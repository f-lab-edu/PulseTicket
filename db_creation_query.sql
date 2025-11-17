-- ============================================================================
-- PulseTicket 데이터베이스 테이블 생성
-- ============================================================================

-- USERS 테이블 생성
CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   user_id VARCHAR(50) UNIQUE NOT NULL,
   password_hash VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- EVENTS 테이블 생성
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    total_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- SEATS 테이블 생성
CREATE TABLE seats (
   id BIGSERIAL PRIMARY KEY,
   event_id BIGINT NOT NULL,
   seat_number INT NOT NULL,
   status VARCHAR(20) DEFAULT 'AVAILABLE',
   reserved_until TIMESTAMP NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT uk_seats_event_seat_number UNIQUE (event_id, seat_number)
);

-- RESERVATIONS 테이블 생성
CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL UNIQUE,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP NULL,
    cancelled_at TIMESTAMP NULL
);