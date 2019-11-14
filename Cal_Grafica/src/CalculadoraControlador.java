public class CalculadoraControlador {
    
    private CalculadoraModelo calcModel;
    public CalculadoraControlador() {
		calcModel = new CalculadoraModelo();
    }
    
    public String[] update(String action) {
		if (action.equals("Graf")) {
			return calcModel.evaluarGraph();
		}
		return calcModel.performAction(action);
    }
}
