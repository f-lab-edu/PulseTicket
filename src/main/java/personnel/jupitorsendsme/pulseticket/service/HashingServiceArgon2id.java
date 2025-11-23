package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.entity.User;

@Service
@Qualifier("argon2id")
@RequiredArgsConstructor
public class HashingServiceArgon2id {

	/**
	 * Salt 길이 (바이트)
	 */
	private static final int SALT_LENGTH = 16;

	/**
	 * 해시 결과 길이 (바이트)
	 */
	private static final int HASH_LENGTH = 32;

	/**
	 * 병렬 처리 스레드 수
	 */
	private static final int PARALLELISM = 1;

	/**
	 * 메모리 사용량 (KB 단위, 15MB)
	 */
	private static final int MEMORY_COST_KB = 15 * 1024;

	/**
	 * 해싱 반복 횟수
	 */
	private static final int ITERATIONS = 2;

	private final Argon2PasswordEncoder encoder;

	public HashingServiceArgon2id() {
		this.encoder = new Argon2PasswordEncoder(
			SALT_LENGTH,
			HASH_LENGTH,
			PARALLELISM,
			MEMORY_COST_KB,
			ITERATIONS
		);
	}

	/**
	 * 패스워드를 argon2id 정알고리즘으로 해싱
	 * @param rawPassword 해싱하고자 하는 원본 패스워드가 담긴 객체
	 * @return 해싱된 패스워드
	 */
	public String hash(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	/**
	 * 입력된 원본 패스워드와 해싱된 패스워드가 일치하는지 확인
	 * @param rawPassword 비교하고자 하는 원본 패스워드, 이전에 해싱된 패스워드가 담긴 객체
	 * @param hashedPassword 이전에 해싱된 패스워드
	 * @return 일치 여부
	 */
	public boolean verify(String rawPassword, String hashedPassword) {
		return encoder.matches(rawPassword, hashedPassword);
	}

	public boolean verify(ReservationBookingRequest request, User user) {
		return this.verify(request.getPassword(), user.getPasswordHash());
	}
}
