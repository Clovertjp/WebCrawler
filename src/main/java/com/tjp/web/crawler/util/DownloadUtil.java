/**
 * 
 */
package com.tjp.web.crawler.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import com.google.common.base.Strings;

/**
 * @author TangJP
 * @date 2017年2月9日
 */
public class DownloadUtil {
	static{
		System.setProperty("https.protocols", "TLSv1");
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			
		}
		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			
		}
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static void download(String urlString, String savePath) {
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		if (Strings.isNullOrEmpty(urlString)) {
			return;
		}
		String[] strs = urlString.split("/");
		String filename = strs[strs.length - 1];
		URL url;
		InputStream is = null;
		OutputStream os = null;
		int len;
		try {
			url = new URL(urlString);
//			URLConnection con = url.openConnection();
//			con.setConnectTimeout(5 * 1000);
			TrustManager[] tm = { new TrustAnyTrustManager() };
			   // SSLContext sc = SSLContext.getInstance("SSL");
			SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
			sc.init(null, tm,new java.security.SecureRandom());
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setRequestProperty("Host", PropertiesUtil.getInstance().getProperty("host", ""));
			
			String agent=PropertiesUtil.getInstance().getProperty("agent", "");
			if(!Strings.isNullOrEmpty(agent))
			{
				connection.setRequestProperty("User-Agent", agent);
			}
			is = connection.getInputStream();
			byte[] bs = new byte[1024];
			os = new FileOutputStream(sf.getPath() + "/" + filename);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error "+urlString);
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	public static void main(String[] args) {
		download("https://pic2.zhimg.com/v2-3d4fd080aa3710e3e839c46289b1c5b5_r.jpg", "/Users/tangjp/program_file/zhihu/img");
	}

}
