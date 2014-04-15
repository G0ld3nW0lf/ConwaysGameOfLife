/*
 * Shashank Guduru
 */
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class GUILifeSGuduru7 extends Guduru_Shashank_7_Life implements ActionListener, ChangeListener{

    private javax.swing.Timer timer;
    private int height;
    private int width;
    private int wd = 20;
    private int hd = 20;
    private int bheight;
    private JSlider slider;
    private int bwidth;
    private int delay;
    int[] vertical = {1,1,1,-1,-1,-1,0,0};
    int[] horizontal = {1,0,-1,1,0,-1,1,-1};
    private JMenuItem saveFile, openFile;
    private JFrame window;
    private JPanel p, p2, p3;
    private JButton b1, b2, b3, b4, ngen, cont;
    private Color[][] board = new Color[wd][hd];
    private MySketchPad panel = null;
    private myMouse mouse = new myMouse();
    private Color mainc = Color.BLUE;
    private File input;
    private File output;
    private String sfile;
    private Color[][] currentGen;
    private Color[][] tempGen;

    public GUILifeSGuduru7(String filename){
        super(filename);
        this.currentGen = new Color[wd][hd];
        this.tempGen = new Color[wd][hd];
    }

    public static void main(String[] args) {
        GUILifeSGuduru7 bob = new GUILifeSGuduru7("life100.txt");
        bob.createBoard();
        bob.createWindow();
    }

    private void createBoard(){
        //boolean cordFound = false;
        for(int row = 0; row <= currentGen.length-1; row++){
            for(int col = 0; col <= currentGen[0].length-1; col++){
                currentGen[row][col] = Color.WHITE;
                tempGen[row][col] = Color.WHITE;
            }
        }

    }

    public void runLife(int numGenerations){
        int i = 0;
        do{
            nextGeneration();
            i++;
        }while(i < numGenerations);

    }

    private int getAround(int x, int y){
        int count = 0;
        for(int i = 0; i < 8; i++){
            int row = x + horizontal[i];
            int col = y + vertical[i];
            if((row > -1 && row < currentGen.length) && (col > -1 && col < currentGen[0].length)){
                if(currentGen[row][col] == Color.BLUE){
                    count++;
                }
            }
        }
        //System.out.println(count);
        return count;
    }

    public void nextGeneration(){
        for(int row = 0; row <= currentGen.length-1; row++){
            for(int col = 0; col <= currentGen[0].length-1; col++){
                int around = getAround(row, col);
                if(currentGen[row][col] != Color.BLUE){
                    if(around == 3){
                        tempGen[row][col] = Color.BLUE;
                    }else{
                        tempGen[row][col] = Color.WHITE;
                    }
                }else if (currentGen[row][col] == Color.BLUE){
                    if(around < 2 || around > 3){
                        tempGen[row][col]= Color.WHITE;
                    }else if(around == 2 || around == 3){
                        tempGen[row][col] = Color.BLUE;
                    }
                }
            }
        }
        currentGen = new Color[height][width];
        currentGen = tempGen;
        tempGen = new Color[height][width];
        this.tempGen = new Color[super.height][super.width];
    }

    public void createWindow(){
        window = new JFrame("sampleDraw");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        width = 425;
        height = 427;
        window.setBounds(100, 100, width, 580);
        hd = super.height;
        wd = super.width;
        bheight = 400/hd;
        bwidth = 400/wd;

        window.setResizable(false);

        //jmenu
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("file");
        saveFile = new JMenuItem("save", 's');
        openFile = new JMenuItem("open", 'o');
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        menu.add(fileMenu);
        window.setJMenuBar(menu);

        saveFile.addActionListener(this);
        openFile.addActionListener(this);

        //BoxLayout awesome = new boxLayout(3,1);
        //window.setLayout(awesome);
        //window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        Dimension pD = new Dimension(width,60); 
        Dimension panelD = new Dimension(width,height); 
        panel = new MySketchPad();
        panel.setPreferredSize(panelD);
        panel.setMaximumSize(panelD); 
        panel.setMinimumSize(panelD);
        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);

        b4 = new JButton("Clear");
        b4.addActionListener(this);
        ngen = new JButton("Step");
        ngen.addActionListener(this);
        cont = new JButton("Run");
        cont.addActionListener(this);
        slider = new JSlider(100, 800, 200);
        slider.addChangeListener(this);
        
        p = new JPanel();
        p2 = new JPanel();
        p2.setPreferredSize(pD);
        p2.setMaximumSize(pD); 
        p2.setMinimumSize(pD);

        p3 = new JPanel();
        p3.setPreferredSize(pD);
        p3.setMaximumSize(pD); 
        p3.setMinimumSize(pD);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        TitledBorder tborder = new TitledBorder("Delay");
        p2.setBorder(tborder);
        p2.setLayout(new FlowLayout());
        p2.add(slider);
        p.add(panel);
        p.add(p2);
        p3.add(cont);
        p3.add(b4);
        p3.add(ngen);
        p.add(p3);
        window.add(p);

        window.setVisible(true);
    }

    class MySketchPad extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.WHITE);

            int xcount = 0;
            int ycount = 0;

            int xpos = 0;
            int ypos = 0;
            for(int i = 0; i < wd; i++){
                for(int j = 0; j < hd; j++){
                    if(currentGen[i][j] != Color.WHITE){

                        g2.setColor(currentGen[i][j]);
                    }
                    g2.fillRect(xpos, ypos, bwidth, bheight);

                    ypos += (bheight+1);
                    if(j == 19){
                        ypos = 0;
                    }
                    g2.setColor(Color.WHITE);
                }
                xpos += (bwidth+1);

            }
        }
    }

    class myMouse extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();

            position bob = new position();
            bob = findCorner(x, y);
            x = bob.getX()-1;
            y = bob.getY()-1;
            if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK){
                if(x <= wd && x >= 0 && y <= hd && y >= 0){
                    currentGen[x][y] = mainc;
                    panel.repaint();
                }
            }else if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK){
                if(x <= wd && x >= 0 && y <= hd && y >= 0){
                    currentGen[x][y] = Color.WHITE;
                    panel.repaint();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e){
            //System.out.println("Clicked");
            int x = e.getX();
            int y = e.getY();
            //System.out.println(x + " " +  y);

            position bob = new position();
            bob = findCorner(x, y);
            x = bob.getX()-1;
            y = bob.getY()-1;
            //System.out.println(e.getButton());
            if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK){
                if(x <= wd && x >= 0 && y <= hd && y >= 0){
                    currentGen[x] [y] = mainc;
                    panel.repaint();
                }
            }else if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK){
                if(x <= wd && x >= 0 && y <= hd && y >= 0){
                    currentGen[x] [y] = Color.WHITE;
                    panel.repaint();
                }
            }

        }
    }

    private position findCorner(int x, int y){
        int xpos = 0;
        int tempx = 0;
        for(int i = 0; i < wd; i++){
            if(xpos <= x){
                tempx++;
            }else{
                break;
            }
            xpos += (bwidth+1);
        }

        int ypos = 0;
        int tempy = 0;
        for(int i = 0; i < hd; i++){
            if(ypos <= y){
                tempy++;
            }else{
                break;
            }
            ypos += (bheight+1);
        }
        //System.out.println("X " + tempx + " Y " + tempy);
        position pos = new position(tempx, tempy);
        return pos;
    }

    public void stateChanged(ChangeEvent e) {
        
        if (e.getSource() == slider) {
            delay = (int)slider.getValue();
            timer.setDelay(delay);
        }    
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b1){
            JColorChooser cc = new JColorChooser(Color.blue);
            mainc = cc.showDialog(null, "cc", Color.blue);
        }else if(e.getSource() == b4){
            clearBoard();
            timer.stop();
            panel.repaint();
        }else if(e.getSource() == ngen){
            nextGeneration();
            panel.repaint();
        }else if(e.getSource() == cont){
            timer = new javax.swing.Timer(delay, this);
            timer.start();

            //panel.repaint();
        }else if(e.getSource() == timer){
            
            nextGeneration();
            panel.repaint();
        }
        if(e.getSource() == openFile){
            JFileChooser bill = new JFileChooser();

            try{
                bill.showOpenDialog(null);
                input = bill.getSelectedFile();
                readPPM();
                panel.repaint();
            }catch(NullPointerException ee){
                //ee.printStackTrace();
            }
        }

        if(e.getSource() == saveFile){
            JFileChooser bill = new JFileChooser();

            try{
                //input = bill.getSelectedFile();
                bill.showSaveDialog(null);
                String dir = bill.getCurrentDirectory().getName();
                //sfile = bill.getSelectedFile().getName();
                //System.out.println(sfile);
                output = bill.getSelectedFile();
                savePPM();

                //panel.repaint();
            }catch(NullPointerException ee){
                //ee.printStackTrace();
            }
        }
    }

    public void clearBoard(){
        for(int i = 0; i < wd; i++){
            for(int j = 0; j < hd; j++){
                currentGen[i][j] = Color.WHITE;
            }
        }
    }

    public void readPPM(){
        try{
            Scanner ppm = new Scanner(input);
            ppm.nextLine();
            ppm.nextLine();
            ppm.nextLine();
            for(int i = 0; i < wd; i++){
                for(int j = 0; j < hd; j++){
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    if(ppm.hasNext()){
                        red = ppm.nextInt();
                    }

                    if(ppm.hasNext()){
                        green = ppm.nextInt();
                    }

                    if(ppm.hasNext()){
                        blue = ppm.nextInt();
                    }
                    Color stuff = new Color(red, green, blue);
                    currentGen[i][j] = stuff;
                }
            }

        }catch(FileNotFoundException e){
            e.getStackTrace();
        }
    }   

    public void savePPM(){
        //File soutput = new File(sfile);

        FileWriter writer = null;
        try{

            writer = new FileWriter(output);
            writer.write("P3\r\n" + super.width + " " + super.height+"\r\n255\r\n");
            for(int i = 0; i < wd; i++){
                for(int j = 0; j < hd; j++){
                    int red = board[i][j].getRed();
                    int green = board[i][j].getGreen();
                    int blue = board[i][j].getBlue();
                    writer.write(red + " " + green + " " + blue + " ");
                }
                writer.write("\r\n");
            }
            //output.renameTo(new File(sfile));
            writer.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }

    }
}

