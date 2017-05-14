package com.tjp.web.crawler.json.zhihu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.puredb.model.Answer;
import com.tjp.web.crawler.util.RedisUtil;
import com.tjp.web.crawler.util.StringToHtml;

public class ZhiHuQuestionJson implements HtmlInter {
	private String url;
	private String jsonData;
	private JSONObject jsonObj;
	
	public ZhiHuQuestionJson(String jsonData,String url)
	{
		this.url=url;
		this.jsonData=jsonData;
		jsonObj=new JSONObject(jsonData);
	}

	public void Head() {
		// TODO Auto-generated method stub

	}

	public void Body() {
		// TODO Auto-generated method stub
		if(jsonObj==null)
		{
			System.out.println("is null");
			return ;
		}
		
		JSONObject pageObj=jsonObj.getJSONObject("paging");
		if(pageObj!=null)
		{
			String next=pageObj.getString("next");
			next=next.replaceFirst("http", "https");
			RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_QUESTION_JSON+next);
		}
		
		JSONArray dataArray=jsonObj.getJSONArray("data");
		if(dataArray!=null)
		{
			for(int i=0;i<dataArray.length();i++)
			{
				JSONObject ansObj=dataArray.getJSONObject(i);
				if(ansObj==null)
				{
					continue;
				}
				JSONObject authObj=ansObj.getJSONObject("author");
				String userName=authObj.getString("name");
				String userToken=authObj.getString("url_token");
				String userUrl="";
				if(!Strings.isNullOrEmpty(userToken))
				{
					userUrl="https://www.zhihu.com/people/"+userToken;
					RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER+userUrl);
				}
				String ansStr=ansObj.getString("content");
				String comment_count=ansObj.getString("comment_count");
				String created_time=ansObj.getString("created_time");
				String voteup_count=ansObj.getString("voteup_count");
				String ansId=ansObj.getString("id");
				JSONObject quesObj=ansObj.getJSONObject("question");
				String quesId=quesObj.getString("id");
				String ansUrl="https://www.zhihu.com/question/"+quesId+"/answer/"+ansId;
				String html=new StringToHtml("", ansStr).toHtml();
				if(!Strings.isNullOrEmpty(html))
				{
					try
					{
						Document htmlDoc=Jsoup.parse(html);
						Elements img=htmlDoc.select("img");
						for(Element im : img)
						{
							String imgUrl=im.attr("data-original");
							if(!Strings.isNullOrEmpty(imgUrl))
							{
								RedisUtil.getInstance().lPush(Config.REDIS_DOWN_URL_KEY, imgUrl);
							}
						}
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				Answer answer=new Answer();
				answer.setAnswerInfo(ansStr);
				answer.setCommentNum(comment_count);
				answer.setDate(created_time);
				answer.setLikeNum(voteup_count);
				answer.setQuestionId(quesId);
				answer.setUrl(ansUrl);
				answer.setUserName(userName);
				answer.setUserUrl(userUrl);
				System.out.println(answer.toJsonStr());
				
				RedisUtil.getInstance().lPush(answer.getQuestionId(), answer.toJsonStr());
			}
		}
	}
	
	public static void main(String[] args) {
		
	}

}
