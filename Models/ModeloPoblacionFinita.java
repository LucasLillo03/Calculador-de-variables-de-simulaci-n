package Models;
public class ModeloPoblacionFinita extends Modelo{

    private int c;    // servidores
    private int k;    // población

    public ModeloPoblacionFinita(double l, double m, int c, int k) {
        this.l = l;
        this.m = m;
        this.c = c;
        this.k = k;
    }

    @Override
    public double P0() {
        double primeraSumatoria = sumatoria(0, this.c - 1,
            n -> combinatoria(this.k, n) * Math.pow(this.l / this.m, n));
            
            double segundaSumatoria = sumatoria(this.c, this.k,
                n -> pesoEstadoNMayorIgualC(n));
                
                return Math.pow((primeraSumatoria + segundaSumatoria), -1);
            }
    
    @Override
    public double Pn(int n) {
        double P0 = this.P0(); 

        return  Pn_aux(n, P0);
    }

    private double Pn_aux(int n, double P0){
        if (n < this.c) {
            return combinatoria(this.k, n) * Math.pow(this.l / this.m, n) * P0;
        } else {
            return pesoEstadoNMayorIgualC(n) * P0;
        }
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        double P0 = this.P0();
        
        return sumatoria(0, this.k,
            n -> (this.k - n) * this.l * Pn_aux(n, P0));
    }
    
    @Override
    public double L() {
        double P0 = this.P0();
        
        return sumatoria(0, this.k, n -> n * Pn_aux(n, P0));
    }

    @Override
    public double Lq() { 
        double a = l / m; 
        double p = l / (c*m);

        return P0() * (Math.pow(a, c)) * p / (factorial(c) * Math.pow(1 - p, 2))
                * (1 - Math.pow(p, k - c) - (k - c) * Math.pow(p, k - c) * (1 - p));
    }
    
    @Override
    public double w(){ return L() / tasaEfectivaDeLlegadas();}

    @Override
    public double wq(){ return Lq() / tasaEfectivaDeLlegadas();}
    
    @Override
    public double p(){ return tasaEfectivaDeLlegadas() /this.c * this.m;}
    
    // n = clientes esperados
    private double pesoEstadoNMayorIgualC(int n) {
        double kFactorial = factorial(this.k);
        double cFactorial = factorial(this.c);
        double a = this.l / this.m;

        return (kFactorial /
                (factorial(this.k - n) * cFactorial * Math.pow(this.c, (n - this.c))))
                * Math.pow(a, n);
    }

    @Override
    public String nombreModelo() {
        return "Modelo M/M/c/K/K (λ = " + l + ", μ = " + m + ", c = " + c + ", k = " + k + ")";
    }

    @Override
    public String menuSecundario() {
        return ""; 
    }
}