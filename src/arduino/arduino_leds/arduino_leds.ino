/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */

// Fact Handler LED Pins
#define F_RED_PIN 5
#define F_GREEN_PIN 6
#define F_BLUE_PIN 3
//#define FADE_SPEED 10  // Higher to slow down 

// Microscope Handler LED Pins
#define M_RED_PIN 10
#define M_GREEN_PIN 11
#define M_BLUE_PIN 9

// Video Handler LED Pins
#define V_RED_PIN 44
#define V_GREEN_PIN 45
#define V_BLUE_PIN 46

void setup() {
  pinMode(F_RED_PIN, OUTPUT);
  pinMode(F_GREEN_PIN, OUTPUT);
  pinMode(F_BLUE_PIN, OUTPUT);

  pinMode(M_RED_PIN, OUTPUT);
  pinMode(M_GREEN_PIN, OUTPUT);
  pinMode(M_BLUE_PIN, OUTPUT);

  pinMode(V_RED_PIN, OUTPUT);
  pinMode(V_GREEN_PIN, OUTPUT);
  pinMode(V_BLUE_PIN, OUTPUT);
  
  Serial.begin(9600);
} 

void loop() {
  while (Serial.available() > 0) { 
    int val = Serial.read();
   
    if (val == 1) {
      lightRed();
    }
    else if (val == 2) {
        lightBlue();
    }
    else if (val == 3) {
      lightGreen();
    }
    else if (val == 0) {
      lightsOff();
    }
  }
}

void lightRed() {
  analogWrite(F_RED_PIN, 200);
  analogWrite(F_GREEN_PIN, 0);
  analogWrite(F_BLUE_PIN, 0);
}

void lightBlue() {
  analogWrite(M_RED_PIN, 0);
  analogWrite(M_GREEN_PIN, 0);
  analogWrite(M_BLUE_PIN, 200);  
}

void lightGreen() {
   analogWrite(V_RED_PIN, 0);
   analogWrite(V_GREEN_PIN, 200);
   analogWrite(V_BLUE_PIN, 0);
}

void lightsOff() {
  analogWrite(F_RED_PIN, 0);
  analogWrite(F_GREEN_PIN, 0);
  analogWrite(F_BLUE_PIN, 0);

  analogWrite(M_RED_PIN, 0);
  analogWrite(M_GREEN_PIN, 0);
  analogWrite(M_BLUE_PIN, 0);

  analogWrite(V_RED_PIN, 0);
  analogWrite(V_GREEN_PIN, 0);
  analogWrite(V_BLUE_PIN, 0);
}
