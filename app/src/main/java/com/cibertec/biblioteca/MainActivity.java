package com.cibertec.biblioteca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cibertec.biblioteca.Helpers.BookRow;
import com.cibertec.biblioteca.Helpers.LibroInterface;
import com.cibertec.biblioteca.Helpers.TableViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    LibroInterface api;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        setTitle(Html.fromHtml("<b>Biblioteca CIBERTEC</b>"));

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LibroInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        api = retrofit.create(LibroInterface.class);
    }

    public void buscar(View view) {

        InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(im).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        view.requestFocusFromTouch();

        findViewById(R.id.scrollView2).setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Consultando el servidor", Toast.LENGTH_SHORT).show();

        String termino = ((EditText)findViewById(R.id.buscador)).getText().toString();

        Call<String> call = api.getLibros(termino);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body();
                        procesarLibros(jsonresponse);
                    }
                    else {
                        Log.i("onEmptyResponse", "La petición no devolvió nada");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {}
        });
    }

    @SneakyThrows
    private void procesarLibros(String jsonresponse) {

        JSONArray dataArray  = new JSONArray(jsonresponse);

        if(dataArray.length()>0){

            findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);

            Log.d("Response", jsonresponse);

            List<BookRow> libros = new ArrayList<>();

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataobj = dataArray.getJSONObject(i);

                libros.add(new BookRow(
                        dataobj.getString("codigo"),
                        dataobj.getString("titulo"),
                        dataobj.getString("estado"),
                        dataobj.getString("tipo")
                ));
            }

            TableViewAdapter adapter = new TableViewAdapter(libros);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No se encontraron libros", Toast.LENGTH_LONG).show();
        }
    }
}