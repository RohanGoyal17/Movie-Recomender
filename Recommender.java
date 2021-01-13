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
		Map<Integer, Object[]> ret = new HashMap<>();
		// StringTokenizer st;
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
       		ret.put(id, new Object[]{name, date, imdb, arr, new Integer(rating)});
		}
		return ret;
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
       		ret.put(id, new Object[]{new Integer(age), gender, occ, zip});
		}
		return ret;
	}

	static void fillRatings(int[][] ratings, String fileName, Map<Integer, Object[]> movieData) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		// StringTokenizer st;
		String line;
		while((line = br.readLine()) != null){
			String[] str = line.split("[\t]");
			int uId = Integer.parseInt(str[0])-1;
			int mId = Integer.parseInt(str[1])-1;
			ratings[uId][mId] = Integer.parseInt(str[2]);
			movieData.get(mId+1)[4] = new Integer((Integer)movieData.get(mId + 1)[4] + ratings[uId][mId]);
		}
	}

	public static void main(String[] args) throws IOException{
		String[] genre = getGenre("genre.data");
		Map<Integer, Object[]> movieData = getMovieData("movie.data");
		int[][] genreData =  new int[movieData.size()][19];
		for(int i = 0; i < genreData.length; i++){
			genreData[i] = (int[])movieData.get(i+1)[3];
		}
		Map<Integer, Object[]> userData = getUserData("user.data");
		int[][] ratingMatrix = new int[userData.size()][movieData.size()];
		fillRatings(ratingMatrix, "ratings.data", movieData);

		System.out.println("Best movie by genre, Enter genre index (refer documents)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int comparat = Integer.parseInt(br.readLine());
			int flag = 0;
			String output = "";
			for(int i =1; i < movieData.size()+1; i++){
				if(genreData[i-1][comparat] == 1){
					if((Integer)movieData.get(i)[4] > flag){
						flag = (Integer)movieData.get(i)[4];
						output = (String)movieData.get(i)[0];
					}
				}
			}
			System.out.println(output);
			flag = 0;
			output = "";

		System.out.println("Best movie by year, Enter year");
			String yearin = br.readLine();
			for(int i =1; i < movieData.size()+1; i++){
				if(((String)movieData.get(i)[1]).compareTo(yearin) == 0){
					if((Integer)movieData.get(i)[4] > flag){
						flag = (Integer)movieData.get(i)[4];
						output = (String)movieData.get(i)[0];
					}
				}
			}
			System.out.println(output);
			flag = 0;
			output = "";

		System.out.println("Best movie by year and genre, Enter year");
			yearin = br.readLine();
			System.out.println("Enter genre index");
			comparat = Integer.parseInt(br.readLine());
			for(int i =1; i < movieData.size()+1; i++){
				if(genreData[i-1][comparat] == 1){
					if(((String)movieData.get(i)[1]).compareTo(yearin) == 0){
						if((Integer)movieData.get(i)[4] > flag){
							flag = (Integer)movieData.get(i)[4];
							output = (String)movieData.get(i)[0];
						}
					}
				}
			}
			System.out.println(output);
			flag = 0;
			output = "";

	}

}