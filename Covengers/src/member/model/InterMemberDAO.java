package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface InterMemberDAO {
	// 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
	int registerMember(MemberVO member) throws SQLException;

	// email 중복검사(tbl_member 테이블에서 email이 존재하면 true를 리턴해주고, email이 존재하지 않으면 false를 리턴한다.)
	boolean emailDuplicateCheck(String email) throws SQLException;
	
	// 추천인 포인트 업데이트
	int pointUpdate(String rcode) throws SQLException;
	
	// 로그인
	MemberVO selectOneMember(Map<String, String> paraMap) throws SQLException;
	
	// 전체 멤버 조회
	List<MemberVO> selectAllMember() throws SQLException;
	
	// 이메일 찾기
	String searchEamil(String name, String password) throws SQLException;
	
	// 패스워드 업데이트
	int updatePassword(String email, String name);
	
	// 유저 존재 여부
	String searchUser(String name, String email) throws SQLException;
	// 배송지 주소 등록
	int addShippingAddress(ShippingAddressVO ship) throws SQLException;

	// 해당 유저가 저장한 배송지 목록 조회
	List<ShippingAddressVO> selectAddressList(String userno) throws SQLException;

	// 기본배송지 유무 확인 및 기본배송지 seq 반환
	String alreayExistDefaultAddress(String userno) throws SQLException;
	
	// 기존 기본배송지의 상태를 0으로 바꿔주기
	void changeDefaultAddress(String shipNo) throws SQLException;

	// 기존 배송지의 정보를 변경해주기
	int updateShippingAddress(ShippingAddressVO svo) throws SQLException;
	
	// 배송지 하나만 불러오기
	ShippingAddressVO selectOneAddress(String shipNo) throws SQLException;

	// 배송지 삭제하기
	boolean shppingAddressDelete(String shipNo) throws SQLException;

	// 한 유저가 등록한 배송지 개수 조회
	int numberOfShippingAddress(String userno) throws SQLException;
	
}
