package com.ta.iqbal.ibakatel.dtbarang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ta.iqbal.ibakatel.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    private EditText mNama;
    private EditText mJumlah;
    private EditText mStatus;
    private EditText mIdbarcode;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    private String nama, jumlah, status, image, idbarcode;
    private int id;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);

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

            Glide.with(EditorActivity.this)
                    .load(image)
                    .apply(requestOptions)
                    .into(mPicture);

        } else {
            getSupportActionBar().setTitle("Tambah Barang");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mNama, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (id == 0) {

                    if (TextUtils.isEmpty(mIdbarcode.getText().toString()) ||
                            TextUtils.isEmpty(mNama.getText().toString()) ||
                            TextUtils.isEmpty(mJumlah.getText().toString()) ||
                            TextUtils.isEmpty(mStatus.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {

                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();

                    }

                } else {

                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorActivity.this);
                dialog.setMessage("Hapus barang ini?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id, image);
                    }
                });
                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String idbarcode = mIdbarcode.getText().toString().trim();
        String nama = mNama.getText().toString().trim();
        String jumlah = mJumlah.getText().toString().trim();
        String status = mStatus.getText().toString().trim();
        String image = null;
        if (bitmap == null) {
            image = "";
        } else {
            image = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Barang> call = apiInterface.tambahBarang(key, idbarcode, nama, jumlah, status, image);

        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {

                progressDialog.dismiss();

                Log.e(EditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String idbarcode = mIdbarcode.getText().toString().trim();
        String nama = mNama.getText().toString().trim();
        String jumlah = mJumlah.getText().toString().trim();
        String status = mStatus.getText().toString().trim();
        String image = null;
        if (bitmap == null) {
            image = "";
        } else {
            image = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Barang> call = apiInterface.perbaruiBarang(key, id, idbarcode, nama, jumlah, status, image);

        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {

                progressDialog.dismiss();

                Log.e(EditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Barang> call = apiInterface.hapusBarang(key, id, pic);

        call.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {

                progressDialog.dismiss();

                Log.e(EditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
