package com.example.secondaryportscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_calculate;
    TextView hw_output_text, lw_output_text;

    EditText input_difference_hw_springs_edit_text,
            input_difference_lw_springs_edit_text,
            input_difference_hw_neaps_edit_text,
            input_difference_lw_neaps_edit_text,
            input_current_primary_port_lw_edit_text,
            input_current_primary_port_hw_edit_text;

    public double output_current_secondary_port_hw;
    public double output_current_secondary_port_lw;

    public double input_difference_hw_springs;
    public double input_difference_lw_springs;
    public double input_difference_hw_neaps;
    public double input_difference_lw_neaps;

    public double input_current_primary_port_hw;
    public double input_current_primary_port_lw;

    public String SELECTED_PRIMARY_PORT;

    // enter primary port data
    public static Map<String, Map> primary_port_data = new HashMap<>();
    static {
        Map<String, Double> portsmouth_port_data = new HashMap<String, Double>();
        portsmouth_port_data.put("MHWS", 4.7);
        portsmouth_port_data.put("MHWN", 3.8);
        portsmouth_port_data.put("MLWS", 0.8);
        portsmouth_port_data.put("MLWN", 1.9);

        primary_port_data.put("Portsmouth", portsmouth_port_data);

        Map<String, Double> southampton_port_data = new HashMap<String, Double>();
        southampton_port_data.put("MHWS", 4.5);
        southampton_port_data.put("MHWN", 3.7);
        southampton_port_data.put("MLWS", 0.5);
        southampton_port_data.put("MLWN", 1.8);

        primary_port_data.put("Southampton", southampton_port_data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.button_add_to_database);
        v.setOnClickListener(this);

        button_calculate = (Button) findViewById(R.id.button_calculate);
        input_difference_hw_springs_edit_text = (EditText) findViewById(R.id.inputDHWS);
        input_difference_lw_springs_edit_text = (EditText) findViewById(R.id.inputDLWS);
        input_difference_hw_neaps_edit_text = (EditText) findViewById(R.id.inputDHWN);
        input_difference_lw_neaps_edit_text = (EditText) findViewById(R.id.inputDLWN);
        input_current_primary_port_hw_edit_text = (EditText) findViewById(R.id.inputCurHW);
        input_current_primary_port_lw_edit_text = (EditText) findViewById(R.id.inputCurLW);
        hw_output_text = (TextView) findViewById(R.id.hw_output_text);
        lw_output_text = (TextView) findViewById(R.id.lw_output_text);

        final Spinner primary_port_spinner = (Spinner) findViewById(R.id.primary_port);

//        // Convert set of keys to string array
        int key_set_size = primary_port_data.keySet().size();
        String[] primary_ports = primary_port_data.keySet().toArray(new String[key_set_size]);

//        String[] primary_ports = new String [] {"Portsmouth", "Southampton"};

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<String> primary_port_adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                primary_ports);

        // Apply the adapter to the spinner
        primary_port_spinner.setAdapter(primary_port_adapter);

        primary_port_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_PRIMARY_PORT = primary_port_spinner.getSelectedItem().toString();
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        button_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // parse variables in EditText fields
                input_current_primary_port_hw = Double.parseDouble(input_current_primary_port_hw_edit_text.getText().toString());
                input_current_primary_port_lw = Double.parseDouble(input_current_primary_port_lw_edit_text.getText().toString());
                input_difference_hw_springs = Double.parseDouble(input_difference_hw_springs_edit_text.getText().toString());
                input_difference_lw_springs = Double.parseDouble(input_difference_lw_springs_edit_text.getText().toString());
                input_difference_hw_neaps = Double.parseDouble(input_difference_hw_neaps_edit_text.getText().toString());
                input_difference_lw_neaps = Double.parseDouble(input_difference_lw_neaps_edit_text.getText().toString());

                // High Water Calculation
                double primary_port_mean_hw_springs = (double) primary_port_data.get(SELECTED_PRIMARY_PORT).get("MHWS");
                double primary_port_mean_hw_neaps = (double) primary_port_data.get(SELECTED_PRIMARY_PORT).get("MHWN");

//                double primary_port_mean_hw_springs = (double) 4.7;
//                double primary_port_mean_hw_neaps = (double) 3.9;

                double gradient_hw = ( (input_current_primary_port_hw - primary_port_mean_hw_springs)
                        / (primary_port_mean_hw_springs - primary_port_mean_hw_neaps) );

                output_current_secondary_port_hw = input_current_primary_port_hw + input_difference_hw_springs
                        + gradient_hw * (input_difference_hw_springs - input_difference_hw_neaps);

                // Low Water Calculation
                double primary_port_mean_lw_springs = (double) primary_port_data.get(SELECTED_PRIMARY_PORT).get("MLWS");
                double primary_port_mean_lw_neaps = (double) primary_port_data.get(SELECTED_PRIMARY_PORT).get("MLWN");

//                double primary_port_mean_lw_springs = (double) 0.9;
//                double primary_port_mean_lw_neaps = (double) 1.8;

                double gradient_lw = ( (input_current_primary_port_lw - primary_port_mean_lw_springs)
                        / (primary_port_mean_lw_springs - primary_port_mean_lw_neaps) );

                output_current_secondary_port_lw = input_current_primary_port_lw + input_difference_lw_springs
                        + gradient_lw * (input_difference_lw_springs - input_difference_lw_neaps);

                // map the selected ports to the data arrays, do the calculation and display the result
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String hw_result_string = decimalFormat.format(output_current_secondary_port_hw);
                String lw_result_string = decimalFormat.format(output_current_secondary_port_lw);
                hw_output_text.setText(hw_result_string);  // set result to edit text box
                lw_output_text.setText(lw_result_string);  // set result to edit text box
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.button_go_to_add_to_database) {
            // define a new Intent for the second Activity
            Intent intent = new Intent(this, AddSecondaryPortToDatabase.class);

            //start the second Activity
            this.startActivity(intent);
        }
    }
}
