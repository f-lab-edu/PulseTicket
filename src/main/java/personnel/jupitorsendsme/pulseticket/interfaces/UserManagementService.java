package personnel.jupitorsendsme.pulseticket.interfaces;

public interface UserManagementService {

    /**
     * 유저 등록 유무 확인
     * @param username 등록되어있는지 체크하려는 사용자 id
     * @return true : 등록된 사용자, false : 미등록된 사용자
     */
    boolean doesUserExist (String username);

    /**
     * 유저 등록
     * @param username 등록하고자 하는 사용자 id
     * @param password 등록하고자 하는 사용자 password (암호화되기 전 원본값)
     * @return 등록 성공 여부
     */
    boolean registeringUser (String username, String password);

    /**
     * 유효한 유저 id / password 인지 판단
     * @param username 유효한지 판단하고자 하는 사용자 id
     * @param password 유효한지 판단하고자 하는 사용자 password
     * @return true : username 이 존재하고 username, password 가 일치함 / false : 불일치
     */
    boolean isUserValid (String username, String password);
}
