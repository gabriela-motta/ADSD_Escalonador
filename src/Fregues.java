
public class Fregues {

	private double chegada;
	private double termino;
	
	public Fregues() {
		this.chegada = System.currentTimeMillis();
		this.termino = -1;
	}
	
	public void finalizar(){
		this.termino = System.currentTimeMillis();
	}	
	
	public void finalizar(double termino){
		this.termino = termino;
	}

	public double getTempoAtendido() {
		return this.termino - this.chegada;
	}
}
