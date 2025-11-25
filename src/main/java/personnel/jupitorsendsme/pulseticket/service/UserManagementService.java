package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.exception.user.InvalidCredentialException;
import personnel.jupitorsendsme.pulseticket.exception.user.UserNotFoundException;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserManagementService {

	final UserRepository userRepository;
	final HashingServiceArgon2id passwordHashingService;

	/**
	 * 유저 등록 유무 확인
	 * @param request 등록되어있는지 체크하려는 사용자 id 가 담긴 객체
	 * @return true : 등록된 사용자, false : 미등록된 사용자
	 */
	public boolean isUserPresent(ReservationBookingRequest request) {
		return userRepository.existsByLoginId(request.getLoginId());
	}

	/**
	 * 유저 등록
	 * @param request 등록하고자 하는 사용자 id, password 가 담긴 객체
	 * @return 등록 성공 여부
	 */
	@Transactional
	public boolean registerUser(ReservationBookingRequest request) {
		if (this.isUserPresent(request)) {
			return false;
		}

		createUser(request);

		return true;
	}

	/**
	 * User 엔티티 생성 & 저장
	 * @param request login Id, 해싱할 password
	 */
	public void createUser(ReservationBookingRequest request) {
		User user = User
			.builder()
			.loginId(request.getLoginId())
			.passwordHash(passwordHashingService.hash(request.getPassword()))
			.build();

		userRepository.save(user);
	}

	/**
	 * 유효한 유저 id / password 인지 판단
	 * @param request 유효한지 판단하고자 하는 사용자 id, password 다 담긴 객체
	 * @return true : loginId 이 존재하고 loginId, password 가 일치함 / false : 불일치
	 */
	public boolean isUserValid(ReservationBookingRequest request) {
		this.getValidUser(request);
		return true;
	}

	/**
	 * user id, password 입력값에 해당하는 유효한 회원 엔티티가 존재하는 경우 해당 엔티티 반환
	 * @param request user id, password 가 담긴 객체
	 * @return 유효한 회원 정보일 경우 회원 Entity 반환
	 */
	public User getValidUser(ReservationBookingRequest request) {
		User user = findUserByLoginId(request);

		if (!passwordHashingService.verify(request, user))
			throw new InvalidCredentialException(request);

		return user;
	}

	public User findUserByLoginId(ReservationBookingRequest request) {
		return userRepository.findUserByLoginId(request.getLoginId())
			.orElseThrow(() -> new UserNotFoundException(request.getLoginId()));
	}
}
