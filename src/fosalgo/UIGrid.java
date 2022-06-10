package fosalgo;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UIGrid extends JFrame {

    //variables
    private JPanel jContentPane = null;
    private JPanel jPanelNorth = null;
    private Canvas canvas = null;
    private JLabel jLabelNumRows = null;
    private JTextField jTextFieldNumRows = null;
    private JLabel jLabelNumCols = null;
    private JTextField jTextFieldNumCols = null;
    private JButton jButtonReset = null;
    private JButton jButtonSolusiDFS = null;
    private JButton jButtonSolusiBFS = null;

    public UIGrid() {
        initialize();
    }

    private void initialize() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setTitle("UI Grid");
        setSize(800, 600);
        setContentPane(getJContentPane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelNorth(), BorderLayout.NORTH);
            jContentPane.add(getCanvas(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JLabel getJLabelNumRows() {
        if (jLabelNumRows == null) {
            jLabelNumRows = new JLabel("Num Rows");
        }
        return jLabelNumRows;
    }

    private JTextField getJTextFieldNumRows() {
        if (jTextFieldNumRows == null) {
            jTextFieldNumRows = new JTextField();
            jTextFieldNumRows.setPreferredSize(new Dimension(50, 30));
        }
        return jTextFieldNumRows;
    }

    private JLabel getJLabelNumCols() {
        if (jLabelNumCols == null) {
            jLabelNumCols = new JLabel("Num Cols");
        }
        return jLabelNumCols;
    }

    private JTextField getJTextFieldNumCols() {
        if (jTextFieldNumCols == null) {
            jTextFieldNumCols = new JTextField();
            jTextFieldNumCols.setPreferredSize(new Dimension(50, 30));
        }
        return jTextFieldNumCols;
    }
    
    private JButton getJButtonReset() {
        if (jButtonReset == null) {
            jButtonReset = new JButton("Reset Array");
            jButtonReset.setPreferredSize(new Dimension(120, 30));
            jButtonReset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int rows = Integer.parseInt(jTextFieldNumRows.getText().trim());
                    int cols = Integer.parseInt(jTextFieldNumCols.getText().trim());
                    canvas.resetGrid(rows, cols);
                }
            });
        }
        return jButtonReset;
    }

    private JButton getJButtonSolusiDFS() {
        if (jButtonSolusiDFS == null) {
            jButtonSolusiDFS = new JButton("Solusi DFS");
            jButtonSolusiDFS.setPreferredSize(new Dimension(120, 30));
            jButtonSolusiDFS.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    canvas.shortestPathDepthFirstSearch();
                }
            });
        }
        return jButtonSolusiDFS;
    }

    private JButton getJButtonSolusiBFS() {
        if (jButtonSolusiBFS == null) {
            jButtonSolusiBFS = new JButton("Solusi BFS");
            jButtonSolusiBFS.setPreferredSize(new Dimension(120, 30));
            jButtonSolusiBFS.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    canvas.shortestPathBreadthFirstSearch();
                }
            });
        }
        return jButtonSolusiBFS;
    }

    private JPanel getJPanelNorth() {
        if (jPanelNorth == null) {
            jPanelNorth = new JPanel();
            jPanelNorth.setBackground(Color.orange);
            jPanelNorth.setLayout(new FlowLayout());
            jPanelNorth.add(getJLabelNumRows());
            jPanelNorth.add(getJTextFieldNumRows());
            jPanelNorth.add(getJLabelNumCols());
            jPanelNorth.add(getJTextFieldNumCols());
            jPanelNorth.add(getJButtonReset());
            jPanelNorth.add(getJButtonSolusiDFS());
            jPanelNorth.add(getJButtonSolusiBFS());
            
                    
        }
        return jPanelNorth;
    }

    private Canvas getCanvas() {
        if (canvas == null) {
            canvas = new Canvas();
            Handler handler = new Handler(this, canvas);
            this.addKeyListener(handler);
            canvas.addMouseListener(handler);
            canvas.addMouseMotionListener(handler);
            canvas.addMouseWheelListener(handler);
        }
        return canvas;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIGrid ui = new UIGrid();
            }
        });
    }//end of main()

}//end of class
