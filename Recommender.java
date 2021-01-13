import java.io.*;
import java.util.*;

class Recommender{

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

	static Map<Integer, Object[]> getMovieData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Map<String, Object[]> ret = new HashMap<>();
		StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			if(line.length() == 0)
				continue;
			st = new StringTokenizer(line, "|");
			int id = Integer.parseInt(st.nextToken());
			/*movie id | movie title | release date | video release date |
       IMDb URL | */
       		String name = st.nextToken();
       		String date = st.nextToken();
       		String imdb = st.nextToken();
       		int[] arr = new int[19];
       		for(int i = 0; i < n; i++){
       			arr[i] = Integer.parseInt(st.nextToken());
       		}
       		ret.put(id, new Object[]{name, date, imdb, arr});
		}
		return ret;
	}

	static Map<Integer, Object[]> getMovieData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Map<String, Object[]> ret = new HashMap<>();
		StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			if(line.length() == 0)
				continue;
			st = new StringTokenizer(line, "|");
			int id = Integer.parseInt(st.nextToken());
       		int age = Integer.parseInt(st.nextToken());
       		String gender = st.nextToken();
       		String occ = st.nextToken();
       		String zip = st.nextToken();
       		ret.put(id, new Object[]{new Integer(age), gender, occ, zip});
		}
		return ret;
	}

	static void fillRatings(int[][] ratings, String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			st = new StringTokenizer(line, "\t");
			int uId = Integer.parseInt(st.nextToken())-1;
			int mId = Integer.parseInt(st.nextToken())-1;
			ratings[uId][mId] = Integer.parseInt(st.nextToken());
		}
	}

	public static void main(String[] args) throws IOException{
		String[] genre = getGenre("");						// 	basically enum
		Map<Integer, Object[]> movieData = getMovieData("");			// 
		int[][] genreData =  new int[movieData.size()][19];
		for(int i = 0; i < genreData.length; i++){
			genreData[i] = (int[])movieData.get(i+1)[3];
		}
		Map<Integer, Object[]> userData = new getUserData("");
		int[][] ratingMatrix = new int[userData.size()][movieData.size()];
		fillRatings(ratingMatrix, "");
	}
}