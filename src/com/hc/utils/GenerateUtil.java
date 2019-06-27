package com.hc.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hc.bean.Item;

public class GenerateUtil extends DBUtils {

	private String base = System.getProperty("user.dir") + "\\src";// user.dir得到的是项目的绝对路径

	private String databaseName;

	private String utils;
	private String baseUrl;
	private String controllerStr;
	private String serviceStr;
	private String entityStr;
	private String implStr;
	private String daoStr;

	public GenerateUtil(String databaseName, String username, String password, String utils, String baseUrl,
			String controllerStr, String serviceStr, String entityStr, String implStr, String daoStr,
			String sourcePath) {
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;

		url = URL_PREFIX + databaseName + "?useSSL=false&serverTimezone=UTC&user=root&password=&useUnicode=true&characterEncoding=UTF8&autoReconnect=true&failOverReadOnly=false";

		this.utils = utils;
		this.baseUrl = baseUrl;
		this.controllerStr = controllerStr;
		this.serviceStr = serviceStr;
		this.entityStr = entityStr;
		this.implStr = implStr;
		this.implStr = implStr;
		this.daoStr = daoStr;

		if (sourcePath.length() > 0) {
			this.base = sourcePath;
		}
	}

	public void createClassByMySqlTable() throws Exception {
		System.out.println("generating....");

		readTableMetaData(databaseName);

		createPackage(); // 创建包

		createEntityFile(); // 创建实体类
		createDaoFile(); // 创建Dao接口
		createDaoImplFile(); // 创建Mapper映射文件

		createServiceFile();
		createServiceImplFile();

		createPageBeanFile();
		createPageParamFile();
		createConstFile();
		createDBUtilFile();

		createDBCPCfgFile();

		createControllerFile();
		
		System.out.println("generate success!");
	}

