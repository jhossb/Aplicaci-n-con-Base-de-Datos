package com.example.tr1bd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class lista extends AppCompatActivity {

    private ListView listView;
    private List<String> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listView = findViewById(R.id.listViewProductos);
        listaProductos = new ArrayList<>();

        // Obtener productos de la base de datos
        obtenerProductos();

        // Mostrar la lista de productos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProductos);
        listView.setAdapter(adapter);
    }

    private void obtenerProductos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        Cursor cursor = baseDeDatos.rawQuery("SELECT * FROM articulos", null);

        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(0);
                String descripcion = cursor.getString(1);
                String precio = cursor.getString(2);
                listaProductos.add("    "+ codigo + "      " + descripcion + "                   " + precio);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No hay productos en la base de datos", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        baseDeDatos.close();
    }
}