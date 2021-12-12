package warehouse.warehouse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import warehouse.warehouse.animations.Shake;

public class AuthController {

    public static String authUser;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void logIn(ActionEvent event) {

        String loginText = loginField.getText();
        String passwordText = passwordField.getText();
        if (!loginText.equals("") && !passwordText.equals("")) {

            try {
                loginUser(loginText, passwordText);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Shake UsernameShake = new Shake(loginField);
            Shake PasswordShake = new Shake(passwordField);
            UsernameShake.playAnim();
            PasswordShake.playAnim();
            System.out.println("Ошибка.Логин/пароль пусты.");
        }
    }

    private void loginUser(String loginText, String passwordText) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUser_name(loginText);
        user.setUser_password(passwordText);
        ResultSet result = dbHandler.getUser(user);
        int counter = 0;
        while (result.next()) {
            counter++;
        }
        if (counter == 1) {
            authUser = loginField.getText();
            LogInButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/warehouse/warehouse/App.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Склад. Пользователь: " + authUser);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse/warehouse/icons/app_icon.png")));
            stage.setMinHeight(530);
            stage.setMinWidth(850);
            stage.showAndWait();
        } else {
            Shake UsernameShake = new Shake(loginField);
            Shake PasswordShake = new Shake(passwordField);
            UsernameShake.playAnim();
            PasswordShake.playAnim();
        }
    }
}
