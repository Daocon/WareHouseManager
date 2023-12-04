package hieudx.fpoly.warehousemanager.fragments.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hieudx.fpoly.warehousemanager.R;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.dao.Bill.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentChartBinding;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;
import hieudx.fpoly.warehousemanager.models.bill.Bill_in_detail;

public class Chart_Fragment extends Fragment {
    private FragmentChartBinding binding;
    private List<BarEntry> entries1;
    private List<BarEntry> entries2;
    private Bill_In_Dao bill_in_dao;
    private Bill_Out_Dao bill_out_dao;

    public Chart_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bill_in_dao = new Bill_In_Dao(getContext());
        bill_out_dao = new Bill_Out_Dao(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpChart();
        setUpData();
    }

    private void setUpData() {
        billIn();
        billOut();
        BarDataSet barDataSet1 = new BarDataSet(entries1, "Phiếu nhập");
        barDataSet1.setColors(Color.GREEN);
        barDataSet1.setValueTextSize(14f);
        barDataSet1.setValueTextColor(Color.RED);

        BarDataSet barDataSet2 = new BarDataSet(entries2, "Phiếu xuất");
        barDataSet2.setColors(Color.BLUE);
        barDataSet2.setValueTextSize(14f);
        barDataSet2.setValueTextColor(Color.RED);

        BarData barData = new BarData(barDataSet1, barDataSet2);
        binding.chart.animateXY(2000, 2000);
        binding.chart.setData(barData);
        binding.chart.invalidate();
    }

    private void billOut() {
        entries2 = new ArrayList<>();
        List<Pair<String, Float>> monthlyTotals = bill_out_dao.getMonthlyTotals();
        for (int i = 0; i < monthlyTotals.size(); i++) {
            Pair<String, Float> monthlyTotal = monthlyTotals.get(i);
            String month = monthlyTotal.first;
            Float total = monthlyTotal.second;
            if (month != null && total != null) {
                entries2.add(new BarEntry(Float.valueOf(month), total));
            }
            Toast.makeText(getContext(), monthlyTotal.first+"-"+monthlyTotal.second, Toast.LENGTH_SHORT).show();
        }
    }

    private void billIn() {
        entries1 = new ArrayList<>();
        List<Pair<String, Float>> monthlyTotals = bill_in_dao.getMonthlyTotals();
        for (int i = 0; i < monthlyTotals.size(); i++) {
            Pair<String, Float> monthlyTotal = monthlyTotals.get(i);
            String month = monthlyTotal.first;
            Float total = monthlyTotal.second;
            if (month != null && total != null) {
                entries1.add(new BarEntry(Float.valueOf(month), total));
            }
            Toast.makeText(getContext(), monthlyTotal.first+"-"+monthlyTotal.second, Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpChart() {
        binding.chart.getDescription().setEnabled(false);
        binding.chart.setDrawValueAboveBar(false);
        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setAxisMaximum(12);
        xAxis.setAxisMinimum(1);
        YAxis yAxisRight = binding.chart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        YAxis yAxisLeft = binding.chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
    }
}