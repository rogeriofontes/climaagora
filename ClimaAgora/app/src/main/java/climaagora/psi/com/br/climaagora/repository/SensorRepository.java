package climaagora.psi.com.br.climaagora.repository;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import climaagora.psi.com.br.climaagora.domain.SensorData;
import climaagora.psi.com.br.climaagora.infra.ActiveRecord;
import climaagora.psi.com.br.climaagora.infra.DatabaseAdapter;
import climaagora.psi.com.br.climaagora.util.date.DateUtil;

import static android.provider.MediaStore.Video.VideoColumns.CATEGORY;

public class SensorRepository extends ActiveRecord<SensorData> {

    /** The context. */
    private Context context;

    /** The generic dao. */
    DatabaseAdapter genericDao = null;

    /** The Constant CATEGORIA. */
    private static final String CATEGORIA = "sensor-data";

    /** Database table. */
    public static final String SQL_TABLE_NAME = "tbl_sensor";

    /** Database table DDL create statement. */
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + SQL_TABLE_NAME + " ("
            + SensorData.SensorDatas._ID + " integer primary key autoincrement, "
            + SensorData.SensorDatas.SENSOR_ID + " text null, "
            + SensorData.SensorDatas.CREATE_DATE + " date null, "
            + SensorData.SensorDatas.TEMPERATURA + " int null, "
            + SensorData.SensorDatas.UMIDADE + " int not null, "
            + SensorData.SensorDatas.STATUS + " text null "
            + "); ";

    /** The Constant SQL_DROP_TABLE. */
    public static final String SQL_DROP_TABLE = " DROP TABLE IF EXISTS " + SQL_TABLE_NAME;

    /**
     * Instantiates a new sensorData repository.
     *
     * @param context the context
     */
    public SensorRepository(Context context) {
        this.context = context;
        genericDao = getInstance(this.context);
    }

    /**
     * Gets the single instance of BalanceDAO.
     *
     * @param cntxt the cntxt
     * @return single instance of BalanceDAO
     */
    public DatabaseAdapter getInstance(Context cntxt) {
        return DatabaseAdapter.getInstance(cntxt);
    }

    /* (non-Javadoc)
     * @see br.com.pressystem.imobiliaria42backoffice.model.repository.dao.ActiveRecord#save(java.lang.Object)
     */
    @Override
    public boolean save(SensorData sensorData) {
        long retval;

        //if (this.validate()) {
        ContentValues values = new ContentValues();

        values.put(SensorData.SensorDatas.SENSOR_ID, sensorData.getSensorId());
        values.put(SensorData.SensorDatas.CREATE_DATE, DateUtil.getDate(sensorData.getDate()));
        values.put(SensorData.SensorDatas.TEMPERATURA, sensorData.getTemperatura());
        values.put(SensorData.SensorDatas.UMIDADE, sensorData.getUmidade());
        values.put(SensorData.SensorDatas.STATUS, sensorData.getStatus());

       if (sensorData.get_id() != null) {
            String _id = String.valueOf(sensorData.get_id());

            String where = SensorData.SensorDatas._ID + "=?";
            String[] whereArgs = new String[] { _id };

            retval = genericDao.update(SQL_TABLE_NAME, values, where, whereArgs);
        } else {
            retval = genericDao.insert(SQL_TABLE_NAME, values);
        }

        //} else {
        //retval = -1;
        //}
        return retval != -1 ? Boolean.TRUE : Boolean.FALSE;
    }

    /* (non-Javadoc)
     * @see br.com.pressystem.imobiliaria42backoffice.model.repository.dao.ActiveRecord#delete(long)
     */
    @Override
    public boolean delete(long id) {
        String where = SensorData.SensorDatas._ID + "=?";

        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };

