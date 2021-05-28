package api.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseUserProperties {

    public static Properties readProperties(String path) {

        Properties props = new Properties();
        Path myPath = Paths.get(path);

	BufferedReader bf = null;

        try {
            /*BufferedReader*/ bf = Files.newBufferedReader(myPath, 
             StandardCharsets.UTF_8);

            props.load(bf);
        } catch (IOException ex) {
            Logger.getLogger(DataBaseUserProperties.class.getName()).log(
                    Level.SEVERE, null, ex);
        }finally{
		try{
			if(bf != null){
				bf.close();
			}
		}catch (Exception e) {
			 e.printStackTrace();
		}
	}

        return props;
    }
}//end class
