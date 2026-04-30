package src.com.calculadoraVMC.app.Models;
import java.util.function.Function;
import src.com.calculadoraVMC.app.Main;

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

    //setters 
    public void setl(double l){
        this.l = l; 
    }

    public void setm(double m){
        this.m = m; 
    }

    public void cambiarParametros(){
        System.out.println("¿Que parámetro desea cambiar? (l (tasa de arribos) / m (tasa de servicio)): ");
        String parametro = Main.scanner.nextLine().trim().toLowerCase();
        switch (parametro) {
            case "l":
                System.out.println("Ingrese la nueva tasa de arribos (l): ");
                double nuevaL = Main.scanner.nextDouble();
                Main.scanner.nextLine(); // Consumir el salto de línea
                setl(nuevaL);
                break;
            case "m":
                System.out.println("Ingrese la nueva tasa de servicio (m): ");
                double nuevaM = Main.scanner.nextDouble();
                Main.scanner.nextLine(); // Consumir el salto de línea
                setm(nuevaM);
                break;
            default: 
                System.out.println("Parámetro no reconocido. No se realizaron cambios.");
        }
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
