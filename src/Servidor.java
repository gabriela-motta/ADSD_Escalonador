
public class Servidor {
	
	private Fila fila;
	private Fregues atendendo;
	
	public Servidor(){
		fila = new Fila();
	}
	
	public boolean isOcupado() {
		return atendendo != null;
	}

	public Fila getFila() {
		return fila;
	}

	public void setFila(Fila fila) {
		this.fila = fila;
	}

	public Fregues getAtendendo() {
		return atendendo;
	}

	public void setAtendendo(Fregues atendendo) {
		this.atendendo = atendendo;
	}

}
