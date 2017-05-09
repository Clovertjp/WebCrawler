package com.tjp.web.crawler.util.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class SqlSessionProxy {
	
	private SqlSession session;
	private long lastAccessTime;
	private final long createTime;
	private volatile boolean operationInProgress = false;
	private SqlLeakSessionManager leakSessionManager=SqlLeakSessionManager.getInstance();
	
	public SqlSessionProxy(SqlSession session) {
		// TODO Auto-generated constructor stub
		this.session=session;
		long now=System.currentTimeMillis()/1000;
		lastAccessTime=now;
		createTime=now;
		leakSessionManager.onOpen(this);
	}

	public SqlSession getSession() {
		return session;
	}



	public void setSession(SqlSession session) {
		this.session = session;
	}



	public long getLastAccessTime() {
		return lastAccessTime;
	}



	public void setLastAccessTime() {
		this.lastAccessTime = System.currentTimeMillis()/1000;
	}



	public boolean isOperationInProgress() {
		return operationInProgress;
	}

	private void setOperationInProgress() {
		operationInProgress = true;
	}

	private void setOperationCompleted() {
		operationInProgress = false;
	}



	public long getCreateTime() {
		return createTime;
	}



	public <T> T selectOne(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectOne(statement);
		}finally {
			setOperationCompleted();
		}
		
	}

	public <T> T selectOne(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectOne(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public <E> List<E> selectList(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectList(statement);
		}finally {
			setOperationCompleted();
		}
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectList(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectList(statement, parameter, rowBounds);
		}finally {
			setOperationCompleted();
		}
	}

	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectMap(statement, mapKey);
		}finally {
			setOperationCompleted();
		}
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectMap(statement, parameter, mapKey);
		}finally {
			setOperationCompleted();
		}
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectMap(statement, parameter, mapKey, rowBounds);
		}finally {
			setOperationCompleted();
		}
	}

	public <T> Cursor<T> selectCursor(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectCursor(statement);
		}finally {
			setOperationCompleted();
		}
	}

	public <T> Cursor<T> selectCursor(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectCursor(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.selectCursor(statement, parameter, rowBounds);
		}finally {
			setOperationCompleted();
		}
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.select(statement, handler);
		}finally {
			setOperationCompleted();
		}
	}

	public void select(String statement, ResultHandler handler) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.select(statement, handler);
		}finally {
			setOperationCompleted();
		}
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.select(statement, parameter, rowBounds, handler);
		}finally {
			setOperationCompleted();
		}
	}

	public int insert(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.insert(statement);
		}finally {
			setOperationCompleted();
		}
	}

	public int insert(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.insert(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public int update(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.update(statement);
		}finally {
			setOperationCompleted();
		}
	}

	public int update(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.update(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public int delete(String statement) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.delete(statement);
		}finally {
			setOperationCompleted();
		}
	}

	public int delete(String statement, Object parameter) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.delete(statement, parameter);
		}finally {
			setOperationCompleted();
		}
	}

	public void commit() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.commit();
		}finally {
			setOperationCompleted();
		}
	}

	public void commit(boolean force) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.commit(force);
		}finally {
			setOperationCompleted();
		}
	}

	public void rollback() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.rollback();
		}finally {
			setOperationCompleted();
		}
	}

	public void rollback(boolean force) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			session.rollback(force);
		}finally {
			setOperationCompleted();
		}
	}

	public List<BatchResult> flushStatements() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.flushStatements();
		}finally {
			setOperationCompleted();
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		leakSessionManager.onClose(this);
		session.close();
	}

	public void clearCache() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		session.clearCache();
	}

	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		return session.getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.getMapper(type);
		}finally {
			setOperationCompleted();
		}
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		setLastAccessTime();
		try{
			setOperationInProgress();
			return session.getConnection();
		}finally {
			setOperationCompleted();
		}
	}

}
