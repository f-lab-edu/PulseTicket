package personnel.jupitorsendsme.pulseticket.service.hashingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.interfaces.PasswordHashingService;

@Service
@Qualifier("argon2id")
@RequiredArgsConstructor
public class HashingServiceArgon2id implements PasswordHashingService {

	@Qualifier("argon2id")
	private final Argon2PasswordEncoder encoder;

	public HashingServiceArgon2id() {
		this.encoder = new Argon2PasswordEncoder(16, 32, 1, 15 * 1024, 2);
	}

	@Override
	public String hash(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	@Override
	public boolean verify(String rawPassword, String hashedPassword) {
		return encoder.matches(rawPassword, hashedPassword);
	}
}
