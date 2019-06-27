package com.hc.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hc.bean.Item;

public class DBUtils {

	// 数据库连接信息
	protected final String URL_PREFIX = "jdbc:mysql://localhost:3306/";
	protected static String driver = "com.mysql.cj.jdbc.Driver";
	protected String url;
	protected String username;
	protected String password;

	public DBUtils() {
	}

	static {// 读取配置文件，初始化数据
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public Connection getConnection(String url) throws ClassNotFoundException, SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try { // 建议采用这种形式来释放资源，因为finally里面的一定会被释放
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 内部数据格式：表名 <对应Java类型 修正后的表字段名 对应Java类型 修正后的表字段名。。。>
	protected static Map<String, List<Item>> tableInfoList = new HashMap<>();// tb_dept <java.lang.Integer deptno>

	protected void readTableMetaData(String databaseName) throws Exception {
		List<String> tables = getAllTableNamesByDatabase(databaseName);

		// 遍历每一个表取出表中字段的名称、类型、对应的Java类型等信息
		for (String table : tables) {// 遍历每一个表取出表中字段的名称、类型、对应的Java类型等信息
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("show full columns from " + table);// 得到表的描述信息（字段名称、数据类型等）

				List<Item> paramList = new ArrayList<>();
				while (rs.next()) {
					String fieldName = rs.getString("field");
					String propertyName = Tools.field2Property(fieldName);

					String comment = rs.getString("Comment");

					String sqlType = rs.getString("type").toUpperCase();
					String javaType = getJavaTypeByDbType(sqlType);
					if (sqlType.contains("(")) {
						sqlType = sqlType.substring(0, sqlType.indexOf("("));
					}

					if (sqlType.equals("INT")) {// MyBatis中JdbcType的int类型的名称为INTEGER
						sqlType = "INTEGER";
					}
					Item item = new Item(propertyName, comment, javaType, fieldName, sqlType);

					if (getPK(table).size() > 0 && getPK(table).get(0).equalsIgnoreCase(fieldName)) {
						item.setPk(true);
					}

					paramList.add(item);
				}
				tableInfoList.put(table, paramList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeAll(conn, stmt, rs);
			}
		}

	}

	private List<String> getAllTableNamesByDatabase(String databaseName) {
		List<String> tables = new ArrayList<>();// 用来放置所有表的名字
		// 获取当前数据库所有的表名
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("select table_name from information_schema.TABLES where TABLE_SCHEMA=?");
			ps.setString(1, databaseName);
			rs = ps.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, ps, rs);
		}
		return tables;
	}

	private String getJavaTypeByDbType(String type) {
		String javaType = null;
		if (type.indexOf("CHAR") > -1 || type.indexOf("TEXT") > -1 || type.indexOf("ENUM") > -1
				|| type.indexOf("SET") > -1) {
			javaType = "java.lang.String";
		} else if (type.indexOf("TIME") > -1 || type.indexOf("DATE") > -1 || type.indexOf("YEAR") > -1) {
			javaType = "java.util.Date";
		} else if (type.indexOf("BIGINT") > -1) {
			javaType = "java.lang.Long";
		} else if (type.indexOf("TINYINT") > -1) {
			javaType = "java.lang.Byte";
		} else if (type.indexOf("INT") > -1) {
			javaType = "java.lang.Integer";
		} else if (type.indexOf("BIT") > -1) {
			javaType = "java.lang.Boolean";
		} else if (type.indexOf("FLOAT") > -1 || type.indexOf("REAL") > -1) {
			javaType = "java.lang.Double";
		} else if (type.indexOf("DOUBLE") > -1 || type.indexOf("NUMERIC") > -1) {
			javaType = "java.lang.Double";
		} else if (type.indexOf("BLOB") > -1 || type.indexOf("BINARY") > -1) {
			javaType = "byte[]";
		} else if (type.indexOf("JSON") > -1) {
			javaType = "java.lang.String";
		} else if (type.indexOf("DECIMAL") > -1) {
			javaType = "java.math.BigDecimal";
		} else {
			System.out.println("type:" + type);
		}
		return javaType;
	}

	/**
	 * 返回数据表中的主键
	 *
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	protected List<String> getPK(String table) throws SQLException {
		List<String> res = new ArrayList<>();
		Connection conn = getConnection();
		String catalog = conn.getCatalog(); // catalog 其实也就是数据库名
		DatabaseMetaData metaData = conn.getMetaData();

		ResultSet rs = null;
		rs = metaData.getPrimaryKeys(catalog, null, table);// 适用mysql
		while (rs.next()) {
			res.add(rs.getString("COLUMN_NAME"));
		}
		closeAll(conn, null, rs);
		return res;
	}

	/**
	 * 返回数据表中的外键
	 *
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	protected List<String> getFk(String table) throws SQLException {
		List<String> res = new ArrayList<>();
		Connection conn = getConnection();
		String catalog = conn.getCatalog(); // catalog 其实也就是数据库名
		DatabaseMetaData metaData = conn.getMetaData();

		ResultSet rs = metaData.getImportedKeys(catalog, null, table);
		while (rs.next()) {
			res.add(rs.getString("FKCOLUMN_NAME"));
		}
		closeAll(conn, null, rs);
		return res;
	}

	protected Map<String, String> getFkNameType(String table) throws SQLException {
		Map<String, String> res = new HashMap<>();
		Connection conn = getConnection();
		String catalog = conn.getCatalog(); // catalog 其实也就是数据库名
		DatabaseMetaData metaData = conn.getMetaData();

		ResultSet rs = metaData.getImportedKeys(catalog, null, table);
		while (rs.next()) {
			String fkColumnName = rs.getString("FKCOLUMN_NAME");
			String fkName = null;
			int endIndex = fkColumnName.lastIndexOf('_');
			if (endIndex > 0) {
				fkName = fkColumnName.substring(0, endIndex);
			} else {
				fkName = fkColumnName;
			}

			String pkTablenName = rs.getString("PKTABLE_NAME");
			String fkType = Tools.getEntryName(pkTablenName);
			res.put(fkType, fkName);
		}
		closeAll(conn, null, rs);
		return res;
	}
}
