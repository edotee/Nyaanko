package gui;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import url.SimpleNyaaUrl;

import java.net.URL;
import java.util.HashSet;

/**
 * @author edotee
 */
public class NyaankoGUI extends Application {

    private final double WIDTH = 854.0f;
    private final double HEIGHT = 480.0f;

    private URL nyaaRSS;

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        nyaaRSS = SimpleNyaaUrl.builder().cat(SimpleNyaaUrl.Category.ANIME.EN)
                //.page(ImmutableNyaaUrl.Page.RSS)         // default value
                //.filter(ImmutableNyaaUrl.Filter.TRUSTED) // default value
                .age(0, 7)
                .user(64513)
                .build()
                .newInstanceOfRssFeed();
        mainStage = stage;
        mainStage.setTitle("Nyaanko");
        mainStage.setMinWidth(WIDTH);
        mainStage.setMinHeight(HEIGHT);

        TextField textField = new TextField(nyaaRSS.toString());

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        mainLayout.setCenter(textField);
        mainLayout.setBottom(numberFieldO("Enter Things Here!"));

        Scene mainScene = new Scene(mainLayout);
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    private void composeNyaaSearchFormular() {

        SimpleNyaaUrl.Page page;
        SimpleNyaaUrl.Filter filter;
        SimpleNyaaUrl.NyaaCategory category;
        int userID, minAge, maxAge, minSize, maxSize;
        HashSet<Integer> exclude;
        String search;

        String normalSearch;
        String rssFeed;
    }

    private TextField numberFieldO(String promptText) {
        String regex = "[0-9]";
        TextField result = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                // If the replaced text would end up being invalid, then simply
                // ignore this call!
                if(text.matches(regex)) {
                    super.replaceText(start, end, text);
                }
                System.out.println("change detected");
            }

            @Override
            public void replaceSelection(String text) {
                if(text.matches(regex)) {
                    super.replaceSelection(text);
                }
                System.out.println("change detected");
            }
        };
        result.promptTextProperty().set(promptText);

        return result;
    }

    /** FAILS */
    private TextField numberField(String promptText) {
        TextField result = new TextField();
        result.promptTextProperty().set(promptText);

        result.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println( "Observable:\t" + observable.toString()
                            + "\nold Value:\t" + oldValue
                            + "\nnew Value:\t" + newValue
                    );

                    String validOld = oldValue;
                    String regex = "[0-9]";

                    if( !oldValue.matches(regex) ) {
                        validOld = "";
                        ((StringProperty)observable).set(validOld);
                    }

                    if( newValue.matches(regex) )
                        ((StringProperty)observable).set(newValue);
                    else {
                        ((StringProperty) observable).setValue(validOld);
                    }

                    System.out.println( "live value:\t" + observable.toString() );
                }
        );

        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
