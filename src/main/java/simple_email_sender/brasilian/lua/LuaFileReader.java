package simple_email_sender.brasilian.lua;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class for reading and parsing Lua configuration files.
 * Uses LuaJ library to process Lua scripts and convert them to Java Properties.
 * 
 * <p>
 * This class implements the Singleton pattern to maintain a single instance
 * when using the default configuration file, while allowing custom instances
 * for different Lua files through {@link #createInstance(InputStream)}.
 * </p>
 *
 * <h3>Usage Examples:</h3>
 * 
 * <pre>
 * // Using default configuration file (email_props.lua)
 * LuaFileReader reader = LuaFileReader.getInstance();
 * Properties props = reader.initReader("Gmail");
 * 
 * // Using custom Lua file
 * try (InputStream stream = new FileInputStream("custom_config.lua")) {
 *     LuaFileReader customReader = LuaFileReader.createInstance(stream);
 *     Properties customProps = customReader.initReader("CustomConfig");
 * }
 * </pre>
 * 
 * @see <a href="https://github.com/luaj/luaj">LuaJ Project</a>
 */
public class LuaFileReader {
    /**
     * Singleton instance of the reader
     */
    private static LuaFileReader instance = null;

    /**
     * Lua global environment context
     */
    final private Globals globals;

    /**
     * Gets the singleton instance using the default Lua file (email_props.lua).
     * The file must be located in the classpath.
     *
     * @return The singleton LuaFileReader instance
     * @throws RuntimeException      if the default Lua file cannot be found or
     *                               loaded
     * @throws IllegalStateException if there's an error initializing the Lua
     *                               environment
     */
    public static LuaFileReader getInstance() {
        if (instance == null) {
            InputStream luaFileStream = LuaFileReader.class.getResourceAsStream("email_props.lua");
            if (luaFileStream == null) {
                throw new RuntimeException("Arquivo Lua não encontrado: email_props.lua");
            }
            instance = new LuaFileReader(luaFileStream);
        }
        return instance;
    }

    /**
     * Creates a new instance with a custom Lua file input stream.
     *
     * @param luaFileStream The input stream for the Lua configuration file
     * @return A new LuaFileReader instance
     * @throws IllegalArgumentException if the input stream is null
     * @throws RuntimeException         if there's an error loading the Lua script
     */
    public static LuaFileReader createInstance(InputStream luaFileStream) {
        if (luaFileStream == null) {
            throw new RuntimeException("O InputStream fornecido é nulo.");
        }
        return new LuaFileReader(luaFileStream);
    }

    /**
     * Private constructor that loads and executes the Lua script.
     *
     * @param luaFileStream The input stream containing the Lua script
     * @throws RuntimeException if there's an error parsing or executing the script
     */
    private LuaFileReader(InputStream luaFileStream) throws RuntimeException {
        try {
            globals = JsePlatform.standardGlobals();
            // Chunk de código carregado do arquivo Lua
            LuaValue chunk = globals.load(luaFileStream, "script.lua", "t", globals);
            chunk.call();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo Lua", e);
        }
    }

    /**
     * Reads properties from the specified main table in the loaded Lua file.
     *
     * @param mainTableName The name of the main configuration table in Lua
     * @return Properties object containing all key-value pairs from the table
     * @throws IllegalArgumentException if the specified table doesn't exist
     * @throws IllegalStateException    if the Lua environment isn't properly
     *                                  initialized
     */
    public Properties initReader(String mainTableName) throws IllegalArgumentException {
        // Tabela principal no arquivo Lua
        LuaTable mainTable = (LuaTable) globals.get(mainTableName);

        if (mainTable.equals(LuaValue.NIL)) {
            throw new IllegalArgumentException("Tabela principal não encontrada: " + mainTableName);
        }

        Properties props = new Properties();
        for (LuaValue key : mainTable.keys()) {
            props.put(key.tojstring(), mainTable.get(key).tojstring());
        }
        return props;
    }
}
