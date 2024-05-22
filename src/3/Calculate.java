package Exercise3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculate{
    
    public volatile static List<Double> listForMean= new ArrayList<>();
    public volatile static List<Double> listForVar= new ArrayList<>();

   
    public static void main(String... args) throws InterruptedException {
  
         List<Thread> threadlist = new ArrayList<>();
            
            for (String arg : args) {
            
                Thread thread = new Thread( new Calculator(arg));
               
                thread.start();

                threadlist.add(thread);
                
            }        
           
            for (int j = 0; j < threadlist.size(); j++) {
             try {
                 (threadlist.get(j)).join();
             } catch (InterruptedException ex) {
                 Logger.getLogger(Calculate.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
           
            
            System.out.println("The mean in total is: "+ findMean()+ "and the standard deviation is: " +findStdDev());
    }
    
        
    public static double findMean(){
        double meanTotal=0.0;
        for (int x = 0; x < listForMean.size(); x++) {
                meanTotal += listForMean.get(x);
        }
        
        return (meanTotal/listForMean.size());
    }
    
        public static double findStdDev(){
        double varianceTotal=0.0;
        for (int x = 0; x < listForVar.size(); x++) {
                varianceTotal += listForVar.get(x);
        }
        
        return Math.sqrt(varianceTotal);
    }
    
}
class Calculator implements Runnable {

	private String fileName;
        double mean=0.0;
        double variance=0.0;
        String[] splitLine = new String[1000];
        double[] numbers = new double[1000];
       
        Calculator(String fname) {
            super();
            this.fileName = fname;
	}
        @Override
	public void run() {
                    BufferedReader in;
                    try {
                        String filePath = new File("").getAbsolutePath();
                        in = new BufferedReader(new FileReader("\\src\\Exercise3"+fileName) );
                     
                    }
                    catch (Exception e) {
                        System.out.println("File can't be opened!");
                        return;
                    }
                    
                
                    try {
                        String line = in.readLine(); 
                   
                        while (line != null) {
                            int i=0;
                            String[] splitLine = line.split(" ");
                            for (String str : splitLine)
                                 numbers[i++] = Double.parseDouble(str);
                            mean = findMean(numbers);
                            variance= findVariance(numbers);
                            line = in.readLine();      
                        }
                        
                    }catch (Exception e) {
                        System.out.println("File can't be read!");
                        return;
                    }
                    Calculate.listForMean.add(mean);
                        Calculate.listForVar.add(variance);
                        
                   System.out.println(fileName + ": " + mean + " mean " + variance + " variance");
    
                }
        
        public double findMean(double[] NumberArr){
             double total = 0;

            for(int i=0; i<NumberArr.length; i++){
        	total = total + NumberArr[i];
            }

           return (total/NumberArr.length);
        }
        
        public double findVariance(double[] arrayNumber)
        {
            double sum = 0.0, value = 0.0;
            int length = arrayNumber.length;

            for(double num : arrayNumber) {
                sum += num;
            }

            double mean = sum/length;

            for(double num: arrayNumber) {
                value += Math.pow(num - mean, 2);
            }

            return value/length;
        }

        
        
      
        }
