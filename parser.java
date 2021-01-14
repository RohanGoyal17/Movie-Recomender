import java.io.*;
import java.util.*;

class parser{
	
	static Map<Integer, Object[]> getMovieData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Map<Integer, Object[]> ret = new HashMap<>();
		String line;
		while((line = br.readLine()) != null){
			if(line.length() == 0)
				continue;
			String[] str = line.split("[|]");
			int id = Integer.parseInt(str[0]);
			String name = str[1];
			String date; 
			if(str[2].length() > 10)
				date = str[2].split("[-]")[2];
			else
				date = "";    
       		String imdb = str[4];
       		int[] arr = new int[19];
       		for(int i = 0; i < 19; i++){
       			arr[i] = Integer.parseInt(str[i+5]);
       		}
       		int rating = 0;
       		int watches = 0;
       		ret.put(id, new Object[]{name, date, imdb, arr, new Integer(rating), new Integer(watches)});
		}
		return ret;
	}
	
	static String[] getGenre(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		List<String> ret = new ArrayList<>();
		StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			if(line.length() == 0)
				continue;
			st = new StringTokenizer(line, "|");
			ret.add(st.nextToken());
		}
		int sz = ret.size();
		return ret.toArray(new String[sz]);
	}


	static Map<Integer, Object[]> getUserData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Map<Integer, Object[]> ret = new HashMap<>();
		// StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			if(line.length() == 0)
				continue;
			String[] str = line.split("[|]");
			int id = Integer.parseInt(str[0]);
       		int age = Integer.parseInt(str[1]);
       		String gender = str[2];
       		String occ = str[3];
       		String zip = str[4];
       		int norat = 0;
       		ret.put(id, new Object[]{new Integer(age), gender, occ, zip, new Integer(norat)});
		}
		return ret;
	}

	static void fillRatings(int[][] ratings, String fileName, Map<Integer, Object[]> movieData, Map<Integer, Object[]> userData, int[][] genreComp, int[][] genreData) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		// StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			String[] str = line.split("[\t]");
			int uId = Integer.parseInt(str[0])-1;
			int mId = Integer.parseInt(str[1])-1;
			ratings[uId][mId] = Integer.parseInt(str[2]);
			movieData.get(mId+1)[4] = new Integer((Integer)movieData.get(mId + 1)[4] + ratings[uId][mId]);
			movieData.get(mId+1)[5] = new Integer((Integer)movieData.get(mId + 1)[5] +  1);
			userData.get(uId+1)[4] =  new Integer((Integer)userData.get(uId+1)[4] + 1);
			for(int k = 0; k< 19; k++){	
				if(genreData[mId][k] == 1){
					genreComp[k][0]++;
					genreComp[k][1] += ratings[uId][mId];
				}
			}
		}
	}
}
