package product.model;

public class CategoryVO {
	private String categorycode;
	private String fk_kindcode;
	private String encategoryname;
	private String krcategoryname;
	
	/////////////////////////////////////////////////////////////////////////////////
	
	private KindVO kindvo;////
	
	
	
	public String getCategorycode() {
		return categorycode;
	}
	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}
	public String getFk_kindcode() {
		return fk_kindcode;
	}
	public void setFk_kindcode(String fk_kindcode) {
		this.fk_kindcode = fk_kindcode;
	}
	public String getEncategoryname() {
		return encategoryname;
	}
	public void setEncategoryname(String encategoryname) {
		this.encategoryname = encategoryname;
	}
	public String getKrcategoryname() {
		return krcategoryname;
	}
	public void setKrcategoryname(String krcategoryname) {
		this.krcategoryname = krcategoryname;
	}
	public KindVO getKindvo() {
		return kindvo;
	}
	public void setKindvo(KindVO kindvo) {
		this.kindvo = kindvo;
	}
	
	
	
}
