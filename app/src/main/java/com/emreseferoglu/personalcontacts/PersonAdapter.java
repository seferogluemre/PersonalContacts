package com.emreseferoglu.personalcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    TextView textName,textPhone;
    public PersonAdapter(Context context, List<Person> persons) {
        super(context, 0, persons);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.drawable.custom_list_item, parent, false);
        }

         textName = convertView.findViewById(R.id.textName);
         textPhone = convertView.findViewById(R.id.textPhone);

        textName.setText(person.getName());
        textPhone.setText(person.getPhone());

        return convertView;
    }
}
