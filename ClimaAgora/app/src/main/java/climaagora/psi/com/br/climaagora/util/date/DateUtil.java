package climaagora.psi.com.br.climaagora.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rogeriofontes on 1/17/15.
 */
public class DateUtil {

    final static SimpleDateFormat parserWithHour = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    final static SimpleDateFormat parserWithOutHour = new SimpleDateFormat("dd/MM/yyyy");

    public static String getStringFormat(Date date) {
      return parserWithHour.format(date);
    }

    public static Date getDate(String date){
        Date result = null;

        try {
            result = parserWithHour.parse(date);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public static String getDate(Date date){
        return parserWithHour.format(date);
    }

    public static String getStringFormatWithOutHour(Date date) {
        return parserWithOutHour.format(date);
    }

    public static Date getDateFormatWithOutHour(String date){
        Date result = null;

        try {
            result = parserWithOutHour.parse(date);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }
}
