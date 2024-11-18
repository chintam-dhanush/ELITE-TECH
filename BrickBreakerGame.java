import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class MapGenrator{
     int Map[][];
     int brickweidth;
     int brickheight;

     MapGenrator(int row,int col){
       Map=new int[row][col];
       for(int i=0;i<Map.length;i++)
         for(int j=0;j<Map[0].length;j++)
          Map[i][j]=1;
          brickweidth=540/col;
          brickheight=150/row;
        }
     
        void draw(Graphics2D g){
            
        for(int i=0;i<Map.length;i++)
            for(int j=0;j<Map[0].length;j++){
                if(Map[i][j]>0)
                {
                    g.setColor(Color.white);
                    g.fillRect(j*brickweidth+80, i*brickheight+50, brickweidth, brickheight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickweidth+80, i*brickheight+50, brickweidth, brickheight);
                }
            } 
        }

        void brickvalue(int value,int row,int col)
        {
            Map[row][col]=value;
        }

}


class gameplay extends JPanel implements KeyListener, ActionListener {
    boolean play = false;
    int score = 0;
    int totalBricks = 21;
    int playerx=310;
    Timer timer;
    int delay = 8;
    int ballXPos = 220;
    int ballYPos = 350;
    int ballXDir = -2; 
    int ballYDir = -4;
    MapGenrator map;

    public gameplay() {
        map=new MapGenrator(3,7);  
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);  
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592); 

        map.draw((Graphics2D)g);
       

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 2, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(685, 0, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("MV Boli",Font.BOLD,20));
        g.drawString(""+score, 590, 30);

        if(totalBricks<=0)
        {
            play=false;
            ballXDir=0;
            ballYDir=0;
        g.setColor(Color.GREEN);
        g.setFont(new Font("MV Boli",Font.BOLD,25));
        g.drawString("You Won,Score:"+score, 190, 300);
            
        g.setFont(new Font("MV Boli",Font.BOLD,20));
        g.drawString("Press Enter to Restart", 230, 350);

        }



        if(ballYPos>570){
            play=false;
            ballXDir=0;
            ballYDir=0;
        g.setColor(Color.RED);
        g.setFont(new Font("MV Boli",Font.BOLD,25));
        g.drawString("Game Over,Score:"+score, 190, 300);
            
        g.setFont(new Font("MV Boli",Font.BOLD,20));
        g.drawString("Press Enter to Restart", 230, 350);


        }
   
 
        g.setColor(Color.GREEN);
        g.fillRect(playerx,550,100,8);
        

        g.setColor(Color.BLUE);
        g.fillOval(ballXPos,ballYPos,20,20);

        g.dispose();
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
          if(new Rectangle(ballXPos,ballYPos,20,20).intersects(new Rectangle(playerx,550,100,8)))
               ballYDir=-ballYDir;

           A:for(int i=0;i<map.Map.length;i++){
              for(int j=0;j<map.Map[0].length;j++)
               if(map.Map[i][j]>0)
               {
                  int brickx=j*map.brickweidth+80;
                  int bricky=i*map.brickheight+50;
                  int brickweidth=map.brickweidth;
                  int brickheight=map.brickheight;
                  
                  Rectangle rect=new Rectangle(brickx,bricky,brickweidth,brickheight);
                  Rectangle ballrect=new Rectangle(ballXPos,ballYPos,20,20);
                  Rectangle brickrect=rect;

                  if(ballrect.intersects(brickrect)){
                     map.brickvalue(0,i,j);
                     totalBricks--;
                     score+=10;

                     if(ballXPos+19<=brickrect.x||ballXPos+1>=brickrect.x+brickrect.width)
                      ballXDir=-ballXDir;
                      else
                      ballYDir=-ballYDir;
                      break A;
                  }

               }
            }
            ballXPos+=ballXDir;
            ballYPos+=ballYDir;
            if(ballXPos<0||ballXPos>670)
             ballXDir=-ballXDir;
            if(ballYPos<0)
              ballYDir=-ballYDir; 
        }
       
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
          if(playerx>=580)
          playerx=580;
          else
           moveright();

         if(e.getKeyCode()==KeyEvent.VK_LEFT)
           if(playerx<=10)
           playerx=10;
           else
            moveleft();
            
            if(e.getKeyCode()==KeyEvent.VK_ENTER)
            {
                if(!play){
                  play=true;
                  ballXPos=220;
                  ballYPos=350;
                  ballXDir=-2;
                  ballYDir=-4;
                  playerx=310;
                  score=0;
                  totalBricks=21;
                  map=new MapGenrator(3, 7);
                  repaint();

                }
            }   
    }
   
    public void moveright()
    {
        play=true;
        playerx+=20;
    }
    public void moveleft()
    {
        play=true;
        playerx-=20;
    }


}

class BrickBreakerGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Game");
        gameplay gamePlay = new gameplay();  
        frame.setBounds(10, 10, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePlay);
        frame.setVisible(true);
    }
}
