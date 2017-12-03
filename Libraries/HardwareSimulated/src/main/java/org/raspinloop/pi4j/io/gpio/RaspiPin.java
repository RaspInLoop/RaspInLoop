package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.raspinloop.config.PinEdge;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinPullResistance;

public class RaspiPin {

    private static final String RASPBERRY_PI_GPIO_PROVIDER = "RaspberryPi GPIO Provider";
	public static final Pin GPIO_00 = createDigitalPin(0, "GPIO 0");
    public static final Pin GPIO_01 = createDigitalAndPwmPin(1, "GPIO 1"); // supports PWM0 [ALT5]
    public static final Pin GPIO_02 = createDigitalPin(2, "GPIO 2");
    public static final Pin GPIO_03 = createDigitalPin(3, "GPIO 3");
    public static final Pin GPIO_04 = createDigitalPin(4, "GPIO 4");
    public static final Pin GPIO_05 = createDigitalPin(5, "GPIO 5");
    public static final Pin GPIO_06 = createDigitalPin(6, "GPIO 6");
    public static final Pin GPIO_07 = createDigitalPin(7, "GPIO 7");
    public static final Pin GPIO_08 = createDigitalPinNoPullDown(8, "GPIO 8");  // SDA.1 pin has a physical pull-up resistor
    public static final Pin GPIO_09 = createDigitalPinNoPullDown(9, "GPIO 9");  // SDC.1 pin has a physical pull-up resistor
    public static final Pin GPIO_10 = createDigitalPin(10, "GPIO 10");
    public static final Pin GPIO_11 = createDigitalPin(11, "GPIO 11");
    public static final Pin GPIO_12 = createDigitalPin(12, "GPIO 12");
    public static final Pin GPIO_13 = createDigitalPin(13, "GPIO 13");
    public static final Pin GPIO_14 = createDigitalPin(14, "GPIO 14");
    public static final Pin GPIO_15 = createDigitalPin(15, "GPIO 15");
    public static final Pin GPIO_16 = createDigitalPin(16, "GPIO 16");

    // the following GPIO pins are only available on the Raspberry Pi Model A, B (revision 2.0)
    public static final Pin GPIO_17 = createDigitalPin(17, "GPIO 17"); // requires B rev2 or newer model (P5 header)
    public static final Pin GPIO_18 = createDigitalPin(18, "GPIO 18"); // requires B rev2 or newer model (P5 header)
    public static final Pin GPIO_19 = createDigitalPin(19, "GPIO 19"); // requires B rev2 or newer model (P5 header)
    public static final Pin GPIO_20 = createDigitalPin(20, "GPIO 20"); // requires B rev2 or newer model (P5 header)

