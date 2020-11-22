package com.example.androidapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFragment extends Fragment {
    TextView name,email,profession;
    FirebaseAuth mAuth;
    FirebaseUser user=mAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);
        name=v.findViewById(R.id.name);
        email=v.findViewById(R.id.email);
        profession=v.findViewById(R.id.profession);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");
        if(mAuth.getInstance().getCurrentUser()!=null){
            String n=user.getDisplayName();
            name.setText("Username: "+n);
            String e=user.getEmail();
            email.setText("Email Id: "+e);
            DatabaseReference ProfRef=rootRef.child("Profession").child(user.getDisplayName());
            ProfRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ProfessionHelperClass p=snapshot.getValue(ProfessionHelperClass.class);
                    String pi=profession.toString().trim();
                    profession.setText("Profession: "+pi);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read Failed");
                }
            });
        }
    }


}
