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


public class MBEditorController {

    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    MB mb = null;

    @FXML
    private TableColumn<MB, String> mbSocetCol;

    @FXML
    private TableColumn<MB, Integer> mbIdCol;

    @FXML
    private TextField mbSocetField;

    @FXML
    private TableColumn<MB, String> mbMemAmountCol;

    @FXML
    private TextField mbMemTypeField;

    @FXML
    private DatePicker mbDate;

    @FXML
    private TableColumn<MB, String> mbMemTypeCol;

    @FXML
    private TableColumn<MB, String> mbNameCol;

    @FXML
    private TextField mbNameField;

    @FXML
    private TextArea mbNote;

    @FXML
    private TableColumn<MB, String> mbNoteCol;

    @FXML
    private TableColumn<MB, String> mbUserCol;

    @FXML
    private TextField mbUserField;

    @FXML
    private TableView<MB> mbTable;

    @FXML
    private TableColumn<MB, String> mbDateCol;

    @FXML
    private TextField mbMemAmountFiled;


    @FXML
    void videocardAdd(ActionEvent event) {
        if(
        mbNameField.getText().equals("") ||
        mbSocetField.getText().equals("")||
        mbMemAmountFiled.getText().equals("")||
        mbMemTypeField.getText().equals("")||
        mbUserField.getText().equals("")
        ){Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Заполните все данные.");
            alert.showAndWait();

        }
            else{
        insert();
        refreshVideocards();
            mbNameField.clear();
            mbSocetField.clear();
            mbMemAmountFiled.clear();
            mbMemTypeField.clear();
            mbNote.clear();
            }
    }

    @FXML
    void Delete(ActionEvent event) {
        mb = mbTable.getSelectionModel().getSelectedItem();
        if (mb == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Материнская плата не выбрана.");
            alert.showAndWait();
        } else {
            query = "DELETE FROM mother_boards WHERE id = " + mb.getId()+";";
            query2 = "INSERT INTO deleted  (id,name, user,date) VALUES("+mb.getId()+",'"+mb.getName()+"','"+mb.getUser()+"','"+mb.getDate()+"')";
            try {
                connection = DbConnection();
                prSt = connection.prepareStatement(query);
                prSt.execute();
                prSt = connection.prepareStatement(query2);
                prSt.execute();
                refreshVideocards();
            } catch (SQLException | ClassNotFoundException throwable) {
                throwable.printStackTrace();
            }
            ;
        }

    }

    @FXML
    void initialize() {
        mbDate.setValue(LocalDate.now());
        mbUserField.setText(authUser);
        try {
            loadMB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadMB() throws SQLException, ClassNotFoundException {

        connection = DbConnection();
        refreshVideocards();

        mbIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mbNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mbSocetCol.setCellValueFactory(new PropertyValueFactory<>("socet"));
        mbMemAmountCol.setCellValueFactory(new PropertyValueFactory<>("ram_amount"));
        mbMemTypeCol.setCellValueFactory(new PropertyValueFactory<>("memtype"));
        mbUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        mbDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        mbNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    ObservableList<MB> MBList = FXCollections.observableArrayList();

    void refreshVideocards() {
        try {

            MBList.clear();

            query = "SELECT * FROM warehouse.mother_boards;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                MBList.add(new MB(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("socet"),
                        resultSet.getInt("ram_amount"),
                        resultSet.getString("memtype"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
            }
            mbTable.setItems(MBList);
        } catch (SQLException ex) {
            Logger.getLogger(MBEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insert() {
        String insert = "INSERT INTO mother_boards  (name,socet,ram_amount,memtype,user,date,note) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, mbNameField.getText());
            prSt.setString(2, mbSocetField.getText());
            prSt.setString(3, mbMemAmountFiled.getText());
            prSt.setString(4, mbMemTypeField.getText());
            prSt.setString(5, mbUserField.getText());
            prSt.setDate(6, Date.valueOf(String.valueOf(mbDate.getValue())));
            prSt.setString(7, mbNote.getText());
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
