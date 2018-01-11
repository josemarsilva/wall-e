package org.gnu.automation.walle.scriptLanguage.structures;

import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;

/*
 * Table class is responsible to represent a *table* structure in script
 * language.
 * 
 * @author josemarsilva
 * 
 */
public class Table {

	private TableOf table = new TableOf();
	
	public void add(RecordOf recordOf) throws Exception {
		table.add(recordOf);
	}
	
	public RecordOf get(int index) {
		return table.get(index);
	}

}
