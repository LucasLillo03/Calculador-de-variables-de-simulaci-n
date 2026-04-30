package src.com.calculadoraVMC.app.Models;

import src.com.calculadoraVMC.app.Main;

public class ModeloMMCN extends Modelo {

    private int c; 
    private int k; 
    private double a; 

    public ModeloMMCN(double l, double m, int c, int k) {
        this.l = l;
        this.m = m;
        this.c = c; 
        this.k = k; 

        a = l / m; 
    }

    @Override
    public void setl(double l){
        this.l = l; 
        a = l / m; 
    }

    @Override
    public void setm(double m){
        this.m = m; 
        a = l / m; 
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

    public void setc(int c){
        this.c = c;
    }

    public void setk(int k){
        this.k = k; 
    }

    @Override
    public double P0() {
        double primeraSumatoria = sumatoria(1, c, n -> Math.pow(a, n) / factorial(n)); 
        double segundaSumatoria = (Math.pow(a, c) / factorial(c)) *  sumatoria(c+1, k, n -> Math.pow(p(), n - c)); 

        return Math.pow(1 + primeraSumatoria + segundaSumatoria, -1);
    }

    @Override
    public double Pn(int n) {
        double numerador = Math.pow(a, n); 
        double denominador = factorial(c) * Math.pow(c, n - c); 

        return  (numerador / denominador) * P0(); 
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        return l * (1 - Pn(k)); 
    }

    @Override
    public double L() {
        return  tasaEfectivaDeLlegadas() * w(); 
    }

    @Override
    public double Lq() {
        double p = p(); 

        return P0() * (Math.pow(a, c)) * p / (factorial(c) * Math.pow(1 - p, 2))
                * (1 - Math.pow(p, k - c) - (k - c) * Math.pow(p, k - c) * (1 - p));
    }

    @Override
    public double w() {
        return wq() + (1 / m);
    }

    @Override
    public double wq() {
        return Lq() / tasaEfectivaDeLlegadas();
    }

    @Override
    public double p() {
        return l / (c * m);
    }

    @Override
    public String nombreModelo() {
        return "Modelo M/M/c/N (λ = " + l + ", μ = " + m + ", c = " + c + ", k = " + k + ")";
    }

    @Override
    public String menuSecundario() {
        return ""; 
    }
    
}
