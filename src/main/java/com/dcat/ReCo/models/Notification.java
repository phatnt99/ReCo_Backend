package com.dcat.ReCo.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.dcat.ReCo.constants.NotiEnum;
import com.dcat.ReCo.constants.ReservationType;

@Entity
@Table(name = "notification")
public class Notification extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String            title;
	private String            content;
	private String            image;
	private String            time;
	private Long              refId;
	private Integer           type;
	private String            token;
	private Integer           isAll;
	private Long              userId;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static Notification fromVoucher(Voucher voucher) {
		Notification noti = new Notification();
		noti.setTitle(voucher.getTitle());
		noti.setContent("Nhà hàng: " + voucher.getRestaurant().getName());
		noti.setImage(voucher.getImage());
		noti.setTime(
				"Từ " + voucher.getFromTime() + " đến " + voucher.getToTime());
		noti.setType(NotiEnum.VOUCHER.getValue());
		noti.setRefId(voucher.getId());
		noti.setIsAll(1);
		
		return noti;
	}

	public static Notification fromRes(Restaurant res) {
		Notification noti = new Notification();
		noti.setTitle("Nhà hàng " + res.getName() + " nay đã có mặt trên ReCo");
		noti.setContent(res.getCuisine());
		noti.setImage(res.getLogo());
		noti.setType(NotiEnum.RESTAURANT.getValue());
		noti.setRefId(res.getId());
		noti.setIsAll(1);
		return noti;
	}

	public static Notification fromApprove(Reservation reser) {
		Notification noti = new Notification();
		noti.setTitle("Trạng thái đặt chỗ tại nhà hàng "
				+ reser.getRestaurant().getName() + " vừa được cập nhật");
		noti.setContent("Trạng thái hiện tại: "
				+ ReservationType.toStr(reser.getType()));
		noti.setImage(reser.getRestaurant().getLogo());
		noti.setType(NotiEnum.RESTAURANT.getValue());
		noti.setRefId(reser.getId());
		noti.setIsAll(0);
		noti.setUserId(reser.getUser().getId());
		return noti;
	}

	public static Notification fromDelete(String title, String logo, int type, Long uid) {
		Notification noti = new Notification();
		if (type == 1) {
			// comment
			noti.setTitle("Bình luận của bạn vừa bị gỡ bỏ");
			noti.setContent("Nội dung bình luận: " + title);
		} else {
			// comment
			noti.setTitle("Review của bạn vừa bị gỡ bỏ");
			noti.setContent("Tiêu đề review: " + title);
		}

		noti.setImage(logo);
		noti.setType(NotiEnum.OTHER.getValue());
		noti.setIsAll(0);
		noti.setUserId(uid);
		return noti;
	}

	public Integer getIsAll() {
		return isAll;
	}

	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
