import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Escalonador implements Runnable {

	private static int UNIDADE_TEMPO = 100; // ms
	private int contador;
	private Map<Integer, List<Evento>> agenda;
	private Servidor servidor;

	public Escalonador() {
		this.servidor = new Servidor();
		this.agenda = new HashMap<>();
		agendaEventoChegadaFregues(0);
	}

	private void agendaEvento(int tempo, Evento ev) {
		if (!agenda.containsKey(contador + tempo)) {
			agenda.put(contador + tempo, new ArrayList<>());
		}
		agenda.get(contador + tempo).add(ev);
	}

	private void agendaEventoChegadaFregues(int tempo) {
		Random rnd = new Random();
		int tempoAtendimento = rnd.nextInt(5) + 3;
		Fregues fregues = new Fregues(tempoAtendimento, contador + tempo);
		Evento ev = new Evento(TipoEvento.CHEGADA_FREGUES, fregues);
		agendaEvento(tempo, ev);
	}

	private void agendaEventoTermino(Fregues fregues) {
		Evento newEv = new Evento(TipoEvento.TERMINO, fregues);
		agendaEvento(fregues.getTempoAtendimento(), newEv);
	}

	private void printEstado(Evento ev) {
		int tempoExecucao;
		if (ev.getTipo() == TipoEvento.CHEGADA_FREGUES) {
			tempoExecucao = ev.getFregues().getTempoAtendimento();
		} else {
			tempoExecucao = contador - ev.getFregues().getTempoChegadaSistema();
		}
		System.out.println("Tipo de evento: " + ev + ", Tempo para execução: " + tempoExecucao);
		System.out.println(
				"Elementos na Fila 1: " + servidor.getFila() + ", Elemento no serviço: " + servidor.getAtendendo());
	}

	private void executaEvento(Evento ev) {
		Fregues fregues;
		switch (ev.getTipo()) {
		case CHEGADA_FREGUES:
			if (servidor.isOcupado()) {
				servidor.getFila().adiciona(ev.getFregues());
			} else {
				servidor.setAtendendo(ev.getFregues());
				agendaEventoTermino(ev.getFregues());
			}
			agendaEventoChegadaFregues(Fregues.TEMPO_CHEGADA);
			break;
		case TERMINO:
			if (servidor.getFila().isVazia()) {
				servidor.setAtendendo(null);
			} else {
				fregues = servidor.getFila().remove();
				servidor.setAtendendo(fregues);
				agendaEventoTermino(fregues);
			}
		}
		printEstado(ev);
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (agenda.containsKey(contador)) {
					List<Evento> eventos = agenda.get(contador);
					for (Evento ev : eventos) {
						executaEvento(ev);
					}
					agenda.remove(contador);
				}
				Thread.sleep(UNIDADE_TEMPO);
				++contador;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public static void main(String[] args) {
		Thread t = new Thread(new Escalonador());
		t.start();
	}

}
