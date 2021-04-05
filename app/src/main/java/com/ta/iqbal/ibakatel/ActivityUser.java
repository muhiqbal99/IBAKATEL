package com.ta.iqbal.ibakatel;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.ta.iqbal.ibakatel.login.Login;

import java.util.ArrayList;
import java.util.List;


public class ActivityUser extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedpreferences;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        View view = getSupportActionBar().getCustomView();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        Dashboard_User dashboard_user = new Dashboard_User();

        fragmentTransaction.replace(R.id.container, dashboard_user);
        fragmentTransaction.commit();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.logout);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        new FancyGifDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin logout ? jika betul klik tombol logout dibawah ini.")
                .setPositiveBtnText("Logout")
                .setPositiveBtnBackground("#b9dbf8")
                .setGifResource(R.drawable.gif1)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(Login.session_status, false);
                        editor.putBoolean("Registered", false);
                        editor.commit();
                        Intent intent = new Intent(ActivityUser.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .build();
    }
}