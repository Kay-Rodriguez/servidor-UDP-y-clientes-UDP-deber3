package servidor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class ServidorPreguntasUDP {
    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP ejecutándose en el puerto " + PUERTO);
            String pregunta = "¿Qué significa POO?";
            String respuestaCorrecta = "programacion orientada a objetos";
            while (true) {
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);
                String mensajeCliente = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength()).trim();
                InetAddress direccionCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();
                String respuestaServidor;

                if (mensajeCliente.equalsIgnoreCase("pregunta")) {
                    respuestaServidor = pregunta;
                } else {
                    if (mensajeCliente.trim().equalsIgnoreCase(respuestaCorrecta)) {
                        respuestaServidor = "Correcto";
                    } else {
                        respuestaServidor = "Incorrecto. La respuesta correcta es: Programación Orientada a Objetos";
                    }
                }

                byte[] bufferSalida = respuestaServidor.getBytes();
                DatagramPacket paqueteSalida = new DatagramPacket(
                        bufferSalida,
                        bufferSalida.length,
                        direccionCliente,
                        puertoCliente
                );

                socket.send(paqueteSalida);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}