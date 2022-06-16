package com.example.sqlitesubject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, phone, dateOfBirth, id;
    Button insert, select, update, delete;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.txtID);
        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtNumber);
        dateOfBirth = findViewById(R.id.txtDate);
        insert = findViewById(R.id.btnInsert);
        select = findViewById(R.id.btnSelect);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        databaseHelper = new DatabaseHelper(this);
        insert.setOnClickListener(view -> {
            Boolean checkInsertData = databaseHelper.insert(name.getText().toString(), phone.getText().toString(), dateOfBirth.getText().toString());
            if (checkInsertData) {
                Toast.makeText(getApplicationContext(), "Данные успешно добавлены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }

        });
        select.setOnClickListener(view -> {
            Cursor res = databaseHelper.getdata();
            if(res.getCount()==0){
                Toast.makeText(MainActivity.this,"Нет данных",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext())
            {
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Имя: ").append(res.getString(1)).append("\n");
                buffer.append("Тел. Номер: ").append(res.getString(2)).append("\n");
                buffer.append("Дата рождения: ").append(res.getString(3)).append("\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Данные пользователей");
            builder.setMessage(buffer.toString());
            builder.show();
        });

        update.setOnClickListener(view -> {
            Boolean checkUpdateData = databaseHelper.Update(id.getText().toString(),name.getText().toString(), phone.getText().toString(), dateOfBirth.getText().toString());
            if (checkUpdateData) {
                Toast.makeText(getApplicationContext(), "Данные успешно отредактированы", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }

        });

        delete.setOnClickListener(view -> {
            Boolean checkDeleteData = databaseHelper.Delete(id.getText().toString());
            if (checkDeleteData) {
                Toast.makeText(getApplicationContext(), "Данные успешно удалены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }

        });
    }
}