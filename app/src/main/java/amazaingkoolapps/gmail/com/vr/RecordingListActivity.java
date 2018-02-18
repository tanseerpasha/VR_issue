package amazaingkoolapps.gmail.com.vr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class RecordingListActivity extends AppCompatActivity {

    private static final String TAG = "RLF_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        if(savedInstanceState== null){
            RecordingListFragment recordingListFragment = new RecordingListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recording_list_details,recordingListFragment , TAG)
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
