package src.com.calculadoraVMC.app.Models;

import src.com.calculadoraVMC.app.Main;

public class ModeloMMC extends Modelo{
    private int c; // servidores

    public ModeloMMC(double l, double m, int c) {
        this.l = l;
        this.m = m;
        this.c = c; 
    }

    public void setc(int c){
        this.c = c;
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
            default:
                System.out.println("Parámetro no reconocido. No se realizaron cambios.");
        }
    }

    @Override
    public double P0() {
        double p = p(); 

        double primerTermino = sumatoria(0, c - 1, n -> Math.pow(c * p, n) / factorial(n)); 
        double segundoTermino = Math.pow(c * p, c) * (1 / factorial(c)) * (1 / (1 - p));

        return Math.pow(primerTermino + segundoTermino, -1); 
    }

    @Override
    public double Pn(int n) {
        double P0 = P0();
        double a = l / m;

        if (n < c) {
            return (Math.pow(a, n) / factorial(n)) * P0;
        } else {
            return (Math.pow(a, n) / (factorial(c) * Math.pow(c, n - c))) * P0;
        }
    }

    public double probabilidadClientesMayorIgualC(){
        double p = p(); 

        return (Math.pow(c * p, c) * P0()) / (factorial(c) * (1 - p));
    }

    @Override
    public double tasaEfectivaDeLlegadas() {
        return l;
    }

    @Override
    public double L() {
        double p  = p();

        return  (c * p) + Lq(); 
    }

    @Override
    public double Lq() {
        double p  = p();

        return (p * probabilidadClientesMayorIgualC()) / (1 - p);
    }

    @Override
    public double w() {
        return  L() / l;
    }

    @Override
    public double wq() {
        return w() - (1 / m);
    }

    @Override
    public double p() {
        return l / (c * m);
    }

    @Override
    public String nombreModelo() {
        return "Modelo M/M/c (λ = " + l + ", μ = " + m + ", c = " + c + ")";
    }

    @Override
    public String menuSecundario() {
        return ""; 
    }
    
}
