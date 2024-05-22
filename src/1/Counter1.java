package Exercise1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Counter1 {

    static volatile int WordTotal = 0;
    static volatile int LinesTotal = 0;

    public static void main(String... args) throws InterruptedException {
        List<Thread> threadlist = new ArrayList<>();

        for (String arg : args) {

            Thread thread = new Thread(new Counter(arg));
            thread.start();
            threadlist.add(thread);

        }

        for (int j = 0; j < threadlist.size(); j++) {
            (threadlist.get(j)).join();

        }

        System.out.println("The total number of lines is: " + LinesTotal + "and the total number of words is: " + WordTotal);

    }
}

class Counter implements Runnable {

    private String fname;
    int countLines;
    int countWords;

    Counter(String fname) {
        super();
        this.fname = fname;
    }

    @Override
    public void run() {
        BufferedReader in;//This is a stream used to read the files
        try {
            String filePath = new File("").getAbsolutePath();
            in = new BufferedReader(new FileReader(filePath + "\\src\\Exercise1\\" + fname));
        } catch (Exception e) {
            System.out.println("File can't be opened!");
            return;
        }

        String[] words;
        try {
            //Read first line
            String line = in.readLine();  
            while (line != null) {
                //Count this line
                countLines++;            
                words = line.split(" ");
                countWords += words.length;
                //Read next line
                line = in.readLine();      
            }

            Counter1.LinesTotal += countLines;
            Counter1.WordTotal += countWords;
        } catch (Exception e) { //in case file can't be read,show this error
            System.out.println("File can't be read!");
            return;
        }

        System.out.println(fname + ": " + countLines + " lines " + countWords + " words");

    }

}
