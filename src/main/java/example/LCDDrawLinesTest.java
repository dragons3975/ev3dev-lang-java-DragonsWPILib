package example;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class LCDDrawLinesTest {

    public static GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args){

        System.out.println("EV3 LCD Example");

        lcd.clear();
        lcd.setColor(0, 0, 0);
        lcd.drawLine(0, 0, 50, 50);
        lcd.drawLine(0, 0, 30, 60);
        lcd.refresh();

        Delay.msDelay(5000);
    }
}
