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
        lightPurple();
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
  analogWrite(RED_PIN, 255);
  analogWrite(GREEN_PIN, 0);
  analogWrite(BLUE_PIN, 128);
}

void lightPurple() {
  analogWrite(RED_PIN, 155);
  analogWrite(GREEN_PIN, 48);
  analogWrite(BLUE_PIN, 255);  
}

void lightGreen() {
   analogWrite(RED_PIN, 188);
   analogWrite(GREEN_PIN, 238);
   analogWrite(BLUE_PIN, 104);
}

void lightsOff() {
  analogWrite(RED_PIN, 0);
  analogWrite(GREEN_PIN, 0);
  analogWrite(BLUE_PIN, 0);
}
