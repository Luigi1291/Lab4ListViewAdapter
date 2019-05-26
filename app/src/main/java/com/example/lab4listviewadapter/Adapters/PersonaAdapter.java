package com.example.lab4listviewadapter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab4listviewadapter.Models.Persona;
import com.example.lab4listviewadapter.R;

import org.w3c.dom.Text;

import java.util.List;

public class PersonaAdapter extends ArrayAdapter<Persona> {

    int mLayoutId;
    public PersonaAdapter(Context context, int layoutId, List<Persona> items) {
        super(context, layoutId, items);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position,View view, ViewGroup parent) {
        Persona persona = getItem(position);
        String name = persona.getName();
        String lastName = persona.getLastName();
        String email = persona.getEmail();
        String phoneNumber= persona.getPhoneNumber();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayoutId, parent, false);
        }

        TextView nameView = (TextView) view.findViewById(R.id.txtName);
        TextView emailView = (TextView) view.findViewById(R.id.txtEmail);
        TextView phoneNumberView = (TextView) view.findViewById(R.id.txtPhoneNumber);

        nameView.setText(name+ " " + lastName);
        emailView.setText(email);
        phoneNumberView.setText(phoneNumber);

        return view;
    }
}
