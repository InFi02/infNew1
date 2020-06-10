package com.example.infi_project.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<ArrayList<String>> interestNames=new MutableLiveData<>();
    public MutableLiveData<String> mobileText=new MutableLiveData<>();

   public void setInterestNames(ArrayList<String> newInterestNames){
       interestNames.setValue(newInterestNames);

   }

   public void setMobileText(String newMobileText){
       mobileText.setValue(newMobileText);
   }

   public LiveData<ArrayList<String>> getInterestNames(){
       return interestNames;
    }

    public LiveData<String> getMobileText(){
        return mobileText;
    }
}
