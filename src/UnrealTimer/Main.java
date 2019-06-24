package UnrealTimer;

import com.tulskiy.keymaster.common.Provider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("WeakerAccess")
public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    // https://github.com/tulskiy/jkeymaster
    public static final Provider keyShortcutsProvider = Provider.getCurrentProvider(false);

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            if (keyShortcutsProvider == null) {
                throw new Exception("Undefined key event listener, platform is not supported");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_window.fxml"));
            loader.setController(new MainController());
            Pane root = loader.load();

            primaryStage.setTitle("Unreal Timer");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 225, 330));
            primaryStage.show();

        } catch (Exception e) {
            log.error("Cannot create main window.", e);
            Platform.exit();
        }
    }

    /**
     * Stop key event listener when program terminate
     */
    @Override
    public void stop() throws Exception {
        if (keyShortcutsProvider != null) {
            keyShortcutsProvider.reset();
            keyShortcutsProvider.stop();
            System.out.println("keyListener was stoped");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
