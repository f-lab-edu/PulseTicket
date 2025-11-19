package personnel.jupitorsendsme.pulseticket.service.userManagement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.interfaces.PasswordHashingService;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

@Service
@Qualifier("default")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserManagementServiceDefault implements UserManagementService {

	final UserRepository userRepository;

	@Qualifier("argon2id")
	final PasswordHashingService passwordHashingService;

	@Override
	public boolean isUserPresent(ReservationBookingRequest request) {
		return userRepository.existsByUserId(request.getUserId());
	}

	@Override
	@SuppressWarnings("DefaultAnnotationParam")
	@Transactional(readOnly = false)
	public boolean registeringUser(ReservationBookingRequest request) {
		if (this.isUserPresent(request))
			return false;

		User user = User.builder()
			.userId(request.getUserId())
			.passwordHash(passwordHashingService.hash(request.getRawPassword()))
			.build();

		userRepository.save(user);

		return true;
	}

	@Override
	public boolean isUserValid(ReservationBookingRequest request) {
		return userRepository.findUserByUserId(request.getUserId())
			.map(user -> passwordHashingService.verify(request.getRawPassword(), user.getPasswordHash()))
			.orElse(false);
	}

	@Override
	public Optional<User> findValidUser(ReservationBookingRequest request) {
		return userRepository.findUserByUserId(request.getUserId())
			.filter(user -> passwordHashingService.verify(request.getRawPassword(), user.getPasswordHash()));
	}
}
