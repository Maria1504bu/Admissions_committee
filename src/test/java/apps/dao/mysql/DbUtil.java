package apps.dao.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class DbUtil {
	
	static DataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		ResourceBundle bundle = ResourceBundle.getBundle("db");
		String url = bundle.getString("db.url");
		String username = bundle.getString("db.username");
		String password = bundle.getString("db.password");
		return dataSource;
	}

}
