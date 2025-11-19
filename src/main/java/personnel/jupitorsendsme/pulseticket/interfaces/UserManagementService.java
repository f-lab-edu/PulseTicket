package personnel.jupitorsendsme.pulseticket.interfaces;

import java.util.Optional;

import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.entity.User;

public interface UserManagementService {

	/**
	 * 유저 등록 유무 확인
	 * @param request 등록되어있는지 체크하려는 사용자 id 가 담긴 객체
	 * @return true : 등록된 사용자, false : 미등록된 사용자
	 */
	boolean isUserPresent(ReservationBookingRequest request);

	/**
	 * 유저 등록
	 * @param request 등록하고자 하는 사용자 id, password 가 담긴 객체
	 * @return 등록 성공 여부
	 */
	boolean registeringUser(ReservationBookingRequest request);

	/**
	 * 유효한 유저 id / password 인지 판단
	 * @param request 유효한지 판단하고자 하는 사용자 id, password 다 담긴 객체
	 * @return true : userId 이 존재하고 userId, password 가 일치함 / false : 불일치
	 */
	boolean isUserValid(ReservationBookingRequest request);

	Optional<User> findValidUser(ReservationBookingRequest request);
}
