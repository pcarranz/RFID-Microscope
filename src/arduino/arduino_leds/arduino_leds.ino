/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */
 
#define RED_PIN 5
#define GREEN_PIN 6
#define BLUE_PIN 3
#define FADE_SPEED 10  // Higher to slow down 

void setup() {
  pinMode(RED_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  pinMode(BLUE_PIN, OUTPUT);
  
  Serial.begin(9600);
} 

void loop() {
  while (Serial.available() > 0) { 
    int val = Serial.read();
   
    if (val == 1) {
      lightOrange();
    }
    else if (val == 2) {
        lightOrange();
    }
    else if (val == 3) {
      lightGreen();
    }
    else if (val == 0) {
      lightsOff();
    }
  }
}

void lightOrange() {
  int i;
  
  for(i = 0; i < 255; i++)
  {
    analogWrite(RED_PIN, i);
    delay(FADE_SPEED);
  }
  for(i = 0; i < 128; i++)
  {
    analogWrite(GREEN_PIN, i);
    delay(FADE_SPEED);
  }
}

void lightPurple() {
  int i;
  
  for(i = 0; i < 155; i++)
  {
    analogWrite(RED_PIN, 75);
    delay(FADE_SPEED);
  }
  for(i = 0; i < 48; i++)
  {
    analogWrite(GREEN_PIN, 75);
    delay(FADE_SPEED);
  }
  for(i = 0; i < 255; i++)
  {
    analogWrite(BLUE_PIN, i);
    delay(FADE_SPEED);
  }  
}

void lightGreen() {
   analogWrite(RED_PIN, 188);
   analogWrite(GREEN_PIN, 238);
   analogWrite(BLUE_PIN, 104);

//  int i;
// 
//  for(i = 0; i < 188; i++)
//  {
//     analogWrite(RED_PIN, i);
//     delay(FADE_SPEED);
//  }  
//  for(i = 0; i < 238; i++)
//  {
//     analogWrite(GREEN_PIN, i);
//     delay(FADE_SPEED);
//  }
//  for(i = 0; i < 104; i++)
//  {
//     analogWrite(BLUE_PIN, i);
//     delay(FADE_SPEED);
//  }
}

void lightsOff() {
   int i;
  
  for(i = 0; i < 120; i++)
  {
    analogWrite(RED_PIN, 0);
    analogWrite(GREEN_PIN, 0);
    analogWrite(BLUE_PIN, 0);
    delay(FADE_SPEED);
  }
}
