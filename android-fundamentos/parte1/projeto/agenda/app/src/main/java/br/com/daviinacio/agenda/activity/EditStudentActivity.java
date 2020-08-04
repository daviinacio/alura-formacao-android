package br.com.daviinacio.agenda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.daviinacio.agenda.R;
import br.com.daviinacio.agenda.model.DataSet;
import br.com.daviinacio.agenda.model.Student;
import br.com.daviinacio.agenda.model.StudentDAO;

public class EditStudentActivity extends AppCompatActivity {
    private int studentId;
    private DataSet<Student> dao;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        // Data
        this.dao = new StudentDAO(this);
        this.studentId = getIntent().getIntExtra("id", 0);

        // Activity Support
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle(
                studentId == 0 ?
                        R.string.title_activity_edit_student_new :
                        R.string.title_activity_edit_student_edit
        );

        // Views
        final EditText inputName = findViewById(R.id.activity_list_students_edit_name);
        final EditText inputPhone = findViewById(R.id.activity_list_students_edit_phone);
        final EditText inputEmail = findViewById(R.id.activity_list_students_edit_email);
        Button save = findViewById(R.id.activity_edit_student_save);

        // View Listeners
        inputPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        save.setOnClickListener((view) -> {
            if(studentId == 0) {
                Student student = new Student(
                        inputName.getText().toString().trim(),
                        inputPhone.getText().toString().trim(),
                        inputEmail.getText().toString().trim()
                );

                dao.insert(student);
            }
            else {
                student.setName(inputName.getText().toString().trim());
                student.setPhone(inputPhone.getText().toString().trim());
                student.setEmail(inputEmail.getText().toString().trim());

                dao.update(student);
            }

            finish();
        });

        // Fill data
        if(studentId != 0){
            student = dao.select(String.format("id = %s", studentId), null).get(0);

            inputName.setText(student.getName());
            inputPhone.setText(student.getPhone());
            inputEmail.setText(student.getEmail());
        }
    }
}
