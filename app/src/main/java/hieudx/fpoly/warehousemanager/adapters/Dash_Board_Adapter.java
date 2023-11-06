package hieudx.fpoly.warehousemanager.adapters;

import static hieudx.fpoly.warehousemanager.MainActivity.translayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.databinding.ItemDashBoardRcvBinding;
import hieudx.fpoly.warehousemanager.fragments.Bill.Bill_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Category_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Delivery_Fragment;
import hieudx.fpoly.warehousemanager.fragments.member.Member_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Product_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Staff_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Statistic_Fragment;
import hieudx.fpoly.warehousemanager.fragments.Supplier_Fragment;
import hieudx.fpoly.warehousemanager.models.item_dash_board;

public class Dash_Board_Adapter extends RecyclerView.Adapter<Dash_Board_Adapter.ViewHolder> {
    private ArrayList<item_dash_board> list;
    private Context context;

    public Dash_Board_Adapter(ArrayList<item_dash_board> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDashBoardRcvBinding binding = ItemDashBoardRcvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.img.setImageResource(list.get(position).getImg());
        holder.binding.tv.setText(list.get(position).getText());

        holder.itemView.setOnClickListener(view -> {
            int clicked = holder.getAdapterPosition();
            switch (clicked) {
                case 0:
                    loadFragment(new Product_Fragment());
                    translayout();
                    break;
                case 1:
                    loadFragment(new Category_Fragment());
                    translayout();
                    break;
                case 2:
                    loadFragment(new Bill_Fragment());
                    translayout();
                    break;
                case 3:
                    loadFragment(new Member_Fragment());
                    translayout();
                    break;

                case 4:
                    loadFragment(new Statistic_Fragment());
                    translayout();
                    break;
                case 5:
                    loadFragment(new Delivery_Fragment());
                    translayout();
                    break;

                case 6:
                    loadFragment(new Supplier_Fragment());
                    translayout();
                    break;
                case 7:
                    loadFragment(new Staff_Fragment());
                    translayout();
                    break;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_container_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDashBoardRcvBinding binding;

        public ViewHolder(@NonNull ItemDashBoardRcvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
