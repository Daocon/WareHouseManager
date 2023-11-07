package hieudx.fpoly.warehousemanager.fragments.Bill.Bill_In;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hieudx.fpoly.warehousemanager.databinding.FragmentAddBillInBinding;

public class Add_Bill_In_Fragment extends Fragment {
    private FragmentAddBillInBinding binding;

    public Add_Bill_In_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBillInBinding.inflate(inflater, container, false);

        binding.btnAdd.setOnClickListener(view -> {
            if (binding.spnSupplier.isSelected()){

            }
        });
        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setCustomActionBar();
//    }
//
//    private void setCustomActionBar() {
//        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            // Xử lý khi nút back được nhấn
//            requireActivity().onBackPressed(); // Quay lại fragment trước đó
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}