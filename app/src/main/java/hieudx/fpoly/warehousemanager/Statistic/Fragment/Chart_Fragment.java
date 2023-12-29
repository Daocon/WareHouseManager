package hieudx.fpoly.warehousemanager.Statistic.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_In_Dao;
import hieudx.fpoly.warehousemanager.Bill.Dao.Bill_Out_Dao;
import hieudx.fpoly.warehousemanager.databinding.FragmentChartBinding;

public class Chart_Fragment extends Fragment {
    private FragmentChartBinding binding;
    private List<Entry> entries1;
    private List<Entry> entries2;
    private Bill_In_Dao bill_in_dao;
    private Bill_Out_Dao bill_out_dao;
    String year;

    public Chart_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bill_in_dao = new Bill_In_Dao(getContext());
        bill_out_dao = new Bill_Out_Dao(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpDataPieChart();
        setUpChart();
        setUpSpinner();
    }

    private void setUpDataPieChart() {
        int totalBillin = bill_in_dao.getTotalBillInToday();
        int totalBillout = bill_out_dao.getTotalBillOutToday();

        if (totalBillin == 0 || totalBillout == 0) {
            binding.pieChart.setVisibility(View.GONE);
            binding.tvPieChart.setText("Chưa có hóa đơn nhập hoặc xuất nào hoàn thành hôm nay.");
            return;
        } else {
            binding.pieChart.setVisibility(View.VISIBLE);

//            Toast.makeText(getContext(), "In"+totalBillin, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "out"+totalBillout, Toast.LENGTH_SHORT).show();
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(totalBillin, "Nhập"));
            entries.add(new PieEntry(totalBillout, "Xuất"));

            PieDataSet pieDataSet = new PieDataSet(entries, "");
            pieDataSet.setColors(Color.GRAY, Color.CYAN);
            pieDataSet.setValueTextSize(14f);

            PieData pieData = new PieData(pieDataSet);
            binding.pieChart.setDrawEntryLabels(true);
            binding.pieChart.setUsePercentValues(false);
            binding.pieChart.setCenterTextRadiusPercent(50);
            binding.pieChart.setHoleRadius(30);
            binding.pieChart.setTransparentCircleRadius(40);
            binding.pieChart.setTransparentCircleColor(Color.RED);
            binding.pieChart.setTransparentCircleAlpha(50);
            binding.pieChart.setDescription(null);

            binding.pieChart.setData(pieData);
            binding.pieChart.invalidate();
        }

    }

    private void setUpSpinner() {
        List<String> yearsIn = bill_in_dao.getAllYears();
        List<String> yearsOut = bill_out_dao.getAllYears();
        Set<String> yearsSet = new HashSet<>(yearsIn);
        yearsSet.addAll(yearsOut);
        List<String> years = new ArrayList<>(yearsSet);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerYear.setAdapter(adapter);
        binding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) parent.getItemAtPosition(position);
                setUpData(year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setUpData(String year) {
        billIn(year);
        billOut(year);
        LineDataSet barDataSet1 = new LineDataSet(entries1, "Phiếu nhập");
        barDataSet1.setColors(Color.GREEN);
        barDataSet1.setValueTextSize(14f);
        barDataSet1.setValueTextColor(Color.RED);

        LineDataSet barDataSet2 = new LineDataSet(entries2, "Phiếu xuất");
        barDataSet2.setColors(Color.BLUE);
        barDataSet2.setValueTextSize(14f);
        barDataSet2.setValueTextColor(Color.RED);

        LineData barData = new LineData(barDataSet1, barDataSet2);
        binding.chart.animateXY(2000, 2000);
        binding.chart.setData(barData);
        binding.chart.invalidate();
    }

    private void billOut(String year) {
        entries2 = new ArrayList<>();
        List<Pair<String, Float>> monthlyTotals = bill_out_dao.getMonthlyTotals(year);
        for (int i = 0; i < monthlyTotals.size(); i++) {
            Pair<String, Float> monthlyTotal = monthlyTotals.get(i);
            String monthYear = monthlyTotal.first;
            Float total = monthlyTotal.second;
            if (monthYear != null && total != null) {
                String[] parts = monthYear.split("-");
                String years = parts[0];
                String month = parts[1];
                entries2.add(new Entry(Float.parseFloat(month), total));
            }
//            Toast.makeText(getContext(), monthlyTotal.first+"-"+monthlyTotal.second, Toast.LENGTH_SHORT).show();
        }
    }

    private void billIn(String year) {
        entries1 = new ArrayList<>();
        List<Pair<String, Float>> monthlyTotals = bill_in_dao.getMonthlyTotals(year);
        for (int i = 0; i < monthlyTotals.size(); i++) {
            Pair<String, Float> monthlyTotal = monthlyTotals.get(i);
            String monthYear = monthlyTotal.first;
            Float total = monthlyTotal.second;
            if (monthYear != null && total != null) {
                String[] parts = monthYear.split("-");
                String years = parts[0];
                String month = parts[1];
                entries1.add(new Entry(Float.parseFloat(month), total));
            }
//            Toast.makeText(getContext(), monthlyTotal.first+"-"+monthlyTotal.second, Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpChart() {
        binding.chart.getDescription().setEnabled(false);
//        binding.chart.setDrawValueAboveBar(false);
        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setAxisMaximum(12);
        xAxis.setAxisMinimum(1);

        YAxis yAxisRight = binding.chart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        YAxis yAxisLeft = binding.chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
    }
}