package com.ta.iqbal.ibakatel.identifikasi;

import com.ta.iqbal.ibakatel.R;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class TicketResultActivity extends AppCompatActivity {
    private static final String TAG = TicketResultActivity.class.getSimpleName();

    // url to search barcode
    private static final String URL = "http://an-naba.id/andro/tes/scan/search.php?code=";

    private TextView txtIdbarcode, txtNama, txtJumlah, txtStatus, txtError;
    private ImageView imgPoster;
    private ProgressBar progressBar;
    private TicketView ticketView;
    private Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtIdbarcode = findViewById(R.id.idbarcode);
        txtNama = findViewById(R.id.nama);
        txtJumlah = findViewById(R.id.jumlah);
        txtStatus = findViewById(R.id.status);
        imgPoster = findViewById(R.id.image);
        txtError = findViewById(R.id.txt_error);
        ticketView = findViewById(R.id.layout_ticket);
        progressBar = findViewById(R.id.progressBar);

        String barcode = getIntent().getStringExtra("ResultText");

        // close the activity in case of empty barcode
        if (TextUtils.isEmpty(barcode)) {
            Toast.makeText(getApplicationContext(), "Barcode is empty!", Toast.LENGTH_LONG).show();
            finish();
        }

        // search the barcode
        searchBarcode(barcode);
    }

    /**
     * Searches the barcode by making http call
     * Request was made using Volley network library but the library is
     * not suggested in production, consider using Retrofit
     */
    private void searchBarcode(String barcode) {
        // making volley's json request
        String finalURL = URL + barcode;
        Log.d(TAG,"url"+finalURL);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL + barcode, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "Ticket response: " + response.toString());

                        // check for success status
                        if (!response.has("error")) {
                            // received movie response
                            renderMovie(response);
                        } else {
                            // no movie found
                            showNoTicket();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                showNoTicket();
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showNoTicket() {
        txtError.setVisibility(View.VISIBLE);
        ticketView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Rendering movie details on the ticket
     */
    private void renderMovie(JSONObject response) {
        try {

            // converting json to movie object
            Movie movie = new Gson().fromJson(response.toString(), Movie.class);

            if (movie != null) {
                txtIdbarcode.setText(movie.getIdbarcode());
                txtNama.setText(movie.getNama());
                txtJumlah.setText(movie.getJumlah());
                txtStatus.setText(movie.getStatus());
                Glide.with(this).load("http://an-naba.id/"+movie.getPoster()).into(imgPoster);
                ticketView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                // movie not found
                showNoTicket();
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "JSON Exception: " + e.getMessage());
            showNoTicket();
            Toast.makeText(getApplicationContext(), "Error occurred. Check your LogCat for full report", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // exception
            showNoTicket();
            Toast.makeText(getApplicationContext(), "Error occurred. Check your LogCat for full report", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class Movie {
        String idbarcode;
        String nama;
        String jumlah;
        String status;
        String image;

        @SerializedName("released")
        boolean isReleased;

        public String getIdbarcode() {
            return idbarcode;
        }

        public String getNama() { return nama; }

        public String getJumlah() {
            return jumlah;
        }

        public String getStatus() {
            return status;
        }

        public String getPoster() {
            return image;
        }

        public boolean isReleased() {
            return isReleased;
        }
    }
}