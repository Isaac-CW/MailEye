package MailViewer.secret;

import java.io.*;
import java.nio.file.Paths;

/**
 * Handles fetching the client ID and secret
 */
public class SecretHandler implements Secret {

    private final String rscPath;
    private final String fileName;
    private String ID;
    private String secret;

    public SecretHandler(String dirPath, String fileName) throws IOException {
        this.rscPath = dirPath;
        this.fileName = fileName;

        File f = new File(Paths.get(rscPath, fileName).toString());
        if (!f.exists()){
            throw new FileNotFoundException(String.format("No file named \"%s\" at directory \"%s\"\n", fileName, rscPath));
        }

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        ID = br.readLine();
        secret = br.readLine();

        br.close();
        fr.close();
    }

    public String getID(){
        return ID;
    }

    public String getSecret(){
        return secret;
    }
}
