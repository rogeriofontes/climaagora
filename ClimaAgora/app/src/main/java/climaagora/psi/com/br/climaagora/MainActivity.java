package climaagora.psi.com.br.climaagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import climaagora.psi.com.br.climaagora.domain.SensorData;
import climaagora.psi.com.br.climaagora.repository.SensorRepository;
import climaagora.psi.com.br.climaagora.util.date.DateUtil;
import climaagora.psi.com.br.climaagora.web.GetData;

public class MainActivity extends AppCompatActivity {
    private SensorRepository sensorRepository = null;
    TextView temperaturaTV, umidadeTv, dataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        temperaturaTV = (TextView) findViewById(R.id.temperaturaSensorTv);
        umidadeTv =  (TextView) findViewById(R.id.umidadeSensorTv);
        dataTv = (TextView) findViewById(R.id.dataSensorTv);

        sensorRepository = new SensorRepository(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent it = new Intent(MainActivity.this, DadosSensorActivity.class);
                startActivity(it);
            }
        });

        // List<SensorData> sensorDatas = new ArrayList<>();
   /*     SensorData sensorData = new SensorData();
        sensorData.setSensorId("5b9e54d84c12be077f7d9352");
        sensorData.setDate(new Date());
        sensorData.setUmidade(18);
        sensorData.setTemperatura(26);
        //sensorDatas.add(sensorData);
        sensorRepository.save(sensorData);

        SensorData sensorData2 = new SensorData();
        sensorData2.setSensorId("5b9e54d84c12be077f7d9352");
        sensorData2.setDate(new Date());
        sensorData2.setUmidade(25);
        sensorData2.setTemperatura(26);
        //sensorDatas.add(sensorData);
        sensorRepository.save(sensorData2);

        SensorData sensorData3 = new SensorData();
        sensorData3.setSensorId("5b9e54d84c12be077f7d9352");
        sensorData3.setDate(new Date());
        sensorData3.setUmidade(25);
        sensorData3.setTemperatura(26);
        //sensorDatas.add(sensorData);
       sensorRepository.save(sensorData3);
*/
        //try {
            String url = getResources().getString(R.string.url);
            new GetData().execute();
           // List<SensorData> sensorDataList = new GetData().execute().get();
            /*for (SensorData data : sensorDataList) {
                boolean result = sensorRepository.save(data);
                Log.i("ta", "-->" + sensorDataList);
            }*/
       // } catch (InterruptedException e) {
       //     e.printStackTrace();
       // } catch (ExecutionException e) {
      //      e.printStackTrace();
       // }

        SensorData data = sensorRepository.findSensorDataByDate();
        dataTv.setText(DateUtil.getDate(data.getDate()));
        umidadeTv.setText(String.valueOf(data.getUmidade()));
        temperaturaTV.setText(String.valueOf(data.getTemperatura()));
        Log.i("LGO", data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
