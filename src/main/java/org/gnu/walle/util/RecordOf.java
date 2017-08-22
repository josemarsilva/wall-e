package org.gnu.walle.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Title:        RecordOf
 * 
 * Description:  RecordOf is an abstraction of a record  of
 * 
 * Author:       Josemar Silva - josemarsilva@inmetrics.com.br
 * 
 */
public class RecordOf {
	
//	final static Logger logger = Logger.getLogger(RecordOf.class);
	
	private Map<String, String> recordOfKeyValue;
	
	private List<String> listOfKeys = null;
	
	/**
	 * Construtor
	 */
	public RecordOf() {
		recordOfKeyValue = new HashMap<String,String>();
		listOfKeys = new ArrayList<String>();
	}
	
	/**
	 * get() - Get the value of a RecordOf key
	 */
	public String get(String key) throws Exception {
//		logger.debug("get('"+key+"')");
		return recordOfKeyValue.get(key);
	}
	
	/**
	 * set() - Set the value of a RecordOf key
	 */
	public void set(String key, String value) {
//		logger.debug("set('"+key+"', '"+value+"' )");
		recordOfKeyValue.put(key, value);
		listOfKeys.add(key);
	}
	
	
	/**
	 * getListOfKeys - Return List of keys sort by add order 
	 * @return
	 */
	public List<String> getListOfKeys() {
		return listOfKeys;
	}

}
