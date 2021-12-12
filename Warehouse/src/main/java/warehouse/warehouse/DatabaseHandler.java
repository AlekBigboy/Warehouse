package warehouse.warehouse;


import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseHandler extends Configs {

    static Connection dbConnection;

    public DatabaseHandler() {
    }

    static Connection DbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser,dbPass);
        return dbConnection;
    }


    public void userAdd(User user) {
        String insert = "INSERT INTO users (user_name,user_password,user_firstname,user_lastname) VALUES(?,?,?,?)";
        try {

            PreparedStatement prSt = DbConnection().prepareStatement(insert);
            prSt.setString(1, user.getUser_name());
            prSt.setString(2, user.getUser_password());
            prSt.setString(3, user.getUser_firstname());
            prSt.setString(4, user.getUser_lastname());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Имя пользователя уже занято");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user){
        ResultSet resultSet = null;
        String select = "SELECT * FROM users WHERE user_name =? AND user_password =?";
        try {
            PreparedStatement prSt = DbConnection().prepareStatement(select);
            prSt.setString(1, user.getUser_name());
            prSt.setString(2, user.getUser_password());
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Не удалось подключиться к базе данных.");
            alert.showAndWait();
        }
        return resultSet;
    }
}
