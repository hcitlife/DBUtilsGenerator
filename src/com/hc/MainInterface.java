package com.hc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.hc.utils.GenerateUtil;

public class MainInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField databaseNameTF = new JTextField("db_test", 20);// todo 产品中前面的数据库名称要去掉
	private JTextField usernameTF = new JTextField("root", 20);
	private JTextField passwordTF = new JTextField("root", 20);
	private JTextField baseUrlTF = new JTextField("com.hc", 20);
	private JTextField entityStrTF = new JTextField("entity", 20);
	private JTextField daoStrTF = new JTextField("dao", 20);
	private JTextField serviceStrTF = new JTextField("service", 20);
	private JTextField controllerStrTF = new JTextField("controller", 20);
	private JTextField implStrTF = new JTextField("impl", 20);
	private JTextField utilsStrTF = new JTextField("utils", 20);
	private JTextField sourcePathTF = new JTextField("d:/abc", 20);
	private JButton btn = new JButton("生成代码");

	public MainInterface() {
		Box vbox = Box.createVerticalBox();// 创建一个垂直箱子，这个箱子将两个水平箱子添加到其中，创建一个横向 glue 组件。

		setTitle("DBUtils代码生成器");
		setSize(335, 294);
		setLayout(new FlowLayout());

		Box hbox1 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox1.add(new JLabel("数据库名称")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(databaseNameTF);
		vbox.add(hbox1);

		Box hbox2 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox2.add(new JLabel("用户名")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(usernameTF);
		vbox.add(hbox2);

		Box hbox3 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox3.add(new JLabel("密码")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox3.add(Box.createHorizontalStrut(10));
		hbox3.add(passwordTF);
		vbox.add(hbox3);

		Box hbox5 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox5.add(new JLabel("基本路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox5.add(Box.createHorizontalStrut(10));
		hbox5.add(baseUrlTF);
		vbox.add(hbox5);

		Box hbox6 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox6.add(new JLabel("实体类包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox6.add(Box.createHorizontalStrut(10));
		hbox6.add(entityStrTF);
		vbox.add(hbox6);

		Box hbox7 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox7.add(new JLabel("dao包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox7.add(Box.createHorizontalStrut(10));
		hbox7.add(daoStrTF);
		vbox.add(hbox7);

		Box hbox8 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox8.add(new JLabel("service包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox8.add(Box.createHorizontalStrut(10));
		hbox8.add(serviceStrTF);
		vbox.add(hbox8);

		Box hbox88 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox88.add(new JLabel("controller包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox88.add(Box.createHorizontalStrut(10));
		hbox88.add(controllerStrTF);
		vbox.add(hbox88);

		Box hbox9 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox9.add(new JLabel("impl包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox9.add(Box.createHorizontalStrut(10));
		hbox9.add(implStrTF);
		vbox.add(hbox9);

		Box hbox10 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox10.add(new JLabel("utils包路径")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox10.add(Box.createHorizontalStrut(10));
		hbox10.add(utilsStrTF);
		vbox.add(hbox10);

		Box hbox4 = Box.createHorizontalBox();// 创建一个水平箱子
		hbox4.add(new JLabel("目标代码位置")); // 在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
		hbox4.add(Box.createHorizontalStrut(10));
		hbox4.add(sourcePathTF);
		vbox.add(hbox4);

		getContentPane().add(vbox);
		getContentPane().add(btn);

		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static String utils;

	// 自动生成配置信息
	private static String databaseName;
	private static String username;
	private static String password;

	protected static String baseUrl;
	protected static String controllerStr;
	protected static String serviceStr;
	protected static String entityStr;
	protected static String implStr;
	protected static String daoStr;
	protected static String sourcePath;

	public static void main(String[] args) {
		MainInterface frame = new MainInterface();
		frame.btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				databaseName = frame.databaseNameTF.getText().trim();
				username = frame.usernameTF.getText().trim();
				password = frame.passwordTF.getText().trim();

				utils = frame.utilsStrTF.getText().trim();

				baseUrl = frame.baseUrlTF.getText().trim();
				serviceStr = frame.serviceStrTF.getText().trim();
				entityStr = frame.entityStrTF.getText().trim();
				controllerStr = frame.controllerStrTF.getText().trim();

				implStr = frame.implStrTF.getText().trim();
				daoStr = frame.daoStrTF.getText().trim();

				sourcePath = frame.sourcePathTF.getText().trim();

				try {
					new GenerateUtil(databaseName, username, password, utils, baseUrl, controllerStr, serviceStr,
							entityStr, implStr, daoStr, sourcePath).createClassByMySqlTable();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				int i = JOptionPane.showConfirmDialog(null, "代码生成成功", "标题", JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

	}
}