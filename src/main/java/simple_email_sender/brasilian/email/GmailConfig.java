package simple_email_sender.brasilian.email;

import java.io.InputStream;

/**
 * Represents an email server configuration for Gmail.
 * This class extends {@link EmailConfig} and implements the abstract method
 * {@link #getMainTableName()} to return the name of the main Lua table
 * related to Gmail configuration.
 *
 * <p>
 * This class can be used to load specific email configurations for Gmail
 * servers
 * from a Lua configuration file.
 * </p>
 *
 * <h3>Usage:</h3>
 * <ul>
 * <li>Create an instance using the default constructor to load the default Lua
 * file</li>
 * <li>Use the constructor that accepts an InputStream to load a custom Lua
 * file</li>
 * </ul>
 *
 * @see EmailConfig
 */
public class GmailConfig extends EmailConfig {
    /**
     * Default constructor.
     * <p>
     * Initializes the email configuration for Gmail using the default Lua file
     * defined in the project.
     * </p>
     */
    public GmailConfig() {
    }

    /**
     * Constructor that allows specifying a custom Lua file input stream.
     * <p>
     * Initializes the email configuration for Gmail using the provided Lua file.
     * </p>
     *
     * @param luaFileStream The input stream for the Lua file to be loaded
     * @throws IllegalArgumentException if the input stream is null
     * @throws RuntimeException         if there's an error parsing the Lua file
     */
    public GmailConfig(InputStream luaFileStream) {
        super(luaFileStream);
    }

    /**
     * Gets the name of the main configuration table in the Lua file.
     *
     * @return The string "Gmail" representing the main configuration table
     * @implNote This value must match exactly with the table name in the Lua file
     */
    @Override
    public String getMainTableName() {
        return "Gmail";
    }
}
