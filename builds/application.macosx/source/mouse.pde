class MouseControl {
MouseControl(){
};
float centerDistance(){
        float distance = dist(width/2,height/2, mouseX, mouseY);
        // println(distance);
        distance = map(distance, 0, width/2,100.,0.);
        return distance;
}

float accumulatedMovement;
void accum(){
        float combined = abs(mouseX - pmouseX) + abs(mouseY - pmouseY);
        float distance = dist(width/2,height/2, mouseX, mouseY);
        distance = map(distance, 0, width/2,10,5);
        // if (combined < 10) {
                println(int(distance));
                poemManager.randomess(int(distance)); // this function is basically meaningless.
        // }

        accumulatedMovement += combined;
        // println("accumulatedMovement",accumulatedMovement);
}
}
