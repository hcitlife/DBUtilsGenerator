package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;
import ${serviceNameWithPkg};
import ${daoNameWithPkg};
import ${daoImplNameWithPkg};
import ${pageBeanWithPkg};
import ${pageParamWithPkg};
import java.sql.SQLException;

public class ${clazzName}ServiceImpl implements ${clazzName}Service {

    private ${clazzName}Dao ${clazzName?uncap_first}Dao = new ${clazzName}DaoImpl();

    @Override
    public int add${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
        int res = ${clazzName?uncap_first}Dao.insert${clazzName}(${clazzName?uncap_first});
        return res;
    }

	@Override
    public int batchAdd${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List) throws SQLException{
		int res = ${clazzName?uncap_first}Dao.batchInsert${clazzName}(${clazzName?uncap_first}List);
		return res;
	}

    @Override
    public int delete${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException{
        int res = ${clazzName?uncap_first}Dao.delete${clazzName}By${pk?cap_first}(${pk});
        return res;
    }
    
 	@Override
	public int delete${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
	 	int res = ${clazzName?uncap_first}Dao.delete${clazzName}ByCondition(${clazzName?uncap_first});
        return res;
	}
	
	@Override
	public int batchDelete${clazzName}By${pk?cap_first}s(String ${pk}s) throws SQLException{
		int res = ${clazzName?uncap_first}Dao.batchDelete${clazzName}By${pk?cap_first}s(${pk}s);
        return res;
	}
	
    @Override
    public int update${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
        int res = ${clazzName?uncap_first}Dao.update${clazzName}(${clazzName?uncap_first});
        return res;
    }

    @Override
    public ${clazzName} get${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException{
        ${clazzName} res = ${clazzName?uncap_first}Dao.select${clazzName}By${pk?cap_first}(${pk});
        return res;
    }

    @Override
    public List<${clazzName}> getAll${clazzName}() throws SQLException{
        List<${clazzName}> res = ${clazzName?uncap_first}Dao.selectAll${clazzName}();
        return res;
    }

	@Override
	public 	List<${clazzName}> get${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException{
		List<${clazzName}> res = ${clazzName?uncap_first}Dao.select${clazzName}ByCondition(${clazzName?uncap_first});
		return res;
	}
	
    @Override
    public PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam) throws SQLException{
        long count = ${clazzName?uncap_first}Dao.selectCount();
        PageBean<${clazzName}> res = new PageBean<>(pageParam, (int)count);
        List<${clazzName}> data = ${clazzName?uncap_first}Dao.select${clazzName}WithPagination(pageParam);
        res.setRecords(data);
        return res;
    }

	@Override
	public PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam, ${clazzName} ${clazzName?uncap_first}) throws SQLException{
        long count = ${clazzName?uncap_first}Dao.selectCountByCondition(${clazzName?uncap_first});
        PageBean<${clazzName}> res = new PageBean<>(pageParam, (int)count);		
		List<${clazzName}> data = ${clazzName?uncap_first}Dao.select${clazzName}WithPaginationByCondition(pageParam,${clazzName?uncap_first});
		res.setRecords(data);
        return res;
	}
  
    <#list fkItemList as item>
    @Override
    public List<${clazzName}> get${clazzName}By${item.propertyName?cap_first}(${item.javaType} ${item.propertyName}) throws SQLException {
        List<${clazzName}> res = ${clazzName?uncap_first}Dao.select${clazzName}By${item.propertyName?cap_first}(${item.propertyName});
        return res;
    }

    </#list>
}