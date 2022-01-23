package net.sf.cotelab.preferenceseditor;

import java.util.prefs.BackingStoreException;

import io.github.alantcote.clutilities.javafx.windowprefs.WindowPrefs;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PreferencesEditor extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	protected WindowPrefs windowPrefs = null;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("PreferencesEditor.fxml"));
//			Scene scene = new Scene(root,1000,900);
			Scene scene = new Scene(root);

			root.setUserData(getHostServices()); // covert communication to controller

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Preferences Editor");

			primaryStage.setOnShown(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					try {
						windowPrefs = new WindowPrefs(PreferencesEditor.class, primaryStage);
					} catch (BackingStoreException e) {
						System.err.println("FileTreeViewDemo.start(): caught: " + e.getMessage());
						e.printStackTrace();
						System.err.println("FileTreeViewDemo.start(): continuing . . .");
					}
				}

			});

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
