package com.emreseferoglu.personalcontacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, 0, persons);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.drawable.custom_list_item, parent, false);
        }

        TextView textName = convertView.findViewById(R.id.textName);
        TextView textPhone = convertView.findViewById(R.id.textPhone);
        ImageView iconAddEmergency = convertView.findViewById(R.id.iconAddEmergency);

        textName.setText(person.getName());
        textPhone.setText(person.getPhone());

        // Acil kişilere ekleme
        iconAddEmergency.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            dbHelper.addToEmergencyContacts(person.getId());
            Toast.makeText(getContext(), person.getName() + " acil kişilere eklendi.", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
