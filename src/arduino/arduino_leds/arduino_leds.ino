/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */
 
int ledpin = 13; 

void setup() {
  Serial.begin(9600);
  pinMode(ledpin, OUTPUT);
  digitalWrite(ledpin, HIGH);
} 

void loop() {  
  while (Serial.available() > 0) { 
    int val = Serial.read();
   
    if (val == 1) {
      digitalWrite(ledpin, HIGH); 
    }
    else if (val == 0) {
      digitalWrite(ledpin, LOW);
    }
  }
}
