package br.com.daviinacio.agenda.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// Created by daviinacio on 03/08/2020.
public class StudentDAO implements DataSet<Student> {
    private static final String [] columns = new String[]{
            "id", "name", "phone", "email"
    };

    private Core core;

    public StudentDAO(Context context){
        this.core = new Core(context);
    }

    @Override
    public void insert(Student student) {
        try (SQLiteDatabase db = core.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(columns[1], student.getName());
            values.put(columns[2], student.getPhone());
            values.put(columns[3], student.getEmail());

            db.insert(Core.DB_NAME, null, values);

        } catch (Exception ex) {
            Toast.makeText(core.context,
                    String.format("DB(%s): insert %s\n%s", Core.DB_NAME, Core.DB_NAME, ex.getMessage()),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void update(Student student) {
        try (SQLiteDatabase db = core.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(columns[1], student.getName());
            values.put(columns[2], student.getPhone());
            values.put(columns[3], student.getEmail());


            db.update(Core.DB_NAME, values, "id = ?", new String[]{Integer.toString(student.getId())});

        } catch (Exception ex) {
            Toast.makeText(core.context,
                    String.format("DB(%s): update %s\n%s", Core.DB_NAME, Core.DB_NAME, ex.getMessage()),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void delete(Student student) {
        SQLiteDatabase db = core.getWritableDatabase();
        db.delete(Core.DB_NAME, "id = ?", new String[]{ Integer.toString(student.getId()) });
        db.close();
    }


    @Override
    public List<Student> select(String where, String order) {
        List<Student> results = new ArrayList<>();

        try (SQLiteDatabase db = core.getReadableDatabase()) {
            Cursor c = db.query(Core.DB_NAME, columns, where, null, null, null, order);

            while (c.moveToNext()) {
                Student student = new Student(
                        c.getInt(c.getColumnIndex(columns[0])),
                        c.getString(c.getColumnIndex(columns[1])),
                        c.getString(c.getColumnIndex(columns[2])),
                        c.getString(c.getColumnIndex(columns[3]))
                );

                results.add(student);
            }

            c.close();
        } catch (Exception ex) {
            Toast.makeText(core.context,
                    String.format("DB(%s): select %s\n%s", Core.DB_NAME, Core.DB_NAME, ex.getMessage()),
                    Toast.LENGTH_LONG).show();
        }

        return results;
    }

    class Core extends SQLiteOpenHelper {
        private static final String DB_NAME = "students";
        private static final int DB_VERSION = 2;

        private Context context;

        public Core(Context context){
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try {
                db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name STRING NOT NULL," +
                        "phone STRING," +
                        "email STRING" +
                        ");", DB_NAME));

            } catch (Exception ex){
                Toast.makeText(context,
                        String.format("DB(%s): Core.onCreate\n%s", DB_NAME, ex.getMessage()),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            try {
                db.execSQL(String.format("DROP TABLE IF EXISTS %s", DB_NAME));
                onCreate(db);

            } catch (Exception ex) {
                Toast.makeText(context,
                        String.format("DB(%s): Core.onUpgrade\n%s", DB_NAME, ex.getMessage()),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