        return genericDao.delete(SQL_TABLE_NAME, where, whereArgs) > 0;
    }


    /* (non-Javadoc)
     * @see br.com.pressystem.imobiliaria42backoffice.model.repository.dao.ActiveRecord#load(android.app.Activity, long)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void load(Activity activity, long id) {
        Cursor cursor = genericDao.getRaw(SQL_TABLE_NAME,
                new String[] { SensorData.SensorDatas._ID, SensorData.SensorDatas.CREATE_DATE,
                        SensorData.SensorDatas.TEMPERATURA, SensorData.SensorDatas.UMIDADE, SensorData.SensorDatas.STATUS }, SensorData.SensorDatas._ID , id);

        if (cursor != null) {
            cursor.moveToFirst();

            activity.startManagingCursor(cursor);

            SensorData sensorData = new SensorData();

            sensorData.setSensorId(cursor.getString(cursor.getColumnIndex(SensorData.SensorDatas.SENSOR_ID)));
            sensorData.setDate(DateUtil.getDate(cursor.getString(cursor.getColumnIndex(SensorData.SensorDatas.CREATE_DATE))));
            sensorData.setTemperatura(cursor.getInt(cursor.getColumnIndex(SensorData.SensorDatas.TEMPERATURA)));
            sensorData.setUmidade(cursor.getInt(cursor.getColumnIndex(SensorData.SensorDatas.UMIDADE)));
        }
    }

    // Busca o sensorData pelo nome "select * from sensorData where nome=?"
    /**
     * Buscar sensorData por address.
     *
     * @return the list
     */
    public SensorData findSensorDataByDate() {
        SensorData sensorData = null;
        Cursor cursor = null;
        try {
            cursor = genericDao.getMax("select MAX(" + SensorData.SensorDatas.CREATE_DATE + ") from " + SQL_TABLE_NAME);
            if (cursor != null && cursor.moveToFirst()) {

                Date findedDate = DateUtil.getDate(cursor.getString(0));
                Log.i("DAte:", "" + findedDate);
                sensorData = findSensorDataByDate(findedDate);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA,
                    "Erro ao buscar o sensorData pelo nome: " + e.toString());
            return null;
        }

        cursor.close();
        return sensorData;
    }

    /**
     * Buscar expense por name.
     *
     * @param createDate the name
     * @return the expense
     */
    public SensorData findSensorDataByDate(Date createDate) {
        SensorData sensorData = null;
        Cursor c = null;

        try {
            // WHERE clause
            String selection = SensorData.SensorDatas.CREATE_DATE + " = ?";
            // WHERE clause arguments
            // String[] selectionArgs = {
            // "rogerio.fontes@pressystem.com.br",
            // "12345" };

            String[] selectionArgs = { DateUtil.getDate(createDate) };

            // Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
            c = genericDao.getRaw(SQL_TABLE_NAME, SensorData.columns, selection, selectionArgs);

            Log.i("i>>", c.toString());

            int count = 0;

            if (c != null) {
                count = c.getCount();
                Log.i("Expense", "there are " + count + " records.");

                // Se encontrou...
                if (c.moveToNext()) {

                    sensorData = new SensorData();

                    // L??? os dados
                    sensorData.set_id(c.getLong(0));
                    sensorData.setSensorId(c.getString(1));
                    sensorData.setDate(DateUtil.getDate(c.getString(2)));
                    sensorData.setTemperatura(c.getInt(3));
                    sensorData.setUmidade(c.getInt(4));
                    sensorData.setStatus(c.getString(5));
                }
            }
        } catch (SQLException e) {
            Log.e(CATEGORY,
                    "Erro ao buscar o Expense pelo nome: " + e.toString());
            return null;
        }
        // Log.i("Expense", Expense.toString());
        c.close();
        return sensorData;
    }

    /**
     * Buscar sensorData by id.
     *
     * @param id the id
     * @return the boolean
     */
    public Boolean isSensorDataById(long id) {
        Boolean findLesson = false;
        Cursor c = genericDao.getRaw(SQL_TABLE_NAME, SensorData.columns, SensorData.SensorDatas._ID, id);

        if (c.getCount() > 0) {
            findLesson = true;
        }
        c.close();
        return findLesson;
    }

    // Retorna um cursor com todos os SensorDatas
    /**
     * Gets the cursor.
     *
     * @return the cursor
     */
    public Cursor getCursor() {
        try {
            // select * from SensorDatas
            return genericDao.get(SQL_TABLE_NAME, SensorData.columns);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os SensorDatas: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os SensorDatas
    /* (non-Javadoc)
     * @see br.com.pressystem.imobiliaria42backoffice.model.repository.dao.ActiveRecord#list()
     */
    public List<SensorData> list() {
        Cursor cursor = getCursor();

        List<SensorData> sensorDatas = new ArrayList<SensorData>();

        if (cursor != null && cursor.moveToFirst()) {

            // Recupera os ???ndices das colunas
            int idxId = cursor.getColumnIndex(SensorData.SensorDatas._ID);
            int idxSensorId = cursor.getColumnIndex(SensorData.SensorDatas.SENSOR_ID);
            int idxCreateDate = cursor.getColumnIndex(SensorData.SensorDatas.CREATE_DATE);
            int idxName = cursor.getColumnIndex(SensorData.SensorDatas.TEMPERATURA);
            int idxDescription = cursor.getColumnIndex(SensorData.SensorDatas.UMIDADE);
            int idxStatus = cursor.getColumnIndex(SensorData.SensorDatas.STATUS);
            // Loop at??? o final
            do {
                SensorData sensorData = new SensorData();
                sensorDatas.add(sensorData);

                // recupera os atributos de sensorData
                sensorData.set_id(cursor.getLong(idxId));
                sensorData.setSensorId(cursor.getString(idxSensorId));
                sensorData.setDate(DateUtil.getDate(cursor.getString(idxCreateDate)));
                sensorData.setTemperatura(cursor.getInt(idxName));
                sensorData.setUmidade(cursor.getInt(idxDescription));
            } while (cursor.moveToNext());

        }

        if (cursor != null)
            cursor.close();

        return sensorDatas != null ? sensorDatas : Collections.EMPTY_LIST;
    }

    @Override
    public Cursor retrieveAll() {
        return null;
    }

    /* (non-Javadoc)
     * @see br.com.pressystem.imobiliaria42backoffice.model.repository.dao.ActiveRecord#validate()
     */
    @Override
    public boolean validate() {
        super.validate();

        boolean validated = true;

        // First name - required
        /*if (!this.validatePresenceOf(lastName, COL_LAST_NAME, res
                .getString(R.string.validation_required_field)))
            validated = false;*/

        // Last name - required
        /*if (!this.validatePresenceOf(firstName, COL_FIRST_NAME, res
                .getString(R.string.validation_required_field)))
            validated = false;*/

        // Address - required
       /* if (!this.validatePresenceOf(address, COL_ADDRESS, res
                .getString(R.string.validation_required_field)))
            validated = false;*/

        // City - required
       /* if (!this.validatePresenceOf(city, COL_CITY, res
                .getString(R.string.validation_required_field)))
            validated = false;*/

        // Postal code - required with format
       /* if (this.validatePresenceOf(postalCode, COL_POSTAL_CODE, res
                .getString(R.string.validation_required_field))) {
            if (!this.validateFormatOf(postalCode, res
                    .getString(R.string.regex_postal_code), COL_POSTAL_CODE,
                    res.getString(R.string.validation_postal_code_format)))
                validated = false;
        } else {
            validated = false;
        }*/

        // Day phone - required with format
       /* if (this.validatePresenceOf(dayPhone, COL_DAY_PHONE, res
                .getString(R.string.validation_required_field))) {
            if (!this.validateFormatOf(dayPhone, res
                    .getString(R.string.regex_phone), COL_DAY_PHONE,
                    res.getString(R.string.validation_phone_format)))
                validated = false;
        } else {
            validated = false;
        }*/

        // Evening phone - format
       /* if (eveningPhone.length() > 0) {
            if (!this.validateFormatOf(eveningPhone, res
                    .getString(R.string.regex_phone), COL_EVENING_PHONE,
                    res.getString(R.string.validation_phone_format)))
                validated = false;
        }*/

        // Mobile phone - format
       /* if (mobilePhone.length() > 0) {
            if (!this.validateFormatOf(mobilePhone, res
                    .getString(R.string.regex_phone), COL_MOBILE_PHONE,
                    res.getString(R.string.validation_phone_format)))
                validated = false;
        }*/

        // E-mail address - format
       /* if (emailAddress.length() > 0) {
            if (!this.validateFormatOf(emailAddress, res
                    .getString(R.string.regex_email), COL_EMAIL_ADDRESS,
                    res.getString(R.string.validation_email_format)))
                validated = false;
        }*/

        return validated;
 }
}
