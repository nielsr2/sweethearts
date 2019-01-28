import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import org.multiply.processing.TimedEventGenerator; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sweethearts extends PApplet {




TimedEventGenerator timer;


// PVector gravity = new PVector(0,0);
boolean DEBUG = true;
String [] sweethearts = {"s","w","e","e","t","h","e","a","r","t","s"};
MouseControl mouseControl;
PoemManager poemManager = new PoemManager();
PFont FB;
public void setup() {
        FB = createFont("data/Fundamental Brigade.ttf", 20);
        loadTaps();
        loadWaves();
        background(255);
        

        timer = new TimedEventGenerator(this,"timed", false);
        timer.setIntervalMs(500);


        mouseControl = new MouseControl();

        textFont(FB,20);

        // poemManager.setPosNow(poem1);

        // poemManager.loadNextPoemPositions(poem2);
        frameRate(25);
        // poemManager.allLetters[10][0].debug = true;
        loadIntro();

        // noLoop();
        println("milis", millis());
};
float ampz;


public void draw(){


        ampz = amp.analyze();
        if (samplePlaying) {
                // println("SAMPLE PLAYING!!!");
                if (!sample.isPlaying()) {
                        // println("!!!!!SAMPLE NOT PLAYING!!!");
                        samplePlaying = false;

                        poemManager.turnOffFXAll();
                        if (DEBUG) {
                                println("sound done, fx off, sat", sample);
                        }

                }
        }
        // println(ampz);
        // poemManager.bgColor = map(ampz, 1.,0., 0., 255.);
        if (!intro)
        {
                if (poemInc == 1 )
                {
                        mouseControl.accum();

                }

                poemManager.drawPoem();
        }

}

int poemInc = 0;
public void mousePressed() {
        if (!intro) {
                // if (mouseButton == LEFT)
                // {
                poemInc++;
                // }
                // else {
                //         poemInc--;
                // }
                poemInc = abs(poemInc) % 19;
                println("poemInc", poemInc);
                goToScene(poemInc);

        }
        else {
                goToScene(poemInc);
                intro = false;

        }
}





public void keyPressed() {
        if (key == TAB) {
                if    (DEBUG) {
                        DEBUG = false;
                }
                else {
                        DEBUG = true;
                };
        }
        if (key == ENTER) {
                // int rx,ry;
                // rx = int(random(0,11));
                // ry = int(random(0,11));
                // poemManager.allLetters[rx][ry].explode(4);
                poemManager.randomess(5);
        }
}
SoundFile[] tapSamples = new SoundFile[11];
public void loadTaps(){
        for (int i = 1; i < 12; i++) {
                if (i < 10) {
                        tapSamples[i - 1]  = new SoundFile(this, "audio/tap-0" + i + ".wav");
                }
                else {
                        tapSamples[i - 1]  = new SoundFile(this, "audio/tap-" + i + ".wav");
                }
                tapSamples[i - 1].amp(0.3f);
        }

}
SoundFile seetheSample, waveSample, hearSample, seatsSample,shortWaveSample, crankSample, kachingSample, seetheAsSample;
// AUwave = new SoundFile(this, "waves.wav");
public void loadWaves(){
        seetheSample = new SoundFile(this, "audio/seethe_04.wav");
        hearSample = new SoundFile(this, "audio/hear2.wav");
        hearSample.amp(.2f);

        seatsSample = new SoundFile(this, "audio/seats.wav");
        crankSample = new SoundFile(this, "audio/crank.wav");
        kachingSample = new SoundFile(this, "audio/kaching.wav");
        shortWaveSample = new SoundFile(this, "audio/shortWave3.wav");
        shortWaveSample.amp(.4f);
        seetheAsSample = new SoundFile(this, "audio/seethes.wav");


        amp = new Amplitude(this);
        // AUwave.amp(0.2);
};


Amplitude amp;
boolean samplePlaying = false;
SoundFile sample;
public void playNamp(SoundFile file){
        sample = file;
        amp.input(sample);

        sample.play();


        // if (!sample.isPlaying()) {
        delay(80);
        samplePlaying = true;
        //         delay(40); // this cuz it seems play() is too slow to start, and then false postive that sample already played
        // }else

        // samplePlaying = true;

}
boolean intro = true;

public void loadIntro(){
  String author = "emmett williams'";
  String title = "sweethearts";
  String descrip = "processing interpretations by";
  String me = "niels erik raursø";
  float start = height/12*4;
  float offset = height/12;
  float margin = width/3;
  fill(255);
  rect(0,0,height,width);
  fill(0);
  text(author, margin ,start );
  text(title, margin, start + offset );
  text(descrip, margin, start + (offset * 2) );
  text(me, margin , start + (offset * 3));
}

class Letter {
PVector letterLocation;
String character;
float xi;
float yj;
float size = 20;
boolean debug = false;

PVector fixedLocation = new PVector();
PVector velocity = new PVector(0,0);
Letter(int int2character, float x, float y){
        pushMatrix();
        this.character = sweethearts[int2character];
        fixedLocation = new PVector(x,y);
        letterLocation = new PVector(x,y);
        this.xi = x;
        this.yj = y;
        popMatrix();
        // this.character = temp_character;
        // println(this.character);
        // println(this.indexOf());

}

float nextX, nextY;
PVector nextVector;
boolean goingToNext = false;


int textColor;
float opacity = 255;

boolean scatter;

/*
   ███████      ██████      █████      ████████     ████████     ███████     ██████
   ██          ██          ██   ██        ██           ██        ██          ██   ██
   ███████     ██          ███████        ██           ██        █████       ██████
     ██     ██          ██   ██        ██           ██        ██          ██   ██
   ███████      ██████     ██   ██        ██           ██        ███████     ██   ██
 */


TableRow randomRow;
Letter randomLetter;
// takes all 'dead' letters an shoots them into the next poem
public void scatter(){
        int random = PApplet.parseInt(random(0,poemManager.nextXYTable.getRowCount()));
        randomRow = poemManager.nextXYTable.getRow(random);
        randomLetter = poemManager.allLetters[randomRow.getInt("x")][randomRow.getInt("y")];
        // this.textColor = #999999;

        opacity = 120;
        scatter = true;
        // lock = false;
}
boolean lock;

/*
   ██████      ██████       █████      ██     ██
   ██   ██     ██   ██     ██   ██     ██     ██
   ██   ██     ██████      ███████     ██  █  ██
   ██   ██     ██   ██     ██   ██     ██ ███ ██
   ██████      ██   ██     ██   ██      ███ ███
 */


public void drawLetter(){
        if (addToOpacity) {
                this.opacity += opaInc;
                if (this.opacity == 255) {
                        this.addToOpacity = false;
                }
        }
        velocity.setMag(0);
        // this.mouser(mouseX,mouseY);
        pushMatrix();
        translate(letterLocation.x, letterLocation.y);
        // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (particlesAlive) {
                for (Particle p : particles) {
                        if(p.drawParticle())
                        {
                                particlesAlive = false;
                                particles = null;
                        }
                }
        }
        if (scatter) {
                PVector randomLetterDirection = new PVector(randomLetter.letterLocation.x,randomLetter.letterLocation.y);
                if (this.debug) {
                        // println(randomLetterDirection.dist(letterLocation));
                        line(this.letterLocation.x,this.letterLocation.y, randomLetterDirection.x,randomLetterDirection.y);
                        // println("HIT");
                }

                randomLetterDirection.sub(letterLocation);
                randomLetterDirection.setMag(4);
                velocity.add(randomLetterDirection);

                float distance = randomLetter.letterLocation.dist(letterLocation);
                if (this.debug) {
                        println("DISTANCE :", distance);
                }
                if ( distance < 100.f) {

                        this.opacity = map(distance, 100,4.f, 100,0);
                }
                if (distance < 4) {
                        scatter = false;
                        this.opacity = 0;
                        lock = true;
                        if (this.debug) {
                                println(this.opacity);
                        }
                }
        }
        else if (!lock && !sine || sineAmp) {
                // else if (!lock && !sine ) {
                center();
        }
        if (sine) {
                sine();
        }
        fill(textColor, this.opacity);
        textSize(this.size);
        textFont(FB,20);
        letterLocation.add(velocity);
        text(character, this.letterLocation.x, this.letterLocation.y);
        popMatrix();
}

public void goToSelf(){
        letterLocation =new PVector(fixedLocation.x,fixedLocation.y);
}
float centerMag = 5;
public void center(){
        PVector center = new PVector(fixedLocation.x,fixedLocation.y);
        center.sub(letterLocation);
        center.setMag(centerMag);
        applyForce(center);
}
public void applyForce(PVector force){
        velocity.add(force);
}

/*
   ███████     ██     ███    ██     ███████
   ██          ██     ████   ██     ██
   ███████     ██     ██ ██  ██     █████
     ██     ██     ██  ██ ██     ██
   ███████     ██     ██   ████     ███████
 */


boolean sine;
float sine_inc;
float sine_posOffX = 1.1f;  // used for positional offset, so each letter on the row 
boolean sineAmp;
float ampMult = 250;
boolean ampInc = false;
public void sine(){
        sine_inc += 0.2f;
        PVector sineVector = new PVector(0, (sin(sine_inc + (this.fixedLocation.x * this.sine_posOffX))));
        if (sineAmp)
        {
                float bah = ampz;
                if (ampInc) {sine_inc += 10.f*ampz;}
                if (debug)
                {
                        // println(bah);
                }
                sineVector.mult(bah * ampMult);
        }

        if (debug) {
                // println("sine_inc", sine_inc, sin(sine_inc));
        }
        applyForce(sineVector);
}
boolean addToOpacity = false;
float opaInc;
public void addToOpacity(float inc) {
        this.opaInc = inc;
        this.addToOpacity = true;
}
/*
   ██████       █████      ██████
   ██   ██     ██   ██     ██   ██
   ██████      ███████     ██████
   ██          ██   ██     ██   ██
   ██          ██   ██     ██   ██
 */


Particle[] particles;
boolean particlesAlive;
public void explode(int number_of_particles){
        particles = new Particle[number_of_particles];
        for (int i = 0; i < number_of_particles; i++) {
                particles[i] = new Particle(PApplet.parseInt(random(1,5)), letterLocation.x, letterLocation.y);
        }
        particlesAlive = true;
        this.opacity = 0;
}

public void turnOffFX(){
        sine = false;
        ampInc = false;
}

}

