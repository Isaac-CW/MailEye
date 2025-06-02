package MailViewer.secret;

import java.io.*;
import java.nio.file.Paths;

public class LoginHandler implements Login{

    private final String rscPath;
    private final String fileName;
    private String uname;
    private String pword;

    public LoginHandler(String dirPath, String fileName) throws IOException {
        this.rscPath = dirPath;
        this.fileName = fileName;

        File f = new File(Paths.get(rscPath, fileName).toString());
        if (!f.exists()){
            throw new FileNotFoundException(String.format("No file named \"%s\" at directory \"%s\"\n", fileName, rscPath));
        }

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        uname = br.readLine();
        pword = br.readLine();

        br.close();
        fr.close();
    }

    @Override
    public String getUname() {
        return uname;
    }

    @Override
    public String getPword() {
        return pword;
    }
}
