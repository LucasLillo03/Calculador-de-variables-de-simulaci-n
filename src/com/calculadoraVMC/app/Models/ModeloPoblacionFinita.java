package src.com.calculadoraVMC.app.Models;

import src.com.calculadoraVMC.app.Main;

public class ModeloPoblacionFinita extends Modelo{

    private int c;    // servidores
    private int k;    // población

    public ModeloPoblacionFinita(double l, double m, int c, int k) {
        this.l = l;
        this.m = m;
        this.c = c;
        this.k = k;
    }

    public void setc(int c){
        this.c = c;
    }

    public void setk(int k){
        this.k = k; 
    }

    @Override 
    public void cambiarParametros(){
        System.out.println("¿Que parámetro desea cambiar? (l (tasa de arribos) / m (tasa de servicio) / c (servidores) / k (población)): ");
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
            case "c":
                System.out.println("Ingrese el nuevo número de servidores (c): ");
                int nuevoC = Main.scanner.nextInt();
                Main.scanner.nextLine(); // Consumir el salto de línea
                setc(nuevoC);
                break;
            case "k":
                System.out.println("Ingrese la nueva población (k): ");
                int nuevoK = Main.scanner.nextInt();
                Main.scanner.nextLine(); // Consumir el salto de línea
                setk(nuevoK);
                break;
            default:
                System.out.println("Parámetro no reconocido. No se realizaron cambios.");
        }
    }


    @Override
    public double P0() {
        double primeraSumatoria = sumatoria(0, this.c - 1,
        n -> combinatoria(this.k, n) * Math.pow(this.l / this.m, n));
        
        double segundaSumatoria = sumatoria(this.c, this.k, n -> pesoEstadoNMayorIgualC(n));
            
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
        double P0 = this.P0();

        return sumatoria(c, k, n -> (n - c) * Pn_aux(n, P0));
    }
    
    @Override
    public double w(){ return L() / tasaEfectivaDeLlegadas();}

    @Override
    public double wq(){ return Lq() / tasaEfectivaDeLlegadas();}
    
    @Override
    public double p(){ return tasaEfectivaDeLlegadas() / (this.c * this.m);}
    
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