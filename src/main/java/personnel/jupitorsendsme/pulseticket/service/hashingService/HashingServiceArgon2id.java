package personnel.jupitorsendsme.pulseticket.service.hashingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Qualifier("argon2id")
@RequiredArgsConstructor
public class HashingServiceArgon2id {

	private final Argon2PasswordEncoder encoder;

	public HashingServiceArgon2id() {
		this.encoder = new Argon2PasswordEncoder(16, 32, 1, 15 * 1024, 2);
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
}
