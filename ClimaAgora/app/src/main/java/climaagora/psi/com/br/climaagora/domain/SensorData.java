package climaagora.psi.com.br.climaagora.domain;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.Date;

public class SensorData implements Serializable {

    public static String[] columns = new String[] { SensorDatas._ID, SensorDatas.SENSOR_ID,
            SensorDatas.CREATE_DATE, SensorDatas.TEMPERATURA, SensorDatas.UMIDADE, SensorDatas.STATUS };

    /**
     * Pacote do Content Provider. Precisa ser ???nico.
     */
    public static final String AUTHORITY = "climaagora.psi.com.br.provider.sensor";

    private Long _id;
    private String sensorId;
    private Date date;
    private int temperatura;
    private int umidade;
    private String status;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getUmidade() {
        return umidade;
    }

    public void setUmidade(int umidade) {
        this.umidade = umidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "date=" + date +
                ", temperatura=" + temperatura +
                ", umidade=" + umidade +
                '}';
    }

    public static final class SensorDatas implements BaseColumns {

        private SensorDatas() {
        }

        // content://br.livro.android.provider.carro/carros
        /** The Constant CONTENT_URI. */
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/sensor-datas");

        // Mime Type para todos os carros
        /** The Constant CONTENT_TYPE. */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.sensor-datas";

        // Mime Type para um ???nico carro
        /** The Constant CONTENT_ITEM_TYPE. */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.sensor-datas";

        // Ordena??????o default para inserir no order by
        /** The Constant DEFAULT_SORT_ORDER. */
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        /** The Constant CREATE_DATE. */
        public static final String _ID = "id";

        /** The Constant CREATE_DATE. */
        public static final String SENSOR_ID = "sensor_id";

        /** The Constant CREATE_DATE. */
        public static final String CREATE_DATE = "create_date";

        /** The Constant OBJECTIVE. */
        public static final String TEMPERATURA = "temperatura";

        /** The Constant DESCRIPTION. */
        public static final String UMIDADE = "umidade";

        /** The Constant STATUS. */
        public static final String STATUS = "status";

        /**
         * Gets the uri id.
         *
         * @param id
         *            the id
         * @return the uri id
         */
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /carros
            Uri uriClass = ContentUris.withAppendedId(SensorDatas.CONTENT_URI, id);
            return uriClass;
        }
    }
}
