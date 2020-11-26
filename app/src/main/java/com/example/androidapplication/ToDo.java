package com.example.androidapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidapplication.MemberAdapter;
import com.example.androidapplication.Member;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ToDo extends AppCompatActivity implements View.OnClickListener {
    Button createEvent;
    MemberAdapter memberAdapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    List<Member> memberList;
    String currentuser;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        createEvent = findViewById(R.id.btn_createEvent);
        recyclerView=findViewById(R.id.recyclerView);
        createEvent.setOnClickListener(this);
        memberList=new ArrayList<Member>();
        currentuser = fAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("To-Do").child(currentuser);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Member p=dataSnapshot.getValue(Member.class);
                    memberList.add(p);
                }
                memberAdapter=new MemberAdapter(ToDo.this, memberList);
                recyclerView.setAdapter(memberAdapter);
                memberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        recreate();
        // Activity being restarted from stopped state
    }
    @Override
    public void onClick(View v) {
        if(v==createEvent)
        {
            goToCreateActivity();
        }
    }
    private void goToCreateActivity()
    {
        Intent intent=new Intent(getApplicationContext(),CreateEvent.class);
        startActivity(intent);

    }

}