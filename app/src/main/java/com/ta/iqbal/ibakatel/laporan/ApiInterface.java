package com.ta.iqbal.ibakatel.laporan;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("dapat_laporan.php")
    Call<List<Laporan>> getLaporan();

    @FormUrlEncoded
    @POST("tambah_laporan.php")
    Call<Laporan> tambahLaporan(
            @Field("key") String key,
            @Field("nm_barang") String nm_barang,
            @Field("tgl_pinjam") String tgl_pinjam,
            @Field("tgl_balik") String tgl_balik,
            @Field("nm_user") String nm_user,
            @Field("gambar") String gambar,
            @Field("status") String status,
            @Field("catatan") String catatan);

    @FormUrlEncoded
    @POST("perbarui_laporan.php")
    Call<Laporan> perbaruiLaporan(
            @Field("key") String key,
            @Field("id") int id,
            @Field("nm_barang") String nm_barang,
            @Field("tgl_pinjam") String tgl_pinjam,
            @Field("tgl_balik") String tgl_balik,
            @Field("nm_user") String nm_user,
            @Field("gambar") String gambar,
            @Field("status") String status,
            @Field("catatan") String catatan);

    @FormUrlEncoded
    @POST("hapus_laporan.php")
    Call<Laporan> hapusLaporan(
            @Field("key") String key,
            @Field("id") int id,
            @Field("gambar") String gambar);

}