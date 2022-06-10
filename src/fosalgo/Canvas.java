package fosalgo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    //variables
    private int[][] labyrinth = {
        {-1, -1, 0, 0, 0, 0, 0, 0, -1, -1, -1},
        {-1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0},
        {0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0},
        {0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0},
        {0, -1, -1, 0, 0, -1, 0, 0, 0, 0, 0},
        {0, 0, -1, 0, 0, -1, -1, -1, -1, -1, -1}
    };

    private int cellSize = 40;
    private double translateX;
    private double translateY;
    private double scale;

    private Titik start = new Titik(0, 0);//null;
    private Titik finish = new Titik(0, 0);//null;
    private ArrayList<Titik[]> shortestPathSolution = null;
    private String stateSolusi = "DFS";//DFS | BFS
    
    Canvas() {
        translateX = 0;
        translateY = 0;
        scale = 1;
        setOpaque(false);
        setDoubleBuffered(true);
    }
    
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int[][] getLabyrinth() {
        return labyrinth;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Titik getStart() {
        return start;
    }

    public void setStart(Titik start) {
        this.start = start;
    }

    public Titik getFinish() {
        return finish;

    }

    public void setFinish(Titik finish) {
        this.finish = finish;
    }

    public void resetGrid(int rows, int cols) {
        labyrinth = new int[rows][cols];
        start = new Titik(0, 0);//null;
        finish = new Titik(0, 0);//null;
        shortestPathSolution = null;
        repaint();
    }

    public void setStart(int x, int y) {
        this.start = new Titik(x, y);
    }

    public void setFinish(int x, int y) {
        this.finish = new Titik(x, y);
    }
    
    ///PENCARIAN SOLUSI DFS dan BFS---------------------------------------------
    public void shortestPathDepthFirstSearch() {
        if (labyrinth != null && start != null && finish != null) {
            shortestPathSolution = Pathfinder.depthFirstSearch(labyrinth, start, finish);
            stateSolusi = "DFS";
            repaint();
        }
    }
    
    public void shortestPathBreadthFirstSearch() {
        if (labyrinth != null && start != null && finish != null) {
            shortestPathSolution = Pathfinder.breadthFirstSearch(labyrinth, start, finish);
            stateSolusi = "BFS";
            repaint();
        }
    }
    
    public void solve() {
        if (stateSolusi.equals("DFS")) {
            shortestPathDepthFirstSearch();
        } else if (stateSolusi.equals("BFS")) {
            shortestPathBreadthFirstSearch();
        }
    }
    //--------------------------------------------------------------------------
    
    @Override
    public void paint(Graphics g) {
        //---------------------------------------------------------------------------------------------------      
        AffineTransform tx = new AffineTransform();
        tx.translate(translateX, translateY);
        tx.scale(scale, scale);
        //---------------------------------------------------------------------------------------------------   
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //---------------------------------------------------------------------------------------------------   
        g2d.setTransform(tx);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));
        //gambar labyrinth
        int rows = labyrinth.length;
        int cols = labyrinth[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = labyrinth[i][j];
                if (value == -1) {
                    g2d.setColor(Color.decode("#34495e"));
                } else if (value == 0) {
                    g2d.setColor(Color.decode("#ecf0f1"));
                }
                g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g2d.setColor(Color.decode("#95a5a6"));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
        
        //gambar shortest path
        if (shortestPathSolution != null) {
            for (Titik[] edge : shortestPathSolution) {
                Titik origin = edge[0];
                Titik destination = edge[1];

                g2d.setColor(Color.decode("#3498db"));
                g2d.fillRect(destination.y * cellSize, destination.x * cellSize, cellSize, cellSize);

                int cx1 = origin.x * cellSize + (int) (0.5 * cellSize);
                int cy1 = origin.y * cellSize + (int) (0.5 * cellSize);
                int cx2 = destination.x * cellSize + (int) (0.5 * cellSize);
                int cy2 = destination.y * cellSize + (int) (0.5 * cellSize);
                g2d.setColor(Color.decode("#e67e22"));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(cy1, cx1, cy2, cx2);
            }
        }
        
        //gambar titik start dan finish
        if (finish != null && finish.x >= 0 && finish.x < labyrinth.length && finish.y >= 0 && finish.y < labyrinth[0].length) {
            g2d.setColor(Color.decode("#1abc9c"));
            g2d.fillRect(finish.y * cellSize, finish.x * cellSize, cellSize, cellSize);
            g2d.setColor(Color.decode("#95a5a6"));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(finish.y * cellSize, finish.x * cellSize, cellSize, cellSize);
        }
        if (start != null && start.x >= 0 && start.x < labyrinth.length && start.y >= 0 && start.y < labyrinth[0].length) {
            g2d.setColor(Color.decode("#e74c3c"));
            g2d.fillRect(start.y * cellSize, start.x * cellSize, cellSize, cellSize);
            g2d.setColor(Color.decode("#95a5a6"));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(start.y * cellSize, start.x * cellSize, cellSize, cellSize);
        }
        
        //---------------------------------------------------------------------------------------------------
        g2d.dispose();
    }//END of PAINT

}