class PoemManager {
PoemManager(){
        println("poem initialized");
        // this.setPosNow(poem1);
}
boolean bgRefresh = true;
float bgColor = 255.f;
float bgOpacity = (255.f/3);
public void drawBG(){
        fill(bgColor,bgOpacity);
        rect(0,0,width,height);
}
int wordLength;
public void  drawPoem(){
        if (allLetters[0][0] != null) {
                if(this.bgRefresh) {
                        this.drawBG();
                }
                for (int i = 0; i < 11; i++) { //<<------- stubborn use of 11, as it's more of a constant than a variable due to the nature of the poem
                        for (int j = 0; j < 11; j++) {
                                allLetters[i][j].drawLetter();
                        }
                }
        }
        else {
                println("NO POEM LOADED!!!");
        }
}
Letter[][] allLetters = new Letter[11][11];
/*
   ███████     ███████     ████████                 ██████       ██████      ███████                 ███    ██      ██████      ██     ██
   ██          ██             ██                    ██   ██     ██    ██     ██                      ████   ██     ██    ██     ██     ██
   ███████     █████          ██                    ██████      ██    ██     ███████                 ██ ██  ██     ██    ██     ██  █  ██
     ██     ██             ██                    ██          ██    ██          ██                 ██  ██ ██     ██    ██     ██ ███ ██
   ███████     ███████        ██                    ██           ██████      ███████                 ██   ████      ██████       ███ ███
 */


public void setPosNow(int[][] array){
        float m = (height*0.8f)/12 - (height*0.025f); // multiplier for position
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        if (array[i][j] != 0) {
                                // println(i,j);
                                allLetters[i][j] = new Letter(j, (j * m) + ((height*0.05f)-(height*0.0125f)), (i * m) + ((height*0.05f)-(height*0.0125f)));
                                // println(allLetters[i][j].xi);
                        }
                }
        }
}

