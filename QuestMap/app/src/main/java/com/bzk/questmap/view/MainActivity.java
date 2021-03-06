package com.bzk.questmap.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.bzk.questmap.R;
import com.bzk.questmap.adapter.PlaceAdapter;
import com.bzk.questmap.database.PlaceDao;
import com.bzk.questmap.database.PlaceDatabase;
import com.bzk.questmap.databinding.ActivityMainBinding;
import com.bzk.questmap.model.Place;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    ArrayList<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.bar));

        places = new ArrayList<>();
        PlaceDatabase db = Room.databaseBuilder(getApplicationContext(),
                PlaceDatabase.class, "Places").allowMainThreadQueries().build();

        PlaceDao placeDao = db.placeDao();

        mDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        binding.addPlaceButton.setOnClickListener(view1 -> callMaps());
    }

    public void callMaps()
    {
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("info","new");
        startActivity(intent);
    }

    private void handleResponse(List<Place> placeList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceAdapter placeAdapter = new PlaceAdapter(placeList);
        binding.recyclerView.setAdapter(placeAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }
}