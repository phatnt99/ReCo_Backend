package com.dcat.ReCo.dtos.reservation;

import java.util.List;

public class ReservationOverviewDTO {

	List<ReservationOverview> waitings;

	List<ReservationOverview> approves;

	List<ReservationOverview> canceles;

	public List<ReservationOverview> getWaitings() {
		return waitings;
	}

	public void setWaitings(List<ReservationOverview> waitings) {
		this.waitings = waitings;
	}

	public List<ReservationOverview> getApproves() {
		return approves;
	}

	public void setApproves(List<ReservationOverview> approves) {
		this.approves = approves;
	}

	public List<ReservationOverview> getCanceles() {
		return canceles;
	}

	public void setCanceles(List<ReservationOverview> canceles) {
		this.canceles = canceles;
	}

}
