package com.example.criminalrecordapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class CriminalRecordAdapter extends BaseAdapter implements Filterable {
    private ArrayList<DBHelper.CriminalRecord> criminalRecords;
    private ArrayList<DBHelper.CriminalRecord> defaultData;
    private LayoutInflater inflater;

    public CriminalRecordAdapter(Context context, ArrayList<DBHelper.CriminalRecord>criminalRecords) {
        this.defaultData = new ArrayList<>(criminalRecords);
        this.criminalRecords = criminalRecords;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return criminalRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return criminalRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewRecord.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new viewRecord.ViewHolder();
            holder.textId = (TextView) convertView.findViewById(R.id.text_view_id_data);
            holder.textName = (TextView) convertView.findViewById(R.id.text_view_name_data);
            holder.textAge = (TextView) convertView.findViewById(R.id.text_view_age_data);
            holder.textDate = (TextView) convertView.findViewById(R.id.text_view_date_data);
            holder.textOffenseType = (TextView) convertView.findViewById(R.id.text_view_offense_type_data);
            holder.textOffenseDescription = (TextView) convertView.findViewById(R.id.text_view_description_data);
            holder.textMunicipality = (TextView) convertView.findViewById(R.id.text_view_municipality_data);
            holder.textProvince = (TextView) convertView.findViewById(R.id.text_view_province_data);
            holder.textStationName = (TextView) convertView.findViewById(R.id.text_view_station_name_data);
            holder.textStationAddress = (TextView) convertView.findViewById(R.id.text_view_station_address_data);
            convertView.setTag(holder);
        } else {
            holder = (viewRecord.ViewHolder) convertView.getTag();
        }

        DBHelper.CriminalRecord criminalRecord = criminalRecords.get(position);
        holder.textId.setText(Integer.toString(criminalRecord.id));
        holder.textName.setText(criminalRecord.name);
        holder.textAge.setText(Integer.toString(criminalRecord.age));
        holder.textDate.setText(criminalRecord.date);
        holder.textOffenseType.setText(criminalRecord.offenseType);
        holder.textOffenseDescription.setText(criminalRecord.offenseDescription);
        holder.textMunicipality.setText(criminalRecord.municipality);
        holder.textProvince.setText(criminalRecord.province);
        holder.textStationName.setText(criminalRecord.stationName);
        holder.textStationAddress.setText(criminalRecord.stationAddress);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<DBHelper.CriminalRecord> filteredList = new ArrayList<>();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = defaultData;  // restore the listview to its default state
                } else {
                    for (DBHelper.CriminalRecord row : defaultData) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getOffenseType().toLowerCase().contains(charString.toLowerCase())
                                || row.getMunicipality().toLowerCase().contains(charString.toLowerCase())
                                || row.getProvince().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                criminalRecords = (ArrayList<DBHelper.CriminalRecord>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

