/**
 * 
 */
package com.tjp.web.crawler.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author TangJP
 * @date 2017年2月9日
 */
public class Download {
	
	private ExecutorService downService;
	
	private static Download download=new Download();
	
	public static Download getInstance()
	{
		return download;
	}
	
	/**
	 * 
	 */
	private Download() {
		// TODO Auto-generated constructor stub
		downService = Executors.newFixedThreadPool(5);
	}
	
	public void downloadService(final String urlString, final String savePath)
	{
		downService.execute(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				download(urlString, savePath);
			}
		});
	}

	private void download(String urlString, String savePath) {
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		if(urlString==null || "".equals(urlString))
		{
			return ;
		}
		String[] strs = urlString.split("/");
		String filename=strs[strs.length-1];
		URL url;
		InputStream is=null;
		OutputStream os=null;
		int len;
		try {
//			System.out.println(urlString);
			url = new URL(urlString);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(5 * 1000);
			is = con.getInputStream();
			byte[] bs = new byte[1024];
			os = new FileOutputStream(sf.getPath() + "\\" + filename);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(os!=null)
			{
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is!=null)
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	public void shutDown()
	{
		downService.shutdown();
	}

}
