package com.example.androidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class viewInventoryActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;
    private TextView totalnoofitem, totalnoofsum;
    private int counttotalnoofitem =0;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        totalnoofitem= findViewById(R.id.totalnoitem);
        totalnoofsum = findViewById(R.id.totalsum);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(users.getDisplayName()).child("Items");
        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counttotalnoofitem = (int) dataSnapshot.getChildrenCount();
                    totalnoofitem.setText(Integer.toString(counttotalnoofitem));
                }else{
                    totalnoofitem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//        mdatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int sum=0;
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    System.out.println("Hello");
////                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
////                    Object price = map.get("itemprice");
////                    int pValue = Integer.parseInt(String.valueOf(price));
////                    sum += pValue;
////                    totalnoofsum.setText(String.valueOf(sum));
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users").child(users.getDisplayName()).child("Items"), Items.class)
                        .build();

        adapter=new ItemAdapter(options);
        mrecyclerview.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}