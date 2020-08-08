package com.tosh.kaleido.enums;

public enum BannerStatus {

	ONLINE("1"), OFFLINE("0");

	private String status;

	BannerStatus(String status) {
		this.status = status;
	}

	public String status() {
		return this.status;
	}

	public BannerStatus shift() {
		return this.equals(OFFLINE) ? ONLINE : OFFLINE;
	}
}
