package org.gnu.automation.walle.scriptLanguage.symbols;

import java.util.LinkedHashMap;

import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;

/*
 * SymbolTable class is responsible for maintain all symbles of script language
 */
public class SymbolTable {
	
	private LinkedHashMap<String, String> symbleTableVariable = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, TableOf> symbleTableTableOf = new LinkedHashMap<String, TableOf>();


	/*
	 * SymbolTable() - Constructor ...
	 */
	public SymbolTable() {
	}
	
	
	/*
	 * add(symbolType,symbolName,symbolRecordOf) - Add to Symble Table a symble ...
	 */
	public void add(String symbolName, int symbolType, RecordOf symbolRecordOf ) {
		
	}
	
	
	/*
	 * add(symbolType,symbolName,symbolTableOf) - Add to symble to SymbleTable ...
	 */
	public void add(String symbolName, int symbolType, TableOf symbolTableOf ) {
		
	}


}
