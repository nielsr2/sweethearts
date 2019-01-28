
class PoemManager {
PoemManager(){
        println("poem initialized");
        // this.setPosNow(poem1);
}
boolean bgRefresh = true;
float bgColor = 255.;
float bgOpacity = (255./3);
void drawBG(){
        fill(bgColor,bgOpacity);
        rect(0,0,width,height);
}
int wordLength;
void  drawPoem(){
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


void setPosNow(int[][] array){
        float m = (height*0.8)/12 - (height*0.025); // multiplier for position
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        if (array[i][j] != 0) {
                                // println(i,j);
                                allLetters[i][j] = new Letter(j, (j * m) + ((height*0.05)-(height*0.0125)), (i * m) + ((height*0.05)-(height*0.0125)));
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


void loadNextPoemPositions(int[][] array){

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
void scatterInto(float opa){
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

void setInstantly(boolean show){
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
void fadeIn(float inc){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].addToOpacity(inc );

                }
        }
}
void hideAll(){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].opacity = 0.;

                }
        }
}

void hideSome(int howMany){
        for (int i = 0; i < howMany; i++) {
                TableRow row =  this.nextXYTable.getRow(i);
                int x,y;
                x = row.getInt("x");
                y = row.getInt("y");
                allLetters[x][y].opacity = 0.;
                println(allLetters[x][y].character);
        }
}
void turnOffFXAll(){
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                        allLetters[i][j].turnOffFX();

                }
        }
        this.setInstantly(true);
}



// this function is continously called to create fuzzy ball of the letters.
void randomess(int extent){
  println("RANDOMESS");

        nextXYTable = new Table();
        nextXYTable.addColumn("x");
        nextXYTable.addColumn("y");
        deadNextXYTable = new Table();
        deadNextXYTable.addColumn("x");
        deadNextXYTable.addColumn("y");
        for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                  int ran = int(random(0,extent));
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
        this.scatterInto(255.);
}
}
