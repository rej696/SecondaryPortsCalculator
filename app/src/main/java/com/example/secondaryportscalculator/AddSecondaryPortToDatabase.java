package com.example.secondaryportscalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddSecondaryPortToDatabase extends AppCompatActivity implements View.OnClickListener {

    EditText standard_port_name_text,
            secondary_port_name_text,
            input_difference_hw_springs_edit_text,
            input_difference_lw_springs_edit_text,
            input_difference_lw_neaps_edit_text,
            input_difference_hw_neaps_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.button_return_to_main_activity);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.button_return_to_main_activity) {
            // define a new Intent for the second activity
            Intent intent = new Intent(this, MainActivity.class);

            // start the second Activity
            this.startActivity(intent);
        }
    }

    public void addSecondaryPort(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        double input_difference_hw_springs = Double.parseDouble(input_difference_hw_springs_edit_text.getText().toString());
        double input_difference_hw_neaps = Double.parseDouble(input_difference_hw_neaps_edit_text.getText().toString());
        double input_difference_lw_neaps = Double.parseDouble(input_difference_lw_neaps_edit_text.getText().toString());
        double input_difference_lw_springs = Double.parseDouble(input_difference_lw_springs_edit_text.getText().toString());
        String port_name = secondary_port_name_text.getText().toString();
        String standard_port_name = standard_port_name_text.getText().toString();
        SecondaryPort secondary_port = new SecondaryPort(
                port_name,
                standard_port_name,
                input_difference_hw_springs,
                input_difference_hw_neaps,
                input_difference_lw_neaps,
                input_difference_lw_springs
        );
        dbHandler.addHandler(secondary_port);
        standard_port_name_text.setText("");
        secondary_port_name_text.setText("");
        input_difference_hw_springs_edit_text.setText("");
        input_difference_lw_springs_edit_text.setText("");
        input_difference_lw_neaps_edit_text.setText("");
        input_difference_hw_neaps_edit_text.setText("");
        secondary_port_name_text.setText("");
    }

    public void findSecondaryPort(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        SecondaryPort secondary_port = dbHandler.findHandler(secondary_port_name_text.getText().toString());
        if (secondary_port != null) {
            lst.setText(String.valueOf(secondary_port.get))
        }
    }
}
