
public class Fregues {

	private int tempoAtendimento;
	private int tempoChegadaSistema;
	public static final int TEMPO_CHEGADA = 10;
	private static int COUNT = 0;
	private int id;
	
	public Fregues(int tempoAtendimento, int tempoChegadaSistema) {
		this.id = COUNT;
		++COUNT;
		this.tempoAtendimento = tempoAtendimento;
		this.tempoChegadaSistema = tempoChegadaSistema;
	}
	
	public int getTempoAtendimento() {
		return tempoAtendimento;
	}
	
	public void setTempoAtendimento(int tempoAtendimento) {
		this.tempoAtendimento = tempoAtendimento;
	}
	
	public int getTempoChegadaSistema() {
		return tempoChegadaSistema;
	}
	
	public void setTempoChegadaSistema(int tempoChegadaSistema) {
		this.tempoChegadaSistema = tempoChegadaSistema;
	}
	
	@Override
	public String toString() {
		return "C1#" + id;
	}	
}
