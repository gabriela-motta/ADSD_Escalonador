import org.apache.commons.math3.distribution.*;
public class Distribuicao {

	public TipoDistribuicao tipo;
	public double media;
	public double[] params;
	
	public Distribuicao(TipoDistribuicao tipo, double media, double[] params) {
		this.tipo = tipo;
		this.media = media;
		this.params = params;
	}

	public double gerar(){
		double dist = 0;
		if (this.tipo.equals(TipoDistribuicao.UNIFORME)){
			dist = new UniformRealDistribution(this.params[0], this.params[1]).sample();
		} else if(this.tipo.equals(TipoDistribuicao.EXPONENCIAL)){
			dist = new ExponentialDistribution(this.params[0]).sample();
		}else if(this.tipo.equals(TipoDistribuicao.NORMAL)){
			dist = new NormalDistribution(this.params[0], this.params[1]).sample();
		}
		return dist;
	}
}
