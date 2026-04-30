package src.com.calculadoraVMC.app.Models;

public class TestP0 {
    public static void main(String[] args) {
        ModeloPoblacionFinita modelo = new ModeloPoblacionFinita(0.025, 0.10, 2, 10);
        
        double p0 = modelo.P0();
        double l = modelo.L();
        double lq = modelo.Lq();
        double w = modelo.w();
        double wq = modelo.wq();
        double lambdaEff = modelo.tasaEfectivaDeLlegadas();
        double rho = modelo.p();
        
        System.out.println("P0 = " + p0);
        System.out.println("LambdEff = " + lambdaEff);
        System.out.println("L = " + l);
        System.out.println("Lq = " + lq);
        System.out.println("W = " + w);
        System.out.println("Wq = " + wq);
        System.out.println("Rho = " + rho);
    }
}
