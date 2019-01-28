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
boolean drawParticle() {
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
                this.opacity -= 11.;
                return false;
        }
}
}
