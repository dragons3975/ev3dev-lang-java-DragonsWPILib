package ev3dev.actuators.lego.motors;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import lejos.hardware.port.Port;

/**
 * Abstraction for an Lego Mindstorms motors with no speed regulation.
 * http://www.ev3dev.org/docs/motors/
 */
public class UnregulatedMotor extends BasicMotor implements MotorController {

    private boolean m_isInverted = false;

    /**
     * Constructor
     *
     * @param portName port
     */
    public UnregulatedMotor(final Port portName) {
        super(portName);
    }
    
    @Override
    public void set(double speed) {
        setPower(Math.abs((int)(speed*100)));
        if ((m_isInverted && speed > 0) || (!m_isInverted && speed < 0))
        {
            backward();
        }
        else
        {
            forward();
        }
    }

    @Override
    public double get() {
        return getPower()/100;
    }

    @Override
    public void setInverted(boolean isInverted) {
        m_isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return m_isInverted;
    }

    @Override
    public void disable() {
        stopMotor();
    }

    @Override
    public void stopMotor() {
        stop();
    }

}
