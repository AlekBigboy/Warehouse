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


public class RamEditorController {

    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    Ram ram = null;

    @FXML
    private TableColumn<Ram, String> ramNameCol;

    @FXML
    private TableColumn<Ram, Integer> ramIdCol;

    @FXML
    private TextField ramFreqField;

    @FXML
    private TableColumn<Ram, String> ramMemCol;

    @FXML
    private TextField ramMemField;

    @FXML
    private DatePicker ramDate;

    @FXML
    private TableColumn<Ram, String> ramDateCol;

    @FXML
    private TableColumn<Ram, String> ramTypeCol;

    @FXML
    private TextField ramNameField;

    @FXML
    private TextArea ramNote;

    @FXML
    private TableColumn<Ram, String> ramNoteCol;

    @FXML
    private TableColumn<Ram, String> ramUserCol;

    @FXML
    private TextField ramUserField;

    @FXML
    private TableView<Ram> ramTable;

    @FXML
    private TableColumn<Ram, String> ramFreqCol;

    @FXML
    private TextField ramTypeField;

    @FXML
    void ramAdd(ActionEvent event) {
        if(
                ramNameField.getText().equals("") ||
                        ramTypeField.getText().equals("")||
                        ramFreqField.getText().equals("")||
                        ramMemField.getText().equals("")||
                        ramUserField.getText().equals("")
        ){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все данные.");
            alert.showAndWait();

        }
            else{
        insert();
        refreshRam();
        ramNameField.clear();
        ramTypeField.clear();
        ramFreqField.clear();
        ramMemField.clear();
        ramNote.clear();
            }
    }

    @FXML
    void Delete(ActionEvent event) {
        ram = ramTable.getSelectionModel().getSelectedItem();
        if (ram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ОЗУ не выбрано.");
            alert.showAndWait();
        } else {
            query = "DELETE FROM ram WHERE id = " + ram.getId();
            query2 = "INSERT INTO deleted  (id,name, user,date) VALUES("+ram.getId()+",'"+ram.getName()+"','"+ram.getUser()+"','"+ram.getDate()+"')";
            try {
                connection = DbConnection();
                prSt = connection.prepareStatement(query);
                prSt.execute();
                prSt = connection.prepareStatement(query2);
                prSt.execute();
                refreshRam();
            } catch (SQLException | ClassNotFoundException throwable) {
                throwable.printStackTrace();
            }
            ;
        }

    }

    @FXML
    void initialize() {
        ramDate.setValue(LocalDate.now());
        ramUserField.setText(authUser);
        try {
            loadRam();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadRam() throws SQLException, ClassNotFoundException {

        connection = DbConnection();
        refreshRam();

        ramIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ramNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ramTypeCol.setCellValueFactory(new PropertyValueFactory<>("memtype"));
        ramFreqCol.setCellValueFactory(new PropertyValueFactory<>("freq"));
        ramMemCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
        ramUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        ramDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        ramNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    ObservableList<Ram> ramList = FXCollections.observableArrayList();

    void refreshRam() {
        try {

            ramList.clear();

            query = "SELECT * FROM warehouse.ram;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                ramList.add(new Ram(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("memtype"),
                        resultSet.getString("freq"),
                        resultSet.getString("memory"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
            }
            ramTable.setItems(ramList);
        } catch (SQLException ex) {
            Logger.getLogger(RamEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insert() {
        String insert = "INSERT INTO ram  (name,memtype,freq,memory,user,date,note) VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, ramNameField.getText());
            prSt.setString(2, ramTypeField.getText());
            prSt.setString(3, ramFreqField.getText());
            prSt.setString(4, ramMemField.getText());
            prSt.setString(5, ramUserField.getText());
            prSt.setDate(6, Date.valueOf(String.valueOf(ramDate.getValue())));
            prSt.setString(7, ramNote.getText());
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
