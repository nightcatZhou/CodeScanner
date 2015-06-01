package michaelw.codescanner;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Michael.W on 6/1/2015.
 */
public class ScanActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        try {
            Context context=createPackageContext("micharlw.codescanner", 0);						//Obtain the context.
        }catch(NameNotFoundException e) {

        }
    }
}
