import processing.sound.*;
import org.multiply.processing.TimedEventGenerator;

TimedEventGenerator timer;


// PVector gravity = new PVector(0,0);
boolean DEBUG = true;
String [] sweethearts = {"s","w","e","e","t","h","e","a","r","t","s"};
MouseControl mouseControl;
PoemManager poemManager = new PoemManager();
PFont FB;
void setup() {
        FB = createFont("data/Fundamental Brigade.ttf", 20);
        loadTaps();
        loadWaves();
        background(255);
        size(800,800);

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


void draw(){


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
void mousePressed() {
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





void keyPressed() {
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
