package com.tjp.web.crawler.task;

import com.tjp.web.crawler.manager.ScheduleManager;
import com.tjp.web.crawler.util.DownloadUtil;

public class DownloadTask implements Runnable {
	
	private String url;
	private String path;
	
	public DownloadTask(String url,String path)
	{
		this.url=url;
		this.path=path;
	}

	public void run() {
		// TODO Auto-generated method stub
		try
		{
			DownloadUtil.download(url, path);
		}finally {
			ScheduleManager.getInstance().desDownCount();
		}
	}

}
