boolean intro = true;

void loadIntro(){
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
