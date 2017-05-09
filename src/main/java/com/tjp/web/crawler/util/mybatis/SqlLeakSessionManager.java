package com.tjp.web.crawler.util.mybatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlLeakSessionManager {
	
	private static SqlLeakSessionManager sqlLeakSessionManager=new SqlLeakSessionManager();
	
	private Map<Long,SqlSessionProxy> sessionMap=null;
	
	private SqlLeakSessionManager()
	{
		sessionMap=new ConcurrentHashMap<Long, SqlSessionProxy>();
	}

	public static SqlLeakSessionManager getInstance()
	{
		return sqlLeakSessionManager;
	}
	
	public void onOpen(SqlSessionProxy sqlSession)
	{
		sessionMap.put(System.currentTimeMillis(), sqlSession);
	}
	
	public void onClose(SqlSessionProxy sqlSession)
	{
		if(sessionMap.containsValue(sqlSession))
		{
			sessionMap.remove(sqlSession);
		}
	}
}
