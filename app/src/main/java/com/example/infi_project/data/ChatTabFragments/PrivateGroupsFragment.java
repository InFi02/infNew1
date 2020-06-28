package com.example.infi_project.data.ChatTabFragments;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.AppMainPage;
import com.example.infi_project.Post_Activity;
import com.example.infi_project.R;
import com.example.infi_project.data.model.ProfileImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Adapter.GroupListAdapter;
import Adapter.Post_Adapter;

public class PrivateGroupsFragment extends Fragment {

    private ArrayList<String> interestNames1= new ArrayList<String>();
    String phone;

    private RecyclerView groupRecycler;
    public ArrayList<String> groupIdList;
    GroupListAdapter adapter;

    DatabaseReference groupRef, interestGroup, peopleGroup;


    public PrivateGroupsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AppMainPage activity= (AppMainPage) getActivity(); // should be replaced with better methods
//        interestNames1.clear();
//        assert activity != null;
//        interestNames1.addAll(activity.sendInterest());
//        phone=activity.sendData();

        //FOLLOWING METHOD TO GET INTEREST LIST WILL BE REPLACED
        phone= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(phone);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("choiceSelected").getValue() != null) {
                    String interestSelected = dataSnapshot.child("choiceSelected").getValue().toString();
                    if (interestSelected.equals("true")) {
                        String interestNoText=dataSnapshot.child("totalNoOfInterest").getValue().toString();
                        int interestNo=Integer.parseInt(interestNoText);
                        interestNames1.clear();
                        for (int i=0; i<interestNo; i++){
                            String interestNumber= String.valueOf(i);
                            String interest= dataSnapshot.child("userInterest").child(interestNumber).getValue().toString();
                            interestNames1.add(interest);
                        }
//                        Toast.makeText(Post_Activity.this, interestNames1.size()+" ", Toast.LENGTH_LONG).show();




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();


            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_private_groups, container, false);
        groupRecycler = view.findViewById(R.id.groupRecycler);
        groupRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        groupRecycler.setLayoutManager(mLayoutManager);
        groupIdList = new ArrayList<>();
         adapter= new GroupListAdapter(getContext(), groupIdList,phone);
        groupRecycler.setAdapter(adapter);
        return  view;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){



        final FloatingActionButton fab = view.findViewById(R.id.fabGroups);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                GroupCreateFragment myGroupFragment = new GroupCreateFragment();
//                GroupCreateFragment myDialogFragment = new GroupCreateFragment();
//
//                myDialogFragment.show(getChildFragmentManager(), "MyFragment");
//                FragmentTransaction fragmentTransaction=getChildFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.group_fragment_container, new GroupCreateFragment());
//                fragmentTransaction.commit();
//                fab.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Create New Group");
                LinearLayout layout= new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText groupName= new EditText(getContext());
                groupName.setHint("Enter Group Name");
                layout.addView(groupName);

                final TextView reqInterest= new TextView(getContext());
                reqInterest.setText("Choose the Interest Related to Group");
                layout.addView(reqInterest);

                RadioGroup interestRadioGroup= new RadioGroup(getContext());
                interestRadioGroup.setOrientation(LinearLayout.VERTICAL);
//                Toast.makeText(getContext(), "length "+interestNames1.size(), Toast.LENGTH_SHORT).show();
                for (int i=0; i<interestNames1.size();i++){
                    RadioButton newButton= new RadioButton(getContext());
                    newButton.setId(View.generateViewId());
                    newButton.setText(interestNames1.get(i));
//                    newButton.setTextColor(Color.parseColor("#FFFFFF"));
//                    newButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    interestRadioGroup.addView(newButton);
                }
                layout.addView(interestRadioGroup);

                builder.setView(layout);
                 groupRef = FirebaseDatabase.getInstance().getReference().child("Groups");
                 interestGroup = groupRef.child("InterestSpecific").getRef();
                 peopleGroup = groupRef.child("PeopleSpecific").getRef();

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int radioButtonId = interestRadioGroup.getCheckedRadioButtonId();
                        if (radioButtonId==-1 || groupName.getText().toString().equals("")){
                            Toast.makeText(getContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                        }

                        else {



                            RadioButton selectedRadio = (RadioButton) layout.findViewById(radioButtonId);
                            String interestSelected = selectedRadio.getText().toString();
                            Uri test=Uri.parse("https://firebasestorage.googleapis.com/v0/b/infiproject-88ce7.appspot.com/o/GroupImages%2Fdefaultgroupimage.png?alt=media&token=9b042064-7158-4771-8eef-ce6388a9bc4f");
                            String picky=test.toString();

                            String groupId = interestGroup.push().getKey();
                            assert groupId != null;
                            String groupNameText= groupName.getText().toString();

                            peopleGroup.child(phone).child(groupId).child("GroupId").setValue(groupId);
                            peopleGroup.child(phone).child(groupId).child("Interest").setValue(interestSelected);
                            peopleGroup.child(phone).child(groupId).child("Image").setValue(picky);
                            peopleGroup.child(phone).child(groupId).child("GroupName").setValue(groupNameText);

                            HashMap<String, Object> groupDetails = new HashMap<>();
                            groupDetails.put("GroupId", groupId);
                            groupDetails.put("Owner", phone);
                            groupDetails.put("Image", picky);
                            groupDetails.put("GroupName", groupNameText);
                            interestGroup.child(interestSelected).child(groupId).setValue(groupDetails);
                        }




                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();







            }
        });

        readGroups();


    }

    private void readGroups (){
        DatabaseReference peopleSpecificGroup=FirebaseDatabase.getInstance().getReference().child("Groups").child("PeopleSpecific").child(phone).getRef();
        peopleSpecificGroup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    groupIdList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String groupId= snapshot.getKey();
                        groupIdList.add(groupId);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
