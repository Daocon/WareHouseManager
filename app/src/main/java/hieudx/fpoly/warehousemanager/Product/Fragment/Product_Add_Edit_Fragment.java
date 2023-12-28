package hieudx.fpoly.warehousemanager.Product.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import hieudx.fpoly.warehousemanager.Category.Dao.Category_Dao;
import hieudx.fpoly.warehousemanager.Category.Model.Category;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Supplier.Model.Supplier;
import hieudx.fpoly.warehousemanager.Supplier.Dao.Supplier_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentProductAddBinding;


public class Product_Add_Edit_Fragment extends Fragment {
    private FragmentProductAddBinding binding;
    private Product product;
    private Product_Dao product_dao;

    public Product_Add_Edit_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        General.onStateIconBack(getActivity(), actionBar, getParentFragmentManager(), false);
        General.onLoadSpinner(getContext(), getListCategory(), binding.spnCategory);
        General.onLoadSpinner(getContext(), getListSupplier(), binding.spnSupplier);

        General.isEmptyValid(binding.edName, binding.name);
        General.isEmptyValid(binding.edPrice, binding.price);

        product_dao = new Product_Dao(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {

            product = (Product) bundle.getSerializable("data");
            if (product.getImg().isEmpty())
                binding.imgProduct.setImageResource(R.mipmap.ic_launcher_foreground);
            else Picasso.get().load(product.getImg()).into(binding.imgProduct);

            binding.edName.setText(product.getName());
            binding.edPrice.setText(String.valueOf(product.getPrice()));

            ArrayList<HashMap<String, Object>> listCat = getListCategory();
            int index = 0;
            int position = -1;
            for (HashMap<String, Object> item : listCat) {
                if ((int) item.get("id") == product.getId_category()) {
                    position = index;
                }
                index++;
            }
            binding.spnCategory.setSelection(position);

            ArrayList<HashMap<String, Object>> listSup = getListSupplier();
            int index1 = 0;
            int position1 = -1;
            for (HashMap<String, Object> item : listSup) {
                if ((int) item.get("id") == product.getId_supplier()) {
                    position1 = index1;
                }
                index1++;
            }
            binding.spnSupplier.setSelection(position1);

            binding.btnAdd.setOnClickListener(view1 -> {
                String name = binding.edName.getText().toString().trim();
                String img = binding.edImg.getText().toString().trim();
                String price = binding.edPrice.getText().toString().trim();

                if (TextUtils.isEmpty(price) || TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    General.isContainSpace(img, binding.img);

                    General.isContainSpecialChar(name, binding.name);

                    if (price.contains(" ")) binding.price.setError("Không được chứa khoảng trắng");
                    else if (price.matches("[a-zA-Z]+"))
                        binding.price.setError("Không được nhập chữ");
                    else if (price.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~].*"))
                        binding.price.setError("Không được chứa ký tự đặc biệt");
                    else if (Double.parseDouble(price) < 0) binding.price.setError("Giá phải > 0");
                    else binding.price.setError(null);

                    if (binding.img.getError() == null && binding.name.getError() == null && binding.price.getError() == null) {
                        HashMap<String, Object> hm_cat = (HashMap<String, Object>) binding.spnCategory.getSelectedItem();
                        product.setId_category((int) hm_cat.get("id"));

                        HashMap<String, Object> hm_sup = (HashMap<String, Object>) binding.spnSupplier.getSelectedItem();
                        product.setId_supplier((int) hm_sup.get("id"));
                        if (binding.img.getError() == null &&
                                binding.name.getError() == null &&
                                binding.price.getError() == null) {
                            product.setName(name);
                            product.setPrice(Double.parseDouble(price));
                            product.setImg(img);
                            if (product_dao.updateProduct(product) > 0) {
                                Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        } else {
            binding.imgProduct.setVisibility(View.GONE);
            binding.btnAdd.setOnClickListener(view12 -> {
                String name = binding.edName.getText().toString().trim();
                String img = binding.edImg.getText().toString().trim();
                String price = binding.edPrice.getText().toString().trim();

                if (TextUtils.isEmpty(price) || TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    General.isContainSpace(img, binding.img);

                    General.isContainSpecialChar(name, binding.name);

                    if (price.contains(" ")) binding.price.setError("Không được chứa khoảng trắng");
                    else if (price.matches("[a-zA-Z]+"))
                        binding.price.setError("Không được nhập chữ");
                    else if (price.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~].*"))
                        binding.price.setError("Không được chứa ký tự đặc biệt");
                    else if (Double.parseDouble(price) < 0) binding.price.setError("Giá phải > 0");
                    else binding.price.setError(null);

                    if (binding.img.getError() == null && binding.name.getError() == null && binding.price.getError() == null) {
                        HashMap<String, Object> hm_cat = (HashMap<String, Object>) binding.spnCategory.getSelectedItem();
                        HashMap<String, Object> hm_sup = (HashMap<String, Object>) binding.spnSupplier.getSelectedItem();
                        if (binding.img.getError() == null && binding.name.getError() == null) {
                            product = new Product(name, Double.parseDouble(price), 0, img, (int) hm_cat.get("id"), (int) hm_sup.get("id"));
                            if (product_dao.insertProduct(product) > 0) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            } else
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private ArrayList<HashMap<String, Object>> getListCategory() {
        Category_Dao category_dao = new Category_Dao(getContext());
        ArrayList<Category> list = category_dao.getListCategory();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Category cat : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", cat.getId());
            hs.put("name", cat.getName());
            listHM.add(hs);
        }
        return listHM;
    }

    private ArrayList<HashMap<String, Object>> getListSupplier() {
        Supplier_Dao supplier_dao = new Supplier_Dao(getContext());
        ArrayList<Supplier> list = supplier_dao.getAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Supplier sup : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", sup.getId());
            hs.put("name", sup.getName());
            listHM.add(hs);
        }
        return listHM;
    }
}