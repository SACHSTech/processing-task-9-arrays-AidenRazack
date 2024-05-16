import processing.core.PApplet;

public class Sketch extends PApplet {
	
  /**
  * A. Razack
  * A program that draws colored stickment and hearts 
  */
  float[] snowflakeX;
  float[] snowflakeY;
  float[] snowflakeSpeed;
  float snowSpeed;
  float playerX;
  float playerY;
  int playerLives;
  boolean keyLeft, keyRight, keyUp, keyDown;
  boolean[] ballHideStatus;
  
  public void settings() {
    // set the number of snowflakes, their initial size, speed as random, and whether they're hidden or not
    size(800, 800);
    snowflakeX = new float[100]; 
    snowflakeY = new float[100]; 
    snowflakeSpeed = new float[snowflakeX.length];
    ballHideStatus = new boolean[snowflakeX.length];
    for (int i = 0; i < snowflakeX.length; i++) {
      snowflakeX[i] = random(width);
      snowflakeY[i] = random(height);
      snowflakeSpeed[i] = random(1, 3); 
    }
    snowSpeed = 1;

    // set the player lives and size
    playerX = width / 2;
    playerY = height - 50;
    playerLives = 3;

    // set key pressed varibles to false
    keyLeft = false;
    keyRight = false;
    keyUp = false;
    keyDown = false;
  }
  
  public void setup() {
    background(50);
  }
  
  public void draw() {
    background(50);
    
    // draw snowflakes that fall downward, reset position when they hit bottom, and that check for player collision
    for (int i = 0; i < snowflakeX.length; i++) {
      if (!ballHideStatus[i]) { 
        ellipse(snowflakeX[i], snowflakeY[i], 20, 20); 
      }
      snowflakeY[i] += snowflakeSpeed[i] * snowSpeed; 
      if (snowflakeY[i] > height) { 
        snowflakeY[i] = 0;
        snowflakeX[i] = random(width);
      }
      if (!ballHideStatus[i] && dist(snowflakeX[i], snowflakeY[i], playerX, playerY) < 20) {
        playerLives--;
        snowflakeY[i] = 0;
        snowflakeX[i] = random(width);
      }
    }

    // Draw player, lives indicator and clear screen to white when player lives run out
    fill(0, 0, 255);
    ellipse(playerX, playerY, 30, 30);
  
    for (int i = 0; i < playerLives; i++) {
      float x = width - 50 - i * 30;
      float y = 30;
      fill(255);
      rect(x, y, 20, 20);
    }

    if (playerLives <= 0) {
      background(255); 
      textSize(32);
      fill(0);
      textAlign(CENTER, CENTER);
      text("Game Over", width / 2, height / 2);
    }

     // move the player with wasd keys
     if (keyLeft) {
      playerX -= 5; 
    } else if (keyRight) {
      playerX += 5; 
    } else if (keyUp) {
      playerY -= 5; 
    } else if (keyDown) {
      playerY += 5;
    }
  }

  public void keyPressed() {
    // increase and decrease snowfall speed with arrow keys while making sure the snowfall can't go negative
    if (keyCode == DOWN) { 
      snowSpeed += 0.2;
    } else if (keyCode == UP) { 
      snowSpeed -= 0.2;
      if (snowSpeed < 0) { 
        snowSpeed = 0;
      }
    }

    playerX = constrain(playerX, 0, width);
    playerY = constrain(playerY, 0, height);

    // store whether the wasd keys are pressed or not
    if (key == 'a' || key == 'A') {
      keyLeft = true;
    } else if (key == 'd' || key == 'D') {
      keyRight = true;
    } else if (key == 'w' || key == 'W') {
      keyUp = true;
    } else if (key == 's' || key == 'S') {
      keyDown = true;
    }
  }

  public void keyReleased() {
    if (key == 'a' || key == 'A') {
      keyLeft = false;
    } else if (key == 'd' || key == 'D') {
      keyRight = false;
    } else if (key == 'w' || key == 'W') {
      keyUp = false;
    } else if (key == 's' || key == 'S') {
      keyDown = false;
    }
  }

  public void mouseClicked() {

    // hide the snowballs when they're clicked
    float clickRadius = 20; 
    for (int i = 0; i < snowflakeX.length; i++) {
      float distance = dist(snowflakeX[i], snowflakeY[i], mouseX, mouseY);
      if (distance < clickRadius) {
        ballHideStatus[i] = true; 
      }
    }
  }
}