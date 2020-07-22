package br.com.daviinacio.agenda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Data
    private ArrayList<String> array;
    private ArrayAdapter<String> adapter;

    // View
    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data
        this.array = new ArrayList<>();

        // View
        this.listView = findViewById(R.id.activity_main_list);
        this.fab = findViewById(R.id.activity_main_fab);

        // Adapter
        this.adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.array);

        this.listView.setAdapter(this.adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapter.clear();

        for (int i = 0; i < 4; i++) {
            this.adapter.add("Teste");
        }
    }
}
