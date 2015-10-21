package cn.edu.bnu.land.model;

import java.util.Date;

public class ProjectArticle implements java.io.Serializable{
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getArticleIsrecycle() {
		return articleIsrecycle;
	}
	public void setArticleIsrecycle(String articleIsrecycle) {
		this.articleIsrecycle = articleIsrecycle;
	}
	public Date getArticlePublishtime() {
		return articlePublishtime;
	}
	public void setArticlePublishtime(Date articlePublishtime) {
		this.articlePublishtime = articlePublishtime;
	}
	public String getArticleSource() {
		return articleSource;
	}
	public void setArticleSource(String articleSource) {
		this.articleSource = articleSource;
	}
	public String getArticleAuthor() {
		return articleAuthor;
	}
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}
	public String getArticleIsdraft() {
		return articleIsdraft;
	}
	public void setArticleIsdraft(String articleIsdraft) {
		this.articleIsdraft = articleIsdraft;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	private Integer articleId;
	private String articleName;
	private String articleContent;
	private Integer channelId;
	private String articleIsrecycle;
	private Date articlePublishtime;
	private String articleSource;
	private String articleAuthor;
	private String articleIsdraft;
	private Integer commentNum;
	private String projectId;

}
