package fpoly.thuyltph35992.lab1_2_ph35992;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btnLogout, btnAdd;
    FirebaseFirestore db;
    private List<City> cityList;
    private CityAdapter cityAdapter;
    RecyclerView rcView;
    CardView animation;
//    private LottieAnimationView lottie;

    public static final String NAME = "name";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String CAPITAL = "capital";
    public static final String POPULATION = "population";
    public static final String REGIONS = "regions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnLogout = findViewById(R.id.btnLogout);
//        lottie = findViewById(R.id.lottie);
        btnAdd = findViewById(R.id.btnAdd);
        rcView = findViewById(R.id.rcView);
        animation = findViewById(R.id.animation);

        rcView.setLayoutManager(new LinearLayoutManager(this));

        cityList = new ArrayList<>();
        cityAdapter = new CityAdapter(this, cityList);
        rcView.setAdapter(cityAdapter);

//        lottie.setVisibility(View.GONE);
        animation.setVisibility(View.GONE);
//        lottie.cancelAnimation();

        docDulieu();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), SignInOption.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_them();
            }
        });

    }
    private void docDulieu() {
        CollectionReference citiesRef = db.collection("cities");

//        citiesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    cityList.clear();
//                    for (QueryDocumentSnapshot document:task.getResult()){
//                        City city = document.toObject(City.class);
//                        cityList.add(city);
//                    }
//                    cityAdapter.notifyDataSetChanged();
//                }else {
//                    Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


    public void dialog_them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText edtName, edtState, edtCountry, edtPopulation, edtRegions;
        TextInputLayout layoutName, layoutState, layoutCountry, layoutPopulation, layoutRegions;
        RadioButton rdoThuDo, rdoKPThuDo;

        edtName = view.findViewById(R.id.edtName);
        edtState = view.findViewById(R.id.edtState);
        edtCountry = view.findViewById(R.id.edtCountry);
        edtPopulation = view.findViewById(R.id.edtPopulation);
        edtRegions = view.findViewById(R.id.edtRegions);

        layoutName = view.findViewById(R.id.layoutName);
        layoutState = view.findViewById(R.id.layoutState);
        layoutCountry = view.findViewById(R.id.layoutCountry);
        layoutPopulation = view.findViewById(R.id.layoutPopulation);
        layoutRegions = view.findViewById(R.id.layoutRegions);


        rdoThuDo = view.findViewById(R.id.rdoThuDo);
        rdoKPThuDo = view.findViewById(R.id.rdoKPThuDo);


        Button btnAdd = view.findViewById(R.id.btnThem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String state = edtState.getText().toString().trim();
                String country = edtCountry.getText().toString().trim();

                String population = edtPopulation.getText().toString().trim();
                String regions = edtRegions.getText().toString().trim();

                layoutName.setError(null);
                layoutRegions.setError(null);
                layoutState.setError(null);
                layoutCountry.setError(null);
                layoutPopulation.setError(null);

                if (name.isEmpty() && state.isEmpty() && country.isEmpty() && population.isEmpty() && regions.isEmpty()) {
                    layoutName.setError("Name đang để trống");
                    layoutRegions.setError("Regions đang để trống");
                    layoutState.setError("State đang để trống");
                    layoutCountry.setError("Country đang để trống");
                    layoutPopulation.setError("Population đang để trống");
                    return;
                }

                if (name.isEmpty()) {
                    layoutName.setError("Name đang để trống");
                    return;
                }

                if (state.isEmpty()) {
                    layoutState.setError("State đang để trống");
                    return;
                }

                if (country.isEmpty()) {
                    layoutCountry.setError("Country đang để trống");
                    return;
                }

                if (!rdoThuDo.isChecked() && !rdoKPThuDo.isChecked()) {
                    Toast.makeText(MainActivity.this, "Thành phố của bạn có phải là thủ đô không?", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean capital = rdoThuDo.isChecked();

                if (population.isEmpty()) {
                    layoutPopulation.setError("Population đang để trống");
                    return;
                }

                if (regions.isEmpty()) {
                    layoutRegions.setError("Regions đang để trống");
                    return;
                }

//                lottie.setVisibility(View.VISIBLE);
                animation.setVisibility(View.VISIBLE);
//                lottie.playAnimation();

                themDuLieu(
                        country,
                        name,
                        Integer.parseInt(population),
                        capital,
                        regions,
                        state
                );
                dialog.dismiss();

//                lottie.setVisibility(View.GONE);
                animation.setVisibility(View.GONE);
//                lottie.cancelAnimation();

            }
        });
    }

    private void themDuLieu(String country, String name, int population, boolean capital, String regions, String state) {

        btnAdd.setEnabled(false);
        btnAdd.setText("Dang xu ly...");

        CollectionReference cities = db.collection("cities");
        Map<String, Object> city = new HashMap<>();
        city.put("name", name);
        city.put("country", country);
        city.put("population", population);
        city.put("capital", capital);
        city.put("regions", Arrays.asList(regions.split("\\s*,\\s*")));
        city.put("state", state);

        cities.add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        docDulieu();
                        btnAdd.setEnabled(true);
                        btnAdd.setText("Thêm");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        docDulieu();
                        btnAdd.setEnabled(true);
                        btnAdd.setText("Thêm");
                    }
                });

    }
}