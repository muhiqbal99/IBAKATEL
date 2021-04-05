package com.ta.iqbal.ibakatel.identifikasi;

import com.google.gson.annotations.SerializedName;

public class tes {
	private String idbarcode;
	private String image;
	private String nama;
	private String jumlah;
	private String id;
	private String status;
	@SerializedName("released")
	boolean isReleased;

	public void setIdbarcode(String idbarcode){
		this.idbarcode = idbarcode;
	}

	public String getIdbarcode(){
		return idbarcode;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getPoster() {
		return image;
	}

	public boolean isReleased() {
		return isReleased;
	}

	@Override
 	public String toString(){
		return 
			"tes{" +
			"idbarcode = '" + idbarcode + '\'' + 
			",image = '" + image + '\'' + 
			",nama = '" + nama + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
