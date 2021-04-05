package com.ta.iqbal.ibakatel.dtbarang;

import com.google.gson.annotations.SerializedName;

public class Barang {

	@SerializedName("id")
	private int id;
	@SerializedName("idbarcode")
	private String idbarcode;
	@SerializedName("nama")
	private String nama;
	@SerializedName("jumlah")
	private String jumlah;
	@SerializedName("status")
	private String status;
	@SerializedName("image")
	private String image;
	@SerializedName("love")
	private Boolean love;
	@SerializedName("value")
	private String value;
	@SerializedName("message")
	private String massage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdbarcode() {
		return idbarcode;
	}

	public void setIdbarcode(String id) {
		this.idbarcode = idbarcode;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJumlah() {
		return jumlah;
	}

	public void setJumlah(String jumlah) {
		this.jumlah = jumlah;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getLove() {
		return love;
	}

	public void setLove(Boolean love) {
		this.love = love;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}
}
