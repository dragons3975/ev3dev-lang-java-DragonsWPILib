package ev3dev.utils;

import ev3dev.hardware.EV3DevPlatform;
import ev3dev.hardware.EV3DevPropertyLoader;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class Sysfs2Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    String BATTERY;
    String BATTERY_EV3;
    String BATTERY_PISTORMS;
    String BATTERY_BRICKPI;
    String BATTERY_BRICKPI3;
    String VOLTAGE = "voltage_now";
    String CURRENT = "current_now";

    @Before
    public void resetTest() throws IOException {

        FakeBattery.resetEV3DevInfrastructure();

        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        BATTERY = ev3DevProperties.getProperty("battery");
        BATTERY_EV3 =  ev3DevProperties.getProperty("ev3.battery");
        BATTERY_PISTORMS =  ev3DevProperties.getProperty("pistorms.battery");
        BATTERY_BRICKPI = ev3DevProperties.getProperty("brickpi.battery");
        BATTERY_BRICKPI3 =  ev3DevProperties.getProperty("brickpi3.battery");

    }

    //OK

    @Test
    public void existPathSuccessTest() throws IOException {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        final boolean result = Sysfs2.existPath(fakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        assertThat(result, is(true));
    }

    @Test
    public void readString() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/" + BATTERY_EV3 + "/" + VOLTAGE;
        String result = Sysfs2.readString(pathToAssert);

        assertThat(result, is(fakeBattery.BATTERY_FIELD_VOLTAGE_VALUE));
    }

    @Test
    public void readInteger() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/" + BATTERY_EV3 + "/" + VOLTAGE;
        int result = Sysfs2.readInteger(pathToAssert);

        assertThat(result, is(Integer.parseInt(FakeBattery.BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void readFloat() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/" + BATTERY_EV3 + "/" + VOLTAGE;
        float result = Sysfs2.readFloat(pathToAssert);

        assertThat(result, is(Float.parseFloat(FakeBattery.BATTERY_FIELD_VOLTAGE_VALUE)));
    }

    @Test
    public void getElements() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY;
        final List<File> fileList = new ArrayList<>();
        fileList.add(new File(pathToAssert + "/" + BATTERY_EV3));

        assertThat(Sysfs2.getElements(pathToAssert), is(fileList));
    }

    @Test
    public void existFile() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/"+ BATTERY_EV3 + "/" + VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        boolean result = Sysfs2.existFile(path);

        assertThat(result, is(true));
    }

    @Test
    public void notExistFile() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/"+ BATTERY_EV3 + "/" + CURRENT + "-ERROR";
        final Path path = Paths.get(pathToAssert);

        assertThat(Sysfs2.existFile(path), is(false));
    }

    @Test
    public void writeString() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/"+ BATTERY_EV3 + "/" + VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        Sysfs2.writeString(pathToAssert, "10");

        assertThat(Sysfs.readString(pathToAssert), is("10"));
    }

    @Test
    public void readStringWithException() throws Exception {

        thrown.expect(RuntimeException.class);

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/"+ BATTERY_EV3 + "/" + VOLTAGE + "-ERROR";
        final Path path = Paths.get(pathToAssert);
        Sysfs2.readString(pathToAssert);
    }

    @Test
    public void writeInteger() throws Exception {

        final FakeBattery fakeBattery = new FakeBattery(EV3DevPlatform.EV3BRICK);

        String pathToAssert = FakeBattery.EV3DEV_FAKE_SYSTEM_PATH + "/"+ BATTERY + "/"+ BATTERY_EV3 + "/" + VOLTAGE;
        final Path path = Paths.get(pathToAssert);
        Sysfs2.writeInteger(pathToAssert, 10);

        assertThat(Sysfs2.readInteger(pathToAssert), is(10));
    }

    @Ignore("Review error in detail for Travis CI")
    @Test(expected = RuntimeException.class)
    public void writeBytesTest() {

        int BUFFER_SIZE = 0;
        byte[] buf = new byte[BUFFER_SIZE];

        final String FB_PATH = "/dev/MY_PERSONAL_PATH";

        Sysfs2.writeBytes(FB_PATH, buf);
    }

}
