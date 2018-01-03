package com.potseluev.gymlog;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ActionFormConstructor extends Fragment {


    final String[] mCats = {"Мурзик", "Рыжик", "Барсик", "Борис",
            "Мурзилка", "Мурка", "tag1", "tag2", "tag3", "tag4"};

    public ActionFormConstructor() {
        // Required empty public constructor
    }

    public static ActionFormConstructor newInstance(String param1, String param2) {
        ActionFormConstructor fragment = new ActionFormConstructor();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_action_form_constructor, null);
        AutoCompleteTextView mAutoCompleteTextView = v.findViewById(R.id.autoCompleteTextView);
        mAutoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, mCats));
        return v;
    }

}
