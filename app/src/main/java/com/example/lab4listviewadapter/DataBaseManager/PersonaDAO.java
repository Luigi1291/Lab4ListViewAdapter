package com.example.lab4listviewadapter.DataBaseManager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lab4listviewadapter.Models.Persona;
import java.util.List;

@Dao
public interface PersonaDAO  {
    @Insert
    public void insert(Persona... personas);

    @Update
    public void update(Persona... personas);

    @Delete
    public void delete(Persona... personas);

    @Query("SELECT * FROM personas")
    public List<Persona> getPersonas();

    @Query("SELECT COUNT(*) as count FROM personas")
    public int getTotalPersonas();
}
