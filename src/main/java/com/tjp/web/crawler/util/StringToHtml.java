package com.tjp.web.crawler.util;

public class StringToHtml {
	
	private String htmlTagBegin="<html>";
	private String htmlTagEnd="</html>";
	
	private String headTagBegin="<head>";
	private String headTagEnd="</head>";
	
	private String titleTagBegin="<title>";
	private String titleTagEnd="</title>";
	
	private String bodyTagBegin="<body>";
	private String bodyTagEnd="</body>";
	
	private String title;
	private String body;
	
	public StringToHtml(String title,String body)
	{
		this.title=title;
		this.body=body;
	}
	
	
	public String toHtml()
	{
		StringBuilder sb=new StringBuilder();
		sb.append(htmlTagBegin)
		.append(headTagBegin)
		.append(titleTagBegin).append(title).append(titleTagEnd)
		.append(headTagEnd)
		.append(bodyTagBegin).append(body).append(bodyTagEnd)
		.append(htmlTagEnd);
		return sb.toString();
	}

}
