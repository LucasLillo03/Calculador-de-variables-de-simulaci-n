package src.com.calculadoraVMC.app.Models;
public class ModeloMGServidoresInfinitos extends Modelo {

    private final double e = 2.71828;

    public ModeloMGServidoresInfinitos(double l, double m) {
        this.l = l; 
        this.m = m;
    }

    @Override
    public double P0() {
        return Math.pow(e, (-l / m));
    }

    @Override
    public double Pn(int n) {
        return  (this.P0() * Math.pow((l / m), n)) / factorial(n);
    }

    @Override
    public double L() {
        return l / m;
    }

    @Override
    public double Lq() {
        return 0; 
    }

    @Override
    public double w() {
        return 1 / m;
    }

    @Override
    public double wq() {
        return 0; 
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        return l;
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
        return "Modelo M/G/∞ (λ = " + l + ", μ = " + m + ")";
    }

}
