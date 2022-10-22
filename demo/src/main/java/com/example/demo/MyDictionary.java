package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;

public class MyDictionary extends Application {

    private Group tileGroup = new Group();
    int xLine = 20;
    int yLine = 20;
    int yline2 = 50;

    DictionaryUsingHashMap dictionary;

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(400,300);
        root.getChildren().addAll(tileGroup);

        dictionary = new DictionaryUsingHashMap();

        TextField wordText = new TextField("Ayasha");
        wordText.setTranslateX(xLine);
        wordText.setTranslateY(yLine);

        Label meaningLabel = new Label("I am meaning");
        meaningLabel.setTranslateX(xLine);
        meaningLabel.setTranslateY(yline2);


        Button searchButton = new Button("Search");
        searchButton.setTranslateX(xLine+200);
        searchButton.setTranslateY(yLine);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word = wordText.getText();
                if(word.isBlank()){
                    meaningLabel.setText("Please enter a wod");
                }
                else{
                    meaningLabel.setText(dictionary.findMeaning(word));
                }

            }
        });


        tileGroup.getChildren().addAll(wordText, searchButton, meaningLabel);
        return  root;
    }

    public void connectToDatabase(){
        final String DB_URL = "jdbc:mysql://localhost:3306/my_diction";
        final String USER = "root";
        final String PASS = "1HarryStyles*";

        System.out.println("Connecting to database");
        String newId = "select * from wordlist";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(newId);
        ) {
            while (rs.next()) {
                //Display values
                System.out.println(rs.getInt("id") + rs.getString("word") + rs.getString("meaning")); //rs.getInt("rollno");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        connectToDatabase();
        Scene scene = new Scene(createContent());
        stage.setTitle("My Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}