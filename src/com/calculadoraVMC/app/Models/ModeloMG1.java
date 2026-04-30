package src.com.calculadoraVMC.app.Models;
public class ModeloMG1 extends Modelo{

    protected double o; //varianza 

    public ModeloMG1(double l, double m, double o){
        this.l = l; 
        this.m = m; 
        this.o = 0; 
    }

    @Override
    public double P0() {
        return 1-p();
    }

    @Override
    public double Pn(int n) {
        throw new UnsupportedOperationException(
        "Pn no tiene forma cerrada en M/G/1 sin conocer la distribución completa del servicio"
        );
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        return l;
    }

    @Override
    public double L() {
        return p() + Lq();
    }

    @Override
    public double Lq() {
        double p = p(); 
        double p2 = Math.pow(p, 2);
        double m2 = Math.pow(m, 2);

        double result = (p2 * (1 + o * m2)) / (2 * (1 - p)); 

        return result; 
    }

    @Override
    public double w() {
        return (1 / m) + wq(); 
    }

    @Override
    public double wq() {
        double p = p(); 
        double m2 = Math.pow(m, 2);

        double result = (l * ((1 / m2) + o)) / (2 * (1 - p)); 

        return result; 
    }

    @Override
    public double p() {
        return l / m; 
    }

    @Override
    public String menuSecundario() {
        return ""; 
    }

    @Override
    public String nombreModelo(){
        return "Modelo M/G/1 (λ = " + l + ", μ = " + m + ", σ² = " + o + ")";
    }
}