	private void createControllerFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);
			// 生成类所对应的package语句
			data.put("pkg", baseUrl.replace("main.java.", "") + "." + controllerStr);
			data.put("serviceNameWithPkg",
					baseUrl.replace("main.java.", "") + "." + serviceStr + "." + newEntity + "Service");
			data.put("serviceNameImplWithPkg", baseUrl.replace("main.java.", "") + "." + serviceStr + "." + implStr
					+ "." + newEntity + "ServiceImpl");
			data.put("clazzName", newEntity);

			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + controllerStr + "\\" + newEntity
					+ "Controller.java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.controllerFile, data, out);
		}
	}

	private void createPackage() {
		String[] baseUrls = baseUrl.split("[.]");
		String basePackage = base;
		File fileBase = new File(basePackage);
		if(fileBase.exists()) {
			DeleteFileUtil.deleteDirectory(basePackage);
		}
		
		for (String b : baseUrls) {
			basePackage += "\\" + b;
			File file = new File(basePackage);
			if (!file.exists()) {
				file.mkdirs();
			}
		}

		new File(basePackage + "\\" + entityStr).mkdir();
		new File(basePackage + "\\" + serviceStr).mkdir();
		new File(basePackage + "\\" + serviceStr + "\\" + implStr).mkdir();
		new File(basePackage + "\\" + controllerStr).mkdir();
		new File(basePackage + "\\" + daoStr).mkdir();
		new File(basePackage + "\\" + daoStr + "\\" + implStr).mkdir();
		new File(basePackage + "\\" + utils).mkdir();
	}

	private void createEntityFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);

			data.put("clazzName", newEntity);// 生成类所对应的package语句
			data.put("pkg", baseUrl.replace("main.java.", "") + "." + entityStr); // 生成Entity类

			Set<String> importList = new HashSet<>(); // 用来生成import语句
			List<Item> propertyList = new ArrayList<>(); // 用来生成各属性

			// 用来生成主键
			String pk = getPK(table).get(0);
			data.put("pk", Tools.field2Property(pk));

			List<Item> paramList = tableInfoList.get(table);
			for (Item item : paramList) {
				String javaType = item.getJavaType();
				String propertyName = item.getPropertyName();
				if (propertyName.equals(Tools.field2Property(pk))) {
					data.put("pkType", javaType);// 主键的数据类型
				}
				importList.add(javaType);
				propertyList.add(new Item(javaType.substring(javaType.lastIndexOf(".") + 1),
						Tools.field2Property(propertyName), item.getComment()));
			}

			Map<String, String> fkNameTypeList = getFkNameType(table);
			data.put("fkNameTypeList", fkNameTypeList);

			data.put("importList", importList);
			data.put("propertyList", propertyList);

			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + entityStr + "\\" + newEntity + ".java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.entityFile, data, out);
		}

	}

	private void createDaoFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);
			// 生成类所对应的package语句
			data.put("clazzNameWithPkg", baseUrl.replace("main.java.", "") + "." + entityStr + "." + newEntity);
			data.put("clazzName", newEntity);
			data.put("pkg", baseUrl.replace("main.java.", "") + "." + daoStr);
			data.put("pageParamWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageParam");
			
			// 获取主键
			String pk = getPK(table).get(0);
			data.put("pk", Tools.field2Property(pk));

			// 获取外键
			List<String> fks = getFk(table);
			List<Item> fkItemList = new ArrayList<>();

			List<Item> paramList = tableInfoList.get(table);
			for (Item item : paramList) {
				String javaType = item.getJavaType();
				String propertyName = item.getPropertyName();
				if (propertyName.equals(Tools.field2Property(pk))) {
					data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));// 主键的数据类型
				}

				if (fks.contains(item.getFieldName())) {
					fkItemList.add(item);
				}

			}

			data.put("fkItemList", fkItemList);

			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + daoStr + "\\" + newEntity + "Dao.java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.daoFile, data, out);
		}
	}

	private void createDaoImplFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);
			// 生成类所对应的package语句
			data.put("clazzNameWithPkg", baseUrl.replace("main.java.", "") + "." + entityStr + "." + newEntity);
			data.put("clazzName", newEntity);
			String pkg = baseUrl.replace("main.java.", "") + "." + daoStr + "." + implStr;
			data.put("pkg", pkg);
			data.put("daoNameWithPkg", baseUrl.replace("main.java.", "") + "." + daoStr + "." + newEntity + "Dao");
			data.put("dbutil", baseUrl.replace("main.java.", "") + "." + utils + "." + "DBUtil");
			data.put("pageParamWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageParam");
			data.put("tableName", table);
			
			// 生成主键
			String pk = getPK(table).get(0);
			data.put("pkColumnName", pk);
			data.put("pk", Tools.field2Property(pk));
			// 获取外键
			List<String> fks = getFk(table);
			List<Item> fkItemList = new ArrayList<>();

			StringBuilder fieldList = new StringBuilder("(");
			StringBuilder wenHaoList = new StringBuilder("(");
			StringBuilder propertyList = new StringBuilder();

			StringBuilder updateList = new StringBuilder();
			StringBuilder updateValueList = new StringBuilder();

			String newEntityObj = Tools.First2LowerCase(newEntity);

			String searchFields = "";

			StringBuffer updateYuJu = new StringBuffer();
			
			List<Item> propList = new ArrayList<>();
			List<Item> paramList = tableInfoList.get(table);
			for (Item item : paramList) {
				fieldList.append(item.getFieldName()).append(", ");
				wenHaoList.append("?, ");
				propertyList.append(newEntityObj).append(".get").append(Tools.First2UpperCase(item.getPropertyName()))
						.append("()").append(", ");

				updateYuJu.append("\t\tif(").append(newEntityObj).append(".get")
						.append(Tools.First2UpperCase(item.getPropertyName())).append("() != null){");
				updateYuJu.append("\n\t\t\tparamBuf.append(\" " + item.getFieldName() + "= ? and\");");
				updateYuJu.append("\n\t\t\tparamValueList.add(" + newEntityObj + ".get"
						+ Tools.First2UpperCase(item.getPropertyName()) + "());");
				updateYuJu.append("\n\t\t}\n");

				String javaType = item.getJavaType();
				String propertyName = item.getPropertyName();
				if (propertyName.equals(Tools.field2Property(pk))) {
					data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));// 主键的数据类型
				} else {
					updateList.append(item.getFieldName()).append("= ? ,");
					updateValueList.append(newEntityObj).append(".get")
							.append(Tools.First2UpperCase(item.getPropertyName())).append("()").append(", ");
				}

				if (fks.contains(item.getFieldName())) {
					fkItemList.add(item);
				}
				searchFields += item.getFieldName() + " as " + item.getPropertyName() + ", ";
				propList.add(new Item(javaType.substring(javaType.lastIndexOf(".") + 1),
						Tools.field2Property(propertyName), item.getComment()));
			}

			data.put("updateYuJu", updateYuJu);
			
			data.put("searchFields", searchFields.substring(0, searchFields.length() - 2));
			data.put("fkItemList", fkItemList);

			String fieldStr = fieldList.substring(0, fieldList.length() - 2).concat(")");
			data.put("fieldStr", fieldStr);
			String wenHaoStr = wenHaoList.substring(0, wenHaoList.length() - 2).concat(")");
			data.put("wenHaoStr", wenHaoStr);
			String valueStr = propertyList.substring(0, propertyList.length() - 2);
			data.put("valueStr", valueStr);

			String updateStr = updateList.substring(0, updateList.length() - 2);
			data.put("updateStr", updateStr);
			updateValueList.append(newEntityObj).append(".get").append(Tools.First2UpperCase(Tools.field2Property(pk))).append("()");
			data.put("updateValueStr", updateValueList.toString());

			data.put("propertyList", propList);
			
			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + daoStr + "\\" + implStr + "\\"
					+ newEntity + "DaoImpl.java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.daoImplFile, data, out);
		}
	}

	private void createServiceFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);
			// 生成类所对应的package语句
			data.put("clazzNameWithPkg", baseUrl.replace("main.java.", "") + "." + entityStr + "." + newEntity);
			data.put("clazzName", newEntity);
			data.put("pkg", baseUrl.replace("main.java.", "") + "." + serviceStr);
			data.put("pageBeanWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageBean");
			data.put("daoNameWithPkg", baseUrl.replace("main.java.", "") + "." + daoStr + "." + newEntity + "Dao");
			data.put("pageParamWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageParam");
			// 生成主键
			String pk = getPK(table).get(0);
			data.put("pk", Tools.field2Property(pk));
			// 获取外键
			List<String> fks = getFk(table);
			List<Item> fkItemList = new ArrayList<>();

			List<Item> paramList = tableInfoList.get(table);
			for (Item item : paramList) {
				String javaType = item.getJavaType();
				String propertyName = item.getPropertyName();
				if (propertyName.equals(Tools.field2Property(pk))) {
					data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));// 主键的数据类型
				}
				if (fks.contains(item.getFieldName())) {
					fkItemList.add(item);
				}
			}
			data.put("fkItemList", fkItemList);

			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + serviceStr + "\\" + newEntity
					+ "Service.java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.serviceFile, data, out);
		}
	}
	
	private void createServiceImplFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型

		for (String table : tableInfoList.keySet()) {
			String newEntity = Tools.getEntryName(table);
			// 生成类所对应的package语句
			data.put("clazzNameWithPkg", baseUrl.replace("main.java.", "") + "." + entityStr + "." + newEntity);
			data.put("serviceNameWithPkg",
					baseUrl.replace("main.java.", "") + "." + serviceStr + "." + newEntity + "Service");
			data.put("daoNameWithPkg", baseUrl.replace("main.java.", "") + "." + daoStr + "." + newEntity + "Dao");
			data.put("daoImplNameWithPkg",
					baseUrl.replace("main.java.", "") + "." + daoStr + "." + implStr + "." + newEntity + "DaoImpl");
			data.put("pageParamWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageParam");
			data.put("clazzName", newEntity);
			data.put("pkg", baseUrl.replace("main.java.", "") + "." + serviceStr + "." + implStr);
			data.put("pageBeanWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageBean");

			// 生成主键
			String pk = getPK(table).get(0);
			data.put("pk", Tools.field2Property(pk));
			// 获取外键
			List<String> fks = getFk(table);
			List<Item> fkItemList = new ArrayList<>();

			List<Item> propertyList = new ArrayList<>(); // 用来生成各属性
			List<Item> paramList = tableInfoList.get(table);
			for (Item item : paramList) {
				String javaType = item.getJavaType();
				String propertyName = item.getPropertyName();
				if (propertyName.equals(Tools.field2Property(pk))) {
					data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));// 主键的数据类型
				}
				if (fks.contains(item.getFieldName())) {
					fkItemList.add(item);
				}
				propertyList.add(new Item(javaType.substring(javaType.lastIndexOf(".") + 1),
						Tools.field2Property(propertyName), item.getComment()));
			}
			data.put("fkItemList", fkItemList);
			data.put("propertyList", propertyList);
			String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + serviceStr + "\\" + implStr + "\\"
					+ newEntity + "ServiceImpl.java";
			Writer out = new FileWriter(new File(fileName));
			FreemarkerUtil.execute(Constant.serviceImplFile, data, out);
		}
	}

	private void createPageBeanFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型
		data.put("pkg", baseUrl.replace("main.java.", "") + "." + utils);
		data.put("pageParamWithPkg", baseUrl.replace("main.java.", "") + "." + utils + "." + "PageParam");
		
		String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + utils + "\\" + "PageBean.java";

		Writer out = new FileWriter(new File(fileName));
		FreemarkerUtil.execute(Constant.pageBeanFile, data, out);
	}
	
	private void createPageParamFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型
		data.put("pkg", baseUrl.replace("main.java.", "") + "." + utils);
		
		String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + utils + "\\" + "PageParam.java";
		
		Writer out = new FileWriter(new File(fileName));
		FreemarkerUtil.execute(Constant.pageParamFile, data, out);
	}

	private void createConstFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型
		data.put("pkg", baseUrl.replace("main.java.", "") + "." + utils);

		String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + utils + "\\" + "Const.java";
		Writer out = new FileWriter(new File(fileName));
		FreemarkerUtil.execute(Constant.constFile, data, out);
	}

	private void createDBUtilFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型
		data.put("pkg", baseUrl.replace("main.java.", "") + "." + utils);

		String fileName = base + "\\" + baseUrl.replace(".", "\\") + "\\" + utils + "\\" + "DBUtil.java";
		Writer out = new FileWriter(new File(fileName));
		FreemarkerUtil.execute(Constant.dbutilFile, data, out);
	}
	
	private void createDBCPCfgFile() throws Exception {
		Map<String, Object> data = new HashMap<>(); // 创建数据模型
		data.put("databaseName", databaseName);
		data.put("username", username);
		data.put("password", password);

		String path = null;
		if (!base.contains(".")) {
			path = base + "\\resources\\";
		} else {
			path = base + "\\" + baseUrl.substring(0, baseUrl.indexOf('.')) + "\\" + "resources\\";
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		String fileName = path + "druid.properties";
		Writer out = new FileWriter(new File(fileName));
		FreemarkerUtil.execute(Constant.druidFile, data, out);
	}

}