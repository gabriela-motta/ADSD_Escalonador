
public class Fregues implements Comparable<Fregues>{

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

	@Override
	public int compareTo(Fregues freg2) {
		if(this.chegada > freg2.chegada){
			return -1;
		}else if(this.chegada == freg2.chegada){
			return 0;
		}else{
			return 1;
		}
	}
}
