package com.example.android.passingdatabetweenactivitiesintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

public class PassingDataTargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_data_target);

        setTitle("Target Activity");

        // Get the transferred data from source activity.
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        TextView textView = (TextView)findViewById(R.id.requestDataTextView);
        textView.setText(message);

        // Click this button to send response result data to source activity.
        Button passDataTargetReturnDataButton = (Button)findViewById(R.id.passDataTargetReturnDataButton);
        passDataTargetReturnDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("message_return", "This data is returned when user click button in target activity.");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message_return", "This data is returned when user click back menu in target activity.");
        setResult(RESULT_OK, intent);
        finish();
    }
}
