package cliente;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClienteFX extends Application {

    private Label lblPregunta;
    private TextField txtRespuesta;
    private Label lblResultado;
    private ClienteUDP clienteUDP;

    @Override
    public void start(Stage stage) {
        clienteUDP = new ClienteUDP("localhost", 5000);
        lblPregunta = new Label("Presione el botón para obtener una pregunta");
        txtRespuesta = new TextField();
        txtRespuesta.setPromptText("Escriba su respuesta aquí");
        Button btnObtenerPregunta = new Button("Obtener pregunta");
        Button btnEnviarRespuesta = new Button("Enviar respuesta");
        lblResultado = new Label("");

        btnObtenerPregunta.setOnAction(e -> obtenerPregunta());
        btnEnviarRespuesta.setOnAction(e -> enviarRespuesta());

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                lblPregunta,
                txtRespuesta,
                btnObtenerPregunta,
                btnEnviarRespuesta,
                lblResultado
        );

        Scene scene = new Scene(root, 450, 250);
        stage.setTitle("Cliente UDP - Cuestionario");
        stage.setScene(scene);
        stage.show();
    }

    private void obtenerPregunta() {
        try {
            String pregunta = clienteUDP.enviarMensaje("pregunta");
            lblPregunta.setText(pregunta);
            lblResultado.setText("");
        } catch (Exception e) {
            lblResultado.setText("Error al obtener la pregunta: " + e.getMessage());
        }
    }

    private void enviarRespuesta() {
        try {
            String respuestaUsuario = txtRespuesta.getText().trim();

            if (respuestaUsuario.isEmpty()) {
                lblResultado.setText("Debe ingresar una respuesta.");
                return;
            }

            String resultado = clienteUDP.enviarMensaje(respuestaUsuario);
            lblResultado.setText(resultado);

        } catch (Exception e) {
            lblResultado.setText("Error al enviar la respuesta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}