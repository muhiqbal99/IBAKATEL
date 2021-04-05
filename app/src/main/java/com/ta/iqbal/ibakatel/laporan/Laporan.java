package com.ta.iqbal.ibakatel.laporan;

import com.google.gson.annotations.SerializedName;

public class Laporan {

	@SerializedName("id")
	private int id;
	@SerializedName("nm_barang")
	private String nm_barang;
	@SerializedName("tgl_pinjam")
	private String tgl_pinjam;
	@SerializedName("tgl_balik")
	private String tgl_balik;
	@SerializedName("nm_user")
	private String nm_user;
	@SerializedName("gambar")
	private String gambar;
	@SerializedName("status")
	private String status;
	@SerializedName("catatan")
	private String catatan;
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

	public String getNm_barang() {
		return nm_barang;
	}

	public void setNm_barang(String nm_barang) {
		this.nm_barang = nm_barang;
	}

	public String getTgl_pinjam() {
		return tgl_pinjam;
	}

	public void setTgl_pinjam(String tgl_pinjam) {
		this.tgl_pinjam = tgl_pinjam;
	}

	public String getTgl_balik() {
		return tgl_balik;
	}

	public void setTgl_balik(String tgl_balik) {
		this.tgl_balik = tgl_balik;
	}

	public String getNm_user() {
		return nm_user;
	}

	public void setNm_user(String nm_user) {
		this.nm_user = nm_user;
	}

	public String getGambar() {
		return gambar;
	}

	public void setGambar(String gambar) {
		this.nm_user = gambar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
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
