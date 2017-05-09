package com.tjp.web.crawler.puredb.model;

import org.json.JSONObject;

import com.google.common.base.Strings;

public class User {
	
	private String name;
	private String desc;
	private int sex;
	private String education;
	private String company;
	private int answerNum;
	private int questionNum;
	private int collectionNum;
	private int shareNum;
	private int followingNum;
	private int followerNum;
	private String indexUrl;
	private String photoUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int getFollowingNum() {
		return followingNum;
	}
	public void setFollowingNum(int followingNum) {
		this.followingNum = followingNum;
	}
	public int getFollowerNum() {
		return followerNum;
	}
	public void setFollowerNum(int followerNum) {
		this.followerNum = followerNum;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getIndexUrl() {
		return indexUrl;
	}
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public int getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}
	public int getShareNum() {
		return shareNum;
	}
	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}
	public JSONObject toJson()
	{
		JSONObject obj=new JSONObject();
		obj.put("name", name);
		obj.put("sex", sex);
		obj.put("answerNum", answerNum);
		obj.put("questionNum", questionNum);
		obj.put("followerNum", followerNum);
		obj.put("followingNum", followingNum);
		obj.put("shareNum", shareNum);
		obj.put("collectionNum", collectionNum);
		obj.put("desc", desc);
		obj.put("education", education);
		obj.put("company", company);
		obj.put("indexUrl", indexUrl);
		obj.put("photoUrl", photoUrl);
		return obj;
	}
	
	public String toJsonStr()
	{
		return toJson().toString();
	}
	
}
