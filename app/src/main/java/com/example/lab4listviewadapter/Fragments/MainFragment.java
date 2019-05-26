package com.example.lab4listviewadapter.Fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lab4listviewadapter.Adapters.PersonaAdapter;
import com.example.lab4listviewadapter.DataBaseManager.AppDataBase;
import com.example.lab4listviewadapter.DataBaseManager.PersonaDAO;
import com.example.lab4listviewadapter.MainActivity;
import com.example.lab4listviewadapter.Models.Persona;
import com.example.lab4listviewadapter.R;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    ArrayList mItems;
    ArrayAdapter<String> mAdapter;
    ListView mListView;
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI (View view) {
        FillDefaultList(view);
        FillPersonaList(view);
        FillPersonaListBD(view);

        //Agregar persona a lista con bundle
        Button btnNext =  (Button) view.findViewById(R.id.btnAdd);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send information to another Fragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FormFragment fragmentForm = new FormFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Persona", mListPersonas);
                fragmentForm.setArguments(bundle);
                ft.replace(android.R.id.content, fragmentForm);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });

        //Agregar persona a lista usando BD
        FloatingActionButton btnAddDB =  (FloatingActionButton) view.findViewById(R.id.btnAddDB);
        btnAddDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FormFragment fragmentForm = new FormFragment();
                ft.replace(android.R.id.content, fragmentForm);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });
    }

    private void FillDefaultList(View view){
        mItems = new ArrayList<String>();
        for(int i=0; i<=10 ;i++) {
            mItems.add("Item : " + i);
        }

        int layoutId = android.R.layout.simple_list_item_1;
        mAdapter = new ArrayAdapter<String>(view.getContext() ,layoutId, mItems);
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
    }

    private void FillPersonaList(View view){

        int layoutId = R.layout.list_element_persona;
        PersonaAdapter adapter = new PersonaAdapter(view.getContext() ,layoutId, mListPersonas);

        ListView mListView = (ListView) view.findViewById(R.id.listViewPersona);
        mListView.setAdapter(adapter);
    }

    private void FillPersonaListBD(View pView){
        //localDB nueva base de datos a crear/consultar
        AppDataBase database = Room.databaseBuilder(pView.getContext(), AppDataBase.class, "localDB")
                .allowMainThreadQueries().build();

        PersonaDAO personaDAO = database.getPersonaDAO();

        List<Persona> mListPersonasBD = personaDAO.getPersonas();
        mListPersonas = new ArrayList<>(mListPersonasBD);

        //Actualizar
        int layoutId = R.layout.list_element_persona;
        PersonaAdapter adapter = new PersonaAdapter(pView.getContext() ,layoutId, mListPersonas);

        ListView mListView = (ListView) pView.findViewById(R.id.listViewPersona);
        mListView.setAdapter(adapter);
    }
}
