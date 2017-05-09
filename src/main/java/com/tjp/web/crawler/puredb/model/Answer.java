package com.tjp.web.crawler.puredb.model;

import org.json.JSONObject;

public class Answer {
	
	private String questionId;
	private String url;
	private String userName;
	private String userUrl;
	private String answerInfo;
	private String likeNum;
	private String commentNum;
	private String date;
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getAnswerInfo() {
		return answerInfo;
	}
	public void setAnswerInfo(String answerInfo) {
		this.answerInfo = answerInfo;
	}
	public String getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public JSONObject toJsonObj()
	{
		JSONObject obj =new JSONObject();
		obj.put("questionId", questionId);
		obj.put("url", url);
		obj.put("userName", userName);
		obj.put("userUrl", userUrl);
		obj.put("answerInfo", answerInfo);
		obj.put("likeNum", likeNum);
		obj.put("commentNum", commentNum);
		obj.put("date", date);
		return obj;
	}
	
	public String toJsonStr()
	{
		return toJsonObj().toString();
	}

}
