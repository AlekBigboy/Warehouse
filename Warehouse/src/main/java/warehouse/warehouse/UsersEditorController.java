package warehouse.warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersEditorController {
    DatabaseHandler dbHandler = new DatabaseHandler();
    String query = null;
    Connection connection = null;
    PreparedStatement prSt = null;
    ResultSet resultSet = null;
    User user = null;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userIdCol;
    @FXML
    private TableColumn<User, String> userLastNameCol;
    @FXML
    private TableColumn<User, String> userLoginCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> userPasswordCol;
    @FXML
    void userAdd(ActionEvent event) {
        userAdd();
        try {
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField firstNameFiled;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField logInFiled;

    @FXML
    private TextField passwordField;

    @FXML
    void userDelete(ActionEvent event) throws SQLException, ClassNotFoundException  {
        user = userTable.getSelectionModel().getSelectedItem();
        if (user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Пользователь не выбран.");
            alert.showAndWait();
        }else
        {
            query = "DELETE FROM users WHERE user_id = " + user.getUser_id();
            connection = DatabaseHandler.DbConnection();
            prSt = connection.prepareStatement(query);
            prSt.execute();
            refreshUsers();
            logInFiled.clear();
            passwordField.clear();
            firstNameFiled.clear();
            lastNameField.clear();
        }
    }
    /*
    @FXML
    void SelectUser(MouseEvent event) {

        user = userTable.getSelectionModel().getSelectedItem();
        if (user == null){}
        else {
            firstNameFiled.setText(user.getUser_firstname());
            lastNameField.setText(user.getUser_lastname());
            logInFiled.setText(user.getUser_name());
            passwordField.setText(user.getUser_password());
        }
    }*/
    @FXML
    void initialize() {
        try {
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadUsers() throws SQLException, ClassNotFoundException {

        connection = DatabaseHandler.DbConnection();
        refreshUsers();

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        userLoginCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        userPasswordCol.setCellValueFactory(new PropertyValueFactory<>("user_password"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("user_firstname"));
        userLastNameCol.setCellValueFactory(new PropertyValueFactory<>("user_lastname"));
    }
    ObservableList<User> userList = FXCollections.observableArrayList();

    void refreshUsers() {
        try {

            userList.clear();

            query = "SELECT * FROM warehouse.users WHERE user_id !=1;";
            prSt = connection.prepareStatement(query);
            resultSet = prSt.executeQuery();
            while (resultSet.next()){
                userList.add(new User(resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_firstname"),
                        resultSet.getString("user_lastname")));
            }
            userTable.setItems(userList);
        }
        catch (SQLException ex){
            Logger.getLogger(UsersEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void userAdd(){
        Integer IdUser = null;
        String Username = logInFiled.getText();
        String Password = passwordField.getText();
        String Fname = firstNameFiled.getText();
        String Lname = lastNameField.getText();
        User user = new User(IdUser,Username,Password,Fname,Lname);
        dbHandler.userAdd(user);
        logInFiled.clear();
        passwordField.clear();
        firstNameFiled.clear();
        lastNameField.clear();
    }
}
