package com.example.lab4listviewadapter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab4listviewadapter.Models.Persona;
import com.example.lab4listviewadapter.R;

import java.util.ArrayList;

public class FormFragment extends Fragment {
    public ArrayList<Persona> mListPersonas = new ArrayList<Persona>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Persona")) {
            ArrayList<Persona> persona = (ArrayList<Persona>) bundle.getSerializable("Persona");
            mListPersonas = persona;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI (final View view){
        Button btnSave = (Button) view.findViewById(R.id.btnSaveForm);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send information to another Fragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                MainFragment fragmentListView = new MainFragment();
                Bundle bundle = new Bundle();
                Persona obj = collectInfo(view); //Our Persona Object
                mListPersonas.add(obj);
                bundle.putSerializable("Persona", mListPersonas);
                fragmentListView.setArguments(bundle);
                ft.replace(android.R.id.content, fragmentListView);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });
    }

    private Persona collectInfo(View view){
        String name =  ((EditText) view.findViewById(R.id.txtName)).getText().toString();
        String lastName =  ((EditText) view.findViewById(R.id.txtLastName)).getText().toString();
        String phoneNumber =  ((EditText) view.findViewById(R.id.txtPhoneNumber)).getText().toString();
        String email =  ((EditText) view.findViewById(R.id.txtEmail)).getText().toString();

        Persona persona = new Persona(name, lastName, phoneNumber, email);

        return persona;
    }
}
