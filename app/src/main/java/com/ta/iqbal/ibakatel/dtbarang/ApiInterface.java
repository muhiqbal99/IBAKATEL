package com.ta.iqbal.ibakatel.dtbarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haerul on 15/03/18.
 */

public interface ApiInterface {

    @POST("dapat_barang.php")
    Call<List<Barang>> getBarang();

    @FormUrlEncoded
    @POST("tambah_barang.php")
    Call<Barang> tambahBarang(
            @Field("key") String key,
            @Field("idbarcode") String idbarcode,
            @Field("nama") String nama,
            @Field("jumlah") String jumlah,
            @Field("status") String status,
            @Field("image") String image);

    @FormUrlEncoded
    @POST("perbarui_barang.php")
    Call<Barang> perbaruiBarang(
            @Field("key") String key,
            @Field("id") int id,
            @Field("idbarcode") String idbarcode,
            @Field("nama") String nama,
            @Field("jumlah") String jumlah,
            @Field("status") String status,
            @Field("image") String image);

    @FormUrlEncoded
    @POST("hapus_barang.php")
    Call<Barang> hapusBarang(
            @Field("key") String key,
            @Field("id") int id,
            @Field("image") String image);

}
