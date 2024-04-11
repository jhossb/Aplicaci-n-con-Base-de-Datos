package com.example.tr1bd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_precio = (EditText) findViewById(R.id.txt_precio);


    }

    //metodo para el alta
    public void Registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "debes llenar todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    public void Buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery("select codigo,descripcion, precio from articulos where codigo = ?", new String[]{codigo});

            if (fila.moveToFirst()) {
                String codigoArt = fila.getString(0);
                String descripciondy = fila.getString(1);
                String precio = fila.getString(2);
                BaseDeDatos.close();
                mostrarPopup(codigoArt,  descripciondy,  precio);
            } else {
                Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String cod = et_codigo.getText().toString();
        int cant = BaseDeDatos.delete("articulos", "codigo=" + cod, null);
        BaseDeDatos.close();
        et_codigo.setText("");
        et_descripcion.setText("");
        et_precio.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró el artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
    }
    public void Modificar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();
        ContentValues registro = new
                ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        int cant = BaseDeDatos.update("articulos",
                registro, "codigo=" + codigo, null);
        BaseDeDatos.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe un artículo con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }


    public void MostrarArt(View view) {
        // Iniciar la otra actividad
        Intent intent = new Intent(this, lista.class);
        startActivity(intent);
    }

    private void mostrarPopup(String codigoArt, String descripcion, String precio) {
        // Crear un diálogo emergente
        Dialog popup = new Dialog(this);
        popup.setContentView(R.layout.buscar);

        // Buscar los TextView en el layout del diálogo emergente
        TextView codigoArtTextView = popup.findViewById(R.id.codigoTextView);
        TextView descripcionTextView = popup.findViewById(R.id.descripcionTextView);
        TextView precioTextView = popup.findViewById(R.id.precioTextView);

        // Establecer los datos del producto en los TextView
        codigoArtTextView.setText(codigoArt);
        descripcionTextView.setText(descripcion);
        precioTextView.setText(precio);

        // Mostrar el diálogo emergente
        popup.show();
    }


    }

