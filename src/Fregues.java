
public class Fregues {

	private int chegada;
	private int termino;
	
	public Fregues() {
		this.chegada = (int) System.currentTimeMillis();
		this.termino = -1;
	}
	
	public void finalizar(){
		this.termino = (int) System.currentTimeMillis();
	}	
	
	public void finalizar(int termino){
		this.termino = termino;
	}

	public int getTempoAtendido() {
		return this.termino - this.chegada;
	}
}
