package sketchoogiri.domain.model;

import java.time.LocalDateTime;

public class Answer {
	private Integer answerId;
	private Integer themeId;
	private String userId;
	private String description;
	private String imgUrl;
	private LocalDateTime postedTime;

	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public LocalDateTime getPostedTime() {
		return postedTime;
	}
	public void setPostedTime(LocalDateTime postedTime) {
		this.postedTime = postedTime;
	}
}

