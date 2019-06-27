package ${pkg};

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtil {
    private static DataSource ds;

    private DBUtil() {
    }

    static {
        Properties properties = new Properties();
        try {
            InputStream is =DBUtil.class.getClassLoader().getResourceAsStream("druid.properties");//读取配置文件，初始化数据源
            properties.load(is);
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(Connection conn) throws SQLException {
        if (conn != null)
            conn.close();
    }

    public static QueryRunner getRunner() {
        return new QueryRunner(ds);
    }

}