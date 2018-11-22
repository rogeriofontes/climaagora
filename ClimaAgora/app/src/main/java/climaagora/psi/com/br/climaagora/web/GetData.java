package climaagora.psi.com.br.climaagora.web;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import climaagora.psi.com.br.climaagora.domain.SensorData;

public class GetData extends AsyncTask<Void, Void, Void> { //List<SensorData>


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String urlString  = "http://192.168.51.181:8081/sensors";
        //String urlString = "http://192.168.0.22:1880/clima-agora/temperaturas";
       /// String urlString = "https://query.yahooapis.com/v1/public/yql?q=select%20item.forecast%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22munic%2C%20de%22)%20and%20u%3D%27c%27%20limit%203&format=json";
        String json = null;
        List<SensorData> sensorDatas = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            json = getStringFromInputStream(inputStream);
            Log.d("TAG", json);

            Gson gson = new Gson(); // Or use new GsonBuilder().create();

            Type listType = new TypeToken<List<SensorData>>() {}.getType();
            sensorDatas = new Gson().fromJson(json, listType);
            Log.d("TAG", String.valueOf(sensorDatas.size()));

        } catch (ProtocolException e1) {
            Log.e("DEVMEDIA", "Falha ao acessar Web service", e1);
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            Log.e("DEVMEDIA", "Falha ao acessar Web service", e1);
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
