package com.example.dnsresolve_experiment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DnsInterceptor dnsInterceptor = new DnsInterceptor();

        Callback callback = new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("Printing Response:");
                System.out.println(response.toString());
                TextView editText = findViewById(R.id.responseField);
                editText.post(new Runnable() {
                    @Override
                    public void run() {
                        // Update your UI elements here
                        editText.setText(response.toString());
                    }
                });

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Printing Error:");
                System.out.println(e.toString());
                TextView editText = findViewById(R.id.responseField);
                editText.post(new Runnable() {
                    @Override
                    public void run() {
                        // Update your UI elements here
                        editText.setText(e.toString());
                    }
                });

            }
        };
        dnsInterceptor.makeGetRequest("https://www.google.com/", callback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}