import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

// Main File for submit

public class SortFile {
	
	// size of the file in disk
	 static int N = 5000;
	// max items the memory buffer can hold
	 static int M = 100; 
	 
	 public static void sortMerge(String fileName) throws IOException {
		 
		 // name of temp files
		 String tfile = "temp-file-";
		 int[] buffer = new int[M < N ? M : N];
		 
		 FileReader fr = new FileReader(fileName);
		 BufferedReader br = new BufferedReader(fr);
		 int slices = (int) Math.ceil((double) N/M);
		 
		 int i = 0;
		 int j = 0;
		 
		 for(i=0;i<slices;i++) {
			 for(j = 0;j<M;j++) {
				 String t = br.readLine();
			     if (t != null) buffer[j] = Integer.parseInt(t);
			 }
			 
			 // sort buffer
			 Arrays.sort(buffer);
			 
			// write the sorted numbers to temp file
			FileWriter fw = new FileWriter(tfile + Integer.toString(i) + ".txt");
			PrintWriter pw = new PrintWriter(fw);
			
			// paste in new temp file
			for (int k = 0; k < j; k++) {
				pw.println(buffer[k]);
			}
			
			pw.close();
			fw.close();
		 }
		 
		 br.close();
		 fr.close();
		 
		 
		// Now open each file and merge them, then write back to disk
		int[] topNums = new int[slices];
		BufferedReader[] brs = new BufferedReader[slices];
		   
		for (i = 0; i < slices; i++){
			
			brs[i] = new BufferedReader(new FileReader(tfile + Integer.toString(i) + ".txt"));
		    String t = brs[i].readLine();
		    if (t != null)
		     topNums[i] = Integer.parseInt(t);
		    else
		     topNums[i] = Integer.MAX_VALUE;
		   }
		   FileWriter fw = new FileWriter("external-sorted.txt");
		   PrintWriter pw = new PrintWriter(fw);
		   
		   for (i = 0; i < N; i++)
		   {
		    int min = topNums[0];
		    int minFile = 0;
		    
		    for (j = 0; j < slices; j++)
		    {
		     if (min > topNums[j])
		     {
		      min = topNums[j];
		      minFile = j;
		     }
		    }
		    
		    pw.println(min);
		    String t = brs[minFile].readLine();
		    if (t != null)
		     topNums[minFile] = Integer.parseInt(t);
		    else
		     topNums[minFile] = Integer.MAX_VALUE;
		    
		   }
		   for (i = 0; i < slices; i++) brs[i].close();
		   
		   pw.close();
		   fw.close();
		
	 }
	 
	public static void main(String[] args) throws IOException {
	
		String fileName = "myfile.txt";
		sortMerge(fileName);
	}
}
