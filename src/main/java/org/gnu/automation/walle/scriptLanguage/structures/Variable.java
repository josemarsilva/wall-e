package org.gnu.automation.walle.scriptLanguage.structures;

import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.scriptLanguage.structures.StructuresConstants;

/*
 * Variable class is responsible to represent a *variable* structure in script
 * language.
 * 
 * @author josemarsilva
 * 
 */
public class Variable {

	private RecordOf properties = new RecordOf();
	
	public void SetValue(String value) {
				properties.set(org.gnu.automation.walle.scriptLanguage.structures.StructuresConstants.STRUCTURE_VARIABLE_PROPERTY_VALUE, value);		
	}

	public String GetValue() throws Exception {
		return properties.get(org.gnu.automation.walle.scriptLanguage.structures.StructuresConstants.STRUCTURE_VARIABLE_PROPERTY_VALUE);
	}
}
