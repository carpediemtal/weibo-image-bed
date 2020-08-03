package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox(20);
        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);
        vBox.getChildren().addAll(hBox1, hBox2);


        Label label = new Label("Files Directory");
        TextField textField = new TextField();
        textField.setMinWidth(215);


        Button browseBtn = new Button("Browse");
        final File[] directory = new File[1];
        browseBtn.setOnAction(actionEvent -> {
            directory[0] = chooseDirectory(primaryStage);
            textField.setText(directory[0].toString());
        });

        Button helpBtn = new Button("Help");
        helpBtn.setOnAction(actionEvent -> showHelp());

        Button executeBtn = new Button("Execute");
        executeBtn.setOnAction(actionEvent -> {
            System.out.println("executing");
            try {
                MarkDownModify.addReferrerPolicy(directory[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(actionEvent -> primaryStage.close());

        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.getChildren().addAll(label, textField, browseBtn);

        hBox2.setAlignment(Pos.BASELINE_RIGHT);
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.getChildren().addAll(helpBtn, executeBtn, exitBtn);

        Scene scene = new Scene(vBox, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add referrer policy");
        primaryStage.show();
    }

    private File chooseDirectory(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory");
        return directoryChooser.showDialog(primaryStage);
    }

    private void showHelp() {
        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(10, 10, 10, 10));
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.setLineSpacing(15);

        Text title = new Text("Help\n");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Text content = new Text("Choose your md files directory by clicking 'Browse' button, and click the 'Execute' button, then all of you <img> labels will be added a referrer policy.");

        textFlow.getChildren().addAll(title, content);

        Scene scene = new Scene(textFlow, 300, 200);
        Stage helpStage = new Stage();
        helpStage.setScene(scene);
        helpStage.setTitle("Help");
        helpStage.show();
    }
}
