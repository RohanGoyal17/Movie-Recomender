import java.io.*;
import java.util.*;



class recommend{

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
       		int watches = 0;
       		ret.put(id, new Object[]{name, date, imdb, arr, new Integer(rating), new Integer(watches)});
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
	static void getGenreWeights(int[][] genreweights, int[][] ratingMatrix, int[][] genreData, int userid, float[][] moviepredict, Map<Integer, Object[]> movieData) throws IOException{
		for(int i = 0; i< movieData.size(); i++){
			if(ratingMatrix[userid-1][i] != 0){
			moviepredict[i][0] = 1; 
				for(int j = 0; j < 19; j++){
					if(genreData[i][j] == 1){
						genreweights[j][0] = genreweights[j][0] + ratingMatrix[userid-1][i] - 3;
						genreweights[j][1]++;
					}
					else{
						genreweights[j][2] = genreweights[j][2] + ratingMatrix[userid-1][i] - 3;
						genreweights[j][3]++;
					}
				}
			}
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
		int[][] genreComp = new int[19][2];
		fillRatings(ratingMatrix, "ratings.data", movieData, userData, genreComp, genreData);

		

		System.out.println("Enter User ID");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int userid = Integer.parseInt(br.readLine());
		float[][] moviepredict = new float[movieData.size()][2];


		int[][] genreweights = new int[19][4];
		getGenreWeights(genreweights, ratingMatrix, genreData, userid, moviepredict, movieData);

		for (int i =0; i < movieData.size(); i++ ) {
			if(moviepredict[i][1] == 0){
				for(int j =0; j < 19; j++){
					if(genreData[i][j] == 1){
						moviepredict[i][0] += (float)genreweights[j][0]/genreweights[j][0];
					}
					else{
						moviepredict[i][0] -= (float)genreweights[j][2]/genreweights[j][3]/20;
					}
				}
			}
		}

		int[] recommended = new int[5];

		
		if(moviepredict[0][0] > moviepredict[1][0]){
			recommended[0] = 0;
			recommended[1] = 1;
		}
		else{
			recommended[0] = 1;
			recommended[1] = 0;
		} 
		/////////////
		if(moviepredict[2][0] > moviepredict[recommended[0]][0]){
			recommended[2] = recommended[1];
			recommended[1] = recommended[0];
			recommended[0] = 2;
		}
		else if(moviepredict[2][0] > moviepredict[recommended[1]][0]) {
			recommended[2] = recommended[1];
			recommended[1] = 2;
		}
		else recommended[2] = 2;
		//////////////
		if(moviepredict[3][0] > moviepredict[recommended[0]][0]){
			recommended[3] = recommended[2];
			recommended[2] = recommended[1];
			recommended[1] = recommended[0];
			recommended[0] = 3;
		}
		else if(moviepredict[3][0] > moviepredict[recommended[1]][0]){
			recommended[3] = recommended[2];
			recommended[2] = recommended[1];
			recommended[1] = 3;
		}
		else if(moviepredict[3][0] > moviepredict[recommended[2]][0]) {
			recommended[3] = recommended[2];
			recommended[2] = 3;
		}
		else recommended[3] = 3;
		///////////////
		if(moviepredict[4][0] > moviepredict[recommended[0]][0]){
			recommended[4] = recommended[3];
			recommended[3] = recommended[2];
			recommended[2] = recommended[1];
			recommended[1] = recommended[0];
			recommended[0] = 4;
		}
		else if(moviepredict[4][0] > moviepredict[recommended[1]][0]){
			recommended[4] = recommended[3];
			recommended[3] = recommended[2];
			recommended[2] = recommended[1];
			recommended[1] = 4;
		}
		else if(moviepredict[4][0] > moviepredict[recommended[2]][0]){
			recommended[4] = recommended[3];
			recommended[3] = recommended[2];
			recommended[2] = 4;
		}
		else if(moviepredict[4][0] > moviepredict[recommended[3]][0]){
			recommended[4] = recommended[3];
			recommended[3] = 4;
		}
		else recommended[4] = 4;
		////////////////
		for(int i = 5; i < movieData.size(); i++){
			if(moviepredict[i][1] == 0){
				if(moviepredict[i][0] > moviepredict[recommended[0]][0]){
				recommended[4] = recommended[3];
				recommended[3] = recommended[2];
				recommended[2] = recommended[1];
				recommended[1] = recommended[0];
				recommended[0] = i;
				}
				else if(moviepredict[i][0] > moviepredict[recommended[1]][0]){
					recommended[4] = recommended[3];
					recommended[3] = recommended[2];
					recommended[2] = recommended[1];
					recommended[1] = i;
				}
				else if(moviepredict[i][0] > moviepredict[recommended[2]][0]){
					recommended[4] = recommended[3];
					recommended[3] = recommended[2];
					recommended[2] = i;
				}
				else if(moviepredict[i][0] > moviepredict[recommended[3]][0]){
					recommended[4] = recommended[3];
					recommended[3] = i;
				}
			}
			else if(moviepredict[i][0] > moviepredict[recommended[4]][0]) recommended[4] = i;
		}

		for(int i = 0; i < 5; i++){
			if(((String)movieData.get(recommended[i])[0]).length() != 0)
			System.out.println(movieData.get(recommended[i])[0]);
		}

	}

}