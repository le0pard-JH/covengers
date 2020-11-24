package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MemberDAO implements InterMemberDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;

	// 생성자
	public MemberDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/covengers_oracle");

			aes = new AES256(SecretMyKey.KEY); // SecretMyKey.KEY는 우리가 만든 비밀키이다.

			// etc.
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 사용한 자원 반납 close() Method 생성하기

	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	// 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)

	@Override
	public int registerMember(MemberVO member) throws SQLException {

		int result = 0;

		String rand = Integer.toString(ThreadLocalRandom.current().nextInt(1000, 9999));
		
		Calendar currentDate = Calendar.getInstance(); 
		int currentYear = currentDate.get(Calendar.YEAR);
		int currentM = currentDate.get(Calendar.MONTH) + 1;
		int currentD = currentDate.get(Calendar.DATE);
		String thisYear = Integer.toString(currentYear);
		thisYear = thisYear.substring(2);
		String thisMonth = Integer.toString(currentM);
		String thisDay = Integer.toString(currentD);
		
		//System.out.println(thisYear + currentM + currentD + rand);
		String userno_choice = thisYear + currentM + currentD + rand;

		
		try {

			conn = ds.getConnection();

			String sql = "insert into tbl_member(userno, name, email, password, mobile, postcode, address, detailAddress, extraAddress, gender, birthday, taste, point) \n"+
					"values(?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userno_choice);
			pstmt.setString(2, member.getName());
			pstmt.setString(3, aes.encrypt(member.getEmail()));
			pstmt.setString(4, Sha256.encrypt(member.getPassword())); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(5, aes.encrypt(member.getMobile())); // 휴대폰번호를 AES256알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(6, member.getPostcode());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getDetailAddress());
			pstmt.setString(9, member.getExtraAddress());
			pstmt.setString(10, member.getGender());
			pstmt.setString(11, member.getBirthday());
			pstmt.setString(12, member.getStrTaste());
			pstmt.setString(13,member.getPoint());

			result = pstmt.executeUpdate();
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	//이메일 중복검사

	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {

		boolean isExist = false;
	

		try {

			// db에 받아온 email값이 있는지 확인해보기

			conn = ds.getConnection();

			String sql = "select email from tbl_member where email = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			isExist = rs.next(); // 행이 있으면(중복된 email) true, 행이 없으면(사용가능한 email) false;


		} finally {
			close();
		}

		return isExist;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	// 회원가입시 추천인 포인트 업데이트 해주기
	@Override
	public int pointUpdate(String rcode) throws SQLException {
		
		int result = 0;
		String norcode = "admin";
		
		try {
			

			conn = ds.getConnection();

			String sql = "update try_member_pjh set point = point + ? where email = ?";

			pstmt = conn.prepareStatement(sql);

			if(rcode != null) {
			
				pstmt.setInt(1, 10000);
				pstmt.setString(2, rcode);
				
				result = pstmt.executeUpdate();
			}			

		} finally {
			close();
		}
			
		
		
		return result;
	}

	@Override
	public MemberVO selectOneMember(Map<String, String> paraMap) {
		MemberVO member = null; 

		try {
			conn = ds.getConnection();
			
			String subsql = " select userno from tbl_member where email = ? ";
			pstmt = conn.prepareStatement(subsql);
			pstmt.setString(1, aes.encrypt(paraMap.get("email")));
			rs = pstmt.executeQuery();
			rs.next();
			String userno = rs.getString(1);

			String sql = "SELECT userno, name, email, mobile, postcode, address, detailaddress, extraaddress, gender, \n"+
					"birthday, taste, point, registerday, pwdchangegap,\n"+
					"nvl(lastlogingap, trunc( months_between(sysdate, registerday) ) ) AS lastlogingap \n"+
					"FROM \n"+
					"(\n"+
					"select userno, name, email, mobile, postcode, address, detailaddress, extraaddress, gender \n"+
					"     , birthday, taste, point\n"+
					"     , to_char(registerday, 'yyyy-mm-dd') AS registerday \n"+
					"     , trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap \n"+
					"from tbl_member \n"+
					"where status = 1 and email = ? and password = ?\n"+
					") M \n"+
					"CROSS JOIN\n"+
					"(\n"+
					"select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap \n"+
					"from TBL_LOGINHISTORY \n"+
					"where fk_userno = ? \n"+
					") H";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, aes.encrypt(paraMap.get("email")));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")) );
			pstmt.setString(3, userno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				
				member.setUserno(rs.getString(1));
				member.setName(rs.getString(2));
				member.setEmail( aes.decrypt(rs.getString(3)) ); // 복호화 
				member.setMobile( aes.decrypt(rs.getString(4)) ); // 복호화 
				member.setPostcode(rs.getString(5));
				member.setAddress(rs.getString(6));
				member.setDetailAddress(rs.getString(7));	
				member.setExtraAddress(rs.getString(8));
				member.setGender(rs.getString(9));
				member.setBirthday(rs.getString(10));
				if (rs.getString(11) != null) {
					member.setTaste(rs.getString(11).split(","));
				} else {
					member.setTaste(null);
				}
				member.setPoint(rs.getNString(12));
				member.setRegisterday(rs.getString(13));
				
				if(rs.getInt(14) >= 3) {
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false	
					member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 한다. 
				}
				
				if(rs.getInt(15) >= 12) {
					// 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정
					member.setIdle(1);
					
					// === tbl_member 테이블의 idle 컬럼의 값을 1 로 변경 하기 === //
					sql = " update tbl_member set idle = 1 "
						+ " where userno = ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userno);
										
					pstmt.executeUpdate();
				}
				
				if(member.getIdle() != 1) {
				    // === tbl_loginhistory(로그인기록) 테이블에 insert 하기 === //
					sql = " insert into TBL_LOGINHISTORY(fk_userno, clientip) "
						+ " values(?, ?) ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userno);
					pstmt.setString(2, paraMap.get("clientip"));
					
					pstmt.executeUpdate();
				}
				
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return member;
	}

	@Override
	public List<MemberVO> selectAllMember() {
		
		List<MemberVO> mList = new ArrayList<>();
		
		try {

			conn = ds.getConnection();

			String sql = "select userno, name, email, mobile, postcode, address, detailAddress, extraAddress, gender, birthday, taste, point, registerday, lastpwdchangedate, status, idle from tbl_member where name != '관리자'";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				MemberVO mvo = new MemberVO();
				
				mvo.setUserno(rs.getString(1));
				mvo.setName(rs.getString(2));
				mvo.setEmail(aes.decrypt(rs.getString(3)));
				mvo.setMobile(aes.decrypt(rs.getString(4)));
				mvo.setPostcode(rs.getString(5));
				mvo.setAddress(rs.getString(6));
				mvo.setDetailAddress(rs.getString(7));
				mvo.setExtraAddress(rs.getString(8));
				mvo.setGender(rs.getString(9));
				mvo.setBirthday(rs.getString(10));
				
				if (rs.getString(11) == null) {
					mvo.setTaste(null);
				} else {
					mvo.setTaste(rs.getString(11).split(","));
				}
				
				mvo.setPoint(rs.getString(12));
				mvo.setRegisterday(rs.getString(13));
				mvo.setLastpwdchangedate(rs.getString(14));
				mvo.setStatus(rs.getInt(15));
				mvo.setIdle( rs.getInt(16) );
				
				mList.add(mvo);
				
			}


		} catch( Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return mList;
		
	}

	@Override
	public String searchEamil(String name, String password) throws SQLException {
		String email = null;
		
		try {

			conn = ds.getConnection();

			String sql = " select email from tbl_member where name = ? and password = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, Sha256.encrypt(password) );

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				email = aes.decrypt( rs.getString(1));

			}			

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		finally {
			close();
		}
		
		
		return email;
	}

	@Override
	public int updatePassword(String email, String password) {
		
		int result = 0;
		
		try {			

			conn = ds.getConnection();

			String sql = "update tbl_member set password = ? where email = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Sha256.encrypt(password) );
			pstmt.setString(2, aes.encrypt(email) );
			
			result = pstmt.executeUpdate();
					
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;

	}

	@Override
	public String searchUser(String name, String email) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		
		try {

			conn = ds.getConnection();

			String sql = " select * from tbl_member where name = ? and email = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, aes.encrypt(email) );

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				result += 1;
			}			

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		finally {
			close();
		}
		
		
		return Integer.toString(result);
	}

    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 배송지 주소 등록
	@Override
	public int addShippingAddress(ShippingAddressVO ship) throws SQLException {
		
		int result = 0;
		
		if("1".equals(ship.getStatus())){ // 지금 등록하는 주소를 기본배송지로 설정하고자 한다면
			
			String shipNo = alreayExistDefaultAddress(ship.getUserno()); // 기본배송지로 등록된 배송지가 있는지 확인
			
			if(shipNo != null) { //기본배송지가 있다면
				changeDefaultAddress(shipNo); // 기존 기본배송지의 상태를 바꿔줌
			}
		}
		
		
		try {

			conn = ds.getConnection();

			String sql = "insert into tbl_shipping(ship_seq, fk_userno, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status) \n"+
					"values (seq_shipping.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ship.getUserno());
			pstmt.setString(2, ship.getReceiverName());
			pstmt.setString(3, ship.getSiteName());
			pstmt.setString(4, ship.getPostcode());
			pstmt.setString(5, ship.getAddress());
			pstmt.setString(6, ship.getDetailAddress());
			pstmt.setString(7, ship.getExtraAddress());
			pstmt.setString(8, aes.encrypt(ship.getMobile())); // 핸드폰번호 양방향 암호화
			pstmt.setString(9, ship.getDeliveryRequest());
			pstmt.setString(10, ship.getStatus());

			result = pstmt.executeUpdate();
			
			if(result == 1 && "1".equals(ship.getStatus())) {
				sql = " update tbl_member "
						+ " set postcode = ?, address = ?, detailAddress = ?, extraAddress = ? "
						+ " where userno = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, ship.getPostcode());
				pstmt.setString(2, ship.getAddress());
				pstmt.setString(3, ship.getDetailAddress());
				pstmt.setString(4, ship.getExtraAddress());
				pstmt.setString(5, ship.getUserno());
				
			  result = pstmt.executeUpdate();
			}
			
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 해당 유저가 저장한 배송지 목록 조회
	@Override
	public List<ShippingAddressVO> selectAddressList(String userno) throws SQLException {

		List<ShippingAddressVO> addressList = new ArrayList<>();
		
		try {

			conn = ds.getConnection();

			String sql = "select ship_seq, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status " + 
					" from tbl_shipping " +
					" where fk_userno = ? "
					+ " order by status desc ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { //행이 여러개나오니까 다음행이 없을때까지 반복
				
				ShippingAddressVO svo = new ShippingAddressVO();
				svo.setShipNo(rs.getString(1));
				svo.setReceiverName(rs.getString(2));
				svo.setSiteName(rs.getString(3));
				svo.setPostcode(rs.getString(4));
				svo.setAddress(rs.getString(5));
				svo.setDetailAddress(rs.getString(6));
				svo.setExtraAddress(rs.getString(7));
				svo.setMobile(aes.decrypt(rs.getString(8))); // 휴대폰번호 복호화
				svo.setDeliveryRequest(rs.getString(9));
				svo.setStatus(rs.getString(10));
				svo.setUserno(userno);
			
				addressList.add(svo); // 생성된 memberVO를 memberList에 담아준다.
				
			}
			
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}  finally {
			close();
		}
		
		return addressList;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	// 기본배송지 유무 확인 및 기본배송지 seq 반환
	@Override
	public String alreayExistDefaultAddress(String userno) throws SQLException {
		
		String shipNo = null;
		
		try {

			conn = ds.getConnection();

			String sql = "select ship_seq, siteName, status " + 
					" from tbl_shipping " +
					" where fk_userno = ? and status = 1 ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				shipNo = rs.getString(1);
			}
			
		}  finally {
			close();
		}
		
		return shipNo;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 기존 기본배송지의 상태를 0으로 바꿔주기
	@Override
	public void changeDefaultAddress(String shipNo) throws SQLException {
		
		try {

			conn = ds.getConnection();

			String sql = " update tbl_shipping "
					+ " set status = 0 " + 
					" where ship_seq = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shipNo);
			
			pstmt.executeUpdate();
			
		}  finally {
			close();
		}
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 기존 배송지의 정보를 변경해주기
	@Override
	public int updateShippingAddress(ShippingAddressVO svo) throws SQLException {
		
		if("1".equals(svo.getStatus())){ // 지금 등록하는 주소를 기본배송지로 설정하고자 한다면
			
			String shipNo = alreayExistDefaultAddress(svo.getUserno()); // 기본배송지로 등록된 배송지가 있는지 확인
			
			if(shipNo != null) { //기본배송지가 있다면
				changeDefaultAddress(shipNo); // 기존 기본배송지의 상태를 0으로 바꿔줌
			}
		}
		
		int result = 0;
		

		try {

			conn = ds.getConnection();

			String sql = " update tbl_shipping "
					+ " set receiverName = ?, siteName = ?, postcode = ?, address = ?, detailAddress = ?, extraAddress = ?, mobile = ?, deliveryRequest = ?, status = ? "
					+ " where ship_seq = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, svo.getReceiverName());
			pstmt.setString(2, svo.getSiteName());
			pstmt.setString(3, svo.getPostcode());
			pstmt.setString(4, svo.getAddress());
			pstmt.setString(5, svo.getDetailAddress());
			pstmt.setString(6, svo.getExtraAddress());
			pstmt.setString(7, aes.encrypt(svo.getMobile())); // 핸드폰번호 양방향 암호화
			pstmt.setString(8, svo.getDeliveryRequest());
			pstmt.setString(9, svo.getStatus());
			pstmt.setString(10, svo.getShipNo());

			result = pstmt.executeUpdate();
			
			if(result == 1 && "1".equals(svo.getStatus())) {
				sql = " update tbl_member "
						+ " set postcode = ?, address = ?, detailAddress = ?, extraAddress = ? "
						+ " where userno = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, svo.getPostcode());
				pstmt.setString(2, svo.getAddress());
				pstmt.setString(3, svo.getDetailAddress());
				pstmt.setString(4, svo.getExtraAddress());
				pstmt.setString(5, svo.getUserno());
				
				result = pstmt.executeUpdate();
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}  finally {
			close();
		}
		
		return result;
	}

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// 배송지 하나만 불러오기	
	@Override
	public ShippingAddressVO selectOneAddress(String shipNo) throws SQLException {
	
		ShippingAddressVO svo = new ShippingAddressVO();
		
		try {

			conn = ds.getConnection();

			String sql = "select ship_seq, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status, fk_userno" + 
					" from tbl_shipping " +
					" where ship_seq = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shipNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //행이 있다면
				
				svo.setShipNo(rs.getString(1));
				svo.setReceiverName(rs.getString(2));
				svo.setSiteName(rs.getString(3));
				svo.setPostcode(rs.getString(4));
				svo.setAddress(rs.getString(5));
				svo.setDetailAddress(rs.getString(6));
				svo.setExtraAddress(rs.getString(7));
				svo.setMobile(aes.decrypt(rs.getString(8))); // 휴대폰번호 복호화
				svo.setDeliveryRequest(rs.getString(9));
				svo.setStatus(rs.getString(10));
				svo.setUserno(rs.getString(11));
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}  finally {
			close();
		}
		
		return svo;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 배송지 삭제하기
	@Override
	public boolean shppingAddressDelete(String shipNo) throws SQLException {

		boolean isDeleted = false;
		int result = 0;
		String userno = "";
		String status = "";
		
		try {

			conn = ds.getConnection();

			
			String sql = " select fk_userno, status from tbl_shipping where ship_seq = ? ";
			
			System.out.println("shipno => " + shipNo);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shipNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userno = rs.getString(1);
				status = rs.getString(2);
				System.out.println("userno => "+ userno);
				System.out.println("status => " + status);
			}
			
			sql = " delete from tbl_shipping " +
					" where ship_seq = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, shipNo);
			
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				
				isDeleted = true;
				
				if("1".equals(status)) {
					sql = " update tbl_member "
							+ " set postcode = '', address = '', detailAddress = '', extraAddress = '' "
							+ " where userno =  ? ";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, userno);
					
					pstmt.executeUpdate();
				}
			}
			
		}  finally {
			close();
		}
		
		return isDeleted;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//해당 유저가 저장한 배송지 개수 확인
	public int numberOfShippingAddress(String userno) throws SQLException {
		int result = 0;
		
		try {

			conn = ds.getConnection();

			String sql = " select ship_seq " + 
					" from tbl_shipping " +
					" where fk_userno = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result++;
			}
			
			System.out.println(result);
			
		}  finally {
			close();
		}
		
		return result;
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
