package member.model;

public class ShippingAddressVO {
	
	private String shipNo;				// 배송지번호
	private String userno;			// 해당배송지를 저장한 유저의 이메일
	private String receiverName;	// 수취인이름
	private String siteName;		// 배송지이름
	private String postcode;		// 배송지 우편번호
	private String address;			// 배송지 주소
	private String detailAddress;	// 배송지 상세주소
	private String extraAddress;	// 배송지 비고
	private String mobile;			// 연락처
	private String deliveryRequest;	// 배송요청사항
	private String status;			// 기본배송지여부 1:기본배송지 0: 기본배송지아님
	
	public ShippingAddressVO() {	}

	public ShippingAddressVO(String userno, String receiverName, String siteName, String postcode, String address,
			String detailAddress, String extraAddress, String mobile, String deliveryRequest, String status) {
		super();
		this.userno = userno;
		this.receiverName = receiverName;
		this.siteName = siteName;
		this.postcode = postcode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.extraAddress = extraAddress;
		this.mobile = mobile;
		this.deliveryRequest = deliveryRequest;
		this.status = status;
	}

	
	
	public ShippingAddressVO(String shipNo, String userno, String receiverName, String siteName, String postcode,
			String address, String detailAddress, String extraAddress, String mobile, String deliveryRequest,
			String status) {
		this.shipNo = shipNo;
		this.userno = userno;
		this.receiverName = receiverName;
		this.siteName = siteName;
		this.postcode = postcode;
		this.address = address;
		this.detailAddress = detailAddress;
		this.extraAddress = extraAddress;
		this.mobile = mobile;
		this.deliveryRequest = deliveryRequest;
		this.status = status;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getExtraAddress() {
		return extraAddress;
	}

	public void setExtraAddress(String extraAddress) {
		this.extraAddress = extraAddress;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDeliveryRequest() {
		return deliveryRequest;
	}

	public void setDeliveryRequest(String deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
