package br.com.daviinacio.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.daviinacio.agenda.R;
import br.com.daviinacio.agenda.model.DataSet;
import br.com.daviinacio.agenda.model.Student;
import br.com.daviinacio.agenda.model.StudentDAO;

public class ListStudentsActivity extends AppCompatActivity {
    // Data
    private List<Student> students;
    private ArrayAdapter<String> adapter;
    DataSet<Student> dao;

    // View
    private ListView listView;
    private FloatingActionButton fab;

    public ListStudentsActivity(){
        this.students = new ArrayList<>();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);

        this.setTitle(R.string.title_activity_list_students);

        // Data
        this.dao = new StudentDAO(this);

        // View
        this.listView = findViewById(R.id.activity_list_students_list);
        this.fab = findViewById(R.id.activity_list_students_fab);

        // Adapter
        this.adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<>());

        this.listView.setAdapter(this.adapter);

        // View Listeners
        this.fab.setOnClickListener((view) -> {
            Intent intent = new Intent(ListStudentsActivity.this, EditStudentActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ListStudentsActivity.this, EditStudentActivity.class);
            intent.putExtra("id", students.get(position).getId());

            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.activity_list_students_dialog_delete_title)
                    .setMessage(R.string.activity_list_students_dialog_delete_message)
                    .setPositiveButton(R.string.activity_list_students_dialog_delete_button_positive, (dialog, which) -> {
                        dao.delete(students.get(position));
                        adapter.remove(adapter.getItem(position));
                    })
                    .setNegativeButton(R.string.activity_list_students_dialog_delete_button_negative, null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.adapter.clear();

        for (Student student : this.students = dao.select(null, null)) {
            this.adapter.add(student.getName());
        }
    }
}
