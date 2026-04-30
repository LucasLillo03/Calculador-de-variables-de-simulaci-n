package src.com.calculadoraVMC.app.Models;
public class ModeloMM1 extends Modelo {

    public ModeloMM1(double l, double m) {
        this.l = l;
        this.m = m;
    }

    @Override
    public double P0() {
        return 1 - p(); 
    }

    @Override
    public double Pn(int n) {
        double p = p(); 

        return (1 - p) * Math.pow(p, n); 
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        return l;
    }

    @Override
    public double L() {
        return l/(m-l); 
    }

    @Override
    public double Lq() {
        return Math.pow(l, 2) / (m * (m - l));
    }

    @Override
    public double w() {
        return 1 / (m - l);
    }

    @Override
    public double wq() {
        return l / (m * (m - l));
    }

    @Override
    public double p() {
        return l/m; 
    }

    @Override
    public String nombreModelo() {
        return "Modelo M/M/1 (λ = " + l + ", μ = " + m + ")";
    }

    @Override
    public String menuSecundario() {
        return ""; 
    }
}
