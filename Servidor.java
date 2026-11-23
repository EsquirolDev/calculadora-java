import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", Servidor::inicio);
        server.createContext("/calcular", Servidor::calcular);
        server.start();
        System.out.println("Servidor arrancado en http://localhost:8080");
    }

    static void inicio(HttpExchange ex) throws IOException {
        byte[] html = Files.readAllBytes(Paths.get("index.html"));
        enviar(ex, html);
    }

    static void calcular(HttpExchange ex) throws IOException {
        Map<String, String> p = parametros(ex.getRequestURI().getRawQuery());
        String resultado;
        try {
            int a = Calculadora.leerNumero(p.get("a"));
            int b = Calculadora.leerNumero(p.get("b"));
            switch (p.getOrDefault("op", "")) {
                case "suma": resultado = a + " + " + b + " = " + Calculadora.sumar(a, b); break;
                case "resta": resultado = a + " - " + b + " = " + Calculadora.restar(a, b); break;
                case "multiplicacion": resultado = a + " × " + b + " = " + Calculadora.multiplicar(a, b); break;
                case "potencia": resultado = a + " ^ " + b + " = " + Calculadora.potencia(a, b); break;
                default: throw new IllegalArgumentException("Operación no permitida");
            }
        } catch (IllegalArgumentException e) {
            resultado = "Error: " + e.getMessage();
        }
        String html = "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Resultado</title></head>"
                + "<body><h1>Resultado</h1><p style='word-break:break-all'>" + resultado + "</p>"
                + "<a href='/'>Volver</a></body></html>";
        enviar(ex, html.getBytes(StandardCharsets.UTF_8));
    }

    static Map<String, String> parametros(String query) {
        Map<String, String> mapa = new HashMap<>();
        if (query == null) return mapa;
        for (String par : query.split("&")) {
            String[] kv = par.split("=", 2);
            if (kv.length == 2) mapa.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
        }
        return mapa;
    }

    static void enviar(HttpExchange ex, byte[] cuerpo) throws IOException {
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        ex.sendResponseHeaders(200, cuerpo.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(cuerpo);
        }
    }
}