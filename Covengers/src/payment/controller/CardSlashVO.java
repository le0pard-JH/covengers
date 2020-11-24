package payment.controller;

public class CardSlashVO {

  private String order_no; // 주문 번호
  private String cartno; // 장바구니 번호
  private String fk_userno; // 회원번호
  private String product_code; // 상품번호
  private String option_code; // 상품옵션코드
  private int pprice; // 상품가격(옵션값 포함)
  private int poqty; // 상품수량
  private int total_amount; // 상품수량
  private String img; // 상품이미지
  private String product_name; // 상품 이름
  private String desc; // 상품 설명
  
  private String shipNo;                // 배송지번호
  private String userno;          // 해당배송지를 저장한 유저의 이메일
  private String receiverName;    // 수취인이름
  private String siteName;        // 배송지이름
  private String postcode;        // 배송지 우편번호
  private String address;         // 배송지 주소
  private String detailAddress;   // 배송지 상세주소
  private String extraAddress;    // 배송지 비고
  private String mobile;          // 연락처
  private String deliveryRequest; // 배송요청사항
  private String status;          // 기본배송지여부 1:기본배송지 0: 기본배송지아님


  public String getOrder_no() {
    return order_no;
  }

  public void setOrder_no(String order_no) {
    this.order_no = order_no;
  }

  public String getCartno() {
    return cartno;
  }

  public void setCartno(String cartno) {
    this.cartno = cartno;
  }

  public String getFk_userno() {
    return fk_userno;
  }

  public void setFk_userno(String fk_userno) {
    this.fk_userno = fk_userno;
  }

  public String getProduct_code() {
    return product_code;
  }

  public void setProduct_code(String product_code) {
    this.product_code = product_code;
  }

  public int getPprice() {
    return pprice;
  }

  public void setPprice(int pprice) {
    this.pprice = pprice;
  }

  public int getPoqty() {
    return poqty;
  }

  public void setPoqty(int poqty) {
    this.poqty = poqty;
  }

  public int getTotal_amount() {
    return total_amount;
  }

  public void setTotal_amount(int total_amount) {
    this.total_amount = total_amount;
  }

  public String getOption_code() {
    return option_code;
  }

  public void setOption_code(String option_code) {
    this.option_code = option_code;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getProduct_name() {
    return product_name;
  }

  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
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
