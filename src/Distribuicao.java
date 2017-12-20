import org.apache.commons.math3.distribution.*;
public class Distribuicao {

	public TipoDistribuicao tipo;
	public int[] params;
	
	public Distribuicao(TipoDistribuicao tipo, int[] params) {
		this.tipo = tipo;
		this.params = params;
	}

	public int gerar(){
		int dist = 0;
		if (this.tipo.equals(TipoDistribuicao.UNIFORME)){
			dist = new UniformIntegerDistribution(params[0], params[1]).sample();
		} else if(this.tipo.equals(TipoDistribuicao.EXPONENCIAL)){
			dist = (int) new ExponentialDistribution(params[0]).sample();
		}else if(this.tipo.equals(TipoDistribuicao.NORMAL)){
			dist = (int) new NormalDistribution(this.params[0], this.params[1]).sample();
		}
		return dist;
	}
}
