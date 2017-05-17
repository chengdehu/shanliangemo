package com.etc.entity;

public class ArticleComment {

	private int commentid;
	private int articleid;
	private User user;
	private int userid;
	private String commentCont;
	
	public int getCommentid() {
		return commentid;
	}
	
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	
	public int getArticleid() {
		return articleid;
	}
	
	public void setArticleid(int articleid) {
		this.articleid = articleid;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getCommentCont() {
		return commentCont;
	}
	
	public void setCommentCont(String commentCont) {
		this.commentCont = commentCont;
	}
	
	@Override
	public String toString() {
		return "ArticleComment [commentid=" + commentid + ", articleid="
				+ articleid + ", userid=" + userid + ", commentCont="
				+ commentCont + "]";
	}
}
