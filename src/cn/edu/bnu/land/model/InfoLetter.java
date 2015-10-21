package cn.edu.bnu.land.model;

// Generated 2013-12-2 20:41:11 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * InfoLetter generated by hbm2java
 */
public class InfoLetter implements java.io.Serializable {

	private Integer letterId;
	private String letterTitle;
	private String letterContent;
	private String letterAuthor;
	private String letterIsreply;
	private String letterIsshow;
	private String letterReplycontent;
	private String letterTel;
	private String letterEmail;
	private String letterAddress;
	private Date letterWritetime;
	private Date letterReplytime;
	private String letterAuthorcardid;

	public InfoLetter() {
	}

	public InfoLetter(String letterTitle, String letterContent,
			String letterAuthor, String letterIsreply, String letterIsshow,
			String letterReplycontent, String letterTel, String letterEmail,
			String letterAddress, Date letterWritetime, Date letterReplytime,
			String letterAuthorcardid) {
		this.letterTitle = letterTitle;
		this.letterContent = letterContent;
		this.letterAuthor = letterAuthor;
		this.letterIsreply = letterIsreply;
		this.letterIsshow = letterIsshow;
		this.letterReplycontent = letterReplycontent;
		this.letterTel = letterTel;
		this.letterEmail = letterEmail;
		this.letterAddress = letterAddress;
		this.letterWritetime = letterWritetime;
		this.letterReplytime = letterReplytime;
		this.letterAuthorcardid = letterAuthorcardid;
	}

	public Integer getLetterId() {
		return this.letterId;
	}

	public void setLetterId(Integer letterId) {
		this.letterId = letterId;
	}

	public String getLetterTitle() {
		return this.letterTitle;
	}

	public void setLetterTitle(String letterTitle) {
		this.letterTitle = letterTitle;
	}

	public String getLetterContent() {
		return this.letterContent;
	}

	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}

	public String getLetterAuthor() {
		return this.letterAuthor;
	}

	public void setLetterAuthor(String letterAuthor) {
		this.letterAuthor = letterAuthor;
	}

	public String getLetterIsreply() {
		return this.letterIsreply;
	}

	public void setLetterIsreply(String letterIsreply) {
		this.letterIsreply = letterIsreply;
	}

	public String getLetterIsshow() {
		return this.letterIsshow;
	}

	public void setLetterIsshow(String letterIsshow) {
		this.letterIsshow = letterIsshow;
	}

	public String getLetterReplycontent() {
		return this.letterReplycontent;
	}

	public void setLetterReplycontent(String letterReplycontent) {
		this.letterReplycontent = letterReplycontent;
	}

	public String getLetterTel() {
		return this.letterTel;
	}

	public void setLetterTel(String letterTel) {
		this.letterTel = letterTel;
	}

	public String getLetterEmail() {
		return this.letterEmail;
	}

	public void setLetterEmail(String letterEmail) {
		this.letterEmail = letterEmail;
	}

	public String getLetterAddress() {
		return this.letterAddress;
	}

	public void setLetterAddress(String letterAddress) {
		this.letterAddress = letterAddress;
	}

	public Date getLetterWritetime() {
		return this.letterWritetime;
	}

	public void setLetterWritetime(Date letterWritetime) {
		this.letterWritetime = letterWritetime;
	}

	public Date getLetterReplytime() {
		return this.letterReplytime;
	}

	public void setLetterReplytime(Date letterReplytime) {
		this.letterReplytime = letterReplytime;
	}

	public String getLetterAuthorcardid() {
		return this.letterAuthorcardid;
	}

	public void setLetterAuthorcardid(String letterAuthorcardid) {
		this.letterAuthorcardid = letterAuthorcardid;
	}

}
