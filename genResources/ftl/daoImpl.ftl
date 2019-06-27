package ${pkg};

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ${clazzNameWithPkg};
import java.util.List;
import java.util.ArrayList;
import ${dbutil};
import ${daoNameWithPkg};
import java.sql.SQLException;
import ${pageParamWithPkg};

public class ${clazzName}DaoImpl implements ${clazzName}Dao {
    private QueryRunner queryRunner = DBUtil.getRunner();

    @Override
    public int insert${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
        String sql ="insert into ${tableName} ${fieldStr} values ${wenHaoStr}";
        return queryRunner.update(sql ,${valueStr});
    }
    
    @Override
    public int batchInsert${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List) throws SQLException{
    	Object[][] params = new Object[${clazzName?uncap_first}List.size()][${propertyList?size}];

		for (int i = 0; i < params.length; i++) {
			${clazzName} ${clazzName?uncap_first} = ${clazzName?uncap_first}List.get(i);
			<#list propertyList as prop>
		    params[i][${prop_index}] = ${clazzName?uncap_first}.get${prop.propertyName?cap_first}();
			</#list>
		}
		
    	StringBuilder wenHao = new StringBuilder();
		for(int i =0;i<params[0].length;i++) {
			wenHao.append("?,");
		}
		String sql = "insert into ${tableName} values("+wenHao.deleteCharAt(wenHao.length()-1)+")";
		
		queryRunner.batch(sql, params);
		return 1;  // 如果不抛出异常，就返回1，表示删除成功
    }

    @Override
    public int delete${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException{
        String sql ="delete from ${tableName} where ${pkColumnName} = ?";
        return queryRunner.update(sql ,${pk});
    }
    
    @Override
	public int delete${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException{
		List<Object> paramValueList = new ArrayList<>();
		StringBuffer paramBuf = new StringBuffer("1=1 and");
		
		${updateYuJu}
		String sql = "delete from ${tableName} where "+paramBuf.substring(0,paramBuf.length() - 3);
		return queryRunner.update(sql, paramValueList.toArray());
	}
	
	@Override
	public int batchDelete${clazzName}By${pk?cap_first}s(String ${pk}s) throws SQLException {
		String[] split = ${pk}s.split(",");
		Object[][] params = new Object[1][];
		
		StringBuilder wenHao = new StringBuilder();
		for(int i =0;i<split.length;i++) {
			wenHao.append("?,");
		}
		params[0] = split;
		
		String sql ="delete from ${tableName} where ${pkColumnName} in ("+wenHao.deleteCharAt(wenHao.length()-1)+")";
		
		queryRunner.batch(sql, params);
		return 1;  // 如果不抛出异常，就返回1，表示删除成功
	}
	
    @Override
    public int update${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
        String sql ="update ${tableName} set ${updateStr} where ${pk} = ?";
        return queryRunner.update(sql ,${updateValueStr});
    }

    @Override
    public long selectCount() throws SQLException{
        String sql ="select count(*) from ${tableName}";
        Long query = queryRunner.query(sql, new ScalarHandler<Long>());
        return query.intValue();
    }
    
    @Override
    public long selectCountByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException{
    	List<Object> paramValueList = new ArrayList<>();
		StringBuffer paramBuf = new StringBuffer("1=1 and");
		
		${updateYuJu}
        String sql ="select count(*) from ${tableName} where "+paramBuf.substring(0,paramBuf.length() - 3);
        Long query = queryRunner.query(sql, new ScalarHandler<Long>(),paramValueList.toArray());
        return query.intValue();
    }

    @Override
    public ${clazzName} select${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException{
        String sql ="select ${searchFields} from ${tableName} where  ${pk} = ?";
        return queryRunner.query(sql, new BeanHandler<>(${clazzName}.class), ${pk});
    }

    @Override
    public List<${clazzName}> selectAll${clazzName}() throws SQLException{
        String sql ="select ${searchFields} from ${tableName}";
        return queryRunner.query(sql, new BeanListHandler<>(${clazzName}.class));
    }

    @Override
	public List<${clazzName}> select${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException{
		List<Object> paramValueList = new ArrayList<>();
		StringBuffer paramBuf = new StringBuffer("1=1 and");
		
		${updateYuJu}
		String sql = "select ${searchFields}  from ${tableName} where "+paramBuf.substring(0,paramBuf.length() - 3);
		return queryRunner.query(sql, new BeanListHandler<>(${clazzName}.class), paramValueList.toArray());
	}
	
    @Override
    public List<${clazzName}> select${clazzName}WithPagination(PageParam pageParam) throws SQLException {
    	int page = pageParam.getPage();
    	int rows = pageParam.getRows();
    	
        String sql ="select ${searchFields} from ${tableName} limit ?, ?";
        return queryRunner.query(sql, new BeanListHandler<>(${clazzName}.class), (page - 1) * rows, rows);
    }

    @Override
  	public List<${clazzName}> select${clazzName}WithPaginationByCondition(PageParam pageParam, ${clazzName} ${clazzName?uncap_first}) throws SQLException{
  		int page = pageParam.getPage();
    	int rows = pageParam.getRows();
  		
  		List<Object> paramValueList = new ArrayList<>();
		StringBuffer paramBuf = new StringBuffer("1=1 and");
		
		${updateYuJu}
		String sql = "select ${searchFields}  from ${tableName} where "+paramBuf.substring(0,paramBuf.length() - 3)+" limit ?, ?";
		
		paramValueList.add((page - 1) * rows);
		paramValueList.add(rows);
		return queryRunner.query(sql, new BeanListHandler<>(${clazzName}.class), paramValueList.toArray());
  	}
 
    <#list fkItemList as item>
    @Override
    public List<${clazzName}> select${clazzName}By${item.propertyName?cap_first}(${item.javaType} ${item.propertyName}) throws SQLException {
        String sql ="select ${searchFields} from ${tableName} where ${item.fieldName} = ?";
        return queryRunner.query(sql, new BeanListHandler<>(${clazzName}.class) , ${item.propertyName});
    }

    </#list>
}