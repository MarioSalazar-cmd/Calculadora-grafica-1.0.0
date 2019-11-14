import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import java.util.*;



public class CalculadoraModelo {
	private ArrayList<String> currentEq;
	private ScriptEngine solver;
	private int tamaño;


	public CalculadoraModelo() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		solver = mgr.getEngineByName("JavaScript");
		currentEq = new ArrayList<String>();
		tamaño = 0;
	}

	
	public String[] performAction(String action) {
		
		String[] finalEquation = new String[2];
		
		
		if (action.equals("Enter")) {
			return this.evaluar();
		}	
		
		// operadores basicos
		else if (action.equals("+")) {
			this.suma();
		}
		else if (action.equals("-")) {
			this.menos();
		}
		else if (action.equals("*")) {
			this.veces();
		}
		else if (action.equals("/")) {
			this.dividir();
		}
		else if (action.equals("(-)")) {
			this.negativo();
		}
		
		// parentesis
		else if (action.equals("(")) {
			this.abrirParen();
		}
		else if (action.equals(")")) {
			this.cerrarParen();
		}
		
		// trigonometricas
		else if (action.equals("sin()")) {
			this.sin();
		}
		else if (action.equals("cos()")) {
			this.cos();
		}
		else if (action.equals("tan()")) {
			this.tan();
		}
		else if (action.equals("pi")) {
			this.pi();
		}
		
		// logaritmos
		else if (action.equals("ln()")) {
			this.ln();
		}
		else if (action.equals("e")) {
			this.e();
		}
		
		// potencias
		else if (action.equals("x^2")) {
			this.Cuadrado();
		}
		else if (action.equals("sqrt")) {
			this.raizC();
		}
		else if (action.equals("^")) {
			this.potencia();
		}
		
		// borrar
		else if (action.equals("Delete")) {
			this.delete();
		}
		// limpiar
		else if (action.equals("Clear")) {
			this.clear();
		}
		else if (action.equals("<html>"+"Clear"+"<br>"+"All"+"<html>")) {
			this.clear();
		}
		else if (action.equals("<html>"+"Clear"+"<br>"+"Graph"+"<html>")) {
			this.clear();
		}
		// punto dec
		else if (action.equals(".")) {
			this.decimal();
		}
		// digitos
		else if (action.matches("[0-9]")) {
			this.numero(action);
		}
		// variable x
		else if (action.matches("x")) {
			this.numero(action);
		}
		
		// retorna la forma de la ecuacion para mostrar
		finalEquation[0]=equationToNiceForm(this.copiarEquacion());
		return finalEquation;
	}
	
	public void clear() {
		currentEq = new ArrayList<String>();
		tamaño = 0;
	}
	
	
	public void delete() {
		if (currentEq.size() > 0) {
			String temp = currentEq.get(tamaño-1);
			// solo 1 digito se borra
			if (esNum(temp)) {
				if (temp.length()>1) {
					currentEq.set(tamaño-1, temp.substring(0,temp.length()-1));
				} else {
					currentEq.remove(tamaño-1);
					tamaño--;
				}
			} 
			// la funcion potencia entera se borra
			else if (temp.charAt(0)==',') {
				currentEq.remove(tamaño-1);
				int i = 2;
				while (!currentEq.get(tamaño-i).equals("Math.pow(")) {
					i++;
				}
				currentEq.remove(tamaño-i);
				tamaño-=2;
			} 
			// borra lo mas reciente que se añadio
			else {
				currentEq.remove(tamaño-1);
				tamaño--;
			}
		}
	}

	/**
	 * agrega digitos a la ecuacion
	 */
	public void numero(String num) {
		if (tamaño>0) {
			String temp = currentEq.get(tamaño-1);
			if (esNum(temp)) {
				currentEq.set(tamaño-1, temp+num);
			} else {
				currentEq.add(num);
				tamaño++;
			}
		} else {
			currentEq.add(num);
			tamaño++;
		}
	}
	
	/**
	 * agrega el punto decimal
	 */
	public void decimal() {
		if (tamaño>0) {
			String temp = currentEq.get(tamaño-1);
			if (esNum(temp)) {
				currentEq.set(tamaño-1, temp+".");
			} else {
				currentEq.add(".");
				tamaño++;
			}
		} else {
			currentEq.add(".");
			tamaño++;
		}
	}

	
	/**
	 * operadores basicos
	 */
	
	public void suma() {
		currentEq.add("+");
		tamaño++;
	}
	public void menos() {
		currentEq.add("-");
		tamaño++;
	}
	public void veces() {
		currentEq.add("*");
		tamaño++;
	}
	public void dividir() {
		currentEq.add("/");
		tamaño++;
	}
	public void negativo() {
		currentEq.add("(-1)*");
		tamaño++;
	}

	/**
	 * funciones de parentesis
	 */ 
	public void abrirParen() {
		currentEq.add("(");
		tamaño++;
	}
	public void cerrarParen() {
		currentEq.add(")");
		tamaño++;
	}

	
	/**
	 * funciones trigo
	 */
	
	public void sin() {
		currentEq.add("Math.sin(");
		tamaño++;
	}
	public void cos() {
		currentEq.add("Math.cos(");
		tamaño++;
	}
	public void tan() {
		currentEq.add("Math.tan(");
		tamaño++;
	}
	public void pi() {
		currentEq.add("Math.PI");
		tamaño++;
	}

	
	/**
	 * funciones algoritmicas
	 */
	public void ln() {
		currentEq.add("Math.log(");
		tamaño++;
	}
	public void e() {
		currentEq.add("Math.E");
		tamaño++;
	}
	
	
	/**
	 * agregar la var X
	 */
	public void x() {
		currentEq.add("x");
		tamaño++;
	}

	/**
	 * la funcion de Raiz Cuadrada
	 */
	public void raizC() {
		currentEq.add("Math.sqrt(");
		tamaño++;
	}
	
	/**
	 * funcion cuadrado
	 */
	public void Cuadrado() {
		if (tamaño>0) {
			String temp = currentEq.get(tamaño-1);
			Stack<String> tempStack = new Stack<String>();
			if (esNum(temp) || temp.equals("x")) {
				currentEq.add(tamaño-1, "Math.pow(");
				currentEq.add(",2)");
				tamaño+=2;
			} else if (temp.equals(")")) {
				tempStack.push(")");
				int i = 2;
				while (!tempStack.empty()) {
					String temp2 = currentEq.get(tamaño-i);
					if (temp2.equals(")")) {
						tempStack.push(")");
					} else if (temp2.equals("(")) {
						tempStack.pop();
					} else if (temp2.matches("Math.+[(]")) {
						tempStack.pop();
					}
					i ++;
				}
				currentEq.add(tamaño-i, "Math.pow(");
				currentEq.add(",2)");
				tamaño+=2;
			} else if (temp.matches("Math.(E|(PI))")) {
				currentEq.add(tamaño-1, "Math.pow(");
				currentEq.add(",2)");
				tamaño+=2;
			}
		}
	}
	

	public void potencia() {
		if (tamaño>0) {
			String temp = currentEq.get(tamaño-1);
			Stack<String> tempStack = new Stack<String>();
			if (esNum(temp) || temp.equals("x")) {
				currentEq.add(tamaño-1, "Math.pow(");
				currentEq.add(",");
				tamaño+=2;
			} else if (temp.equals(")")) {
				tempStack.push(")");
				int i = 2;
				while (!tempStack.empty()) {
					String temp2 = currentEq.get(tamaño-i);
					if (temp2.equals(")")) {
						tempStack.push(")");
					} else if (temp2.equals("(")) {
						tempStack.pop();
					} else if (temp2.matches("Math.+[(]")) {
						tempStack.pop();
					}
					i ++;
				}
				i--;
				currentEq.add(tamaño-i, "Math.pow(");
				currentEq.add(",");
				tamaño+=2;
			} else if (temp.matches("Math.(E|(PI))")) {
				currentEq.add(tamaño-1, "Math.pow(");
				currentEq.add(",");
				tamaño+=2;
			}
		}
	}

	/**
	 * conseguir la copia de la eq actual 
	 * retorna una ArrayList de String que representa la ecuacion
	 */
	public ArrayList<String> copiarEquacion() {
		return (ArrayList<String>) currentEq.clone();
	}

	public String[] evaluar() {
		String[] evaluatedEquation = new String[2];
		

		String displayableEquation = equationToNiceForm(copiarEquacion());
		evaluatedEquation[0] = displayableEquation;
		
		String readableEquation = javascriptEquation();
		String fixedParen = revisionParetesis(readableEquation);
		
		// Redondear a 6 deciamles
		String withRounding = "Math.round(1000000*(" + fixedParen + "))/1000000";
		String tempSolucion = "";
		
		// se evalua la ecuacion por errores
		try {
			tempSolucion = solver.eval(withRounding).toString();
		} catch (Exception e) {
			tempSolucion = "Error";
		}
		evaluatedEquation[1] = tempSolucion;
		
		// resetea la equacion a 0
		currentEq = new ArrayList<String>();
		tamaño=0; 
		return evaluatedEquation;
	}
	
	/**
	 * Evaluates the equation over the range x = -10 to x = 10, 
	 * and returns an Array of Strings which contain the y-values 
	 * for the graph.
	 */
	public String[] evaluarGraph() {
		String[] solucionArray = new String[600];
		
		// Gets the equation to a form that the ScriptEngine can read
		String readableEquation = javascriptEquation();
		String fixedParen = revisionParetesis(readableEquation);
		String scaledEquation = "30*(" + fixedParen + ")";
		
		for (int i =-300; i<300; i++) {
			// escala la x para que entre en una matriz 20*20
			double scaleFactor = i*1/30.0;
			
			// resuelve la ecuacion
			String graphedEq = remplazarx(scaledEquation, Double.toString(scaleFactor));
			String tempSolution = "";
			try {
				tempSolution = solver.eval(graphedEq).toString();
			} catch (Exception e) {
				tempSolution = "0";
			}
			solucionArray[i+300] = tempSolution;
		}
		
		// resetea la ecuacion
		currentEq = new ArrayList<String>();
		tamaño=0; 
		return solucionArray;
	}
	

	private String remplazarx(String equation, String num){
		String output = new String(equation);
		
		
		for (int i=0;i<output.length();i++){
			if (output.charAt(i) == 'x'){
				String firstPart = output.substring(0, i);
				String secondPart = output.substring(i+1);
				output = "";
				output = output.concat(firstPart);
				output = output.concat(num);
				output = output.concat(secondPart);
			}	
		}
		return output;
	}


	private boolean esNum(String nm) {
		if (nm.matches("[0-9]+.?[0-9]*")) {
			return true;
		} 
		return false;
	}
	

	private String javascriptEquation() {
		String currentEquation = "";
		for (int i = 0; i<tamaño; i++) {
			if (i<tamaño-1) {
				if (esNum(currentEq.get(i)) && currentEq.get(i+1).matches("Math.+")) {
					currentEq.add(i+1, "*");
					tamaño++;
				}
			}
			currentEquation += currentEq.get(i);
		}
		return currentEquation;
	}
	

	private String equationToNiceForm(ArrayList<String> eq) {
		String currentEquation = "";
		for (int i = 0; i<eq.size(); i++) {
			if (i<eq.size()-1) {
				if (esNum(eq.get(i)) && eq.get(i+1).matches("Math.+")) {
					eq.add(i+1, "*");
				}
			} if (eq.get(i).equals("Math.pow(")) {
				eq.remove(i);
			} if (eq.get(i).matches("Math.+")) {
				String replace = eq.get(i).substring(5);
				eq.set(i, replace);
			} if (eq.get(i).equals(",")) {
				eq.set(i, "^(");
			} if (eq.get(i).equals(",2)")) {
				eq.set(i, "^2");
			} if (eq.get(i).equals("(-1)*")) {
				eq.set(i, "(-)");
			} 
			currentEquation += eq.get(i);
		}
		return currentEquation;
	}
	

	private String revisionParetesis(String checkedEq) {
		String withParens = new String(checkedEq);
		Stack<String> parenStack = new Stack<String>();
		for (int i=0; i<checkedEq.length(); i++) {
			if (withParens.charAt(i) == '(') {
				parenStack.push("Fuera de los Margenes");
			}
			if (withParens.charAt(i) == ')' && !parenStack.empty()) {
				parenStack.pop();
			}
		}
		while (!parenStack.empty()) {
			withParens += ")";
			parenStack.pop();
		}
		return withParens;
	}
}
