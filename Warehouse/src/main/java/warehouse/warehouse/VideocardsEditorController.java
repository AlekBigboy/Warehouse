package warehouse.warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static warehouse.warehouse.AuthController.authUser;
import static warehouse.warehouse.DatabaseHandler.DbConnection;


public class VideocardsEditorController {

    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    Videocard videocard = null;

    @FXML
    private TableColumn<Videocard, String> videoPciCol;

    @FXML
    private TableColumn<Videocard, Integer> videoIdCol;

    @FXML
    private TextField videoPciFiled;

    @FXML
    private TableColumn<Videocard, String> videoPortCol;

    @FXML
    private TextField videoPortField;

    @FXML
    private DatePicker videoDate;

    @FXML
    private TableColumn<Videocard, String> videoDateCol;

    @FXML
    private TableColumn<Videocard, String> videoNameCol;

    @FXML
    private TextField videoNameField;

    @FXML
    private TextArea videoNote;

    @FXML
    private TableColumn<Videocard, String> videoNoteCol;

    @FXML
    private TableColumn<Videocard, String> videoUserCol;

    @FXML
    private TextField videoUserField;

    @FXML
    private TableView<Videocard> videocardsTable;

    @FXML
    private TableColumn<Videocard, String> videoMemCol;

    @FXML
    private TextField videoMemField;

    @FXML
    void videocardAdd(ActionEvent event) {
        if(
        videoNameField.getText().equals("") ||
        videoMemField.getText().equals("")||
        videoPciFiled.getText().equals("")||
        videoPortField.getText().equals("")||
        videoUserField.getText().equals("")
        ){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все данные.");
            alert.showAndWait();

        }
            else{
        insert();
        refreshVideocards();
            videoNameField.clear();
            videoMemField.clear();
            videoPciFiled.clear();
            videoPortField.clear();
            videoNote.clear();
            }
    }

    @FXML
    void Delete(ActionEvent event) {
        videocard = videocardsTable.getSelectionModel().getSelectedItem();
        if (videocard == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Видеокарта не выбрана.");
            alert.showAndWait();
        } else {
            query = "DELETE FROM videocards WHERE id = " + videocard.getId();
            query2 = "INSERT INTO deleted  (id,name, user,date) VALUES("+videocard.getId()+",'"+videocard.getName()+"','"+videocard.getUser()+"','"+videocard.getDate()+"')";
            try {
                connection = DbConnection();
                prSt = connection.prepareStatement(query);
                prSt.execute();
                prSt = connection.prepareStatement(query2);
                prSt.execute();
                refreshVideocards();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            ;
        }

    }

    @FXML
    void initialize() {
        videoDate.setValue(LocalDate.now());
        videoUserField.setText(authUser);
        try {
            loadVideocards();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadVideocards() throws SQLException, ClassNotFoundException {

        connection = DbConnection();
        refreshVideocards();

        videoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        videoNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        videoMemCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
        videoPciCol.setCellValueFactory(new PropertyValueFactory<>("pci_version"));
        videoPortCol.setCellValueFactory(new PropertyValueFactory<>("port"));
        videoUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        videoDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        videoNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    ObservableList<Videocard> videocardList = FXCollections.observableArrayList();

    void refreshVideocards() {
        try {

            videocardList.clear();

            query = "SELECT * FROM warehouse.videocards;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                videocardList.add(new Videocard(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("memory"),
                        resultSet.getString("pci_version"),
                        resultSet.getString("port"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
            }
            videocardsTable.setItems(videocardList);
        } catch (SQLException ex) {
            Logger.getLogger(VideocardsEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insert() {
        String insert = "INSERT INTO videocards  (name,memory,pci_version,port,user,date,note) VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, videoNameField.getText());
            prSt.setString(2, videoMemField.getText());
            prSt.setString(3, videoPciFiled.getText());
            prSt.setString(4, videoPortField.getText());
            prSt.setString(5, videoUserField.getText());
            prSt.setDate(6, Date.valueOf(String.valueOf(videoDate.getValue())));
            prSt.setString(7, videoNote.getText());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Не удалось добавить запись, проверте данные.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
