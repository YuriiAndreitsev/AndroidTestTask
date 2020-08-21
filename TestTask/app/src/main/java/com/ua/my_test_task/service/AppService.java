package com.ua.my_test_task.service;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ua.my_test_task.FragmentA;
import com.ua.my_test_task.FragmentB;
import com.ua.my_test_task.R;
import com.ua.my_test_task.model.RequestAObject;
import com.ua.my_test_task.model.RequestBObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppService {

    public List<Fragment> fragmentsList = new ArrayList<>();
    ExecutorService es = Executors.newCachedThreadPool();
    FragmentA fragmentA;
    FragmentB fragmentB;

    public AppService(FragmentA fragmentA, FragmentB fragmentB) {
        this.fragmentA = fragmentA;
        this.fragmentB = fragmentB;
        fragmentsList.add(fragmentA);
        fragmentsList.add(fragmentB);

    }


    public List<RequestAObject> handleRequestA() {
        Callable<List<RequestAObject>> requestA = new CallableRequestA();
        Future<List<RequestAObject>> futureA = es.submit(requestA);
        List<RequestAObject> objectsList = null;
        try {
            objectsList = futureA.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objectsList;
    }

    public RequestBObject handleRequestB(String ObjectId) {
        RequestBObject requestBObject = null;
        Callable<RequestBObject> requestB = new CallableRequestB(String.valueOf(ObjectId));
        Future<RequestBObject> futureB = es.submit(requestB);

        try {
            requestBObject = futureB.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return requestBObject;
    }

    public List<RequestBObject> getObjectBList() {
        List<RequestAObject> objectAList = handleRequestA();
        List<RequestBObject> objectBList = new ArrayList<>();
        for (RequestAObject temp : objectAList) {
            objectBList.add(handleRequestB(String.valueOf(temp.getId())));
        }
        return objectBList;
    }

    public Fragment fragmentTypeHandler(String typeOfView) {

        if (typeOfView.equals("text")) {

                Log.i("MY FUCKING FRAGMENTS", fragmentsList.get(0).toString());

            return fragmentsList.get(0);


        } else {
            Log.i("MY FUCKING FRAGMENTS", fragmentsList.get(1).toString());
            return fragmentsList.get(1);

        }

    }

    //    public void changeFragment(FragmentTransaction transaction, Fragment fragment) {
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
//    }
    public void changeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
