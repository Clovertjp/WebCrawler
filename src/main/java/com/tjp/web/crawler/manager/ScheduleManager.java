package com.tjp.web.crawler.manager;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.jsoup.helper.Validate;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.task.DownloadTask;
import com.tjp.web.crawler.task.HttpTask;
import com.tjp.web.crawler.util.RedisUtil;

public class ScheduleManager {
	
	private static ScheduleManager scheduleManager=new ScheduleManager();
	
	private ScheduledThreadPoolExecutor httpScheduler;
	private ScheduledThreadPoolExecutor htmlScheduler;
	private ScheduledThreadPoolExecutor downScheduler;
	private volatile boolean runing=true;
	private volatile int httpCount;
	private Object httpLock=new Object();
	private volatile int downCount;
	private Object downLock=new Object();
	
	private ScheduleManager()
	{
		httpCount=0;
		downCount=0;
		httpScheduler=new ScheduledThreadPoolExecutor(Config.HTTP_POOL, 
				new ThreadFactoryBuilder().setNameFormat("http-pool-%d").build());
		
		htmlScheduler=new ScheduledThreadPoolExecutor(Config.HTML_POOL, 
				new ThreadFactoryBuilder().setNameFormat("html-pool-%d").build());
		
		downScheduler=new ScheduledThreadPoolExecutor(Config.DOWN_POOL, 
				new ThreadFactoryBuilder().setNameFormat("down-pool-%d").build());
	}
	
	public static ScheduleManager getInstance()
	{
		return scheduleManager;
	}
	
	public void start()
	{
		startHttp();
		startDown();
	}
	
	private void startDown()
	{
		Thread thread=new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				while(runing)
				{
					List<String> list=RedisUtil.getInstance().lRange(Config.REDIS_DOWN_URL_KEY, 0, 1);
//					System.out.println(list.size());
					if(list.isEmpty() || downCount==Config.DOWN_POOL)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					incDownCount();
					String url=RedisUtil.getInstance().rPop(Config.REDIS_DOWN_URL_KEY);
					downScheduler.execute(new DownloadTask(url, Config.IMAGE_PATH));
				}
			}
		});
		thread.start();
	}
	
	private void startHttp()
	{
		Thread thread=new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				while(runing)
				{
					List<String> list=RedisUtil.getInstance().lRange(Config.REDIS_URL_KEY, 0, 1);
//					System.out.println(list.size());
					if(list.isEmpty() || httpCount==Config.HTTP_POOL)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					incHttpCount();
					String url=RedisUtil.getInstance().rPop(Config.REDIS_URL_KEY);
//					System.out.println(url);
					String methodKey=url.substring(0, 3);
					String httpUrl=url.substring(3, url.length());
					httpScheduler.execute(new HttpTask(methodKey, httpUrl));
				}
			}
		});
		thread.start();
	}
	
	public void desHttpCount()
	{
		synchronized (httpLock) {
			httpCount--;
		}

	}
	
	public void incHttpCount()
	{
		synchronized (httpLock) {
			httpCount++;
		}
	}
	
	public void desDownCount()
	{
		synchronized (downLock) {
			downCount--;
		}

	}
	
	public void incDownCount()
	{
		synchronized (downLock) {
			downCount++;
		}
	}
	
	
	public ScheduledThreadPoolExecutor getHttpScheduler() {
		return httpScheduler;
	}

	public void setHttpScheduler(ScheduledThreadPoolExecutor httpScheduler) {
		this.httpScheduler = httpScheduler;
	}

	public ScheduledThreadPoolExecutor getHtmlScheduler() {
		return htmlScheduler;
	}

	public void setHtmlScheduler(ScheduledThreadPoolExecutor htmlScheduler) {
		this.htmlScheduler = htmlScheduler;
	}

	public ScheduledThreadPoolExecutor getDownScheduler() {
		return downScheduler;
	}

	public void setDownScheduler(ScheduledThreadPoolExecutor downScheduler) {
		this.downScheduler = downScheduler;
	}

	public void shutdown()
	{
		runing=false;
		httpScheduler.shutdown();
		htmlScheduler.shutdown();
		downScheduler.shutdown();
	}
	
	
	

}
