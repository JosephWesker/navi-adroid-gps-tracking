package uv.er.joseph.gpsdriver.location;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uv.er.joseph.gpsdriver.R;

public class MainActivitySettings extends AppCompatActivity {


    private EditText
            //idEditText,
            vehiclenoEditText,
            destEditText,
            nextStopEditText,
            lateEditText,
            uncertaintyEditText,
            stopSequenceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("uv.er.joseph.gpsdriver","uv.er.joseph.gpsdriver.login.LoginActivity"));
        startActivity(intent);

        /*Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/

        //idEditText = (EditText) findViewById(R.id.editText_id);
        vehiclenoEditText = (EditText) findViewById(R.id.editText_vehicleno);
        destEditText = (EditText) findViewById(R.id.editText_dest);
        nextStopEditText = (EditText) findViewById(R.id.editText_nextStop);
        lateEditText = (EditText) findViewById(R.id.editText_late);
        uncertaintyEditText = (EditText) findViewById(R.id.editText_uncertainty);
        stopSequenceEditText = (EditText) findViewById(R.id.editText_stopSequence);
        //textMessage = (TextView) findViewById(R.id.text_Message);

        findViewById(R.id.btn_accept_settings).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*final int id = Integer.parseInt(idEditText.getText().toString());
                if (!isValidMin(id)) {
                    idEditText.setError("Invalid");
                }*/

                final String vehicleno = vehiclenoEditText.getText().toString();
                if (!isValid(vehicleno)) {
                    vehiclenoEditText.setError("Invalid");
                }

                final String dest = destEditText.getText().toString();
                if (!isValid(dest)) {
                    destEditText.setError("Invalid");
                }

                final String nextstop = nextStopEditText.getText().toString();
                if (!isValid(nextstop)) {
                    nextStopEditText.setError("Invalid");
                }

                final int late = Integer.parseInt(lateEditText.getText().toString());
                if (!isValidMin(late)) {
                    lateEditText.setError("Invalid");
                }

                final int uncertainty = Integer.parseInt(uncertaintyEditText.getText().toString());
                if (!isValidMin(uncertainty)) {
                    uncertaintyEditText.setError("Invalid");
                }

                final int stopSequence = Integer.parseInt(stopSequenceEditText.getText().toString());
                if (!isValidMin(stopSequence)) {
                    stopSequenceEditText.setError("Invalid");
                }

                if ( isValid(vehicleno) && isValid(dest) && isValid(nextstop) && isValidMin(late) && isValidMin(uncertainty) && isValidMin(stopSequence)) {
                    Intent intent = new Intent(arg0.getContext(), MainActivityConsume.class);
                    //intent.putExtra("id", id);
                    intent.putExtra("vehicleno", vehicleno);
                    intent.putExtra("dest", dest);
                    intent.putExtra("nextStop", nextstop);
                    intent.putExtra("late", late);
                    intent.putExtra("uncertainty", uncertainty);
                    intent.putExtra("stopSequence", stopSequence);
                    startActivity(intent);
                }

            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValid(String rule) {
        if (rule != null && rule.length() > 0) {
            return true;
        }
        return false;
    }

    private boolean isValidMin(int rule) {
        if (rule >= 0) {
            return true;
        }
        return false;
    }

}