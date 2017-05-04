package gui;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedButton;
import url.NyaaUrl;
import url.SimpleNyaaUrl;

import java.net.URL;

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
        mainStage = stage;
        mainStage.setTitle("Nyaanko");
        mainStage.setMinWidth(WIDTH);
        mainStage.setMinHeight(HEIGHT);


        Scene mainScene = new Scene(  initMainLayout()  );


        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    private Parent initMainLayout() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(WIDTH, HEIGHT);
        mainLayout.setTop(wtf());
        mainLayout.setCenter(categoryPicker());
        mainLayout.setBottom(filterPicker());
        return mainLayout;
    }

    private Node categoryPicker() {
        VBox result = new VBox();
        result.setAlignment(Pos.CENTER);
        for(NyaaUrl.MainCategory mc : NyaaUrl.Category.values())
            result.getChildren().add( categoryPane(mc) );
        return result;
    }

    private TitledPane categoryPane(NyaaUrl.MainCategory maincat) {

        SegmentedButton sb_category = new SegmentedButton();
        sb_category.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        for(NyaaUrl.SubCategory sc : maincat.subcatsAsArray())
            sb_category.getButtons().add( new ToggleButton(sc.getFullName()) );

        TitledPane result = new TitledPane();
        result.setAlignment(Pos.CENTER);
        result.setText(maincat.getName());
        result.setContent(sb_category);
        result.expandedProperty().setValue(false);

        return result;
    }

    private Node bottom() {
        VBox result = new VBox();
        result.setAlignment(Pos.CENTER);
        for(NyaaUrl.MainCategory mc : NyaaUrl.Category.values())
            result.getChildren().addAll( dynamicallyPopulatedCategoryButtons(mc) );
        return result;
    }

    private SegmentedButton dynamicallyPopulatedCategoryButtons(NyaaUrl.MainCategory maincat) {
        SegmentedButton sb_category = new SegmentedButton();
        sb_category.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        for( NyaaUrl.NyaaCategory nc : maincat.subcatsAsArray() )
            sb_category.getButtons().add(new ToggleButton(nc.getFullName()));
        return sb_category;
    }

    private Node filterPicker() {
        SegmentedButton segmentedButton = new SegmentedButton();
        segmentedButton.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        for(NyaaUrl.Filter f : NyaaUrl.Filter.values()) {
            ToggleButton tb = new ToggleButton(f.getName());
            tb.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(tb, Priority.ALWAYS);
            segmentedButton.getButtons().add( tb );
        }

        segmentedButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(segmentedButton, Priority.ALWAYS);

        HBox result = new HBox();
        result.setAlignment(Pos.CENTER);
        result.getChildren().add(segmentedButton);
        return result;
    }

    private TextField wtf() {
        nyaaRSS = SimpleNyaaUrl.builder().cat(SimpleNyaaUrl.Category.AnimeSubCategory.EN)
                //.page(NyaaUrl.Page.RSS)         // default value
                //.filter(NyaaUrl.Filter.TRUSTED) // default value
                .age(0, 7)
                .user(64513)
                .build()
                .newInstanceOfRssFeed();

        return new TextField(nyaaRSS.toString());
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
