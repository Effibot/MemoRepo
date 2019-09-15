package com.effirossimotoi.memome.alfa;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IconFragment extends Fragment {


    public IconFragment() {
        // Required empty public constructor
    }

    private ImageButton favouriteButton;
    private ImageButton workButton;
    private ImageButton schoolButton;
    private ImageButton foodButton;
    private ImageButton travelButton;
    private ImageButton shoppingButton;
    private ImageButton videogamesButton;
    private ImageButton smileButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_icon, container, false);
        favouriteButton = view.findViewById(R.id.favouriteButton);
        workButton= view.findViewById(R.id.workButton);
        schoolButton= view.findViewById(R.id.schoolButton);
        foodButton= view.findViewById(R.id.foodButton);
        travelButton= view.findViewById(R.id.travelButton);
        shoppingButton= view.findViewById(R.id.shoppingButton);
        videogamesButton= view.findViewById(R.id.videogamesButton);
        smileButton= view.findViewById(R.id.smileButton);

        final EditActivity activity = (EditActivity) getActivity();

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(1);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(2);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        schoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(3);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(4);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        travelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(5);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(6);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        videogamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(7);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        smileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setIdIcon(8);
                getFragmentManager().beginTransaction().remove(IconFragment.this).commit();
            }
        });
        return view;
    }

}