//
// void randomShootAll(float mag){
//         for (int i = 0; i < this.deadNextXYTable.getRowCount(); i++) {
//                 TableRow row =  poemManager.deadNextXYTable.getRow(i);
//                 // println("gotINT", poemManager.nextXYTable.getInt(random,"x"));
//                 int x,y;
//                 x = row.getInt("x");
//                 y = row.getInt("y");
//                 allLetters[x][y].randomShoot(mag);
//         }
// }
Table nextXYTable;
Table deadNextXYTable;
/*
   ██           ██████           █████      ██████                  ███    ██     ███████     ██   ██     ████████
   ██          ██    ██         ██   ██     ██   ██                 ████   ██     ██           ██ ██         ██
   ██          ██    ██         ███████     ██   ██                 ██ ██  ██     █████         ███          ██
   ██          ██    ██         ██   ██     ██   ██                 ██  ██ ██     ██           ██ ██         ██
   ███████      ██████          ██   ██     ██████                  ██   ████     ███████     ██   ██        ██
 */


public void loadNextPoemPositions(int[][] array){

        int jsonI = 0;
        nextXYTable = new Table();
        nextXYTable.addColumn("x");
        nextXYTable.addColumn("y");
        deadNextXYTable = new Table();
        deadNextXYTable.addColumn("x");
        deadNextXYTable.addColumn("y");
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        if (array[i][j] == 1) {
                                // allLetters[i][j].textColor = #00AAAA;

                                TableRow newRow = nextXYTable.addRow();
                                newRow.setInt("x", i);
                                newRow.setInt("y", j);

                        }
                        else {

                                // allLetters[i][j].textColor = #FF0000;
                                TableRow newRow = deadNextXYTable.addRow();
                                newRow.setInt("x", i );
                                newRow.setInt("y", j );

                        }
                }
        }

        // println("nextXY", nextXYTable.getRowCount());

        // this.setInstantly(true);
}
public void scatterInto(float opa){
        for (int i = 0; i < this.deadNextXYTable.getRowCount(); i++) {
                TableRow row =  this.deadNextXYTable.getRow(i);
                int x,y;
                x = row.getInt("x");
                y = row.getInt("y");
                allLetters[x][y].opacity = opa;
                allLetters[x][y].lock = false;
                allLetters[x][y].scatter = false;
                allLetters[x][y].scatter();
        }
}

