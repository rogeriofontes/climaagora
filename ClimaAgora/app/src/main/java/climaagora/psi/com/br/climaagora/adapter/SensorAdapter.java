package climaagora.psi.com.br.climaagora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import climaagora.psi.com.br.climaagora.R;
import climaagora.psi.com.br.climaagora.domain.SensorData;
import climaagora.psi.com.br.climaagora.util.date.DateUtil;

/**
 * Created by rogeriofontes on 17/03/17.
 */

public class SensorAdapter extends BaseAdapter {
    private List<SensorData> classes = null;
    private Context context = null;

    public SensorAdapter(List<SensorData> classes, Context context) {
        this.classes = classes;
        this.context = context;
      //  clazzRepository = new ClazzRepository(context);
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SensorData c = classes.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sensor_item, null);

        TextView temperaturaTv = (TextView) view.findViewById(R.id.temperaturaTv);
        TextView umidadeTv = (TextView) view.findViewById(R.id.umidadeTv);
        TextView dataTv = (TextView) view.findViewById(R.id.dataTv);

        if (classes != null) {
            temperaturaTv.setText(c.getTemperatura() + "\n");
            umidadeTv.setText(c.getUmidade() + "\n");

            dataTv.setText(DateUtil.getDate(c.getDate()));

        }

        return view;
    }
}
