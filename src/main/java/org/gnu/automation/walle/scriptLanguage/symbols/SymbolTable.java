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
	private LinkedHashMap<String, RecordOf> symbolTableProgramAddress = new LinkedHashMap<String, RecordOf>();


	/*
	 * SymbolTable() - Constructor ...
	 */
	public SymbolTable() {
	}
	
	
	/*
	 * addVariable(symbolName,symbolRecordOf) - Add a symbol to Variable's table ...
	 */
	public void addVariable(String symbolName, RecordOf symbolRecordOf ) {
		symbolTableVariable.put(symbolName, symbolRecordOf);
	}
	
	
	/*
	 * addTable(symbolName,symbolTableOf) - Add a symbol to Table's tables...
	 */
	public void addTable(String symbolName, TableOf symbolTableOf ) {
		symbolTableTableOf.put(symbolName, symbolTableOf);
	}
	
	
	/*
	 * ProgramAddress(symbolName,symbolRecordOf) - Add a symbol to ProgramAddress's tables...
	 */
	public void addProgramAddress(String symbolName, RecordOf symbolRecordOf ) {
		symbolTableProgramAddress.put(symbolName, symbolRecordOf);
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
	 * containsKeyVariable() - Check if 'key' is contained in symbolTableVariable ... 
	 * @return boolean
	 */
	public boolean containsKeyVariable(String key) {
		return (symbolTableVariable.containsKey(key));
	}

	
	/*
	 * containsKeyTableOf() - Check if 'key' is contained in symbolTableTableOf ... 
	 * @return boolean
	 */
	public boolean containsKeyTableOf(String key) {
		return (symbolTableTableOf.containsKey(key));
	}
	

	/*
	 * containsKeyProgramAddress() - Check if 'key' is contained in symbolTableProgramAddress ... 
	 * @return boolean
	 */
	public boolean containsKeyProgramAddress(String key) {
		return (symbolTableProgramAddress.containsKey(key));
	}

	
	/*
	 * getValueSymbolTableVariable(key) - Return attribute 'value' of symbol table symbolTableVariable indexed by 'key' ...
	 */
	public String getValueSymbolTableVariable(String key) throws Exception {
		return symbolTableVariable.get(key).get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_VALUE);
	}
	

	/*
	 * getSymbolTypeSymbolTableVariable(key) - Return attribute 'symbolType' of symbol table symbolTableVariable indexed by 'key' ...
	 */
	public String getSymbolTypeSymbolTableVariable(String key) throws Exception {
		return symbolTableVariable.get(key).get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_SYMBOLTYPE);
	}
	
	
	/*
	 * getSymbolTableTableOf(key) - Returns tableOf of 'key' ...
	 */
	public TableOf getSymbolTableTableOf(String key) {
		return symbolTableTableOf.get(key);
	}
	

	/*
	 * getValueSymbolTableProgramAddress(key) - Return attribute of symbol table symbolTableProgramAddress indexed by 'key' ...
	 */
	public String getAttributeSymbolTableProgramAddress(String key, String attribute) throws Exception {
		return symbolTableProgramAddress.get(key).get(attribute);
	}
	
	
	/*
	 * getRecordOfSymbolTableProgramAddress(key) - Get RecordOf a ProgramAddress ...
	 */
	public RecordOf getRecordOfSymbolTableProgramAddress(String key) throws Exception {
		return symbolTableProgramAddress.get(key);
	}
	
	
	/*
	 * setRecordOfSymbolTableProgramAddress(symbolName,symbolRecordOf)
	 */
	public void putRecordOfSymbolTableProgramAddress(String symbolName, RecordOf symbolRecordOf ) {
		symbolTableProgramAddress.put(symbolName, symbolRecordOf);
	}
	

}
