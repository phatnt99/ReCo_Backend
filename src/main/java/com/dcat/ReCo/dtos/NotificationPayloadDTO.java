package com.dcat.ReCo.dtos;

public class NotificationPayloadDTO {
	private String title;
	private String content;
	private String image;
	// additional data will go here
	private Long notiId;

	public NotificationPayloadDTO() {
		super();
	}

	public NotificationPayloadDTO(String title, String content, String image) {
		super();
		this.title   = title;
		this.content = content;
		this.image   = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getNotiId() {
		return notiId;
	}

	public void setNotiId(Long notiId) {
		this.notiId = notiId;
	}

}
