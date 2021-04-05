package com.ta.iqbal.ibakatel.laporan;

import android.widget.Filter;

import com.ta.iqbal.ibakatel.laporan.Adapter;
import com.ta.iqbal.ibakatel.laporan.Laporan;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    Adapter adapter;
    ArrayList<Laporan> filterList;

    public CustomFilter(ArrayList<Laporan> filterList, Adapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Laporan> filteredLaporan=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getTgl_pinjam().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredLaporan.add(filterList.get(i));
                }
            }

            results.count=filteredLaporan.size();
            results.values=filteredLaporan;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.laporan= (ArrayList<Laporan>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}