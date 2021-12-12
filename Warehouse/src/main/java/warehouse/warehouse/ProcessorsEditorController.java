package warehouse.warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static warehouse.warehouse.AuthController.authUser;
import static warehouse.warehouse.DatabaseHandler.DbConnection;


public class ProcessorsEditorController {

    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    Processor processor = null;

    @FXML
    private TableColumn<Processor, Integer> coresCol;

    @FXML
    private TableColumn<Processor, Integer> procIdCol;

    @FXML
    private TextField coresFiled;

    @FXML
    private TableColumn<Processor, String> freqCol;

    @FXML
    private TextField freqField;

    @FXML
    private DatePicker procDate;

    @FXML
    private TableColumn<Processor, String> procDateCol;

    @FXML
    private TableColumn<Processor, String> procNameCol;

    @FXML
    private TextField procNameField;

    @FXML
    private TextArea procNote;

    @FXML
    private TableColumn<Processor, String> procNoteCol;

    @FXML
    private TableColumn<Processor, String> procUserCol;

    @FXML
    private TextField procUserField;

    @FXML
    private TableView<Processor> processorsTable;

    @FXML
    private TableColumn<Processor, String> socetCol;

    @FXML
    private TextField socetField;

    @FXML
    void processorAdd(ActionEvent event) {
        if(
        procNameField.getText().equals("") ||
        socetField.getText().equals("")||
        coresFiled.getText().equals("")||
        freqField.getText().equals("")||
        procUserField.getText().equals("")
        ){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все данные.");
            alert.showAndWait();
        }
            else{
        insert();
        refreshProcessors();
            procNameField.clear();
            socetField.clear();
            coresFiled.clear();
            freqField.clear();
            procNote.clear();
            }
    }

    @FXML
    void Delete(ActionEvent event) {
        processor = processorsTable.getSelectionModel().getSelectedItem();
        if (processor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Процессор не выбран.");
            alert.showAndWait();
        } else {
            query = "DELETE FROM processors WHERE id = " + processor.getId();
            query2 = "INSERT INTO deleted  (id,name, user,date) " +
                    "VALUES("+processor.getId()+",'"
                    +processor.getName()+"','"
                    +processor.getUser()+"','"
                    +processor.getDate()+"')";
            try {
                connection = DbConnection();
                prSt = connection.prepareStatement(query);
                prSt.execute();
                prSt = connection.prepareStatement(query2);
                prSt.execute();
                refreshProcessors();
            } catch (SQLException | ClassNotFoundException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        procDate.setValue(LocalDate.now());
        procUserField.setText(authUser);
        try {
            loadProcessors();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadProcessors() throws SQLException, ClassNotFoundException {

        connection = DbConnection();
        refreshProcessors();

        procIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        procNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        socetCol.setCellValueFactory(new PropertyValueFactory<>("socet"));
        coresCol.setCellValueFactory(new PropertyValueFactory<>("cores"));
        freqCol.setCellValueFactory(new PropertyValueFactory<>("freq"));
        procUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        procDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        procNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    ObservableList<Processor> processorList = FXCollections.observableArrayList();

    void refreshProcessors() {
        try {

            processorList.clear();

            query = "SELECT * FROM warehouse.processors;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                processorList.add(new Processor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("socket"),
                        resultSet.getInt("cores"),
                        resultSet.getString("freq"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
            }
            processorsTable.setItems(processorList);
        } catch (SQLException ex) {
            Logger.getLogger(UsersEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insert() {
        String insert = "INSERT INTO processors  (name,socket,cores,freq,user,date,note) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, procNameField.getText());
            prSt.setString(2, socetField.getText());
            prSt.setInt(3, Integer.parseInt(coresFiled.getText()));
            prSt.setString(4, freqField.getText());
            prSt.setString(5, procUserField.getText());
            prSt.setDate(6, Date.valueOf(String.valueOf(procDate.getValue())));
            prSt.setString(7, procNote.getText());
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
