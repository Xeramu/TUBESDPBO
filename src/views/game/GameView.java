/***********************************************************************
 * Filename : GameView.java
 * Deskripsi :
 *      JFrame utama untuk gameplay yang berfungsi sebagai container
 *      dari GamePanel.
 ***********************************************************************/

package views.game;

//import statemtn
import javax.swing.*;

public class GameView extends JFrame {

    public GameView(String username) {
        setTitle("Hide and Seek - Gameplay");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // GamePanel menerima username dari menu
        add(new GamePanel(username));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
