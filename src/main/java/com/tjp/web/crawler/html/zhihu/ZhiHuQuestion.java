/**
 * 
 */
package com.tjp.web.crawler.html.zhihu;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.puredb.model.Answer;
import com.tjp.web.crawler.puredb.model.Question;
import com.tjp.web.crawler.util.RedisUtil;

/**
 * @author TangJP
 * @date 2017年2月10日
 */
public class ZhiHuQuestion implements HtmlInter {

	private Document htmlDoc = null;
	private Element head = null;
	private Element body = null;
	private String title = "";
	private String url = "";

	private String savePath = Config.IMAGE_PATH;

	/**
	 * 
	 */
	public ZhiHuQuestion(Document htmlDoc, String url) {
		// TODO Auto-generated constructor stub
		this.htmlDoc = htmlDoc;
		this.url = url;
		if (this.htmlDoc != null) {
			head = this.htmlDoc.head();
			body = this.htmlDoc.body();
			title = this.htmlDoc.title();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tjp.web.crawler.inter.HtmlInter#Head()
	 */
	public void Head() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tjp.web.crawler.inter.HtmlInter#Body()
	 */
	public void Body() {
		// TODO Auto-generated method stub
		
		if(body==null)
		{
			return;
		}
		
		Elements questionHead=body.getElementsByClass("QuestionHeader-content");
		if(questionHead==null || questionHead.size()<=0)
		{
			System.out.println("question is null "+url);
			return;
		}
		
		Question question=new Question();
		question.setUrl(url);
		String[] urls=url.split("/");
		String id=urls[urls.length-1];
		question.setId(id);
		
		//插入json的url
		String jsonUrl="https://"+Config.HOST+"/api/v4/questions/"+id+"/answers?offset=3&limit=20&sort_by=default";
		RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_QUESTION_JSON+jsonUrl);
		
		Element questHead=questionHead.first();
		String questTitle=questHead.getElementsByClass("QuestionHeader-title").first().text();
		question.setQuestionInfo(questTitle);
		String questDesc=questHead.getElementsByClass("RichText").first().text();
		question.setDesc(questDesc);
		Elements questViewNum=questHead.getElementsByClass("NumberBoard-item");
		for(Element e : questViewNum)
		{
			String qhtml=e.html();
			String value=e.getElementsByClass("NumberBoard-value").first().text();
			if(qhtml.contains("关注者"))
			{
				question.setNoticerNum(value);
			}else if(qhtml.contains("被浏览"))
			{
				question.setViewNum(value);
			}
		}
		
		RedisUtil.getInstance().hset(Contants.REDIS_KEY_QUESTION, question.getId(), question.toJsonStr());
		
		
		Elements itemDivs = body.getElementsByClass("List-item");
//		System.out.println(itemDivs.size());
		for (Element item : itemDivs) {
			
			Answer answer=new Answer();
			answer.setQuestionId(id);
			
			// 用户信息
			Elements userInfos = item.getElementsByClass("AuthorInfo");
			if (userInfos != null && userInfos.size() > 0) {
				Element userInfo = userInfos.first();
				Elements info = userInfo.getElementsByClass("UserLink");
				if(info!=null && info.size()>0)
				{
					String userName=info.first().text();
					answer.setUserName(userName);
					String userUrl=info.first().attr("href");
					if(!Strings.isNullOrEmpty(userUrl))
					{
						answer.setUserUrl(Config.WEB_URL+userUrl);
					}
				}
			}

			//回答信息
			Elements ansInfo=item.getElementsByClass("RichText CopyrightRichText-richText");
			if(ansInfo!=null && ansInfo.size()>0)
			{
				String ans=ansInfo.first().text();
				answer.setAnswerInfo(ans);
				
				Elements img=ansInfo.select("img");
				for(Element im : img)
				{
					String imgUrl=im.attr("data-original");
					if(!Strings.isNullOrEmpty(imgUrl))
					{
						RedisUtil.getInstance().lPush(Config.REDIS_DOWN_URL_KEY, imgUrl);
					}
				}
			}
			
			//回答时间 以及 url
			Elements time=item.getElementsByClass("ContentItem-time");
			if(time!=null && time.size()>0)
			{
				Element ahref=time.first().select("a").first();
				String timeStr=ahref.text();
				String href=ahref.attr("href");
				answer.setDate(timeStr);
				answer.setUrl(Config.WEB_URL+href);
			}
			
			//点赞 以及 评论
			Elements bottom=item.getElementsByClass("ContentItem-actions Sticky is-bottom");
			if(bottom!=null && bottom.size()>0)
			{
				String likeNum=bottom.first().getElementsByClass("Button VoteButton VoteButton--up").first().text();
				String commt=bottom.first().getElementsByClass("Button ContentItem-action Button--plain").first().text();
				answer.setCommentNum(commt);
				answer.setLikeNum(likeNum);
			}
			System.out.println("question id "+answer.getQuestionId());
			RedisUtil.getInstance().lPush(answer.getQuestionId(), answer.toJsonStr());
			
			if(!Strings.isNullOrEmpty(answer.getUserUrl()))
			{
				RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER+answer.getUserUrl());
			}
			
		}
	}

}
