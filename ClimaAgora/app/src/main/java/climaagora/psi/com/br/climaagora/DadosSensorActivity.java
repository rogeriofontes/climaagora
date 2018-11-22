package climaagora.psi.com.br.climaagora;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import climaagora.psi.com.br.climaagora.adapter.SensorAdapter;
import climaagora.psi.com.br.climaagora.domain.SensorData;
import climaagora.psi.com.br.climaagora.repository.SensorRepository;

public class DadosSensorActivity extends AppCompatActivity {

    private SensorRepository sensorRepository = null;
    SensorAdapter sensorAdapter = null;
    ListView clazzListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sensorRepository = new SensorRepository(this);

        clazzListView = (ListView) findViewById(R.id.clazzListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<SensorData> sensorDatas = sensorRepository.list();

        sensorAdapter = new SensorAdapter(sensorDatas, getApplicationContext());
        clazzListView.setAdapter(sensorAdapter);
    }

}
