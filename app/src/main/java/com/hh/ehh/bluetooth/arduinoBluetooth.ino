/*** Bluetooth Sensor Required***/
#include <SoftwareSerial.h>




//Pin variables
const int bluetoothTx = 2; // TX-O pin of bluetooth mate, Arduino Digital
const int bluetoothRx = 3; // RX-I pin of bluetooth mate, Arduino Digital
const int bluetoothLed = 12; // Bluetooth PinLed
const int pinSelect = 4; // Joystick Select : Digital



/****Inici Variables Joystick ****/
int selectPressed = 0;
/****Fi Variables Joystick ****/


/****Inici Variables Bluetooth*****/
int dataFromBt;
boolean lightBlink = false;
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);
/***Fi Variables Bluetooth*****/



void setup() {

  Serial.begin(9600);

  //For Joystick Select
  pinMode(pinSelect, INPUT);
  digitalWrite(pinSelect, HIGH);

  //For Bluetooth
  initBluetooth();
}

void loop() {

  /***JOYSTICK***/
  selectPressed = digitalRead(pinSelect);

  if(selectPressed==0){
     Serial.print("Sending Bluettoth");
     Serial.println();
    bluetooth.write("PANIC#");
  }

 // printJoystick();
  /***End JOYSTICK***/

}

void initBluetooth() {
  bluetooth.begin(115200); // The Bluetooth Mate defaults to 115200bps
  bluetooth.print("$"); // Print three times individually
  bluetooth.print("$");
  bluetooth.print("$"); // Enter command mode
  delay(100); // Short delay, wait for the Mate to send back CMD
  bluetooth.println("U,9600,N"); // Temporarily Change the baudrate to 9600, no parity
  // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
  bluetooth.begin(9600); // Start bluetooth serial at 9600
  pinMode(bluetoothLed, OUTPUT);

}



/************************************Print Functions******************************
En aquestes funcions, haurem de ficar-hi la logica per decidir que enviem cap al PACMAN
******/

void printJoystick() {

  Serial.print('S');
  Serial.print(':');
  Serial.print(selectPressed);
  Serial.println();
}


void printBluetooth() {
  Serial.print('B');
  Serial.print(':');
  Serial.print(dataFromBt);
  Serial.print(';');
  Serial.println();
}

