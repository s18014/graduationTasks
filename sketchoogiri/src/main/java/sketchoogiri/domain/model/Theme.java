package sketchoogiri.domain.model;

import java.time.LocalDateTime;

public class Theme {
	private Integer themeId;
	private String userId;
	private String request;
	private String imgUrl;
	private LocalDateTime postedTime;

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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
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
