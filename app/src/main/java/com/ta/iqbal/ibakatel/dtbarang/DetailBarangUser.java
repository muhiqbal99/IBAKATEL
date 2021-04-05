package com.ta.iqbal.ibakatel.dtbarang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ta.iqbal.ibakatel.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBarangUser extends AppCompatActivity {

    private EditText mNama;
    private EditText mJumlah;
    private EditText mStatus;
    private EditText mIdbarcode;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    private String nama, jumlah, status, image, idbarcode;
    private int id;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_baranguser);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mIdbarcode = findViewById(R.id.idbarcode);
        mNama = findViewById(R.id.nama);
        mJumlah = findViewById(R.id.jumlah);
        mStatus = findViewById(R.id.status);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);


        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        idbarcode = intent.getStringExtra("idbarcode");
        nama = intent.getStringExtra("nama");
        jumlah = intent.getStringExtra("jumlah");
        status = intent.getStringExtra("status");
        image = intent.getStringExtra("image");
        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + nama.toString());

            mIdbarcode.setText(idbarcode);
            mNama.setText(nama);
            mJumlah.setText(jumlah);
            mStatus.setText(status);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(DetailBarangUser.this)
                    .load(image)
                    .apply(requestOptions)
                    .into(mPicture);

        } else {
            getSupportActionBar().setTitle("Tambah Barang");
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    void readMode(){

        mIdbarcode.setFocusableInTouchMode(false);
        mNama.setFocusableInTouchMode(false);
        mJumlah.setFocusableInTouchMode(false);
        mStatus.setFocusableInTouchMode(false);
        mIdbarcode.setFocusable(false);
        mNama.setFocusable(false);
        mJumlah.setFocusable(false);
        mStatus.setFocusable(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        mIdbarcode.setFocusableInTouchMode(true);
        mNama.setFocusableInTouchMode(true);
        mJumlah.setFocusableInTouchMode(true);
        mStatus.setFocusableInTouchMode(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}
