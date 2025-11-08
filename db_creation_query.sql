-- ============================================================================
-- PulseTicket 데이터베이스 테이블 생성
-- ============================================================================

-- USERS 테이블 생성
CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   username VARCHAR(50) UNIQUE NOT NULL,
   password_hash VARCHAR(255) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- EVENTS 테이블 생성
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    total_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
   CONSTRAINT fk_seats_event FOREIGN KEY (event_id) REFERENCES events(id),
   CONSTRAINT uk_seats_event_seat_number UNIQUE (event_id, seat_number)
);

-- RESERVATIONS 테이블 생성
CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL UNIQUE,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP NULL,
    cancelled_at TIMESTAMP NULL,
    CONSTRAINT fk_reservations_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_reservations_seat FOREIGN KEY (seat_id) REFERENCES seats(id)
);

-- updated_at 자동 업데이트를 위한 트리거 함수 생성 (아래 Trigger 를 위함)
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- seats 테이블의 updated_at 자동 업데이트 트리거
CREATE TRIGGER trigger_seats_updated_at
    BEFORE UPDATE ON seats
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();