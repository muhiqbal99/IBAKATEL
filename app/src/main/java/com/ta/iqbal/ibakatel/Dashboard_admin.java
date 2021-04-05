package com.ta.iqbal.ibakatel;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ta.iqbal.ibakatel.dtbarang.ActivityBarangAdmin;
import com.ta.iqbal.ibakatel.identifikasi.ScanActivity;
import com.ta.iqbal.ibakatel.laporan.ActivityLaporan;
import com.ta.iqbal.ibakatel.login.Login;
import com.ta.iqbal.ibakatel.pinjam.ActivityPinjam;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard_admin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard_admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard_admin extends Fragment {

    private CardView cardTop,cardRight,cardLeft,cardLeft2,cardRight2;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_ROLE = "role";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Dashboard_admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard_admin.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard_admin newInstance(String param1, String param2) {
        Dashboard_admin fragment = new Dashboard_admin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SharedPreferences sharedpreferences;
        Boolean session = false;
        Boolean Registered;

        sharedpreferences = getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        Registered = sharedpreferences.getBoolean("Registered", false);

        if (!session && !Registered) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_admin, container, false);

        LinearLayout linearlayout = (LinearLayout) v.findViewById(R.id.list_item);
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityBarangAdmin.class);
                startActivity(intent);
            }
        });

        LinearLayout linearlayout2 = (LinearLayout) v.findViewById(R.id.pinjam);
        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityPinjam.class);
                startActivity(intent);
            }
        });

        LinearLayout linearlayout3 = (LinearLayout) v.findViewById(R.id.laporan);
        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityLaporan.class);
                startActivity(intent);
            }
        });

        LinearLayout linearlayout4 = (LinearLayout) v.findViewById(R.id.about);
        linearlayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), about.class);
                startActivity(intent);
            }
        });


        // card ini
        cardTop = v.findViewById(R.id.cardTop);
        cardRight = v.findViewById(R.id.cardRight);
        cardRight2 = v.findViewById(R.id.cardRight2);
        cardLeft = v.findViewById(R.id.cardLeft);
        cardLeft2 = v.findViewById(R.id.cardLeft2) ;
        // ini Animations

        Animation animeBottomToTop = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_bottom_to_top);
        Animation animeTopToBottom = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_top_to_bottom);
        Animation animeRightToleft = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_right_to_left);
        Animation animeLeftToRight = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_left_to_right);


        // setup Animation :
        cardLeft2.setAnimation(animeBottomToTop);
        cardTop.setAnimation(animeTopToBottom);
        cardRight2.setAnimation(animeRightToleft);
        cardRight.setAnimation(animeRightToleft);
        cardLeft.setAnimation(animeLeftToRight);


        // Inflate the layout for this fragment
        return  v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
