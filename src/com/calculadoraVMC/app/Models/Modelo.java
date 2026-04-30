package src.com.calculadoraVMC.app.Models;
import java.util.function.Function;

public abstract class Modelo {
    
    protected double l; // tasa de llegadas
    protected double m; // tasa de servicio

    //funciones comunes 
    public static double factorial(int n) {
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double combinatoria(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    public static double sumatoria(int inicio, int fin, Function<Integer, Double> f) {
        double suma = 0;
        for (int i = inicio; i <= fin; i++) {
            suma += f.apply(i);
        }
        return suma;
    }

    //funciones abstractas
    public abstract double P0();
    public abstract double Pn(int n); 
    public abstract double tasaEfectivaDeLlegadas();
    public abstract double L();
    public abstract double Lq(); 
    public abstract double w(); 
    public abstract double wq(); 
    public abstract double p(); 

    public abstract String nombreModelo();
    public abstract String menuSecundario(); 
}
