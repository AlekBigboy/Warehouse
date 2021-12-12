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


public class MonitorsEditorController {

    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    Monitor monitor = null;

    @FXML
    private TableColumn<Monitor, String> monitorNameCol;

    @FXML
    private TableColumn<Monitor, Integer> monitorIdCol;

    @FXML
    private TextField monitorPortField;

    @FXML
    private TableColumn<Monitor, String> monitorPortCol;

    @FXML
    private TextField monitorResField;

    @FXML
    private DatePicker monitorDate;

    @FXML
    private TableColumn<Monitor, String> monitorDateCol;

    @FXML
    private TableColumn<Monitor, String> monitorResCol;

    @FXML
    private TextField monitorNameField;

    @FXML
    private TextArea monitorNote;

    @FXML
    private TableColumn<Monitor, String> monitorNoteCol;

    @FXML
    private TableColumn<Monitor, String> monitorUserCol;

    @FXML
    private TextField monitorUserField;

    @FXML
    private TableView<Monitor> monitorsTable;

    @FXML
    private TableColumn<Monitor, String> monitorFreqCol;

    @FXML
    private TextField monitorFreqField;


    @FXML
    void monitorAdd(ActionEvent event) {
        if(
                monitorNameField.getText().equals("") ||
                        monitorPortField.getText().equals("")||
                        monitorFreqField.getText().equals("")||
                        monitorResField.getText().equals("")||
                        monitorUserField.getText().equals("")
        ){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все данные.");
            alert.showAndWait();

        }
            else{
        insert();
        refreshMonitors();
            monitorNameField.clear();
            monitorPortField.clear();
            monitorFreqField.clear();
            monitorResField.clear();
            monitorNote.clear();
            }
    }

    @FXML
    void Delete(ActionEvent event) {
        monitor = monitorsTable.getSelectionModel().getSelectedItem();
        if (monitor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Монитор не выбран.");
            alert.showAndWait();
        } else {
            query = "DELETE FROM monitors WHERE id = " + monitor.getId();
            query2 = "INSERT INTO deleted  (id,name, user,date) VALUES("+monitor.getId()+",'"+monitor.getName()+"','"+monitor.getUser()+"','"+monitor.getDate()+"')";
            try {
                connection = DbConnection();
                prSt = connection.prepareStatement(query);
                prSt.execute();
                prSt = connection.prepareStatement(query2);
                prSt.execute();
                refreshMonitors();
            } catch (SQLException | ClassNotFoundException throwable) {
                throwable.printStackTrace();
            }
            ;
        }

    }

    @FXML
    void initialize() {
        monitorDate.setValue(LocalDate.now());
        monitorUserField.setText(authUser);
        try {
            loadMonitors();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadMonitors() throws SQLException, ClassNotFoundException {

        connection = DbConnection();
        refreshMonitors();

        monitorIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        monitorNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        monitorPortCol.setCellValueFactory(new PropertyValueFactory<>("port"));
        monitorFreqCol.setCellValueFactory(new PropertyValueFactory<>("freq"));
        monitorResCol.setCellValueFactory(new PropertyValueFactory<>("resol"));
        monitorUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        monitorDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        monitorNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    ObservableList<Monitor> monitorList = FXCollections.observableArrayList();

    void refreshMonitors() {
        try {

            monitorList.clear();

            query = "SELECT * FROM warehouse.monitors;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                monitorList.add(new Monitor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("port"),
                        resultSet.getString("freq"),
                        resultSet.getString("resol"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
            }
            monitorsTable.setItems(monitorList);
        } catch (SQLException ex) {
            Logger.getLogger(MonitorsEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insert() {
        String insert = "INSERT INTO monitors  (name,port,freq,resol,user,date,note) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, monitorNameField.getText());
            prSt.setString(2, monitorPortField.getText());
            prSt.setString(3, monitorFreqField.getText());
            prSt.setString(4, monitorResField.getText());
            prSt.setString(5, monitorUserField.getText());
            prSt.setDate(6, Date.valueOf(monitorDate.getValue()));
            prSt.setString(7, monitorNote.getText());
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
