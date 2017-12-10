import java.util.LinkedList;
import java.util.Queue;

public class Fila {

	private Queue<Fregues> fila;
	
	public Fila() {
		fila = new LinkedList<>();
	}
	
	public void adiciona(Fregues fregues) {
		fila.add(fregues);
	}
	
	public Fregues remove() {
		return fila.remove();
	}
	
	public boolean isVazia() {
		return fila.isEmpty();
	}
	
	@Override
	public String toString() {
		return fila.toString();
	}
	
}
