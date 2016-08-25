package study.pmoreira.messenger;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveMessageActivity extends Activity {

    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        String message = getIntent().getStringExtra(EXTRA_MESSAGE);
        TextView textViewMessage = (TextView) findViewById(R.id.message);
        textViewMessage.setText(message);
    }
}
