
public class Fregues {

	private double chegada;
	private double termino;
	
	public Fregues(double momento) {
		this.chegada = momento;
		this.termino = -1;
	}
	
	public void finalizar(double termino){
		this.termino = termino;
	}

	public double getTempoAtendido() {
		return this.termino - this.chegada;
	}
}
