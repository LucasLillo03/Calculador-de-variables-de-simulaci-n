import java.util.Scanner;

public class main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = obtenerOpcionValida(1, 7);
            
            if (opcion == 7) {
                continuar = false;
            } else {
                Modelo modelo = crearModelo(opcion);
                
                if (modelo != null) {
                    mostrarResultados(modelo);
                }
            }
            
            if (continuar) {
                System.out.println("\n¿Desea analizar otro modelo? (s/n): ");
                continuar = scanner.next().toLowerCase().equals("s");
            }
        }
        
        System.out.println("\n¡Hasta luego!");
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CALCULADOR DE VARIABLES DE MODELOS DE SIMULACIÓN");
        System.out.println("=".repeat(50));
        System.out.println("Seleccione un modelo:");
        System.out.println("1. Modelo M/M/1");
        System.out.println("2. Modelo M/M/c");
        System.out.println("3. Modelo M/M/c/N");
        System.out.println("4. Modelo M/G/1");
        System.out.println("5. Modelo M/G/c servidores infinitos");
        System.out.println("6. Modelo M/M/c/K/K (Población Finita)");
        System.out.println("7. Salir");
        System.out.print("Opción: ");
    }
    
    private static int obtenerOpcionValida(int min, int max) {
        try {
            int opcion = scanner.nextInt();
            if (opcion >= min && opcion <= max) {
                scanner.nextLine(); // Limpiar el salto de línea del buffer
                return opcion;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
                scanner.nextLine(); // Limpiar buffer
                System.out.print("Opción: ");
                return obtenerOpcionValida(min, max);
            }
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            System.out.println("Entrada inválida. Intente nuevamente.");
            System.out.print("Opción: ");
            return obtenerOpcionValida(min, max);
        }
    }
    
    private static Modelo crearModelo(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    return crearModeloMM1();
                case 2:
                    return crearModeloMMC();
                case 3:
                    return crearModeloMMCN();
                case 4:
                    return crearModeloMG1();
                case 5:
                    return crearModeloMGServidoresInfinitos();
                case 6:
                    return crearModeloPoblacionFinita();
                case 7:
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error al crear el modelo: " + (e.getMessage() != null ? e.getMessage() : e.toString()));
            scanner.nextLine(); // Limpiar buffer
            return null;
        }
    }
    
    private static ModeloMM1 crearModeloMM1() {
        System.out.println("\n--- Modelo M/M/1 ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double mu = obtenerDouble("Ingrese μ (tasa de servicio): ", 0.0001);
        
        if (lambda >= mu) {
            System.out.println("❌ Error: λ debe ser menor que μ para estabilidad");
            return null;
        }
        
        return new ModeloMM1(lambda, mu);
    }
    
    private static ModeloMMC crearModeloMMC() {
        System.out.println("\n--- Modelo M/M/c ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double mu = obtenerDouble("Ingrese μ (tasa de servicio): ", 0.0001);
        int c = obtenerEntero("Ingrese c (número de servidores): ", 1);
        
        if (lambda >= c * mu) {
            System.out.println("❌ Error: λ debe ser menor que c×μ para estabilidad (λ = " + lambda + ", c×μ = " + (c*mu) + ")");
            return null;
        }
        
        return new ModeloMMC(lambda, mu, c);
    }
    
    private static ModeloMMCN crearModeloMMCN() {
        System.out.println("\n--- Modelo M/M/c/N ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double mu = obtenerDouble("Ingrese μ (tasa de servicio): ", 0.0001);
        int c = obtenerEntero("Ingrese c (número de servidores): ", 1);
        int n = obtenerEntero("Ingrese N (capacidad máxima del sistema): ", c);
        
        if (c > n) {
            System.out.println("❌ Error: c no puede ser mayor que N");
            return null;
        }
        
        return new ModeloMMCN(lambda, mu, c, n);
    }
    
    private static ModeloMG1 crearModeloMG1() {
        System.out.println("\n--- Modelo M/G/1 ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double tiempoMedio = obtenerDouble("Ingrese E[S] (tiempo medio de servicio): ", 0.0001);
        double mu = 1.0 / tiempoMedio;
        double sigma2 = obtenerDouble("Ingrese σ² (varianza del tiempo de servicio): ", 0.0);
        
        if (lambda >= mu) {
            System.out.println("❌ Error: λ debe ser menor que μ para estabilidad");
            return null;
        }
        
        return new ModeloMG1(lambda, mu, sigma2);
    }
    
    private static ModeloMGServidoresInfinitos crearModeloMGServidoresInfinitos() {
        System.out.println("\n--- Modelo M/G/∞ ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double mu = obtenerDouble("Ingrese μ (tasa de servicio): ", 0.0001);
        
        return new ModeloMGServidoresInfinitos(lambda, mu);
    }
    
    private static ModeloPoblacionFinita crearModeloPoblacionFinita() {
        System.out.println("\n--- Modelo de Población Finita ---");
        double lambda = obtenerDouble("Ingrese λ (tasa de llegadas): ", 0.0001);
        double mu = obtenerDouble("Ingrese μ (tasa de servicio): ", 0.0001);
        int c = obtenerEntero("Ingrese c (número de servidores): ", 1);
        int k = obtenerEntero("Ingrese K (tamaño de la población): ", c);
        
        if (c > k) {
            System.out.println("❌ Error: c no puede ser mayor que K");
            return null;
        }
        
        return new ModeloPoblacionFinita(lambda, mu, c, k);
    }
    
    private static double obtenerDouble(String mensaje, double minimo) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("❌ Por favor ingrese un valor.");
                    continue;
                }
                double valor = Double.parseDouble(input);
                if (valor < minimo) {
                    System.out.println("❌ El valor debe ser mayor o igual a " + minimo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
    
    private static int obtenerEntero(String mensaje, int minimo) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("❌ Por favor ingrese un valor.");
                    continue;
                }
                int valor = Integer.parseInt(input);
                if (valor < minimo) {
                    System.out.println("❌ El valor debe ser mayor o igual a " + minimo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Ingrese un número entero válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
    
    private static void mostrarResultados(Modelo modelo) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULTADOS");
        System.out.println("=".repeat(50));
        System.out.println("Modelo: " + modelo.nombreModelo());
        System.out.println("-".repeat(50));
        
        try {
            System.out.printf("P₀ (Probabilidad de 0 clientes):     %.6f%n", modelo.P0());
            System.out.printf("λ efectiva (Tasa efectiva llegadas): %.6f%n", modelo.tasaEfectivaDeLlegadas());
            System.out.printf("L (Clientes en el sistema):          %.6f%n", modelo.L());
            System.out.printf("Lq (Clientes en la cola):            %.6f%n", modelo.Lq());
            System.out.printf("W (Tiempo en el sistema):            %.6f%n", modelo.w());
            System.out.printf("Wq (Tiempo en la cola):              %.6f%n", modelo.wq());
            System.out.printf("ρ (Factor de utilización):           %.6f%n", modelo.p());
            
            // Mostrar algunas probabilidades Pn
            System.out.println("-".repeat(50));
            System.out.println("Probabilidades Pn (primeras 5):      ");
            for (int i = 0; i <= 4; i++) {
                try {
                    System.out.printf("  P%d = %.6f%n", i, modelo.Pn(i));
                } catch (Exception e) {
                    System.out.printf("  P%d = No disponible para este modelo%n", i);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al calcular algunos valores: " + e.getMessage());
        }
        
        System.out.println("=".repeat(50));
    }
}