public void setInstantly(boolean show){
        for (int i = 0; i < this.nextXYTable.getRowCount(); i++) {
                TableRow row =  this.nextXYTable.getRow(i);
                int x,y;
                x = row.getInt("x");
                y = row.getInt("y");
                if (show)
                {allLetters[x][y].opacity = 255;}
                allLetters[x][y].lock = false;
                allLetters[x][y].scatter = false;
                allLetters[x][y].goToSelf();
        }
}
public void fadeIn(float inc){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].addToOpacity(inc );

                }
        }
}
public void hideAll(){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].opacity = 0.f;

                }
        }
}

public void hideSome(int howMany){
        for (int i = 0; i < howMany; i++) {
                TableRow row =  this.nextXYTable.getRow(i);
                int x,y;
                x = row.getInt("x");
                y = row.getInt("y");
                allLetters[x][y].opacity = 0.f;
                println(allLetters[x][y].character);
        }
}
public void turnOffFXAll(){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].turnOffFX();

                }
        }
        this.setInstantly(true);
}



// this function is continously called to create fuzzy ball of the letters.
public void randomess(int extent){
  println("RANDOMESS");

        nextXYTable = new Table();
        nextXYTable.addColumn("x");
        nextXYTable.addColumn("y");
        deadNextXYTable = new Table();
        deadNextXYTable.addColumn("x");
        deadNextXYTable.addColumn("y");
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                  int ran = PApplet.parseInt(random(0,extent));
                  if (ran == 0 ){
                    TableRow newRow = nextXYTable.addRow();
                    newRow.setInt("x", i);
                    newRow.setInt("y", j);
                    allLetters[i][j].scatter = false;
                    // allLetters[i][j].scatter();
                  }
                  else {
                    TableRow newRow = deadNextXYTable.addRow();
                    newRow.setInt("x", i);
                    newRow.setInt("y", j);
                  }
                }
        }
        // poemManager.setInstantly(true);
        this.scatterInto(255.f);
}
}
class MouseControl {
MouseControl(){
};
public float centerDistance(){
        float distance = dist(width/2,height/2, mouseX, mouseY);
        // println(distance);
        distance = map(distance, 0, width/2,100.f,0.f);
        return distance;
}

float accumulatedMovement;
public void accum(){
        float combined = abs(mouseX - pmouseX) + abs(mouseY - pmouseY);
        float distance = dist(width/2,height/2, mouseX, mouseY);
        distance = map(distance, 0, width/2,10,5);
        // if (combined < 10) {
                println(PApplet.parseInt(distance));
                poemManager.randomess(PApplet.parseInt(distance)); // this function is basically meaningless.
        // }

        accumulatedMovement += combined;
        // println("accumulatedMovement",accumulatedMovement);
}
}
class Particle {
PVector direction;
PVector location;
float size;
Particle(int temp_size, float x, float y){
        direction = direction.random2D();
        location = new PVector(x,y);

        // printArray(direction.array());
        direction.setMag(5);
        this.size = temp_size;
}
float opacity = 255;
public boolean drawParticle() {
        if (this.opacity <= 0) {
                return true;
        }
        else {
                location.add(direction);
                fill(0,this.opacity);
                noStroke();
                ellipseMode(CENTER);
                println(this.location.x,this.location.y);
                printArray(direction.array());
                ellipse(this.location.x,this.location.y,this.size,this.size);
                this.opacity -= 11.f;
                return false;
        }
}
}
// HE SHE
// WEEEEEEEEEE
// (S)HE HAS A SWEETHEART
// THE SWEETHEARTS HEAR THE SEA
// THE SEA SEETHES
// THE SWEETHEARTS SEETHE AS THE SEA SEETHES
// THE SWEETHEARTS SEE STARS
// STARS AT SEA / STAR SEA 
//
//
//
//
//
//
//
//
//
//

