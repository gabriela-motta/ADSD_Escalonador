
public class Evento {

	private TipoEvento tipo;
	private Fregues fregues;
	
	public Evento(TipoEvento tipo, Fregues fregues) {
		this.tipo = tipo;
		this.fregues = fregues;
	}
	
	public TipoEvento getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	
	public Fregues getFregues() {
		return fregues;
	}
	
	public void setFregues(Fregues fregues) {
		this.fregues = fregues;
	}

	@Override
	public String toString() {
		switch (tipo) {
		case CHEGADA_FREGUES:
			return "Chegada";
		default:
			return "Saída";
		}
	}
	
}
