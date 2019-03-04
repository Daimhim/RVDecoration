package org.daimhim.irregularlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
    }
    public void onClick(View pView){
        startActivity(new Intent(pView.getContext(),TestActivity.class));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,new TestFragment())
                .commit();
    }
}
