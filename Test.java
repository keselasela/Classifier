package theme2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 
public class Test {
	static double[][] samples = new double[150][5];
    public static void main(String[] args) {
        String path = "C:\\Users\\cgub1073\\Desktop\\iris.txt";
        samples = read_file(path);
        //double[][] ave = culc_average(samples);
        //double[][] dev = culc_deviation(samples, ave);
        
       
        double[] weight = perceptron(samples);
        //for(int i=0; i<100; i++) {
        //	System.out.println(samples[i][4]);
        //}
        vector_print(weight, 2);
        Classifier7(samples, weight);
    }
    public static double[] perceptron(double[][] data) {
   
    	double n=15;
    	double p = 0.01;
    	double[] I = {1.0, 1.0, 1.0, 1.0, 1.0};
    	double[] weight = {0.33, 0.23, -0.38, -0.05, 0.47};
    	double[] temp = {1.0, 0.0, 0.0, 0.0, 0.0};
    	
    	for(int h=0; h<n; h++) {
    		for(int i=0; i<100; i++) {
        		for(int j=1; j<temp.length; j++) {
            		temp[j] = data[i][j-1];
            	}
        		
        		if(-1.0==data[i][4]-label(temp, weight)) {
        			for(int j=0; j<temp.length; j++) {
        				weight[j] = weight[j] + temp[j]*p;
        			}
        		}else if(1.0==data[i][4]-label(temp, weight)) {
        			for(int j=0; j<temp.length; j++) {
        				weight[j] = weight[j] - temp[j]*p;
        			}
        		}
        		//System.out.println(n);
        	}
    	}
    	return weight;
    	
    }
    public static void Classifier7(double[][] data, double[] weight) {
    	double[] temp = {1.0, 0.0, 0.0, 0.0, 0.0};
    	double n=0;
    	double[] weight6 = {0.33, 0.23, -0.38, -0.05, 0.47};
    	for(int i=0; i<100; i++) {
    		for(int j=1; j<temp.length; j++) {
        		temp[j] = data[i][j-1];
        	}
    		
    		if((label(temp, weight)-data[i][4])==0) {
    			n++;
    		}
    		//System.out.println(n);
    	}
    	System.out.println("分類制度は：" + n + "％");
    	//String.format("%.2f", n/100)
    }
    public static double label(double[] a, double[] weight) {
    	//In x = (x1. x2, x3, x4, Cn)
    	//Out x = (x1. x2, x3, x4, label)
    	double n =0.0;
    	
    	n = product(a, weight, 1.0);
    	
    	if (n>0) {
    		//System.out.println("1");
    		
    		return 1.0;
    	}else {
    		//System.out.println("2");
    		
    		return 2.0;
    	}
    }
    public static void vector_print(double[] a, int b) {
    	for(int i=0; i<a.length; i++) {
    		System.out.print(String.format("%."+ b +"f", a[i]) + ", ");
    	}
    }
    public static double product(double[] a, double[] b, double multiple) {
    	
    	//vector_print(a);
    	//vector_print(b);
    	//System.out.println("");
    	double n = 0.0;
    	for(int i=0; i<a.length; i++) {
    		n += a[i]*b[i]*multiple;
    	}
    	//System.out.println(n);
    	return n;
    }
    public static double[][] culc_deviation(double[][] data, double[][] ave) {
    		System.out.println("標準偏差を求める");
        	double[][] dev = new double[3][5];
        	double[] line1 = {0.0,0.0,0.0,0.0,1.0};
        	double[] line2 = {0.0,0.0,0.0,0.0,2.0};
        	double[] line3 = {0.0,0.0,0.0,0.0,3.0};
        	//double[][] class1 = new String[50][5];
        	//double[][] class2 = new String[50][5];
        	//double[][] class3 = new String[50][5];
        	
        	for(int i=0; i<150; i++) {
        		if(data[i][4]==1.0) {
        			for(int j=0; j<data[i].length-1; j++) {
        				
        				line1[j] += (data[i][j]-ave[0][j])*(data[i][j]-ave[0][j]);
        			}
        		}else if(data[i][4]==2.0){
        			for(int j=0; j<data[i].length-1; j++) {
        				line2[j] += (data[i][j]-ave[1][j])*(data[i][j]-ave[1][j]);
        			}
        		}else{
        			for(int j=0; j<data[i].length-1; j++) {
        				line3[j] += (data[i][j]-ave[2][j])*(data[i][j]-ave[2][j]);
        			}
        		}
        	}
        	for(int i=0; i<4; i++) {
        		line1[i] = Math.sqrt(line1[i]/50);
        		line2[i] = Math.sqrt(line2[i]/50);
        		line3[i] = Math.sqrt(line3[i]/50);
        	}
        	
        	for(int i=0; i<line1.length; i++) {
        		System.out.print(String.format("%.2f", line1[i]) );
        		if(i==line1.length-1) {
        			System.out.println("");
        		}else {
        			System.out.print(", ");
        		}
        	}
        	for(int i=0; i<line2.length; i++) {
        		System.out.print(String.format("%.2f", line2[i]) );
        		if(i==line2.length-1) {
        			System.out.println("");
        		}else {
        			System.out.print(", ");
        		}
        	}
        	for(int i=0; i<line3.length; i++) {
        		System.out.print(String.format("%.2f", line3[i]) );
        		if(i==line3.length-1) {
        			System.out.println("");
        		}else {
        			System.out.print(", ");
        		}
        	}
        	dev[0] = line1;
        	dev[1] = line2;
        	dev[2] = line3;
        	return dev;
        	
        }
    
