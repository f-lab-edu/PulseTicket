package personnel.jupitorsendsme.pulseticket.service.hashingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import personnel.jupitorsendsme.pulseticket.interfaces.PasswordHashingService;

@Service
@Qualifier("Argon2id")
public class HashingServiceDefault implements PasswordHashingService {

	private final Argon2PasswordEncoder encoder;

	public HashingServiceDefault() {
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
