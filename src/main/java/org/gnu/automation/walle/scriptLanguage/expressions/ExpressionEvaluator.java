package org.gnu.automation.walle.scriptLanguage.expressions;

import org.gnu.automation.walle.scriptLanguage.symbols.SymbolTable;

/*
 * ExpressionEvaluator class is responsible for variable replacement for 
 * respective contents and expression evaluation 
 */
public class ExpressionEvaluator {
	
	// properties ...
	
	SymbolTable symbolTable;
	
	public ExpressionEvaluator(SymbolTable s) {
		this.symbolTable = s;
	}

	/*
	 * evaluate(expression) - return string value result for expression evaluation ...
	 */
	public String evaluate(String expression) {
		if (expression!=null) {
			if (!expression.equals("")) {
					return variableReplacement(expression);
			}
		}
		// return untouched expression ...
		return expression;
	}
	
	/*
	 * 
	 */
	private String variableReplacement(String expression) {
		
		// return untouched expression ...
		return expression;
	}

}
