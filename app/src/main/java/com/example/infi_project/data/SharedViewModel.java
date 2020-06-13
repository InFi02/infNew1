package com.example.infi_project.data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SharedViewModel extends ViewModel {

//    public MutableLiveData<ArrayList<String>> interestNames = new MutableLiveData<>();
//    public MutableLiveData<String> mobileText = new MutableLiveData<>();
    public MutableLiveData<String> mobileText ;
    public MutableLiveData<ArrayList<String>> interestNames ;
    public ArrayList<String> interestList = new ArrayList<>();







    public void setInterestNames(ArrayList<String> newInterestNames) {
        interestNames.setValue(newInterestNames);

    }


    public void setMobileText(String newMobileText) {
        mobileText.setValue(newMobileText);
    }

    public LiveData<ArrayList<String>> getInterestNames() {
            loadInterest();
        return interestNames;
    }

    public LiveData<String> getMobileText() {
        if (mobileText==null)
            loadNumber();
        return mobileText;
    }

    private void loadInterest() {
//          ArrayList<String> interestList = new ArrayList<>();
        interestList.clear();
        final String mobileNumber = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        assert mobileNumber != null;
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileNumber);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
//                    int i = 0;
//                    while (iterator.hasNext()) {
//                        DataSnapshot next = (DataSnapshot) iterator.next();
//                        interestNames.add(Objects.requireNonNull(next.getValue()).toString());
//                        i = i + 1;
//                    }
                    String interestNoText = dataSnapshot.child("totalNoOfInterest").getValue().toString();
                    int interestNo = Integer.parseInt(interestNoText);
                    for (int i = 0; i < interestNo; i++) {
                        String interestNumber = String.valueOf(i);
                        String interest = Objects.requireNonNull(dataSnapshot.child("userInterest").child(interestNumber).getValue()).toString();
                        interestList.add(interest);
                    }

                    interestNames.setValue(interestList);

                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadNumber() {
        final String mobileNumber = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        mobileText.setValue(mobileNumber);

    }
}