package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;
import java.sql.SQLException;
import ${pageParamWithPkg};

public interface ${clazzName}Dao {
    int insert${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;

	int batchInsert${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List) throws SQLException;

    int delete${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException;

	int delete${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException;

	int batchDelete${clazzName}By${pk?cap_first}s(String ${pk}s) throws SQLException;

    int update${clazzName}(${clazzName} ${clazzName?uncap_first}) throws SQLException;

    long selectCount() throws SQLException;
    
    long selectCountByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException;

    ${clazzName} select${clazzName}By${pk?cap_first}(${pkType} ${pk}) throws SQLException;

    List<${clazzName}> selectAll${clazzName}() throws SQLException;

	List<${clazzName}> select${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}) throws SQLException;

    List<${clazzName}> select${clazzName}WithPagination(PageParam pageParam) throws SQLException;

    List<${clazzName}> select${clazzName}WithPaginationByCondition(PageParam pageParam, ${clazzName} ${clazzName?uncap_first}) throws SQLException;
    
    <#list fkItemList as item>
    List<${clazzName}> select${clazzName}By${item.propertyName?cap_first}(${item.javaType} ${item.propertyName}) throws SQLException;

    </#list>
}