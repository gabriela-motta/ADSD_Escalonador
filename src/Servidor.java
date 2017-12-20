
public class Servidor {
	
	private boolean estado;
	
	public Servidor(){
		this.estado = true;
	}
	
	public boolean isLivre() {
		return estado;
	}
	
	public void liberar(){
		this.estado = true;
	}
	
	public void ocupar(){
		this.estado = false;
	}

}
