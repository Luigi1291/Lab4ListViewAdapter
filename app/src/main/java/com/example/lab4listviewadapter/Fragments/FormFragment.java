package com.example.lab4listviewadapter.Fragments;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab4listviewadapter.DataBaseManager.AppDataBase;
import com.example.lab4listviewadapter.DataBaseManager.PersonaDAO;
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
        //Guardar persona a lista con bundle
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

        //Guardar persona a lista con BD
        Button btnSaveBD = (Button) view.findViewById(R.id.btnSaveFormBD);
        btnSaveBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send information to another Fragment
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                MainFragment fragmentListView = new MainFragment();
                Bundle bundle = new Bundle();
                Persona obj = collectInfo(view); //Our Persona Object

                //Guardar persona en BD
                InsertPersona(view, obj);

                //Mostrar snackbar con total de personas
                SharedPreferences sharedPref = view.getContext().getSharedPreferences("preferences", view.getContext().MODE_PRIVATE);
                int size = sharedPref.getInt("total", 0);
                CharSequence message = view.getContext().getResources().getString(R.string.countMessage) +
                        " " +
                        String.valueOf(size);
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

                ft.replace(android.R.id.content, fragmentListView);
                ft.addToBackStack(null); //Add fragment in back stack
                ft.commit();
            }
        });
    }

    private void InsertPersona(View pView,Persona pPersona){
        //localDB nueva base de datos a crear
        AppDataBase database = Room.databaseBuilder(pView.getContext(), AppDataBase.class, "localDB")
                .allowMainThreadQueries().build();

        PersonaDAO personaDAO = database.getPersonaDAO();

        //Insertar persona en la BD
        personaDAO.insert(pPersona);

        //Actualizar shared preferences con total de Personas en BD
        SharedPreferences sharedPref = pView.getContext().getSharedPreferences("preferences", pView.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //Obtener total de personas del Entity
        editor.putInt("total", personaDAO.getTotalPersonas());
        editor.commit(); //guarda los datos en el fichero
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
