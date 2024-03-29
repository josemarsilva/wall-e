package org.gnu.automation.walle.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Title:        TableOf
 * 
 * Description:  TableOf is an abstraction of a collection of Record Of
 * 
 * Author:       Josemar Silva
 * 
 */
public class TableOf {
	
//	final static Logger logger = Logger.getLogger(TableOf.class);
	
	private List<RecordOf> rows = new ArrayList<RecordOf>();
	
	/**
	 * Construtor
	 */
	public TableOf() {
		rows = new ArrayList<RecordOf>();
	}

	/**
	 * add() - Add a 'recordOf' to rows of 'tableOf'
	 */
	public void add(RecordOf recordOf) throws Exception {
//		logger.debug("add( )");
		rows.add(recordOf);
	}
	
	/**
	 * Get object RecordOf
	 * @param pos
	 * @return
	 */
	public RecordOf get(int pos) {
		if (size() > 0) {
			return rows.get(pos);
		}
		return null;
	}
	
	/**
	 * export(exportFileName) - Export 'table of' contents to a file
	 * @throws Exception 
	 */
	public void export(String exportFileName) throws Exception{
//		logger.info("export( '"+exportFileName+"' )");
		
		// New export file
		File exportFile = new File(exportFileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile));

		// Is there rows in table?
		if (!rows.isEmpty()) {

			// Get first record columns names
			RecordOf firstRecordOf = rows.get(0);

			// Print .csv headers to file ...
			String csvTitleRow = new String("");
			for (String key : firstRecordOf.getListOfKeys()) {
				csvTitleRow = csvTitleRow + "\"" + key + "\";";
			}
			writer.write(csvTitleRow + "\n");

			// Loop all rows of table
			for (RecordOf record : rows) {
				String csvRow = new String("");
				for (String key : firstRecordOf.getListOfKeys()) {
					csvRow = csvRow + "\"" + record.get(key) + "\";";
				}
				writer.write(csvRow + "\n");
			}
		}

		// Close file
		writer.close();

	}

	/**
	 * size() - Retrn size of 'table of'
	 * @throws Exception 
	 */
	public int size() {
		return rows.size();
	}


}
