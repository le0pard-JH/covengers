package product.model;

public class CartVO {

	private String cartno;  		// 장바구니 번호
	private String fk_userno;  		// 회원번호
	private String fk_productcode;  // 상품번호
	private String fk_optioncode;	// 옵션번호
	private String krproductname;   // 상품명
	private int pprice;     		// 상품가격(옵션값 포함)
	private int poqty;      		// 상품수량
	
	private int totalprice; 		// 합계금액
	private String productimg1;  	// 상품이미지

	
	public String getFk_optioncode() {
		return fk_optioncode;
	}

	public void setFk_optioncode(String fk_optioncode) {
		this.fk_optioncode = fk_optioncode;
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

	public String getFk_productcode() {
		return fk_productcode;
	}

	public void setFk_productcode(String fk_productcode) {
		this.fk_productcode = fk_productcode;
	}

	public String getKrproductname() {
		return krproductname;
	}

	public void setKrproductname(String krproductname) {
		this.krproductname = krproductname;
	}

	public String getProductimg1() {
		return productimg1;
	}

	public void setProductimg1(String productimg1) {
		this.productimg1 = productimg1;
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

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice() {
		this.totalprice = pprice * poqty;
	}

}
