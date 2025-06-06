package com.emreseferoglu.personalcontacts;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentOne extends Fragment {
    DatabaseHelper database;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        listView = view.findViewById(R.id.listViewContacts);
        database = new DatabaseHelper(getContext());

        List<Person> persons = database.getAllPersons();
        PersonAdapter adapter = new PersonAdapter(getContext(), persons);
        listView.setAdapter(adapter);

        return view;
    }
}

