package airwire.org.airwirewallet.ui.transaction_detail_activity;

import android.os.Bundle;
import android.view.ViewGroup;

import airwire.org.airwirewallet.R;
import airwire.org.airwirewallet.ui.base.BaseActivity;

/**
 * Created by akshaynexus on 8/14/17.
 */

public class InputsDetailActivity extends BaseActivity {

    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        setTitle("Inputs Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getLayoutInflater().inflate(R.layout.inputs_tx_detail_main,container);
    }
}
