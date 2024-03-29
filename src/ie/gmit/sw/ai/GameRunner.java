package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 100;
	private static final int IMAGE_COUNT = 14;
	private ControlledSprite player;
	private GameView view;
	private Maze model;
	private int currentRow;
	private int currentCol;
	// Whenever the player presses a direction, we will set the orientation to that direction
	private int orientation; //0=south 1=west, 2=north, 3=east
	
	public GameRunner() throws Exception{
		model = new Maze(MAZE_DIMENSION);
    	view = new GameView(model);
    	
    	Sprite[] sprites = getSprites();
    	view.setSprites(sprites);
    	
    	placePlayer();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
	}
	
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model.set(currentRow, currentCol, '5'); //Player is at index 5
    	updateView(); 		
	}
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}

    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && currentCol < MAZE_DIMENSION - 1) {
        	orientation = 3;
        	if (isValidMove(currentRow, currentCol + 1)){
				player.setDirection(Direction.RIGHT);
				currentCol++; 
        	}   		
        }else if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && currentCol > 0) {
        	orientation = 1;
        	if (isValidMove(currentRow, currentCol - 1)) {
				player.setDirection(Direction.LEFT);
				currentCol--;	
			}
        }else if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && currentRow > 0) {
        	orientation = 2;
        	if (isValidMove(currentRow - 1, currentCol)) {
				player.setDirection(Direction.UP);
				currentRow--;
			}
        }else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && currentRow < MAZE_DIMENSION - 1) {
        	orientation = 0;
        	if (isValidMove(currentRow + 1, currentCol)){
        		player.setDirection(Direction.DOWN);
				currentRow++;
        	}         	  	
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
        	interactWithBlock(currentRow, currentCol);
        }else{
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
   
	private boolean isValidMove(int row, int col){
		if (row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col) == ' '){
			model.set(currentRow, currentCol, '\u0020');
			model.set(row, col, '5');
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	private void interactWithBlock(int row, int col) {
		// Depending on orientation, detect what type of block the player wants to interact with
		if(orientation == 0) {
			// If the block south of player is...
			if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0032') {
				QuestionBlock();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0031') {
				Sword();
				model.set(row+1, col, '0'); //Get rid of sword
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0033') {
				Bomb();
				model.set(row+1, col, '0'); //Get rid of bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0034') {
				HBomb();
				model.set(row+1, col, '0'); //Get rid of h-bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0036') {
				BlackSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0037') {
				BlueSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0038') {
				BrownSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u0039') {
				GreenSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u003A') {
				GreySpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u003B') {
				OrangeSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u003C') {
				RedSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row+1, col) == '\u003D') {
				YellowSpider();
			}
		}else if(orientation == 1) {
			// If the block west of the player is...
			if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0032') {
				QuestionBlock();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0031') {
				Sword();
				model.set(row, col-1, '0'); //Get rid of sword
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0033') {
				Bomb();
				model.set(row, col-1, '0'); //Get rid of bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0034') {
				HBomb();
				model.set(row, col-1, '0'); //Get rid of h-bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0036') {
				BlackSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0037') {
				BlueSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0038') {
				BrownSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u0039') {
				GreenSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u003A') {
				GreySpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u003B') {
				OrangeSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u003C') {
				RedSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col-1) == '\u003D') {
				YellowSpider();
			}
		}else if(orientation == 2) {
			// If the block north of the player is...
			if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0032') {
				QuestionBlock();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0031') {
				Sword();
				model.set(row-1, col, '0'); //Get rid of sword
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0033') {
				Bomb();
				model.set(row-1, col, '0'); //Get rid of bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0034') {
				HBomb();
				model.set(row-1, col, '0'); //Get rid of h-bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0036') {
				BlackSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0037') {
				BlueSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0038') {
				BrownSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u0039') {
				GreenSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u003A') {
				GreySpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u003B') {
				OrangeSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u003C') {
				RedSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row-1, col) == '\u003D') {
				YellowSpider();
			}
		}else if(orientation == 3) {
			// If the block east of the player is...
			if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0032') {
				QuestionBlock();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0031') {
				Sword();
				model.set(row, col+1, '0'); //Get rid of sword
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0033') {
				Bomb();
				model.set(row, col+1, '0'); //Get rid of bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0034') {
				HBomb();
				model.set(row, col+1, '0'); //Get rid of h-bomb
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0036') {
				BlackSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0037') {
				BlueSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0038') {
				BrownSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u0039') {
				GreenSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u003A') {
				GreySpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u003B') {
				OrangeSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u003C') {
				RedSpider();
			}else if(row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col+1) == '\u003D') {
				YellowSpider();
			}
		}
	}
	
	private void QuestionBlock() {
		JOptionPane.showMessageDialog(null, "Display information");
	}
	
	private void Sword() {
		JOptionPane.showMessageDialog(null, "Attack +1");
	}
	
	private void Bomb() {
		JOptionPane.showMessageDialog(null, "Little BOOM!");
	}
	
	private void HBomb() {
		JOptionPane.showMessageDialog(null, "Big BOOM!");
	}
	
	private void BlackSpider() {
		JOptionPane.showMessageDialog(null, "It's a Black Spider");
	}
	
	private void BlueSpider() {
		JOptionPane.showMessageDialog(null, "It's a Blue Spider");
	}
	
	private void BrownSpider() {
		JOptionPane.showMessageDialog(null, "It's a Brown Spider");
	}
	
	private void GreenSpider() {
		JOptionPane.showMessageDialog(null, "It's a Green Spider");
	}
	
	private void GreySpider() {
		JOptionPane.showMessageDialog(null, "It's a Grey Spider");
	}
	
	private void OrangeSpider() {
		JOptionPane.showMessageDialog(null, "It's a Orange Spider");
	}
	
	private void RedSpider() {
		JOptionPane.showMessageDialog(null, "It's a Red Spider");
	}
	
	private void YellowSpider() {
		JOptionPane.showMessageDialog(null, "It's a Yellow Spider");
	}
	
	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		
		player = new ControlledSprite("Main Player", 3, "resources/images/player/d1.png", "resources/images/player/d2.png", "resources/images/player/d3.png", "resources/images/player/l1.png", "resources/images/player/l2.png", "resources/images/player/l3.png", "resources/images/player/r1.png", "resources/images/player/r2.png", "resources/images/player/r3.png");
		
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", 1, "resources/images/objects/hedge.png");
		sprites[1] = new Sprite("Sword", 1, "resources/images/objects/sword.png");
		sprites[2] = new Sprite("Help", 1, "resources/images/objects/help.png");
		sprites[3] = new Sprite("Bomb", 1, "resources/images/objects/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", 1, "resources/images/objects/h_bomb.png");
		sprites[5] = player;
		sprites[6] = new Sprite("Black Spider", 2, "resources/images/spiders/black_spider_1.png", "resources/images/spiders/black_spider_2.png");
		sprites[7] = new Sprite("Blue Spider", 2, "resources/images/spiders/blue_spider_1.png", "resources/images/spiders/blue_spider_2.png");
		sprites[8] = new Sprite("Brown Spider", 2, "resources/images/spiders/brown_spider_1.png", "resources/images/spiders/brown_spider_2.png");
		sprites[9] = new Sprite("Green Spider", 2, "resources/images/spiders/green_spider_1.png", "resources/images/spiders/green_spider_2.png");
		sprites[10] = new Sprite("Grey Spider", 2, "resources/images/spiders/grey_spider_1.png", "resources/images/spiders/grey_spider_2.png");
		sprites[11] = new Sprite("Orange Spider", 2, "resources/images/spiders/orange_spider_1.png", "resources/images/spiders/orange_spider_2.png");
		sprites[12] = new Sprite("Red Spider", 2, "resources/images/spiders/red_spider_1.png", "resources/images/spiders/red_spider_2.png");
		sprites[13] = new Sprite("Yellow Spider", 2, "resources/images/spiders/yellow_spider_1.png", "resources/images/spiders/yellow_spider_2.png");
		return sprites;
	}
	
	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}