void goToScene(int sceneCount) {
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
                poemManager.scatterInto(0.);
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
                poemManager.scatterInto(255.);
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
                poemManager.bgOpacity = 120.;
        }
        else if (sceneCount == 13) {
                poemManager.hideAll();
                poemManager.loadNextPoemPositions(poem13); // THE SWEETHEARTS SEE STARS
                // poemManager.setInstantly(true);
                // poemManager.scatterInto(255.);
                resetTimer();
        }
        else if (sceneCount == 14) { // STARS AT SEA
                poemManager.bgColor = 0.;
                for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 11; j++) {
                                poemManager.allLetters[i][j].textColor = 255;
                        }
                }
                poemManager.bgOpacity = 20.;
                poemManager.loadNextPoemPositions(poem14);
                poemManager.setInstantly(true);
                poemManager.scatterInto(255.);
        }

        else if (sceneCount == 15) {
                poemManager.hideAll();
                for (int i = 0; i < 11; i++) {
                        for (int j = 0; j < 11; j++) {
                                poemManager.allLetters[i][j].textColor = 0;
                        }
                }
                poemManager.bgOpacity = 240;
                poemManager.bgColor = 255.;

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
                poemManager.fadeIn(15.);
        }

}



int timerCount = 0;
int howmany = 0;

void resetTimer(){
        howmany = 0;
        timerCount = 0;
        timer.setEnabled(true);
        timed();
}

void resetTimer(int many, int tcount){
        howmany = many;
        timerCount = tcount;
        timer.setEnabled(true);
        timed();
}
void timed(){


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
                int ranCount = int(random(0,11));
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

                        poemManager.allLetters[7][0].centerMag = 1.;
                        poemManager.allLetters[7][2].centerMag = 1.;
                        poemManager.allLetters[7][7].centerMag = 1.;

                        poemManager.allLetters[7][0].ampInc = true;
                        poemManager.allLetters[7][2].ampInc = true;
                        poemManager.allLetters[7][7].ampInc = true;

                        poemManager.allLetters[7][0].sine_inc = 10.;
                        poemManager.allLetters[7][2].sine_inc = 10.;
                        poemManager.allLetters[7][7].sine_inc = 10.;

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
                timer.setIntervalMs(constrain((500/timerCount + (int(random(-80,80)))),80,300));
                if (poemInc == 15) {
                        timer.setIntervalMs(constrain((600/timerCount + (int(random(-200,80)))),120,550));
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
