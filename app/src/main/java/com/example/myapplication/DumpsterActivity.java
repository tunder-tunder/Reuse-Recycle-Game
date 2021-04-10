package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DumpsterActivity extends AppCompatActivity {

    private static final String TAG = "DebugMap";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView textViewAddress, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumpster);

        String address = getIntent().getStringExtra("address");

        textViewAddress = findViewById(R.id.textViewAddress);
        textViewDescription = findViewById(R.id.textViewDescription);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://p.ecopetit.cat/wpic/lpic/26-263518_tumblr-photography-wallpaper-rocks-on-earth-background.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg","2 Image", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://live.staticflickr.com/7006/6621416427_8504865e6a_z.jpg","3 Image", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://c4.wallpaperflare.com/wallpaper/662/618/496/natur-2560x1600-sceneries-wallpaper-preview.jpg","4 Image", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        textViewAddress.setText(address);

        Task<DocumentSnapshot> test = db.collection("Dumpster").document(getIntent().getStringExtra("id")).get();

        test.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d(TAG, String.valueOf(task.getResult().getData().get("description")));
                textViewDescription.setText(String.valueOf(task.getResult().getData().get("description")));

            }
        });

    }
}