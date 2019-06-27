package ${pkg};

<#list importList as importItem>
import ${importItem};
</#list>

public class ${clazzName} {
    <#list propertyList as property>
    <#if "${property.comment}"?default("")?trim?length gt 0>
	/**
    * ${property.comment}
    */
	</#if>
    private ${property.javaType} ${property.propertyName};
    </#list>

    public ${clazzName}(){
    }

    public ${clazzName}(<#list propertyList as prop>${prop.javaType} ${prop.propertyName}<#if prop_has_next>, </#if></#list>){
    <#list propertyList as prop>
        this.${prop.propertyName}=${prop.propertyName};
    </#list>
    }

    <#list propertyList as prop>
    public ${prop.javaType} get${prop.propertyName?cap_first}(){
        return this.${prop.propertyName};
    }
    public void set${prop.propertyName?cap_first}(${prop.javaType} ${prop.propertyName}){
        this.${prop.propertyName} = ${prop.propertyName};
    }
    
    </#list>

    @Override
    public String toString() {
        return "${clazzName}{" +
                <#list propertyList as prop>
                "<#if prop_index !=0>, </#if>${prop.propertyName}=" + ${prop.propertyName} +
                </#list>
                '}';
    }

    <#list fkNameTypeList?keys as k>
    //////////////////////////// 外键相关 /////////////////////////////////////
    private ${k} ${k?uncap_first};

    public ${k} get${k}(){
        return this.${k?uncap_first};
    }

    public void set${k}(${k} ${k?uncap_first}){
        this.${k?uncap_first} = ${k?uncap_first};
    }

    </#list>
}
