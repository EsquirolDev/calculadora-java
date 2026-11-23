import java.math.BigInteger;

public class Calculadora {

    public static final int MIN = -99999;
    public static final int MAX = 99999;

    public static int leerNumero(String texto) {
        if (texto == null || !texto.trim().matches("-?\\d{1,5}")) {
            throw new IllegalArgumentException("Solo se permiten enteros entre -99999 y 99999");
        }
        return Integer.parseInt(texto.trim());
    }

    public static long sumar(int a, int b) { return (long) a + b; }
    public static long restar(int a, int b) { return (long) a - b; }
    public static long multiplicar(int a, int b) { return (long) a * b; }

    public static BigInteger potencia(int base, int exponente) {
        if (exponente < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo (el resultado no sería entero)");
        }
        return BigInteger.valueOf(base).pow(exponente);
    }
}