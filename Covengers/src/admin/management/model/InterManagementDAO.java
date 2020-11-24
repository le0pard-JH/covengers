package admin.management.model;

import java.sql.SQLException;
import java.util.*;



public interface InterManagementDAO {

	Map<String, String> getManagementInfo() throws SQLException;

	List<Map<String, String>> getListManagementInfo() throws SQLException;
}
