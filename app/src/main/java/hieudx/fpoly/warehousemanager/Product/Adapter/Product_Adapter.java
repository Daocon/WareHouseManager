package hieudx.fpoly.warehousemanager.Product.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Category.Dao.Category_Dao;
import hieudx.fpoly.warehousemanager.General;
import hieudx.fpoly.warehousemanager.Product.Dao.Product_Dao;
import hieudx.fpoly.warehousemanager.Product.Fragment.Product_Add_Edit_Fragment;
import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.Supplier.Supplier_Dao;
import hieudx.fpoly.warehousemanager.databinding.BotSheetProductDetailBinding;
import hieudx.fpoly.warehousemanager.databinding.ItemRcvProductBinding;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.Viewholder> implements Filterable {
    private ArrayList<Product> list;
    private ArrayList<Product> mlist;
    private LayoutInflater layoutInflater;
    private FragmentManager fragmentManager;
    private Context context;
    private Product_Dao dao;

    public Product_Adapter(Context context, ArrayList<Product> list, LayoutInflater layoutInflater, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.layoutInflater = layoutInflater;
        this.fragmentManager = fragmentManager;
        dao = new Product_Dao(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvProductBinding binding = ItemRcvProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        holder.binding.tvId.setText("Mã: " + list.get(position).getId());

        String price = General.formatSumVND(list.get(position).getPrice());
        holder.binding.tvPrice.setText("Giá : "+price+"đ");

        holder.binding.tvTonKho.setText("Tồn kho: " + list.get(position).getQuantity() + "");
        if (list.get(position).getImg().isEmpty()) holder.binding.img.setImageResource(R.mipmap.ic_launcher_foreground);
        else Picasso.get().load(list.get(position).getImg()).into(holder.binding.img);

        holder.itemView.setOnClickListener(view -> {
            onShowBotSheetDetail(list.get(holder.getAdapterPosition()), price);
        });
    }

    private void onShowBotSheetDetail(Product product, String price) {
        BotSheetProductDetailBinding btnBinding = BotSheetProductDetailBinding.inflate(layoutInflater);

        btnBinding.tvName.setText(product.getName());
        btnBinding.tvPrice.setText("Giá nhập: " + price+"đ");
        btnBinding.tvTonKho.setText("Tồn kho: " + product.getQuantity());
        Category_Dao category_dao = new Category_Dao(context);
        btnBinding.tvCategory.setText("Loại sản phẩm: " + category_dao.getCategoryById(product.getId_category()).getName());
        Supplier_Dao supplier_dao = new Supplier_Dao(context);
        btnBinding.tvSupplier.setText("Nhà cung cấp: "+supplier_dao.getSupplierById(product.getId_supplier()).getName());
        Dialog dialog = General.onSettingsBotSheet(context, btnBinding);

        btnBinding.btnDelete.setOnClickListener(view1 -> {
            onDeleteProduct(product, dialog);
        });

        btnBinding.btnEdit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", product);
            General.loadFragment(fragmentManager, new Product_Add_Edit_Fragment(), bundle);
            dialog.dismiss();

        });
    }

    private void onDeleteProduct(Product product, Dialog dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa");
        builder.setNegativeButton("Có", (dialog1, which) -> {
            switch (dao.deleteProduct(product.getId())) {
                case 1:
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.getProductList());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    break;
                case 0:
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case -1:
                    Toast.makeText(context, "Không thể xóa vì sản phẩm này đã được sử dụng trong hóa đơn", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        });

        builder.setPositiveButton("Không", null);
        builder.create();
        builder.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (TextUtils.isEmpty(search)) {
                    list = mlist;
                } else {
                    ArrayList<Product> listFilter = new ArrayList<>();
                    for (Product product : mlist) {
                        if (product.getName().toUpperCase().contains(search.toUpperCase())) {
                            listFilter.add(product);
                        }
                    }
                    list = listFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        private ItemRcvProductBinding binding;

        public Viewholder(ItemRcvProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}