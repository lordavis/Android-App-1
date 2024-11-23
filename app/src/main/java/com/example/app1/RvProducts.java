package com.example.app1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RvProducts extends AppCompatActivity {
    RecyclerView rvProducts;
    List<ProductResult> productResults = new ArrayList<>();
    Button btn_usr_logout, btn_next_page;
    TextView ViewUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rv_products);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String GmailId = getIntent().getStringExtra("GmailId");
        btn_usr_logout = findViewById(R.id.usrLogout);
        btn_next_page = findViewById(R.id.btn_next_page);
        ViewUserId = findViewById(R.id.ViewUserId);
        ViewUserId.setText(GmailId);

        btn_next_page.setOnClickListener(view ->{
            Intent intent = new Intent(this, NewMail.class);
            startActivity(intent);
        });

        btn_usr_logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(RvProducts.this, Login.class);
            startActivity(intent);
            finish();
        });

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        getProducts();
    }

    private void getProducts() {
        Call<List<ProductResult>> apiCall = RetrofitClient.getInstance().getApis().getProducts();
        apiCall.enqueue(new Callback<List<ProductResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductResult>> call, @NonNull Response<List<ProductResult>> response) {
                productResults = response.body();
                Toast.makeText(RvProducts.this, "Got Products", Toast.LENGTH_SHORT).show();
                setAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductResult>> call, @NonNull Throwable t) {
                Toast.makeText(RvProducts.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        RvAdapter adapter = new RvAdapter(this, productResults);
        rvProducts.setAdapter(adapter);
    }

    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }
}