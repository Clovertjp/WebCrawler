package com.tjp.web.crawler.html.zhihu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.config.Contants;
import com.tjp.web.crawler.inter.HtmlInter;
import com.tjp.web.crawler.puredb.model.User;
import com.tjp.web.crawler.util.RedisUtil;

public class ZhiHuUser implements HtmlInter {

	private Document htmlDoc = null;
	private Element head = null;
	private Element body = null;
	private String title = "";
	private String url = "";
	private String patternStr = "<!-- react-text[\\s\\S]*?>[\\s\\S]*?<!-- /react-text[\\s\\S]*?>";

	public ZhiHuUser(Document htmlDoc, String url) {
		// TODO Auto-generated constructor stub
		this.htmlDoc = htmlDoc;
		this.url = url;
		if (this.htmlDoc != null) {
			head = this.htmlDoc.head();
			body = this.htmlDoc.body();
			title = this.htmlDoc.title();
		}
	}

	public void Head() {
		// TODO Auto-generated method stub

	}

	public void Body() {
		// TODO Auto-generated method stub
		if (body == null) {
			return;
		}
		Elements profileHead = body.getElementsByClass("ProfileHeader-main");
		if (profileHead == null || profileHead.size() <= 0) {
			// 不是用户页面
			return;
		}
		User user = new User();
		user.setIndexUrl(url);
		Element userProfile = profileHead.first();

		// 获取头像
		Elements avatars = userProfile.getElementsByClass("Avatar Avatar--large UserAvatar-inner");
		if (avatars != null && avatars.size() > 0) {
			Element avatarElement = avatars.first();
			if (avatarElement != null) {
				String photoUrl = avatarElement.attr("srcset");
				user.setPhotoUrl(photoUrl.split(" ")[0]);
			}
		}

		// 获取名字及描述
		Elements titles = userProfile.getElementsByClass("ProfileHeader-title");
		if (titles != null && titles.size() > 0) {
			Element titleElement = titles.first();
			if (titleElement != null) {

				Elements nameEle = titleElement.getElementsByClass("ProfileHeader-name");
				if (nameEle != null && nameEle.size() > 0) {
					String name = nameEle.first().text();
					user.setName(name);
				}

				Elements descEle = titleElement.getElementsByClass("RichText ProfileHeader-headline");
				if (descEle != null && descEle.size() > 0) {
					String desc = descEle.first().text();
					user.setDesc(desc);
				}

			}
		}

		// 获取性别 公司 教育等
		Elements others = userProfile.getElementsByClass("ProfileHeader-infoItem");
		if (others != null && others.size() > 0) {

			for (Element e : others) {
				String otherHtml = e.html();
				String valStr = e.text();
//				System.out.println("----"+otherTest);
//				List<String> ls = new ArrayList<String>();
//				Pattern pattern = Pattern.compile(patternStr);
//				Matcher matcher = pattern.matcher(otherTest);
//				while (matcher.find()) {
//					String groupStr = matcher.group();
//					System.out.println("+++"+groupStr);
//					if (Strings.isNullOrEmpty(groupStr)) {
//						continue;
//					}
//					String[] groups = groupStr.split("-->");
//					if (groups.length < 2) {
//						continue;
//					}
//					String str = groups[1];
//					if (Strings.isNullOrEmpty(str)) {
//						continue;
//					}
//					String[] value = str.split("<!--");
//					ls.add(value[0]);
//				}
//				String valStr = "";
//				if (ls.size() > 0) {
//					valStr = Joiner.on("|").join(ls);
//				}

				// 教育
				if (otherHtml.contains("Icon Icon--education")) {
					user.setEducation(valStr);
				}

				// 公司
				if (otherHtml.contains("Icon Icon--company")) {
					user.setCompany(valStr);
				}

				// 性别
				if (otherHtml.contains("Icon Icon--male")) {
					user.setSex(Contants.USER_SEX_MALE);
				}
				// 性别
				if (otherHtml.contains("Icon Icon--female")) {
					user.setSex(Contants.USER_SEX_FEMALE);
				}
			}

		}
		
		Elements tabHead = body.getElementsByClass("Tabs ProfileMain-tabs");
		if(tabHead!=null && tabHead.size()>0)
		{
			Elements items=tabHead.first().getElementsByClass("Tabs-item");
			for(Element item : items)
			{
				String control=item.attr("aria-controls");
				String href=item.getElementsByClass("Tabs-link").attr("href");
				if("Profile-answers".equals(control))
				{
					//回答
					String numStr=item.getElementsByClass("Tabs-meta").first().text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setAnswerNum(Integer.parseInt(numStr));
					}
					if(!Strings.isNullOrEmpty(href))
					{
						RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER_QUESTION+Config.WEB_URL+href);
					}
				}else if("Profile-posts".equals(control))
				{
					//分享
					String numStr=item.getElementsByClass("Tabs-meta").first().text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setShareNum(Integer.parseInt(numStr));
					}
				}else if("Profile-asks".equals(control))
				{
					//提问
					String numStr=item.getElementsByClass("Tabs-meta").first().text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setQuestionNum(Integer.parseInt(numStr));
					}
					if(!Strings.isNullOrEmpty(href))
					{
						RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER_QUESTION+Config.WEB_URL+href);
					}
				}else if("Profile-collections".equals(control))
				{
					//收藏
					String numStr=item.getElementsByClass("Tabs-meta").first().text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setCollectionNum(Integer.parseInt(numStr));
					}
				}
			}
		}
		
		Elements follwerHead = body.getElementsByClass("NumberBoard FollowshipCard-counts");
		if(follwerHead!=null && follwerHead.size()>0)
		{
			Elements items=follwerHead.first().getElementsByClass("Button NumberBoard-item Button--plain");
			for(Element item : items)
			{
				String outHtml=item.html();
				String href=item.getElementsByClass("Button NumberBoard-item Button--plain").first().attr("href");
				if(outHtml.contains("关注了"))
				{
					String numStr=item.getElementsByClass("NumberBoard-value").text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setFollowingNum(Integer.parseInt(numStr));
					}
				}else if(outHtml.contains("关注者"))
				{
					String numStr=item.getElementsByClass("NumberBoard-value").text();
					if(!Strings.isNullOrEmpty(numStr))
					{
						user.setFollowerNum(Integer.parseInt(numStr));
					}
				}
				if(!Strings.isNullOrEmpty(href))
				{
					RedisUtil.getInstance().lPush(Config.REDIS_URL_KEY, Contants.KEY_USER_FOLLOW+Config.WEB_URL+href);
				}
			}
		}
		RedisUtil.getInstance().hset(Contants.REDIS_KEY_USER, user.getName(), user.toJsonStr());
		RedisUtil.getInstance().lPush(Config.REDIS_DOWN_URL_KEY, user.getPhotoUrl());
	}

}

