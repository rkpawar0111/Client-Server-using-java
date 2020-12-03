package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class Controller {

    @FXML
    public Button receive;
    @FXML
    private ListView<String> files;
    @FXML
    public TextField inform;

    public void ReceiveFiles(){
        ObservableList<String> lt = FXCollections.observableArrayList();
        inform.setText("Receiving file(s)...");
        Client client = new Client();
        client.receive();
        lt.addAll(Arrays.asList(client.getFiles()));
        files.setItems(lt);
        inform.setText("Received "+client.getFiles().length+" File(s)");
    }
}
