package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerList extends Fragment {

    public CustomerList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_list, container, false);
//        Program saat cust1 diklik
        TextView cust1 = v.findViewById(R.id.txtNamaCustomer);
        cust1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Customer 1 is clicked!", Toast.LENGTH_SHORT).show();
                VerifyCustomerIdentity v1 = new VerifyCustomerIdentity();
                v1.setArguments(getArguments());
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, v1).addToBackStack(null).commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}
