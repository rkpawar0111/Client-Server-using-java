package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    private GridPane grid;
    @FXML
    private ListView<String> files;
    @FXML
    private TextField inform;

    @FXML
    public void SelectFiles(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Application File");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text","*.txt"),
                new FileChooser.ExtensionFilter("ImageFile","*.jpg","*.png","*.gif"),
                new FileChooser.ExtensionFilter("AllFiles","*.*"));
        List<File> file = chooser.showOpenMultipleDialog(grid.getScene().getWindow());
        ObservableList<String> lt = FXCollections.observableArrayList();
        if(file != null){
            for (File value : file) {
                lt.add(value.getPath());
            }
        }else{
            System.out.println("error!");
        }
        inform.setText("Loading File(s)....");
        files.setItems(lt);
        inform.setText("File(s) loaded");
    }

    @FXML
    public void FastShare(){
        ObservableList<String> temp = files.getItems();
        if(temp != null){
            String[] send = new String[temp.size()];
            for (int i=0;i<temp.size();i++){
                send[i] = temp.get(i);
            }
            inform.setText("Sending Files....");
            Server s1 = new Server();
            s1.serve(send);
            inform.setText("Sent "+files.getItems().size()+" File(s).");
        }
    }
}