    // the following GPIO pins are only available on the Raspberry Pi Model A+, B+, Model 2B, Model 3B, Zero
    public static final Pin GPIO_21 = createDigitalPin(21, "GPIO 21"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_22 = createDigitalPin(22, "GPIO 22"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_23 = createDigitalAndPwmPin(23, "GPIO 23"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header) : supports PWM1 [ALT0]
    public static final Pin GPIO_24 = createDigitalAndPwmPin(24, "GPIO 24"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header) : supports PWM1 [ALT5]
    public static final Pin GPIO_25 = createDigitalPin(25, "GPIO 25"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_26 = createDigitalAndPwmPin(26, "GPIO 26"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header) : supports PWM0 [ALT0]
    public static final Pin GPIO_27 = createDigitalPin(27, "GPIO 27"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_28 = createDigitalPin(28, "GPIO 28"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_29 = createDigitalPin(29, "GPIO 29"); // requires 3B, 2B, Zero, A+, B+ or newer model (40 pin header)
    public static final Pin GPIO_30 = createDigitalPinNoPullDown(30, "GPIO 30");  // SDA.0 pin has a physical pull-up resistor
    public static final Pin GPIO_31 = createDigitalPinNoPullDown(31, "GPIO 31");  // SDC.0 pin has a physical pull-up resistor

    protected static Pin createDigitalPinNoPullDown(int address, String name) {
        return createDigitalPin(RASPBERRY_PI_GPIO_PROVIDER, address, name,
                EnumSet.of(PinPullResistance.OFF, PinPullResistance.PULL_UP),
                PinEdge.all());
    }

    protected static Pin createDigitalPin(int address, String name) {
        return createDigitalPin(RASPBERRY_PI_GPIO_PROVIDER, address, name);
    }

    protected static Pin createDigitalAndPwmPin(int address, String name) {
        return createDigitalAndPwmPin(RASPBERRY_PI_GPIO_PROVIDER, address, name);
    }


    // *override* static method from subclass
    // (overriding a static method is not supported in Java
    //  so this method definition will hide the subclass static method)
    public static Pin[] allPins() {
        List<Pin> pins = new ArrayList<>();

        // pins for all Raspberry Pi models
        pins.add(RaspiPin.GPIO_00);
        pins.add(RaspiPin.GPIO_01);
        pins.add(RaspiPin.GPIO_02);
        pins.add(RaspiPin.GPIO_03);
        pins.add(RaspiPin.GPIO_04);
        pins.add(RaspiPin.GPIO_05);
        pins.add(RaspiPin.GPIO_06);
        pins.add(RaspiPin.GPIO_07);
        pins.add(RaspiPin.GPIO_08);
        pins.add(RaspiPin.GPIO_09);
        pins.add(RaspiPin.GPIO_10);
        pins.add(RaspiPin.GPIO_11);
        pins.add(RaspiPin.GPIO_12);
        pins.add(RaspiPin.GPIO_13);
        pins.add(RaspiPin.GPIO_14);
        pins.add(RaspiPin.GPIO_15);
        pins.add(RaspiPin.GPIO_16);

//        // no further pins to add for Model B Rev 1 boards
//        if(board == SystemInfo.BoardType.RaspberryPi_B_Rev1){
//            // return pins collection
//            return pins.toArray(new Pin[0]);
//        }

//        // add pins exclusive to Model A and Model B (Rev2)
//        if(board == SystemInfo.BoardType.RaspberryPi_A ||
//           board == SystemInfo.BoardType.RaspberryPi_B_Rev2){
            pins.add(RaspiPin.GPIO_17);
            pins.add(RaspiPin.GPIO_18);
            pins.add(RaspiPin.GPIO_19);
            pins.add(RaspiPin.GPIO_20);
//        }
//
//        // add pins exclusive to Models A+, B+, 2B, 3B, and Zero
//        else{
//            pins.add(RaspiPin.GPIO_21);
//            pins.add(RaspiPin.GPIO_22);
//            pins.add(RaspiPin.GPIO_23);
//            pins.add(RaspiPin.GPIO_24);
//            pins.add(RaspiPin.GPIO_25);
//            pins.add(RaspiPin.GPIO_26);
//            pins.add(RaspiPin.GPIO_27);
//            pins.add(RaspiPin.GPIO_28);
//            pins.add(RaspiPin.GPIO_29);
//            pins.add(RaspiPin.GPIO_30);
//            pins.add(RaspiPin.GPIO_31);
//        }

        // return pins collection
        return pins.toArray(new Pin[0]);
    }
    
    protected static Map<String, Pin> pins;
    
    protected synchronized static final Map<String, Pin>  getPins(){
    	if ( pins == null)
    		pins =  new HashMap<String, Pin>();
    	return pins;
    }

    protected static Pin createDigitalPin(String providerName, int address, String name) {
        return createDigitalPin(providerName, address, name, EnumSet.allOf(PinEdge.class));
    }

    protected static Pin createDigitalPin(String providerName, int address, String name, EnumSet<PinPullResistance> resistance, EnumSet<PinEdge> edges) {
        return createPin(providerName, address, name,
                EnumSet.of(PinMode.IN, PinMode.OUT),
                resistance,
                edges);
    }

    protected static Pin createDigitalPin(String providerName, int address, String name, EnumSet<PinEdge> edges) {
        return createPin(providerName, address, name,
                EnumSet.of(PinMode.IN, PinMode.OUT),
                PinPullResistance.all(),
                edges);
    }

    protected static Pin createDigitalAndPwmPin(String providerName, int address, String name, EnumSet<PinEdge> edges) {
        return createPin(providerName, address, name,
                         EnumSet.of(PinMode.IN, PinMode.OUT),
                         PinPullResistance.all(),
                         edges);
    }

    protected static Pin createDigitalAndPwmPin(String providerName, int address, String name) {
        return createDigitalAndPwmPin(providerName, address, name, EnumSet.allOf(PinEdge.class));
    }

    protected static Pin createAnalogInputPin(String providerName, int address, String name) {
        return createPin(providerName, address, name, EnumSet.of(PinMode.IN));
    }

    protected static Pin createPin(String providerName, int address, String name, EnumSet<PinMode> modes) {
        Pin pin = new PinImpl(providerName, address, name, modes);
        getPins().put(name, pin);
        return pin;
    }

    protected static Pin createPin(String providerName, int address, String name, EnumSet<PinMode> modes,
                                   EnumSet<PinPullResistance> resistance, EnumSet<PinEdge> edges) {
        Pin pin = new PinImpl(providerName, address, name, modes, resistance, edges);
        getPins().put(name, pin);
        return pin;
    }

    public static Pin getPinByName(String name) {
    	return  getPins().get(name);
    }

    public static Pin getPinByAddress(int address) {
        for(Pin pin :  getPins().values()){
            if(pin.getAddress() == address){
                return pin;
            }
        }
        return null;
    }


    /**
     * Get all pin instances from this provider that support one of the provided pin modes.
     * @param mode one or more pin modes that you wish to include in the result set
     * @return pin instances that support the provided pin modes
     */
    public static Pin[] allPins(PinMode ... mode) {
        List<Pin> results = new ArrayList<>();
        for(Pin p :  getPins().values()){
            EnumSet<PinMode> supported_modes = p.getSupportedPinModes();
            for(PinMode m : mode){
                if(supported_modes.contains(m)){
                    results.add(p);
                    continue;
                }
            }
        }
        return results.toArray(new Pin[0]);
    }
}
