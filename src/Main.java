import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Simulador sim = new Simulador(30, 10, 1, new double[]{0.5, 0.7}, TipoDistribuicao.NORMAL);
		
		try {
			FileWriter fw = new FileWriter("resultados_normal.txt");
			fw.write(sim.run());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
