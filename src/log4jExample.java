import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class log4jExample
{
    public static void main( String[] args ) {
        Logger logger = LogManager.getRootLogger();
        logger.trace("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
    }
}