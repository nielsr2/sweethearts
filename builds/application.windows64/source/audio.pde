SoundFile[] tapSamples = new SoundFile[11];
void loadTaps(){
        for (int i = 1; i < 12; i++) {
                if (i < 10) {
                        tapSamples[i - 1]  = new SoundFile(this, "audio/tap-0" + i + ".wav");
                }
                else {
                        tapSamples[i - 1]  = new SoundFile(this, "audio/tap-" + i + ".wav");
                }
                tapSamples[i - 1].amp(0.3);
        }

}
SoundFile seetheSample, waveSample, hearSample, seatsSample,shortWaveSample, crankSample, kachingSample, seetheAsSample;
// AUwave = new SoundFile(this, "waves.wav");
void loadWaves(){
        seetheSample = new SoundFile(this, "audio/seethe_04.wav");
        hearSample = new SoundFile(this, "audio/hear2.wav");
        hearSample.amp(.2);

        seatsSample = new SoundFile(this, "audio/seats.wav");
        crankSample = new SoundFile(this, "audio/crank.wav");
        kachingSample = new SoundFile(this, "audio/kaching.wav");
        shortWaveSample = new SoundFile(this, "audio/shortWave3.wav");
        shortWaveSample.amp(.4);
        seetheAsSample = new SoundFile(this, "audio/seethes.wav");


        amp = new Amplitude(this);
        // AUwave.amp(0.2);
};


Amplitude amp;
boolean samplePlaying = false;
SoundFile sample;
void playNamp(SoundFile file){
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
