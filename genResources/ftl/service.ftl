package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;
import java.sql.SQLException;
import ${pageBeanWithPkg};
import ${pageParamWithPkg};

public interface ${clazzName}Service {
    int add${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;
    
    int batchAdd${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List) throws SQLException;

    int delete${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException;

	int delete${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;
	
	int batchDelete${clazzName}By${pk?cap_first}s(String ${pk}s) throws SQLException;
	
    int update${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;

    ${clazzName} get${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException;

    List<${clazzName}> getAll${clazzName}() throws SQLException;

	List<${clazzName}> get${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;
	
    PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam) throws SQLException;

	PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam, ${clazzName} ${clazzName?uncap_first}) throws SQLException;
  
    <#list fkItemList as item>
    List<${clazzName}> get${clazzName}By${item.propertyName?cap_first}(${item.javaType} ${item.propertyName}) throws SQLException;

    </#list>
}