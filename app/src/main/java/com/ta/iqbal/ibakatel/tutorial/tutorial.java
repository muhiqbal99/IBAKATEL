package com.ta.iqbal.ibakatel.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ta.iqbal.ibakatel.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.P;

public class tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Company> companies = new ArrayList<>();

        ArrayList<Product> kelola = new ArrayList<>();
        kelola.add(new Product("Pada menu ini pengguna dapat melihat detail barang yang tersedia di kampus AKATEL"));

        Company tutorial2 = new Company("Daftar Alat", kelola);
        companies.add(tutorial2);

        ArrayList<Product> Pinjamalat = new ArrayList<>();
        Pinjamalat.add(new Product("Pada menu ini pengguna dapat melihat status peminjaman barang, selain itu juga pengguna dapat melakukan peminjaman barang dengan mengklik tombol yang berada dibawah layar. Untuk melakukan peminjaman barang, pengguna diharuskan login terlebih dahulu."));

        Company tutorial = new Company("Pinjam Alat", Pinjamalat);
        companies.add(tutorial);

        ArrayList<Product> microsoftProduct = new ArrayList<>();
        microsoftProduct.add(new Product("Pada menu ini berisi penjelasan singkat tentang aplikasi ini"));
        Company microsoft = new Company("About", microsoftProduct);
        companies.add(microsoft);

        ProductAdapter adapter = new ProductAdapter(companies);
        recyclerView.setAdapter(adapter);
    }
}
