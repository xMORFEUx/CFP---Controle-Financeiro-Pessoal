package com.example.cfp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;

import com.example.cfp.databinding.ActivityGastosBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Gastos_Activity extends AppCompatActivity {

    private ActivityGastosBinding binding;

    private NumberFormat numberFormat = new DecimalFormat("###,###,##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGastosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.btObjetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gastosString = binding.ValorSeek.getText().toString();
                double gastos = Double.parseDouble(gastosString);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Usuarios");

                Map<String, Object> update = new HashMap<>();
                update.put("gastos",gastos);

                reference.updateChildren(update);

                Intent intent = new Intent(Gastos_Activity.this,Categorias_Activity.class);
                startActivity(intent);
            }
        });

        binding.ValorSeek.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    int value = Integer.parseInt(s.toString().replaceAll("[^\\^\\d]", ""));
                    binding.seekBar.setProgress(value);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = numberFormat.format(progress);
                binding.ValorSeek.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private String formatCurrency(double value){
        return numberFormat.format(value);
    }

    private double parseDoubleFromCurrencyString(String currencyString){
        try{
            return numberFormat.parse(currencyString).doubleValue();
        } catch (Exception e){
            return 0.0;
        }
    }
}