package com.example.lab4listviewadapter.DataBaseManager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.lab4listviewadapter.Models.Persona;

@Database(entities = {Persona.class}, version = 1)
public abstract class AppDataBase extends
        RoomDatabase {
    public abstract PersonaDAO getPersonaDAO();
}
