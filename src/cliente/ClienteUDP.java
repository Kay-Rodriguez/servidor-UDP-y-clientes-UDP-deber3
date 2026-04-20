package cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteUDP {

    private final String host;
    private final int puerto;

    public ClienteUDP(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public String enviarMensaje(String mensaje) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress direccion = InetAddress.getByName(host);

            byte[] bufferEnvio = mensaje.getBytes();
            DatagramPacket paqueteEnvio = new DatagramPacket(
                    bufferEnvio,
                    bufferEnvio.length,
                    direccion,
                    puerto);

            socket.send(paqueteEnvio);
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
            socket.receive(paqueteRespuesta);

            return new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength()).trim();
        }
    }
}