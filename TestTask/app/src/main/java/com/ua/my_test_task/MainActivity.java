package com.ua.my_test_task;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ua.my_test_task.model.RequestBObject;
import com.ua.my_test_task.service.AppService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    FragmentA fragmentA = new FragmentA();
    FragmentB fragmentB = new FragmentB();
    AppService service = new AppService(fragmentA, fragmentB);
    List<RequestBObject> objectBList = new ArrayList<>();
    public int counter = 0;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = findViewById(R.id.button);
        objectBList = service.getObjectBList();
        buttonAction(findViewById(R.id.MainLayout));
    }

    public void buttonAction(View view) {
        if (service.fragmentTypeHandler(objectBList.get(counter).getType()) instanceof FragmentA) {
            FragmentA fragment = (FragmentA) service.fragmentTypeHandler(objectBList.get(counter).getType());

            fragment.setText(objectBList.get(counter).getContents()); //changing text if onStart() (if previously there was another fragment
            fragment.changeText(objectBList.get(counter).getContents()); //change text if the fragment type didnt change

            service.changeFragment(fm, fragment);
        } else {
            FragmentB fragment = (FragmentB) service.fragmentTypeHandler(objectBList.get(counter).getType());
            fragment.setUrl(objectBList.get(counter).getContents());
            fragment.changeUrl(objectBList.get(counter).getContents());
            service.changeFragment(fm, fragment);
        }
        if (counter < objectBList.size()-1) {
            counter++;
        } else {
            counter = 0;
        }
    }

}