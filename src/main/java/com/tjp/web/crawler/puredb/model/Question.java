package com.tjp.web.crawler.puredb.model;

import org.json.JSONObject;

public class Question {
	private String id;
	private String questionInfo;
	private String desc;
	private String noticerNum;
	private String viewNum;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionInfo() {
		return questionInfo;
	}
	public void setQuestionInfo(String questionInfo) {
		this.questionInfo = questionInfo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getNoticerNum() {
		return noticerNum;
	}
	public void setNoticerNum(String noticerNum) {
		this.noticerNum = noticerNum;
	}
	public String getViewNum() {
		return viewNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public JSONObject toJsonObj()
	{
		JSONObject obj=new JSONObject();
		obj.put("id", id);
		obj.put("questionInfo", questionInfo);
		obj.put("desc", desc);
		obj.put("noticerNum", noticerNum);
		obj.put("viewNum", viewNum);
		obj.put("url", url);
		return obj;
	}
	
	public String toJsonStr() {
		// TODO Auto-generated method stub
		return toJsonObj().toString();
	}

}
