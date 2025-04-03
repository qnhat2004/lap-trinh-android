package com.example.portfolio;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rcv_projects), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Project[] project = {
                new Project("Project 1", "Description 1", R.drawable.calculator),
                new Project("Project 2", "Description 2", R.drawable.getting_started),
                new Project("Project 3", "Description 3", R.drawable.hungry_developer),
                new Project("Project 4", "Description 4", R.drawable.quote),
                new Project("Project 5", "Description 5", R.drawable.tape),
                new Project("Project 1", "Description 1", R.drawable.calculator),
                new Project("Project 2", "Description 2", R.drawable.getting_started),
                new Project("Project 3", "Description 3", R.drawable.hungry_developer),
                new Project("Project 4", "Description 4", R.drawable.quote),
                new Project("Project 5", "Description 5", R.drawable.tape),
                new Project("Project 1", "Description 1", R.drawable.calculator),
                new Project("Project 2", "Description 2", R.drawable.getting_started),
                new Project("Project 3", "Description 3", R.drawable.hungry_developer),
                new Project("Project 4", "Description 4", R.drawable.quote),
                new Project("Project 5", "Description 5", R.drawable.tape),
                new Project("Project 1", "Description 1", R.drawable.calculator),
                new Project("Project 2", "Description 2", R.drawable.getting_started),
                new Project("Project 3", "Description 3", R.drawable.hungry_developer),
                new Project("Project 4", "Description 4", R.drawable.quote),
                new Project("Project 5", "Description 5", R.drawable.tape),
        };

        recyclerView = (RecyclerView) findViewById(R.id.rcv_projects);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProjectsAdapter myAdapter = new ProjectsAdapter(project);
        recyclerView.setAdapter(myAdapter);
    }
}