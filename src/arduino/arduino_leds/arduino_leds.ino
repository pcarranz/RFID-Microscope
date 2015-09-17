/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */

#include <Adafruit_NeoPixel.h>

#ifdef __AVR__
  #include <avr/power.h>
#endif

// Fact Handler LED Pins
#define F_RED_PIN 5
#define F_GREEN_PIN 6
#define F_BLUE_PIN 3

// Microscope Handler LED Pins
#define M_RED_PIN 10
#define M_GREEN_PIN 11
#define M_BLUE_PIN 9

// Video Handler LED Pins
#define V_RED_PIN 44
#define V_GREEN_PIN 45
#define V_BLUE_PIN 46

// Arrow LED Pins
#define ARROW_PIN_1 22
#define ARROW_PIN_2 24
#define NUM_PIXELS 11  // Number of neopixels per strip

// Commands sent from Java program
#define ALL_LIGHTS_OFF 0
#define FACTS_LED_ON 1
#define MICROSCOPE_LED_ON 2
#define VIDEO_LED_ON 3
#define ARROW_1_LED_ON 4
#define ARROW_2_LED_ON 5
#define ARROW_1_LED_OFF 6
#define ARROW_2_LED_OFF 7


Adafruit_NeoPixel arrow1 = Adafruit_NeoPixel(NUM_PIXELS, ARROW_PIN_1, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel arrow2 = Adafruit_NeoPixel(NUM_PIXELS, ARROW_PIN_2, NEO_GRB + NEO_KHZ800);

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

  arrow1.begin();
  arrow2.begin();

  // Initialize all pixels to off
  arrow1.show();
  arrow2.show();
  
  Serial.begin(9600);
} 

void loop() {
  while (Serial.available() > 0) { 
    int val = Serial.read();
   
    if (val == FACTS_LED_ON) {
      factsIndicatorOn();
    }
    else if (val == MICROSCOPE_LED_ON) {
        microscopeIndicatorOn();
    }
    else if (val == VIDEO_LED_ON) {
      videoIndicatorOn();
    }
    else if (val == ARROW_1_LED_ON) {
      arrow1RainbowCycle(3);
    }
    else if(val == ARROW_2_LED_ON) {
       arrow2RainbowCycle(3);
    }
    else if(val == ARROW_1_LED_OFF) {
      arrow1Off();
    }
    else if(val == ARROW_2_LED_OFF) {
      arrow2Off();
    }
    else if (val == ALL_LIGHTS_OFF) {
      lightsOff();
    }
  }
}

void factsIndicatorOn() {  
  // Magenta
  analogWrite(F_RED_PIN, 255);
  analogWrite(F_GREEN_PIN, 0);
  analogWrite(F_BLUE_PIN, 155);
}

void microscopeIndicatorOn() { 
  // Cyan
  analogWrite(M_RED_PIN, 0);
  analogWrite(M_GREEN_PIN, 255);
  analogWrite(M_BLUE_PIN, 255);  
}

void videoIndicatorOn() { 
  // Yellow
   analogWrite(V_RED_PIN, 255);
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

  arrow1Off();  
  arrow2Off();
}

/* Arrow 1 Rainbow Functions */
void arrow1RainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j = 0; j < 256; j++) {
    for(i = 0; i< arrow1.numPixels(); i++) {
      arrow1.setPixelColor(i, arrow1Wheel(((i * 256 / arrow1.numPixels()) + j) & 255));
    }
    arrow1.show();
    delay(wait);
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t arrow1Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return arrow1.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return arrow1.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return arrow1.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void arrow1Off() {
  int i;

  for (i = 0; i <  arrow1.numPixels(); i++) {
    arrow1.setPixelColor(i, arrow1.Color(0, 0, 0));
  }
  arrow1.show();
}

/* Arrow 2 Rainbow Functions */
void arrow2RainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j = 0; j < 256; j++) {
    for(i = 0; i< arrow2.numPixels(); i++) {
      arrow2.setPixelColor(i, arrow2Wheel(((i * 256 / arrow2.numPixels()) + j) & 255));
    }
    arrow2.show();
    delay(wait);
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t arrow2Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return arrow2.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return arrow2.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return arrow2.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void arrow2Off() {
  int i;
  
  for (i = 0; i < arrow2.numPixels(); i++) {
    arrow2.setPixelColor(i, arrow2.Color(0, 0, 0));
  }
  arrow2.show();
}