class position{
    private int x;
    private int y;

    public position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public position(){
        this.x = 0;
        this.y = 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

class Guduru_Shashank_7_Life
{
    private ArrayList<Cell> input = new ArrayList<Cell>();
    private Comparable[][] currentGen;
    private Comparable[][] tempGen;
    int[] vertical = {1,1,1,-1,-1,-1,0,0};
    int[] horizontal = {1,0,-1,1,0,-1,1,-1};
    int height;
    int width;
    public Guduru_Shashank_7_Life(String fileName){
        File file = new File(fileName);
        int size;
        Scanner filer = null;
        try{
            filer = new Scanner(file);
            height = filer.nextInt();
            width = filer.nextInt();

            currentGen = new Comparable[height][width];
            tempGen = new Comparable[height][width];
            while(filer.hasNext()){
                int x = filer.nextInt();
                int y = filer.nextInt();
                Cell cell = new Cell(x, y);
                input.add(cell);
            }
        }catch(java.io.FileNotFoundException E){
            System.out.println("can't find the file, sorry");
            System.out.println(E.getMessage());
        }

        createBoard();
    }

    private void createBoard(){
        for(int i = 0; i <= input.size()-1; i++){
            int x = input.get(i).getX();
            int y = input.get(i).getY();
            for(int row = 0; row <= currentGen.length-1; row++){
                for(int col = 0; col <= currentGen[0].length-1; col++){
                    if(row == x && col == y){
                        currentGen[x][y] = '*';
                        break;
                    }
                }
            }
        }
    }
    
    public int getInputSize(){
        return input.size();
    }
    
    public Cell inputGet(int i){
        return input.get(i);
    }
    
    public void runLife(int numGenerations){
        int i = 0;
        do{
            nextGeneration();
            i++;
        }while(i < numGenerations);
        print();
    }

    public int rowCount(int row){
        int count = 0;

        for(int col = 0;  col < currentGen[0].length; col++){
            if(currentGen[row][col] == '*'){
                count++;
            }
        }

        return count;
    }

    public int colCount(int col){
        int count = 0;
        for(int row = 0; row <= currentGen.length-1; row++){
            if(currentGen[row][col] == '*'){
                count++;
            }
        }
        return count;
    }

    public int totalCount(){
        int count = 0;
        
        for(int row = 0; row <= currentGen.length-1; row++){
            for(int col = 0; col <= currentGen[0].length-1; col++){
                if(currentGen[row][col] =='*'){
                    count++;
                }
            }
        }
        return count;
    }

    public void nextGeneration(){
        for(int row = 0; row <= currentGen.length-1; row++){
            for(int col = 0; col <= currentGen[0].length-1; col++){
                int around = getAround(row, col);
                if(currentGen[row][col] != '*'){
                    if(around == 3){
                        tempGen[row][col] = '*';
                    }else{
                        tempGen[row][col] = 32;
                    }
                }else if (currentGen[row][col] == '*'){
                    if(around < 2 || around > 3){
                        tempGen[row][col]= 32;
                    }else if(around == 2 || around == 3){
                        tempGen[row][col] = '*';
                    }
                }
            }
        }
        currentGen = new Comparable[height][width];
        currentGen = tempGen;
        tempGen = new Comparable[height][width];
    }

    private int getAround(int x, int y){
        int count = 0;
        for(int i = 0; i < 8; i++){
            int row = x + horizontal[i];
            int col = y + vertical[i];
            if((row > -1 && row < currentGen.length) && (col > -1 && col < currentGen[0].length)){
                if(currentGen[row][col] == '*'){
                    count++;
                }
            }
        }
        //System.out.println(count);
        return count;
    }

    public void print(){
        int count = 0;
        int i = 0;
        System.out.print("  ");
        while(i < currentGen.length){
            if(count < 10){
                System.out.printf("%2d", count);
                count += 1;
            }else if(count == 10){
                System.out.printf("%2d", 0);
                count += 1;
            }else if(count > 10){
                count = 1;
                System.out.printf("%2d", count);
                count +=1;
            }
            i+=1;
        }
        System.out.println();
        for(int row = 0; row < currentGen.length; row++){
            count = row;
            System.out.printf("%2d", count);
            for(int col = 0; col < currentGen.length; col++){
                Comparable star = currentGen[row][col];
                if(star == '*'){
                    System.out.printf("%2c", star);
                }else{
                    System.out.print("  ");
                }

            }
            System.out.println();
        }
        
        System.out.println("number of living cells in col 9 --> " + colCount(9));
        System.out.println("number of living cells in row 9 --> " + rowCount(9));
        System.out.println("number of living cells total --> " + totalCount());
    }

    public class Cell{
        private int myX;
        private int myY;
        public Cell(int x, int y){
            myX = x;
            myY = y;
        }

        public int getX(){
            return myX;
        }

        public int getY(){
            return myY;
        }
    }

    public static void main(String[]args){
        Guduru_Shashank_7_Life bob = new Guduru_Shashank_7_Life("life100.txt");
        bob.print();
        bob.runLife(10);
        
    }
}

