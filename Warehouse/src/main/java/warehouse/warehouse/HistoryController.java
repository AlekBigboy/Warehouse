package warehouse.warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static warehouse.warehouse.AuthController.authUser;

public class HistoryController {
    String query = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;


    @FXML
    private TableColumn<Unit, String> deletedDateCol;

    @FXML
    private TableColumn<Unit, String> deletedIdCol;

    @FXML
    private TableColumn<Unit, String> deletedNameCol;

    @FXML
    private TableColumn<Unit, String> deletedUserCol;
    @FXML
    private TableView<Unit> historyTable;

    @FXML
    private Button clean;

    @FXML
    void ClearHistory(ActionEvent event) {
        query = "TRUNCATE TABLE deleted;";
        try {
            prSt = connection.prepareStatement(query);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        historyTable.getItems().clear();
    }

    @FXML
    void initialize() {
        clean.setVisible(authUser.equals("admin"));
        refreshHistory();
    }

    void refreshHistory() {
        try {
            connection = DatabaseHandler.DbConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Unit> historyList = FXCollections.observableArrayList();
        try {
            historyList.clear();

            query = "select * from deleted ";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()){
                historyList.add(new Unit( resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        null));
            }
            historyTable.setItems(historyList);
        }
        catch (SQLException ex){
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        deletedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deletedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deletedUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        deletedDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

}
