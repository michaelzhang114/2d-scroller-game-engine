package Screens.AccTabs;

import javafx.scene.control.Tab;

import java.util.function.Consumer;

/**
 * This class extends the JavaFx Tab class to add a lambda that will allow the display screen to be changed when a
 * tab is selected. The class simply adds the use of a lambda to the pre built Tab class, so it is very simple and works
 * in the same way as the general Tab class.
 *
 * @author Eric Werbel
 */
public class MyTab extends Tab {

    Consumer<Tab> mySelectionLambda;

    public MyTab() {

    }

    /**
     * Sets the lambda associated with the current tab to update the screen to the correct display.
     *
     * @param myLambda = consumer that will update the screen when the tab is selected
     */
    public void setSelectionLambda(Consumer<Tab> myLambda) {
        mySelectionLambda = myLambda;
    }

    /**
     * Runs the tab's lambda function that was previously set up to update the screen display.
     */
    public void selected() {
        mySelectionLambda.accept(this);
    }

}
