package personnel.jupitorsendsme.pulseticket.interfaces;

public interface PasswordHashingService {

	/**
	 * 패스워드를 특정알고리즘으로 해싱
	 * @param rawPassword 해싱하고자 하는 원본 패스워드가 담긴 객체
	 * @return 해싱된 패스워드
	 */
	String hash(String rawPassword);

	/**
	 * 입력된 원본 패스워드와 해싱된 패스워드가 일치하는지 확인
	 * @param rawPassword 비교하고자 하는 원본 패스워드, 이전에 해싱된 패스워드가 담긴 객체
	 * @param hashedPassword 이전에 해싱된 패스워드
	 * @return 일치 여부
	 */
	boolean verify(String rawPassword, String hashedPassword);
}
