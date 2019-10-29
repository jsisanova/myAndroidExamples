package com.example.android.passingdatabetweenactivitiesintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

public class PassingDataSourceActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_data_source);

        setTitle("Source Activity");

        // Click this button to pass data to target activity.
        Button passDataSourceButton = (Button)findViewById(R.id.passDataSourceButton);
        passDataSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassingDataSourceActivity.this, PassingDataTargetActivity.class);
                intent.putExtra("message", "This message comes from PassingDataSourceActivity's first button");
                startActivity(intent);
            }
        });

        // Click this button to pass data to target activity and
        // then wait for target activity to return result data back.
        Button passDataReturnResultSourceButton = (Button)findViewById(R.id.passDataReturnResultSourceButton);
        passDataReturnResultSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassingDataSourceActivity.this, PassingDataTargetActivity.class);
                intent.putExtra("message", "This message comes from PassingDataSourceActivity's second button");
                startActivityForResult(intent, REQUEST_CODE_1);
            }
        });
    }

    // This method is invoked when target activity return result data back.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode)
        {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case REQUEST_CODE_1:
                TextView textView = (TextView)findViewById(R.id.resultDataTextView);
                if(resultCode == RESULT_OK)
                {
                    String messageReturn = dataIntent.getStringExtra("message_return");
                    textView.setText(messageReturn);
                }
        }
    }
}
