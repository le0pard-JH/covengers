package product.model;

public class OptionVO {
	private String optioncode;
	private String fk_productcode;
	private String optionname;
	private int addprice;
	private int stock;
	
	
	
	
	public String getOptioncode() {
		return optioncode;
	}
	public void setOptioncode(String optioncode) {
		this.optioncode = optioncode;
	}
	public String getFk_productcode() {
		return fk_productcode;
	}
	public void setFk_productcode(String fk_productcode) {
		this.fk_productcode = fk_productcode;
	}
	public String getOptionname() {
		return optionname;
	}
	public void setOptionname(String optionname) {
		this.optionname = optionname;
	}
	public int getAddprice() {
		return addprice;
	}
	public void setAddprice(int addprice) {
		this.addprice = addprice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
