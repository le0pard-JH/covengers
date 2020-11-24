package admin.product.model;

import java.sql.SQLException;
import java.util.*;

import product.model.*;



public interface InterAdminProductDAO {

	int insertProductKind(Map<String, String> paraMap) throws SQLException;

	int insertProductCategory(Map<String, String> paraMap) throws SQLException;

	List<KindVO> selectKindList() throws SQLException;

	List<CategoryVO> selectCategoryList() throws SQLException;

	List<ProductVO> selectProductList() throws SQLException;

	List<OptionVO> selectOptionList() throws SQLException;

	List<EachGoodsVO> selectEachGoodsList() throws SQLException;

	String getLastCategorycode(Map<String, String> paraMap) throws SQLException;

	String getKindCategorycode(Map<String, String> paraMap) throws SQLException;

	String getOptioncodeAction(Map<String, String> paraMap) throws SQLException;

	int insertProductOption(Map<String, String> paraMap) throws SQLException;

	int insertProduct(ProductVO product) throws SQLException;

	String kindCheck(String kindcode) throws SQLException;

	
}
