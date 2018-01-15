package org.gnu.automation.walle.scriptLanguage.symbols;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;

/*
 * SymbolTable class is responsible for maintain all symbols of script language
 */
public class SymbolTable {
	
	private LinkedHashMap<String, RecordOf> symbolTableVariable = new LinkedHashMap<String, RecordOf>();
	private LinkedHashMap<String, TableOf> symbolTableTableOf = new LinkedHashMap<String, TableOf>();


	/*
	 * SymbolTable() - Constructor ...
	 */
	public SymbolTable() {
	}
	
	
	/*
	 * add(symbolName,symbolRecordOf) - Add to Symble Table a symbol ...
	 */
	public void add(String symbolName, RecordOf symbolRecordOf ) {
		symbolTableVariable.put(symbolName, symbolRecordOf);
	}
	
	
	/*
	 * add(symbolName,symbolTableOf) - Add to symbol to SymbleTable ...
	 */
	public void add(String symbolName, TableOf symbolTableOf ) {
		symbolTableTableOf.put(symbolName, symbolTableOf);
		
	}
	
	
	/*
	 * keySetSymbolTableVariable() - Returns a list with key set of symbolTableVariable
	 */
	public List<String> keySetSymbolTableVariable() {
		List<String> list = new ArrayList<String>(symbolTableVariable.keySet());
		return list;
	}
	

	/*
	 * keySetSymbolTableTableOf() - Returns a list with key set of symbolTableTableOf
	 */
	public List<String> keySetSymbolTableTableOf() {
		List<String> list = new ArrayList<String>(symbolTableTableOf.keySet());
		return list;
	}
	
	
	/*
	 * getValueSymbolTableVariable(key) - Return attribute 'value' of symbol table key ...
	 */
	public String getValueSymbolTableVariable(String key) throws Exception {
		return symbolTableVariable.get(key).get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_VALUE);
	}
	

	/*
	 * getSymbolTypeSymbolTableVariable(key) - Return attribute 'type' of symbol table key ...
	 */
	public String getSymbolTypeSymbolTableVariable(String key) throws Exception {
		return symbolTableVariable.get(key).get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_SYMBOLTYPE);
	}
	

}
