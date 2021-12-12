package warehouse.warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static warehouse.warehouse.AuthController.authUser;


public class AppController {
    String query = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;


    @FXML
    private DatePicker secondDate;

    @FXML
    private DatePicker firstDate;

    @FXML
    private TableColumn<Unit, String> unitDateCol;

    @FXML
    private TableColumn<Unit, String> unitNameCol;

    @FXML
    private TableColumn<Unit, String> unitNoteCol;

    @FXML
    private TableColumn<Unit, String> unitUserCol;
    @FXML
    private TableColumn<Unit, Integer> unitIdCol;

    @FXML
    private TableView<Unit> unit_table;

    @FXML
    private Button usersButton;

    @FXML
    private TextField Find;


    @FXML
    void showMB(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/MBEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setMinWidth(950);
        stage.setMinHeight(630);
        stage.setTitle("Управление Материнскими платами. Пользователь: " + authUser);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/mb_icon.jpg")));
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    void showMonitors(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/MonitorsEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setMinWidth(950);
        stage.setMinHeight(630);
        stage.setTitle("Управление мониторами. Пользователь: " + authUser);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/monitor_icon.png")));
        stage.setScene(new Scene(root));
        stage.showAndWait();



    }

    @FXML
    void showProcessors(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/ProcessorsEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setMinWidth(950);
        stage.setMinHeight(630);
        stage.setTitle("Управление процессорами. Пользователь: " + authUser);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/proc_icon.png")));
        stage.setScene(new Scene(root));
        stage.showAndWait();}

    @FXML
    void showRAM(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/RamEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setMinWidth(950);
        stage.setMinHeight(630);
        stage.setTitle("Управление ОЗУ. Пользователь: " + authUser);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/ram_icon.png")));
        stage.setScene(new Scene(root));
        stage.showAndWait();



    }

    @FXML
    void showVideocards(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/VideocardsEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setMinWidth(950);
        stage.setMinHeight(630);
        stage.setTitle("Управление видеокартами. Пользователь: " + authUser);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/video_icon.jpg")));
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void showUserEditor(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/UsersEditor.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Зарегистрированные пользователи");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/users_icon.jpg")));
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void search(ActionEvent event) {
        if (Find.getText().equals("")){
            System.out.println("Поле поиска пустое.");}
        else {
        ObservableList<Unit> PartList = FXCollections.observableArrayList();
        try {PartList.clear();
            query = "select id,name, user, date, note from monitors where (name like '%"+Find.getText()
                    +"%' or user like '%"+Find.getText()+"%' or note like '%"+Find.getText()+"%') union "+
            "select id,name, user, date, note from mother_boards where (name like '%"+Find.getText()
                    +"%' or user like '%"+Find.getText()+"%' or note like '%"+Find.getText()+"%')union "+
            "select id,name, user, date, note from processors where (name like '%"+Find.getText()
                    +"%' or user like '%"+Find.getText()+"%' or note like '%"+Find.getText()+"%')union "+
            "select id,name, user, date, note from ram where (name like '%"+Find.getText()
                    +"%' or user like '%"+Find.getText()+"%' or note like '%"+Find.getText()+"%')union "+
            "select id,name, user, date, note from videocards where (name like '%"+Find.getText()
                    +"%' or user like '%"+Find.getText()+"%' or note like '%"+Find.getText()+"%');";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()){
                PartList.add(new Unit(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("user"),
                        resultSet.getDate("date"),
                        resultSet.getString("note")));
                unit_table.setItems(PartList);
            }
        }
        catch (SQLException ex){
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);}
        Find.clear();}}

    @FXML
    void dateSearch(ActionEvent e) {
            ObservableList<Unit> PartList = FXCollections.observableArrayList();
            if (firstDate.getValue()== null || firstDate.getValue()== null){
                System.out.println("Поля даты заполнены некорректно.");}
            else {
            try {
                PartList.clear();
                query = "select id,name, user, date, note from monitors where date between '"+firstDate.getValue()
                        +"' and '"+firstDate.getValue()+"'\n" +
                        "union\n" +
                        "    select id,name, user, date, note from mother_boards where date between '"+firstDate.getValue()
                        +"' and '"+secondDate.getValue()+"'\n" +
                        "union\n" +
                        "    select id,name, user, date, note from processors where date between '"+firstDate.getValue()
                        +"' and '"+secondDate.getValue()+"'\n" +
                        "union\n" +
                        "    select id,name, user, date, note from ram where date between  '"+firstDate.getValue()
                        +"' and '"+secondDate.getValue()+"'\n" +
                        "union\n" +
                        "    select id,name, user, date, note from videocards where date between '"+firstDate.getValue()
                        +"' and '"+secondDate.getValue()+"';";
                prSt = connection.prepareStatement(query);
                resultSet = prSt.executeQuery();
                while (resultSet.next()){
                    PartList.add(new Unit(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("user"),
                            resultSet.getDate("date"),
                            resultSet.getString("note")));
                    unit_table.setItems(PartList);
                }}
            catch (SQLException ex){
                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);}}}

    @FXML
    void showHistory(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/warehouse/warehouse/History.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Убытия");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/history_icon.jpg")));
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    void initialize() {
        usersButton.setVisible(authUser.equals("admin"));
        refreshMainTable();
    }

    void refreshMainTable() {
        try {connection = DatabaseHandler.DbConnection();
        } catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}
        ObservableList<Unit> PartList = FXCollections.observableArrayList();
        try {PartList.clear();
            query = "select   id,name, user, date, note from monitors " +
                    "union    select id,name, user, date, note from mother_boards " +
                    "union    select id,name, user, date, note from processors " +
                    "union    select id,name, user, date, note from ram " +
                    "union    select id,name, user, date, note from videocards;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()){
                PartList.add(new Unit( resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("user"), resultSet.getDate("date"), resultSet.getString("note")));
            }
            unit_table.setItems(PartList);
        }
        catch (SQLException ex){Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);}
        unitIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitUserCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        unitDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        unitNoteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    @FXML
    void refreshMainTable(ActionEvent event) {
        refreshMainTable();
    }
}