int[][] poem1 = {
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1}
};



int[][] poem2 = { // HE SHE
        {0,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0}

};

int[][] poem3 = { // HE SHE2
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem3_2 = { // HE SHE2.2
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem3_3 = { // HE SHE2.2
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};


int[][] poem4 = { // HE SHE3
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem5 = { // HE SHE4
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem6 = { // WEEEEE
        {0,1,0,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,0}
};

int[][] poem7 = { // HE has a sweetheart
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0}, // HE
        {0,0,0,0,0,1,0,1,0,0,1}, // HAS
        {0,0,0,0,0,0,0,1,0,0,0}, // A
        {1,1,1,1,1,1,1,1,1,1,0}, // SWEETHEART
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem8 = { // SHE has a sweetheart
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,1,1,0,0,0,0}, // SHE
        {0,0,0,0,0,1,0,1,0,0,1}, // HAS
        {0,0,0,0,0,0,0,1,0,0,0}, // A
        {1,1,1,1,1,1,1,1,1,1,0}, // SWEETHEART
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};


int[][] poem9 = { // the sweethears hear the sea
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,1,1,1,1,1,1,1,1,1,1}, // SWEETHEARTS
        {0,0,0,0,0,1,1,1,1,0,0}, // HEAR
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,0,1,0,0,0,0,1,0,0,0}, // SEA
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem10 = { // he seats her at the sea
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,0,0,0,0}, // HE
        {1,0,1,0,0,0,0,1,0,1,1}, // SEATS
        {0,0,0,0,0,1,1,0,1,0,0}, // HER
        {0,0,0,0,0,0,0,1,0,1,0}, // AT
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,0,1,0,0,0,0,1,0,0,0}, // SEA
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem11 = { // the sea seethes

        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,0,1,0,0,0,0,1,0,0,0}, // SEA
        {1,0,1,1,1,1,1,0,0,0,1}, // SEETHES
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem12 = { // THE SWEETHEARTS SEETHES AS THE SEA SEETHES

        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,1,1,1,1,1,1,1,1,1,1}, // SWEETHEARTS
        {1,0,1,1,1,1,1,0,0,0,0}, // SEETHE
        {0,0,0,0,0,0,0,1,0,0,1}, // AS
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,0,1,0,0,0,0,1,0,0,0}, // SEA
        {1,0,1,1,1,1,1,0,0,0,1}, // SEETHES
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};


int[][] poem13 = { // THE SWEETHEARTS SEE STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {1,1,1,1,1,1,1,1,1,1,1}, // SWEETHEARTS
        {1,0,1,1,0,0,0,0,0,0,0}, // SEE
        {1,0,0,0,1,0,0,1,1,0,1}, // STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
};

int[][] poem14 = { // STARS AT SEA

        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,1,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,0},
        {0,0,0,0,0,0,0,1,0,1,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,0,0,1,0,0,0},
        {0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
        // S,W,E,E,T,H,E,A,R,T,S
};

int[][] poem15 = { // A SEA THAT EATS STARS

        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,1,0,0,0}, // A
        {1,0,1,0,0,0,0,1,0,0,0}, // SEA
        {0,0,0,0,1,1,0,1,0,1,0}, // THAT
        {0,0,0,0,0,0,1,1,0,1,1}, // EATS
        {1,0,0,0,1,0,0,1,1,0,1}, // STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
        // S,W,E,E,T,H,E,A,R,T,S
};

int[][] poem16 = { // STARS THAT EATS STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,1,0,0,1,1,0,1}, // STARS
        {0,0,0,0,1,1,0,1,0,1,0}, // THAT
        {0,0,0,0,0,0,1,1,0,1,1}, // EATS
        {1,0,0,0,1,0,0,1,1,0,1}, // STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
        // S,W,E,E,T,H,E,A,R,T,S
};

