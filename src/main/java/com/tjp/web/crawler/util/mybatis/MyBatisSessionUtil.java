package com.tjp.web.crawler.util.mybatis;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.util.PropertiesUtil;

public class MyBatisSessionUtil {
	private static final String MYBATIS_CONFIG="mybatis-config.xml";
	private static MyBatisSessionUtil myBatisSessionUtil=new MyBatisSessionUtil();
	private volatile SqlSessionFactory sqlSessionFactory=null;
	
	private MyBatisSessionUtil()
	{
		
	}
	
	public static MyBatisSessionUtil getInstance()
	{
		return myBatisSessionUtil;
	}
	
	public synchronized void init()
	{
		if(sqlSessionFactory!=null)
		{
			return;
		}
		try {
			InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					inputStream,
					PropertiesUtil.getInstance().getProperties());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private SqlSessionFactory getSessionFactory()
	{
		if(sqlSessionFactory==null)
		{
			init();
		}
		return sqlSessionFactory;
	}
	
	public SqlSessionProxy getSqlSessionAtuoClose() throws Exception
	{
		return getSqlSession(true);
	}
	
	public SqlSessionProxy getSqlSession() throws Exception
	{
		return getSqlSession(false);
	}
	
	private SqlSessionProxy getSqlSession(boolean autoClose) throws Exception
	{
		SqlSessionFactory factory=getSessionFactory();
		SqlSession session=factory.openSession(autoClose);
		if(session==null)
		{
			throw new Exception("session is null");
		}
		SqlSessionProxy sqlSessionProxy=new SqlSessionProxy(session);
		return sqlSessionProxy;
	}
	
	
	public static void main(String[] args) throws IOException {
	}

}
