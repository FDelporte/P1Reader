package be.webtechie.p1reader;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * https://fazecast.github.io/jSerialComm/
 */
public class P1ReaderApp {

    private static final Logger logger = LogManager.getLogger(P1ReaderApp.class.getName());

    private static final String USB_SERIAL_PORT = "usbtty0";

    public static void main(String[] args) {
        logger.info("Starting serial connection to P1 on {}", USB_SERIAL_PORT);
        SerialPort comPort = SerialPort.getCommPort(USB_SERIAL_PORT);
        comPort.openPort();
        logger.info("Serial port open: {}", comPort.isOpen());
        try {
            while (comPort.isOpen()) {
                while (comPort.bytesAvailable() == 0) {
                    Thread.sleep(20);
                }

                byte[] readBuffer = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                logger.info("Read {} bytes: {}", numRead, readBuffer);
            }
        } catch (Exception ex) {
            logger.error("Serial error: {}", ex.getMessage());
        }
        comPort.closePort();
    }
}
