package bb;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Test4 {

	public static void main(String[] args) {
		double[][] samples = new double[150][5];
		String path = "C:\\Users\\cgub1073\\Desktop\\テーマ\\iris.txt";
		samples = read_file(path);
		swap(samples);
		vector_print(samples[149],5);
		/*
		int K = 10;
		// k-means
		double [][][] prototype = new double[3][K][5];

		double [][][] classes = div_class(samples);
		
		for(int i=0; i<3; i++) {
			prototype[i] = k_means(classes[i], K, 0.0001, 1000,i);
		}
		
		


		double[][] processed_data = classifier(prototype,  samples); 
		accuracy(samples,processed_data);*/

	}
	public static void swap(double[][] samples) {
		long seed = 1;
		Random r = new Random(seed);
		int sample_num = samples.length;
		int irand;
		for (int i=0; i<5; i++) {
			for(int j=0; j<sample_num; j++) {
				irand = r.nextInt(sample_num-j);
				for(int k=0; k<5; k++) {
					double temp = samples[irand][k];
					samples[irand][k] =samples[sample_num-j-1][k];
					samples[sample_num-j-1][k] = temp;
				}
			}
		}
	}
	
	public static double[][] classifier(double[][][] prototype, double[][] samples){
		double[][] sample_data = new double[150][50];
		
		sample_data = list_copy(samples);
		
		for(int i=0; i<samples.length; i++) {
			double distance = Double.MAX_VALUE;
			int index = -1;
			for(int j=0; j<prototype.length; j++) {
				for(int k=0; k<prototype[j].length; k++) {
					double temp = distance(samples[i], prototype[j][k], 0, 3);
					if(temp<distance) {
						distance = temp;

						index = 1+j;
					}
				}
			}
			//System.out.println(index);
			sample_data[i][4] = index;
		}
		return sample_data;
	}
	public static double[][] list_copy(double[][] a){
		
		double[][] temp = new double[a.length][a[0].length] ;
		
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[i].length; j++) {
				temp[i][j] = a[i][j];
			}
		}
		return temp;
	}
	public static void vector_print(double[] a, int b) {
		for(int i=0; i<a.length; i++) {
			System.out.print(String.format("%."+ b +"f", a[i]) + ", ");

		}
		System.out.println("");
	}
	public static void accuracy(double[][] samples, double[][] processed_data) {
		int n=0;
		for(int i=0; i<samples.length; i++) {
			//System.out.println(samples[i][4]);
			if((samples[i][4]-processed_data[i][4])==0) {
				n++;
			}

		}
		System.out.println("分類制度は：" + (double)n/150+ "％");
	}
	public static double[][] k_means(double[][] data, int K, double sita, int step,int label){
		int cluster_num = data.length/K;
		double[][] sample_data  = list_copy(data);
		double[][] average_k = new double[K][4];
		//初期化---------------------------------------------------
		for(int i=0; i<K; i++) {
			for(int j=0; j<4; j++) {
				average_k[i][j] =0;
			}
		}
		for(int i=0; i<K; i++) {
			for(int j=0; j<cluster_num; j++) {
				
				average_k[i] = vector_add(sample_data[i*cluster_num + j],average_k[i], 4);
			}
			average_k[i] = vector_div(average_k[i], cluster_num,4);	
		}

		//初期化終わり------------------------------------------------
		
    	double D =scoreD(sample_data, average_k);
    	int n=0;
    	double R = Double.MAX_VALUE;
    	while(sita<R || n<step) {
    		sample_data = reassign(sample_data, average_k);
        	average_k = culc_average_k(sample_data, K);
        	double postD = scoreD(sample_data, average_k);
        	R = culc_R(D,postD);

        	D = postD;
        	n++;
    	}

		double[][] temp = new double[K][5];
		for(int i=0; i<temp.length; i++) {
			for(int j=0; j<4; j++) {
				temp[i][j] = average_k[i][j];
			}
			temp[i][4] = label;
		}
		return temp;

	}
	public static double culc_R(double D, double postD) {
		double R;
		R =(D-postD)*(D-postD)/D*postD;
		return R;
	}
	public static double[][] culc_average_k(double[][] data, int K){
		double[][] average_k = new double[K][4];
		double temp;
		int[] element_num = new int[K];
		for(int i=0; i<K; i++) {
			element_num[i]=0;
			for(int j=0; j<4; j++) {
				average_k[i][j] =0;
			}
		}
		for(int i=0; i<data.length; i++) {
			temp = data[i][4];
			average_k[(int)temp] = vector_add(average_k[(int)temp], data[i], 4);
			element_num[(int)temp]++;
		}
		for(int i=0; i<K; i++) {
			average_k[i] = vector_div(average_k[i], element_num[i],4);
		}
		double[][] temp2 = new double[K][5];
		for(int i=0; i<temp2.length; i++) {
			for(int j=0; j<4; j++) {
				temp2[i][j] = average_k[i][j];
			}
			temp2[i][4] = 5;
		}
		return temp2;


	}
	public static double[][] reassign(double[][] data, double[][] average_k) {
		int K = average_k.length;
		double temp;
		double index = -1;
		for(int i=0; i<data.length; i++) {
			double distance =Double.MAX_VALUE;
			for(int j=0; j<K; j++) {
				temp = distance(data[i], average_k[j], 0, 3);
				if(temp<distance) {
					distance = temp;
					index = j;
				}
			}
			data[i][4] = index;
		}
		return data;
	}
	public static double scoreD(double[][] data, double[][] average_k) {
		double temp;
		double D =0;
		for(int i=0; i<data.length; i++) {
			double distance =Double.MAX_VALUE;
			for(int j=0; j<average_k.length; j++) {
				temp = distance(data[i], average_k[j], 0, 3);
				if(temp<distance) {
					distance = temp;
				}
			}
			D += distance;
		}
		return D;
	}
	public static double distance(double[] a, double[] b, int pre, int post) {
		double n = 0.0;
		for(int i=pre; i<post+1; i++) {
			n += (a[i]-b[i])*(a[i]-b[i]);
		}
		//System.out.println(n);
		return n;
	}
	public static double[] vector_div(double[] a,int div,int num) {
		double[] temp = new double[num];
		for(int i=0; i<num; i++) {
			temp[i] = 0;
		}
		for(int i=0; i<num; i++) {
			temp[i] = (double)a[i]/div;
		}
		return temp;
	}
	public static double[] vector_add(double[] a, double[] b, int num) {
		double[] temp = new double[num];
		for(int i=0; i<num; i++) {
			temp[i] = a[i] + b[i];
		}
		return temp;
	}
	public static double[][][] div_class(double[][]data) {

		double[][][] classes = new double[3][50][5];;
		for(int i=0; i<150; i++) {
			if(data[i][4]==1.0) {
				classes[0][i] = data[i];
			}else if(data[i][4]==2.0){
				classes[1][i-50] = data[i];
			}else{
				classes[2][i-100] = data[i];
			}
		}
		return classes;
	}
	public static double[][] culc_average(double[][] data) {
		System.out.println("平均を求める");
		double[][] ave = new double[3][5];
		double[] line1 = {0.0,0.0,0.0,0.0,0.0};
		double[] line2 = {0.0,0.0,0.0,0.0,0.0};
		double[] line3 = {0.0,0.0,0.0,0.0,0.0};


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