int[][] poem17 = { // HEART AT HEAT
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,0,0,0,0}, // THE
        {0,1,0,0,0,0,0,1,1,0,0}, // WAR
        {1,0,0,0,1,0,0,1,1,1,1}, // STARS
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0}
        // S,W,E,E,T,H,E,A,R,T,S
};
//
// int[][] poem17 = { // HEART AT HEAT
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,1,1,1,1,1,0}, // HEART
//         {0,0,0,0,0,0,0,1,0,1,0}, // AT
//         {0,0,0,0,0,1,1,1,1,1,0}, // HEART
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0},
//         {0,0,0,0,0,0,0,0,0,0,0}
//         // S,W,E,E,T,H,E,A,R,T,S
// };
public void goToScene(int sceneCount) {
        println(sceneCount);

        if (sceneCount == 0) { // ALL
                // AUwave.play();
                poemManager.setPosNow(poem1);
                // poemManager.loadNextPoemPositions(poem1);
                // poemManager.setInstantly(true);

                // poemManager.randomess();
                // poemManager.scatterInto(255.);

        }
        if (sceneCount == 1) { //    mous mess
                tapSamples[1].play();
                // poemManager.setPosNow(poem1);

                poemManager.loadNextPoemPositions(poem1);
                printArray(poemManager.nextXYTable);
                poemManager.setInstantly(true);
                // poemManager.scatterInto(0.); // NOTE
        }
        else if (sceneCount == 2) { //    HE/SHE2
                tapSamples[2].play();
                poemManager.bgOpacity = (255);
                poemManager.loadNextPoemPositions(poem2);
                poemManager.hideAll();
                poemManager.setInstantly(true);
                poemManager.scatterInto(0.f);
                // this.scatterInto();
        }
        else if (sceneCount == 3) { //    HE/SHE2
                tapSamples[1].play();

                poemManager.loadNextPoemPositions(poem3);
                poemManager.hideAll();
                poemManager.setInstantly(true);
                // poemManager.scatterInto(0.);
                // this.scatterInto();
        }
        else if (sceneCount == 4) { //    HE/SHE3
                tapSamples[1].play();
                poemManager.loadNextPoemPositions(poem4);
                poemManager.hideAll();
                poemManager.setInstantly(true);
                // poemManager.scatterInto(0.);
        }
        else if (sceneCount == 5) { //    HE/SHE4
                tapSamples[1].play();
                poemManager.loadNextPoemPositions(poem5);
                poemManager.hideAll();
                poemManager.setInstantly(true);
                // poemManager.scatterInto(0.);
        }
        else if (sceneCount == 6) {
                kachingSample.play();
                poemManager.loadNextPoemPositions(poem6);//   ********************************* WEEE
                poemManager.setInstantly(true);
                poemManager.scatterInto(255.f);
        }
        else if (sceneCount == 7) {
                poemManager.loadNextPoemPositions(poem7); //*********************************   HE HAS A SWEETHEART
                poemManager.hideAll();
                resetTimer();
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
        }
        else if (sceneCount == 8) { //********************************* SHE HAS A SWEETHEART
                poemManager.loadNextPoemPositions(poem8);
                // crankSample.play();

                // howmany = 3;
                // poemManager.hideSome(3);


                // timerCount = 0;
                // timer.setEnabled(true);
                // timed();
                // poemManager.hideSome(3);
                poemManager.setInstantly(true);
                kachingSample.play();
        }
        else if (sceneCount == 9) { //********************************* THE SWEETHREATS HEAR THE SEA
                poemManager.loadNextPoemPositions(poem9);
                // poemManager.setInstantly(true);
                poemManager.hideAll();
                howmany = 0;
                timerCount = 0;
                timer.setEnabled(true);
                timed();
                // poemManager.scatterInto(255.);
                // hearSample.play();
        }
        else if (sceneCount == 10) {
                // playNfreeze(crankSample);

                crankSample.play();
                poemManager.hideSome(18);


                poemManager.loadNextPoemPositions(poem10); //********************************* HE SEATS HER AT THE SEA
                // delayBy(1500);
                // poemManager.setInstantly(true);

                resetTimer();
                // poemManager.allLetters[7][0].debug = true;
                poemManager.allLetters[7][0].sine = true;
                poemManager.allLetters[7][7].sine = true;
                poemManager.allLetters[7][6].sine = true;
                // seatsSample.play();
                // poemManager.scatterInto(255.);
        }
        else if (sceneCount == 11) { //********************************* THE SEA SEETHES

                poemManager.hideAll();
                // howmany = 0;
                // timerCount = 0;
                poemManager.loadNextPoemPositions(poem11);

                resetTimer();
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
        }
        else if (sceneCount == 12) {
                // poemManager.hideAll();
                // shortWaveSample.play();
                playNamp(seetheAsSample);
                poemManager.hideSome(9);
                poemManager.loadNextPoemPositions(poem12); //********************************* T// THE SWEETHEARTS SEETHES AS THE SEA SEETHES
                // poemManager.allLetters[5][10].textColor = #FF0000;
                poemManager.allLetters[6][0].opacity = 0;
                poemManager.allLetters[6][2].opacity = 0;
                poemManager.allLetters[6][3].opacity = 0;
                poemManager.allLetters[6][4].opacity = 0;
                poemManager.allLetters[6][10].opacity = 0;
                //
                // poemManager.allLetters[6][0].opacity = 0;
                // poemManager.allLetters[6][2].opacity = 0;
                // poemManager.allLetters[6][3].opacity = 0;
                // poemManager.allLetters[6][10].opacity = 0;

                resetTimer();
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                poemManager.bgOpacity = 120.f;
        }
        else if (sceneCount == 13) {
                poemManager.hideAll();
                poemManager.loadNextPoemPositions(poem13); // THE SWEETHEARTS SEE STARS
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                resetTimer();
        }
        else if (sceneCount == 14) { // STARS AT SEA
                poemManager.bgColor = 0.f;
                for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 11; j++) {
                                poemManager.allLetters[i][j].textColor = 255;
                        }
                }
                poemManager.bgOpacity = 20.f;
                poemManager.loadNextPoemPositions(poem14);
                poemManager.setInstantly(true);
                poemManager.scatterInto(255.f);
        }

        else if (sceneCount == 15) {
                poemManager.hideAll();
                for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 11; j++) {
                                poemManager.allLetters[i][j].textColor = 0;
                        }
                }
                poemManager.bgOpacity = 240;
                poemManager.bgColor = 255.f;

                // A SEA THAT EATS STARS
                // for (int i = 0; i < 11; i++) {
                //         for (int j = 0; j < 11; j++) {
                //                 poemManager.allLetters[i][j].textColor = 0;
                //         }
                // }
                poemManager.loadNextPoemPositions(poem15);
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                resetTimer();
        }
        else if (sceneCount == 16) {
                poemManager.hideSome(4);

                poemManager.loadNextPoemPositions(poem16); // STARS THAT EAT STARS
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                resetTimer();
        }
        else if (sceneCount == 17) {
                poemManager.hideAll();
                poemManager.loadNextPoemPositions(poem17); // THE WAR STARTS
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                resetTimer();
        }
        else if (sceneCount == 18) {
                poemManager.loadNextPoemPositions(poem1);
                poemManager.setInstantly(false);
                poemManager.fadeIn(15.f);
        }

}



