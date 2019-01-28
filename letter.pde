
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


color textColor;
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
void scatter(){
        int random = int(random(0,poemManager.nextXYTable.getRowCount()));
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


void drawLetter(){
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
                if ( distance < 100.) {

                        this.opacity = map(distance, 100,4., 100,0);
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

void goToSelf(){
        letterLocation =new PVector(fixedLocation.x,fixedLocation.y);
}
float centerMag = 5;
void center(){
        PVector center = new PVector(fixedLocation.x,fixedLocation.y);
        center.sub(letterLocation);
        center.setMag(centerMag);
        applyForce(center);
}
void applyForce(PVector force){
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
float sine_posOffX = 1.1;  // used for positional offset, so each letter on the row
boolean sineAmp;
float ampMult = 250;
boolean ampInc = false;
void sine(){
        sine_inc += 0.2;
        PVector sineVector = new PVector(0, (sin(sine_inc + (this.fixedLocation.x * this.sine_posOffX))));
        if (sineAmp)
        {
                float bah = ampz;
                if (ampInc) {sine_inc += 10.*ampz;}
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
void addToOpacity(float inc) {
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
void explode(int number_of_particles){
        particles = new Particle[number_of_particles];
        for (int i = 0; i < number_of_particles; i++) {
                particles[i] = new Particle(int(random(1,5)), letterLocation.x, letterLocation.y);
        }
        particlesAlive = true;
        this.opacity = 0;
}

void turnOffFX(){
        sine = false;
        ampInc = false;
}

}
