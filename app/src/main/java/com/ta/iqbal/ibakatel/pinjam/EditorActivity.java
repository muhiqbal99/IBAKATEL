package com.ta.iqbal.ibakatel.pinjam;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ta.iqbal.ibakatel.R;
import com.ta.iqbal.ibakatel.login.Login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    private EditText mNm_barang, mTgl_pinjam, mTgl_balik, mNm_user, mStatus, mCatatan;
    private CircleImageView mGambar;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();

    private String nm_barang, tgl_pinjam, tgl_balik, gambar, nm_user, status, catatan;
    private int id;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor2);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mNm_barang = findViewById(R.id.nm_barang);
        mTgl_pinjam = findViewById(R.id.tgl_pinjam);
        mTgl_balik = findViewById(R.id.tgl_balik);
        mNm_user = findViewById(R.id.nm_user);
        mGambar = findViewById(R.id.gambar);
        mStatus = findViewById(R.id.status);
        mCatatan = findViewById(R.id.catatan);

        mFabChoosePic = findViewById(R.id.fabChoosePic);

        mTgl_pinjam = findViewById(R.id.tgl_pinjam);

        mTgl_pinjam.setFocusableInTouchMode(false);
        mTgl_pinjam.setFocusable(false);
        mTgl_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mTgl_balik = findViewById(R.id.tgl_balik);

        mTgl_balik.setFocusableInTouchMode(false);
        mTgl_balik.setFocusable(false);
        mTgl_balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        nm_barang = intent.getStringExtra("nm_barang");
        tgl_pinjam = intent.getStringExtra("tgl_pinjam");
        tgl_balik = intent.getStringExtra("tgl_balik");
        nm_user = intent.getStringExtra("nm_user");
        gambar = intent.getStringExtra("gambar");
        status = intent.getStringExtra("status");
        catatan = intent.getStringExtra("catatan");
        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + nm_barang.toString());

            mNm_barang.setText(nm_barang);
            mTgl_pinjam.setText(tgl_pinjam);
            mTgl_balik.setText(tgl_balik);
            mNm_user.setText(nm_user);
            mStatus.setText(status);
            mCatatan.setText(catatan);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(EditorActivity.this)
                    .load(gambar)
                    .apply(requestOptions)
                    .into(mGambar);

        } else {
            getSupportActionBar().setTitle("Tambah Pinjam Barang");
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
                imm.showSoftInput(mNm_barang, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (id == 0) {

                    if (TextUtils.isEmpty(mNm_barang.getText().toString()) ||
                            TextUtils.isEmpty(mTgl_pinjam.getText().toString()) ||
                            TextUtils.isEmpty(mTgl_balik.getText().toString()) ||
                            TextUtils.isEmpty(mNm_user.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Tolong lengkapi kolom diatas !");
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
                dialog.setMessage("Hapus peminjaman barang ini ??");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id, gambar);
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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTgl_pinjam();
        }

    };

    private void setTgl_pinjam() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTgl_pinjam.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTgl_balik();
        }

    };

    private void setTgl_balik() {
        String myFormat2 = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat2, Locale.US);

        mTgl_balik.setText(sdf.format(myCalendar.getTime()));
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
        startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mGambar.setImageBitmap(bitmap);

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

        String nm_barang = mNm_barang.getText().toString().trim();
        String tgl_pinjam = mTgl_pinjam.getText().toString().trim();
        String tgl_balik = mTgl_balik.getText().toString().trim();
        String nm_user = mNm_user.getText().toString().trim();
        String status = mStatus.getText().toString().trim();
        String catatan = mCatatan.getText().toString().trim();

        String gambar = null;
        if (bitmap == null) {
            gambar = "";
        } else {
            gambar = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pinjam> call = apiInterface.tambahPinjam(key, nm_barang, tgl_pinjam, tgl_balik, nm_user, gambar, status, catatan);

        call.enqueue(new Callback<Pinjam>() {
            @Override
            public void onResponse(Call<Pinjam> call, Response<Pinjam> response) {

                progressDialog.dismiss();

                Log.i(EditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pinjam> call, Throwable t) {
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

        String nm_barang = mNm_barang.getText().toString().trim();
        String tgl_pinjam = mTgl_pinjam.getText().toString().trim();
        String tgl_balik = mTgl_balik.getText().toString().trim();
        String nm_user = mNm_user.getText().toString().trim();
        String status = mStatus.getText().toString().trim();
        String catatan = mCatatan.getText().toString().trim();
        String gambar = null;
        if (bitmap == null) {
            gambar = "";
        } else {
            gambar = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pinjam> call = apiInterface.perbaruiPinjam(key, id, nm_barang, tgl_pinjam, tgl_balik, nm_user, gambar, status, catatan);

        call.enqueue(new Callback<Pinjam>() {
            @Override
            public void onResponse(Call<Pinjam> call, Response<Pinjam> response) {

                progressDialog.dismiss();

                Log.i(EditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pinjam> call, Throwable t) {
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

        Call<Pinjam> call = apiInterface.hapusPinjam(key, id, pic);

        call.enqueue(new Callback<Pinjam>() {
            @Override
            public void onResponse(Call<Pinjam> call, Response<Pinjam> response) {

                progressDialog.dismiss();

                Log.i(EditorActivity.class.getSimpleName(), response.toString());

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
            public void onFailure(Call<Pinjam> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void readMode(){

        mNm_barang.setFocusableInTouchMode(false);
        mNm_user.setFocusableInTouchMode(false);
        mCatatan.setFocusableInTouchMode(false);
        mStatus.setFocusableInTouchMode(false);

        mNm_barang.setFocusable(false);
        mNm_user.setFocusable(false);
        mStatus.setFocusable(false);
        mCatatan.setFocusable(false);

        mTgl_pinjam.setEnabled(false);
        mTgl_balik.setEnabled(false);
        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        mNm_barang.setFocusableInTouchMode(true);
        mNm_user.setFocusableInTouchMode(true);
        mStatus.setFocusableInTouchMode(true);
        mCatatan.setFocusableInTouchMode(true);

        mTgl_pinjam.setEnabled(true);
        mTgl_balik.setEnabled(true);
        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}