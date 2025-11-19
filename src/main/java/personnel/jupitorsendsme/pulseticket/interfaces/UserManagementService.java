package personnel.jupitorsendsme.pulseticket.interfaces;

public interface UserManagementService {

	/**
	 * 유저 등록 유무 확인
	 * @param userId 등록되어있는지 체크하려는 사용자 id
	 * @return true : 등록된 사용자, false : 미등록된 사용자
	 */
	boolean isUserPresent(String userId);

	/**
	 * 유저 등록
	 * @param userId 등록하고자 하는 사용자 id
	 * @param password 등록하고자 하는 사용자 password (암호화되기 전 원본값)
	 * @return 등록 성공 여부
	 */
	boolean registeringUser(String userId, String password);

	/**
	 * 유효한 유저 id / password 인지 판단
	 * @param userId 유효한지 판단하고자 하는 사용자 id
	 * @param password 유효한지 판단하고자 하는 사용자 password
	 * @return true : userId 이 존재하고 userId, password 가 일치함 / false : 불일치
	 */
	boolean isUserValid(String userId, String password);
}
