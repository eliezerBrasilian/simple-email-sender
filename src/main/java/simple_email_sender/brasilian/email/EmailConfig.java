package simple_email_sender.brasilian.email;

import java.io.InputStream;
import java.util.Properties;

import simple_email_sender.brasilian.lua.LuaFileReader;

/**
 * Abstract base class for email service configurations.
 * Manages reading configurations from `.lua` files and stores email server
 * properties
 * in a {@link Properties} object.
 *
 * <p>
 * This class uses the Template Method design pattern, delegating the definition
 * of the main Lua table name to its subclasses through the abstract method
 * {@link #getMainTableName()}.
 * </p>
 *
 * <h3>Usage:</h3>
 * <ul>
 * <li><strong>Inheritance</strong>: Create a subclass implementing
 * {@link #getMainTableName()}
 * to specify the main configuration table name in the Lua file</li>
 * <li><strong>Initialization</strong>: Use either constructor to create
 * instances with
 * default or custom Lua file paths</li>
 * <li><strong>Accessing Properties</strong>: Use {@link #getServerProperties()}
 * to
 * retrieve server configurations</li>
 * </ul>
 *
 * <p>
 * <strong>Note</strong>: This class assumes the {@link LuaFileReader} library
 * is used
 * for processing Lua configuration files.
 * </p>
 */
public abstract class EmailConfig {

    /**
     * Gets the name of the main configuration table in the Lua file.
     * Must be implemented by concrete subclasses.
     *
     * @return The name of the main configuration table
     * @throws IllegalStateException if the table name hasn't been properly
     *                               initialized
     */
    protected abstract String getMainTableName();

    /**
     * Default constructor.
     * Initializes configurations by reading from the default Lua file embedded in
     * the project.
     *
     * @throws IllegalArgumentException if the main table name is empty or invalid
     * @throws RuntimeException         if there's an error reading the
     *                                  configuration file
     */
    public EmailConfig() {
        readEmailFile();
    }

    /**
     * Constructor with custom Lua file input stream.
     *
     * @param luaFileStream The input stream for the Lua configuration file
     * @throws IllegalArgumentException if the table name is empty or the stream is
     *                                  null
     * @throws RuntimeException         if there's an error parsing the Lua file
     */
    public EmailConfig(InputStream luaFileStream) {
        if (getMainTableName().isEmpty()) {
            throw new IllegalArgumentException("Main table name cannot be empty");
        }

        LuaFileReader reader = LuaFileReader.createInstance(luaFileStream);
        props = reader.initReader(getMainTableName());
    }

    /**
     * Reads configurations from the default Lua file.
     *
     * @throws IllegalArgumentException if the main table name is empty
     * @throws RuntimeException         if the configuration file cannot be read
     */
    private void readEmailFile() {
        if (getMainTableName().isEmpty()) {
            throw new IllegalArgumentException("Main table name cannot be empty");
        }

        LuaFileReader reader = LuaFileReader.getInstance();
        props = reader.initReader(getMainTableName());
    }

    /**
     * The Properties object storing email server configurations.
     */
    private Properties props;

    /**
     * Retrieves the email server properties loaded from the Lua file.
     *
     * @return {@link Properties} object containing server configurations
     * @throws IllegalStateException if properties haven't been properly initialized
     */
    public Properties getServerProperties() {
        if (props == null) {
            throw new IllegalStateException("Server properties not initialized");
        }
        return props;
    }
}