import java.util.Queue;

public class Simulador {

	private int quantRepeticoes;        
	private int duracaoSimulacao;
	private int tempoMedioServico;
	private int reqRecebidas;
	private Distribuicao dist;
	
	
	public Simulador(Queue<Fregues> fila, int quantRepeticoes, int duracaoSimulacao, int tempoMedioServico,
			int[] paramsDistribuicao, TipoDistribuicao tipoDistribuicaoChegada) {
		this.quantRepeticoes = quantRepeticoes;
		this.duracaoSimulacao = duracaoSimulacao;
		this.tempoMedioServico = tempoMedioServico;
		this.reqRecebidas = 0;
		this.dist = new Distribuicao(tipoDistribuicaoChegada, paramsDistribuicao);
	}
	
	public void run(){
		for(int i = 0; i<this.quantRepeticoes; i++){
			Servidor serv = new Servidor();
			Escalonador esc = new Escalonador(this.tempoMedioServico, serv);
			double tempoInicio = System.currentTimeMillis();
			int proximaChegada = (int) (System.currentTimeMillis() + this.dist.gerar());
			int proximoTermino = 0;
			while(!this.fimExecucao(tempoInicio)){
				System.out.println(System.currentTimeMillis() - tempoInicio + " " + this.reqRecebidas);
				if(System.currentTimeMillis() >= proximoTermino){
					serv.liberar();
				}
				if(serv.isLivre()){
					if(!esc.filaIsEmpty()){
						proximoTermino = (int) esc.atenderFregues();
						System.out.println("free, prox termino: " + (proximoTermino - tempoInicio));
					}else if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						proximaChegada = (int) (System.currentTimeMillis() +  this.dist.gerar());
						proximoTermino = (int) esc.escalonar(freg);
						System.out.println("free, chegou fregues! prox termino: " + (proximoTermino - tempoInicio) + "prox chegada: " + (proximaChegada - tempoInicio));
					}
				}else{
					if(this.chegouFregues(proximaChegada)){
						Fregues freg = new Fregues();
						esc.enfileirar(freg);
						System.out.println("not free, enfileirar cliente");
					}
				}
			}
		}
	}
	
	public boolean fimExecucao(double tempoInicio){
		double delta = System.currentTimeMillis() - tempoInicio;
		return delta > this.duracaoSimulacao;
	}

	public boolean chegouFregues(int proximaChegada){
		if(System.currentTimeMillis() >= proximaChegada){
			this.reqRecebidas++;
			return true;
		}
		return false;
	}
}
