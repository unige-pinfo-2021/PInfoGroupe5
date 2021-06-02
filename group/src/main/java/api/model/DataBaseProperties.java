package api.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseProperties {

    public static Properties readProperties(String path) {

        Properties props = new Properties();
        Path myPath = Paths.get(path);
        BufferedReader bf = null;
        try {
            bf = Files.newBufferedReader(myPath, 
             StandardCharsets.UTF_8);

            props.load(bf);
        } catch (IOException ex) {
            Logger.getLogger(DataBaseProperties.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {

			if (bf != null)
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }

        return props;
    }
}//end class