    public static double[][] culc_average(double[][] data) {
    	System.out.println("平均を求める");
    	double[][] ave = new double[3][5];
    	double[] line1 = {0.0,0.0,0.0,0.0,0.0};
    	double[] line2 = {0.0,0.0,0.0,0.0,0.0};
    	double[] line3 = {0.0,0.0,0.0,0.0,0.0};
    	//double[][] class1 = new String[50][5];
    	//double[][] class2 = new String[50][5];
    	//double[][] class3 = new String[50][5];
    	
    	for(int i=0; i<150; i++) {
    		if(data[i][4]==1.0) {
    			for(int j=0; j<data[i].length; j++) {
    				
    				line1[j] += data[i][j];
    			}
    		}else if(data[i][4]==2.0){
    			for(int j=0; j<data[i].length; j++) {
    				line2[j] += data[i][j];
    			}
    		}else{
    			for(int j=0; j<data[i].length; j++) {
    				line3[j] += data[i][j];
    			}
    		}
    	}
    	for(int i=0; i<5; i++) {
    		line1[i] = line1[i]/50;
    		line2[i] = line2[i]/50;
    		line3[i] = line3[i]/50;
    	}
    	
    	for(int i=0; i<line1.length; i++) {
    		System.out.print(String.format("%.2f", line1[i]) );
    		if(i==line1.length-1) {
    			System.out.println("");
    		}else {
    			System.out.print(", ");
    		}
    	}
    	for(int i=0; i<line2.length; i++) {
    		System.out.print(String.format("%.2f", line2[i]) );
    		if(i==line2.length-1) {
    			System.out.println("");
    		}else {
    			System.out.print(", ");
    		}
    	}
    	for(int i=0; i<line3.length; i++) {
    		System.out.print(String.format("%.2f", line3[i]) );
    		if(i==line3.length-1) {
    			System.out.println("");
    		}else {
    			System.out.print(", ");
    		}
    	}
    	ave[0] = line1;
    	ave[1] = line2;
    	ave[2] = line3;
    	return ave;
    	
    }
    
    
    public static double[][] read_file(String path){
    	double[][] samples = new double[150][5];
    	try {
            // ファイルのパスを指定する
            File file = new File(path);
         
            // ファイルが存在しない場合に例外が発生するので確認する
            if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return samples;
            }
         
            // BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data;
            int n=0;
            while ((data = bufferedReader.readLine()) != null) {
                String[] line = data.split(" ");
                for(int i=0; i<line.length; i++) {
                	samples[n][i] = Double.valueOf(line[i]);
                }
                n++;
            }
         
            // 最後にファイルを閉じてリソースを開放する
            bufferedReader.close();
            
         
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return samples;
    }
 
}