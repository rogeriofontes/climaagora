package climaagora.psi.com.br.climaagora.infra;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * The Class ActiveRecord.
 *
 * @param <T> the generic type
 */
public abstract class ActiveRecord<T> {
	 
 	/** Database object. */
    protected SQLiteDatabase db;
 	
    /** Errors collection - database column name|error message. */
    protected HashMap<String, String> errors = new HashMap<String, String>();
     
    /** Application resources object. */
    protected Resources res;
     
    /**
     * Instantiates a new active record.
     */
    public ActiveRecord() {
    	
    }
     
    /**
     * Save.
     *
     * @param t the t
     * @return the long
     */
    abstract public boolean save(T t);
 
    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     */
    abstract public boolean delete(long id);
 
    /**
     * Load.
     *
     * @param activity the activity
     * @param id the id
     */
    abstract public void load(Activity activity, long id);
    
    /**
     * List.
     *
     * @return the list
     */
    abstract public List<T> list();
 
    /**
     * Retrieve all.
     *
     * @return the cursor
     */
    abstract public Cursor retrieveAll();
    
    /**
     * Gets the errors.
     *
     * @return the errors
     */
    public HashMap<String, String> getErrors() {
        return errors;
    }
     
    /**
     * Validate.
     *
     * @return true, if successful
     */
    public boolean validate() {
        errors.clear();
        return true;
    }
     
    /**
     * Validate presence of.
     *
     * @param value the value
     * @param colName the col name
     * @param msg the msg
     * @return true, if successful
     */
    protected boolean validatePresenceOf(String value, String colName,
            String msg) {
        boolean validated = true;
         
        if (value.length() == 0) {
            errors.put(colName, msg);
            validated = false;
        }
         
        return validated;
    }
     
    /**
     * Validate format of.
     *
     * @param value the value
     * @param regex the regex
     * @param colName the col name
     * @param msg the msg
     * @return true, if successful
     */
    protected boolean validateFormatOf(String value, String regex,
            String colName, String msg) {
        boolean validated = true;
         
        if (!Pattern.matches(regex, value)) {
            errors.put(colName, msg);
            validated = false;
        }
         
        return validated;
    }
     
    /**
     * Validate length of.
     *
     * @param value the value
     * @param min the min
     * @param max the max
     * @param colName the col name
     * @param msg the msg
     * @return true, if successful
     * @throws InvalidParameterException the invalid parameter exception
     */
    protected boolean validateLengthOf(String value, int min, int max,
            String colName, String msg) throws InvalidParameterException {
        boolean minValidated = true;
        boolean maxValidated = true;
 
        // Check for invalid length parameters
        if (min < 0 || max < 0 || min > max)
            throw new InvalidParameterException();
         
        // Check minimum length
        if (value.length() < min) {
            minValidated = false;
        }
         
        // Check maximum length
        if (value.length() > max) {
            maxValidated = false;
        }
             
        // Check the results and return
        if (minValidated && maxValidated) {
            return true;
        } else {
            errors.put(colName, msg);
            return false;
        }
    }
}
