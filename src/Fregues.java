
public class Fregues {

	private Long chegada;
	private Long termino;
	
	public Fregues() {
		this.chegada = System.currentTimeMillis();
		this.termino = (long) -1;
	}
	
	public void finalizar(){
		this.termino = System.currentTimeMillis();
	}	
}
