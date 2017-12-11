import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Escalonador {

	private int contador;
	private Map<Integer, List<Evento>> agenda;
	private Servidor servidor;

	public Escalonador() {
		this.servidor = new Servidor();
		this.agenda = new HashMap<>();
	}

	private void agendaEvento(int tempo, Evento ev) {
		if (!agenda.containsKey(contador + tempo)) {
			agenda.put(contador + tempo, new ArrayList<>());
		}
		agenda.get(contador + tempo).add(ev);
	}

	private void agendaEventoChegadaFreguesUniforme(int tempo) {
		Random rnd = new Random();
		int tempoAtendimento = rnd.nextInt();
		Fregues fregues = new Fregues(tempoAtendimento, contador + tempo);
		Evento ev = new Evento(TipoEvento.CHEGADA_FREGUES, fregues);
		agendaEvento(tempo, ev);
	}
	
	private void agendaEventoChegadaFreguesExponencial(int tempo, int lambda) {
		Random rnd = new Random();
		double tempoAtendimento = Math.log(1-rnd.nextDouble())/(-lambda);
		Fregues fregues = new Fregues((int) tempoAtendimento, contador + tempo);
		Evento ev = new Evento(TipoEvento.CHEGADA_FREGUES, fregues);
		agendaEvento(tempo, ev);
	}
	
	private void agendaEventoChegadaFreguesNormal(int tempo) {
		Random rnd = new Random();
		double tempoAtendimento = rnd.nextGaussian();
		Fregues fregues = new Fregues((int) tempoAtendimento, contador + tempo);
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
			agendaEventoChegadaFreguesUniforme(Fregues.TEMPO_CHEGADA);
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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Tipo de distribuicao: ");
		String distribuicao = sc.nextLine();

		System.out.println("Valor medio tempo de servico: ");
		int valorMedio = sc.nextInt();
		sc.nextLine();

		System.out.println("Duracao da simulacao: ");
		int duracaoSimulacao = sc.nextInt();
		sc.nextLine();

		System.out.println("Quantidade de repeticoes: ");
		int quantRepeticoes = sc.nextInt();
		sc.nextLine();

		for (int i = 0; i < quantRepeticoes; i++) {
			Escalonador esc = new Escalonador();
			if (distribuicao.equals("uniforme")) {
				System.out.println("Parametro a: ");
				int a = sc.nextInt();
				sc.nextLine();
				System.out.println("Parametro b: ");
				int b = sc.nextInt();
				sc.nextLine();
				esc.agendaEventoChegadaFreguesUniforme(duracaoSimulacao);
			} else if (distribuicao.equals("exponencial")) {
				System.out.println("Parametro lambda: ");
				int lambda = sc.nextInt();
				sc.nextLine();
				esc.agendaEventoChegadaFreguesExponencial(duracaoSimulacao, lambda);
			} else if (distribuicao.equals("normal")) {
				System.out.println("Media: ");
				int media = sc.nextInt();
				sc.nextLine();
				System.out.println("Desvio padrao: ");
				int desvio = sc.nextInt();
				sc.nextLine();
				esc.agendaEventoChegadaFreguesNormal(duracaoSimulacao);
			} else {
				System.out.println("Distribuicao invalida");
			}
		}

		sc.close();

	}

}