int timerCount = 0;
int howmany = 0;

public void resetTimer(){
        howmany = 0;
        timerCount = 0;
        timer.setEnabled(true);
        timed();
}

public void resetTimer(int many, int tcount){
        howmany = many;
        timerCount = tcount;
        timer.setEnabled(true);
        timed();
}
public void timed(){


        int sizeOfPoem;

        if (howmany == 0 )
        {
                sizeOfPoem = poemManager.nextXYTable.getRowCount();
        } else {
                println("exception!!!!!!");
                sizeOfPoem = howmany;
        }

        // println(poemManager.nextXYTable);
        // println("poemManager.nextXYTable.getColumnCount(); ", poemManager.nextXYTable.getRowCount());
        println(timerCount, sizeOfPoem);
        if (timerCount < sizeOfPoem) {
                TableRow row =  poemManager.nextXYTable.getRow(timerCount);
                int x,y;
                // println(poemManager.nextXYTable.getRow(timerCount+1).getInt("x"));
                x = row.getInt("x");
                y = row.getInt("y");
                poemManager.allLetters[x][y].opacity = 255;
                poemManager.allLetters[x][y].lock = false;
                poemManager.allLetters[x][y].scatter = false;
                poemManager.allLetters[x][y].goToSelf();
                int sampleCount = timerCount % 11;
                // tapSamples[sampleCount].play();
                int ranCount = PApplet.parseInt(random(0,11));
                // println(ranCount);
                if (poemInc == 12
                    ) {
                        println("SEETHE AS PLAY");
                        // poemManager.allLetters[6][0].textColor = #FF5599;
                        println(poemManager.allLetters[6][0].lock);
                        if (timerCount == 4) {
                                for (int i = 0; i < 11; i++)
                                {poemManager.allLetters[3][i].sineAmp = true;
                                 poemManager.allLetters[3][i].sine = true;

                                 poemManager.allLetters[3][i].ampInc = true;
                                 poemManager.allLetters[3][i].ampMult = 90;}
                        }
                        if (timerCount == 24) {
                                // poemManager.allLetters[5][0].ampMult = 120;
                                //                      poemManager.allLetters[5][2].ampMult = 120;
                                //                      poemManager.allLetters[5][7].ampMult = 120;

                        }
                        if (timerCount == 24) {
                                poemManager.allLetters[7][0].sineAmp = true;
                                poemManager.allLetters[7][2].sineAmp = true;
                                poemManager.allLetters[7][7].sineAmp = true;

                                poemManager.allLetters[7][0].sine = true;
                                poemManager.allLetters[7][2].sine = true;
                                poemManager.allLetters[7][7].sine = true;

                                poemManager.allLetters[7][0].ampInc = true;
                                poemManager.allLetters[7][2].ampInc = true;
                                poemManager.allLetters[7][7].ampInc = true;

                                poemManager.allLetters[7][0].ampMult = 90;
                                poemManager.allLetters[7][2].ampMult = 90;
                                poemManager.allLetters[7][7].ampMult = 90;
                        }
                }
                if (poemInc == 11  && timerCount == 12
                    ) {
                        println("SEETHE PLAY");
                        // poemManager.allLetters[6][0].textColor = #FF5599;
                        println(poemManager.allLetters[6][0].lock);
                        poemManager.allLetters[6][0].sineAmp = true;
                        poemManager.allLetters[6][2].sineAmp = true;
                        poemManager.allLetters[6][3].sineAmp = true;
                        poemManager.allLetters[6][4].sineAmp = true;
                        poemManager.allLetters[6][5].sineAmp = true;
                        poemManager.allLetters[6][6].sineAmp = true;
                        poemManager.allLetters[6][10].sineAmp = true;
                        poemManager.allLetters[6][0].sine = true;
                        poemManager.allLetters[6][2].sine = true;
                        poemManager.allLetters[6][3].sine = true;
                        poemManager.allLetters[6][4].sine = true;
                        poemManager.allLetters[6][5].sine = true;
                        poemManager.allLetters[6][6].sine = true;
                        poemManager.allLetters[6][10].sine = true;

                        poemManager.allLetters[6][0].ampInc = true;
                        poemManager.allLetters[6][2].ampInc = true;
                        poemManager.allLetters[6][3].ampInc = true;
                        poemManager.allLetters[6][4].ampInc = true;
                        poemManager.allLetters[6][5].ampInc = true;
                        poemManager.allLetters[6][6].ampInc = true;
                        poemManager.allLetters[6][10].ampInc = true;
                        playNamp(seetheSample);

                }
                if (poemInc == 9  && timerCount == 21
                    ) {
                        println("HEAR PLAY");

                        poemManager.allLetters[7][0].centerMag = 1.f;
                        poemManager.allLetters[7][2].centerMag = 1.f;
                        poemManager.allLetters[7][7].centerMag = 1.f;

                        poemManager.allLetters[7][0].ampInc = true;
                        poemManager.allLetters[7][2].ampInc = true;
                        poemManager.allLetters[7][7].ampInc = true;

                        poemManager.allLetters[7][0].sine_inc = 10.f;
                        poemManager.allLetters[7][2].sine_inc = 10.f;
                        poemManager.allLetters[7][7].sine_inc = 10.f;

                        poemManager.allLetters[7][0].sineAmp = true;
                        poemManager.allLetters[7][2].sineAmp = true;
                        poemManager.allLetters[7][7].sineAmp = true;

                        poemManager.allLetters[7][0].ampMult = 90;
                        poemManager.allLetters[7][2].ampMult = 90;
                        poemManager.allLetters[7][7].ampMult = 90;

                        poemManager.allLetters[7][0].sine = true;
                        poemManager.allLetters[7][2].sine = true;
                        poemManager.allLetters[7][7].sine = true;

                        playNamp(hearSample);

                }
                if (poemInc == 10  && timerCount == 10
                    ) {
                        // seatsSample.play();
                        poemManager.allLetters[7][0].ampInc = true;
                        poemManager.allLetters[7][2].ampInc = true;
                        poemManager.allLetters[7][7].ampInc = true;

                        poemManager.allLetters[7][0].sine = true;
                        poemManager.allLetters[7][2].sine = true;
                        poemManager.allLetters[7][7].sine = true;

                        poemManager.allLetters[7][0].ampMult = 20;
                        poemManager.allLetters[7][2].ampMult = 20;
                        poemManager.allLetters[7][7].ampMult = 20;
                        playNamp(shortWaveSample);
                }
                if (poemInc == 8  && timerCount == 2
                    ) {
                        // kachingSample.play();

                }
                if (poemInc == 16 && timerCount > 4
                    ) {}
                else{

                        tapSamples[ranCount].play();
                }

                // println(timerCount, poemManager.allLetters[x][y].character, sizeOfPoem);
                timerCount++;
                timer.setIntervalMs(constrain((500/timerCount + (PApplet.parseInt(random(-80,80)))),80,300));
                if (poemInc == 15) {
                        timer.setIntervalMs(constrain((600/timerCount + (PApplet.parseInt(random(-200,80)))),120,550));
                }
                if (poemInc == 16) {
                        timer.setIntervalMs(250);
                }
                if (poemInc == 17) {
                        timer.setIntervalMs(500);
                }
        }
        else {
                println("timer off");
                timer.setEnabled(false);

        }
}
  public void settings() {  size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sweethearts" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
