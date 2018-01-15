package org.gnu.automation.walle.scriptLanguage.expressions;

import java.util.List;

import org.gnu.automation.walle.pageObjects.WebPage;
import org.gnu.automation.walle.scriptLanguage.symbols.SymbolTable;

/*
 * ExpressionEvaluator class is responsible for variable replacement for 
 * respective contents and expression evaluation 
 */
public class ExpressionEvaluator {
	
	// properties ...
	
	SymbolTable symbolTable;
	WebPage webPage;
	
	public ExpressionEvaluator(SymbolTable st, WebPage wp) {
		this.symbolTable = st;
		this.webPage = wp;
	}

	/*
	 * evaluate(expression) - return string value result for expression evaluation ...
	 */
	public String evaluate(String expression) throws Exception {
		String expressionEvaluated = new String(expression);
		if (expression!=null) {
			if (!expression.equals("")) {
				List<String> keySetVariable = symbolTable.keySetSymbolTableVariable();
				for (int i=0;i<keySetVariable.size();i++) {
					if (!keySetVariable.get(i).equals("")) {
						String replacement = new String(keySetVariable.get(i));
						if (symbolTable.getSymbolTypeSymbolTableVariable(keySetVariable.get(i)).equals(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_NAME_LASTWEBELEMENTFOUND)) {
							replacement = webPage.getText();
						} else {
							replacement = symbolTable.getValueSymbolTableVariable(keySetVariable.get(i));
						}
						// variables replacement on expression ...
						expressionEvaluated = expressionEvaluated.replaceAll( (keySetVariable.get(i)).replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]"), replacement);
					}
				}
			}
		}
		// return untouched expression ...
		return expressionEvaluated;
	}
	

}
