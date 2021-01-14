import java.io.*;
import java.util.*;



class recommend{

	
	

	public static void main(String[] args) throws IOException{
		System.out.println("entre path for genreData");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String fin = br.readLine();
		if(fin.length() == 0) fin = "genre.data";
		String[] genre = parser.getGenre(fin);

		System.out.println("entre path for movieData");
		fin = br.readLine();
		if(fin.length() == 0) fin = "movie.data";
		Map<Integer, Object[]> movieData = parser.getMovieData(fin);

		int[][] genreData =  new int[movieData.size()][19];
		for(int i = 0; i < genreData.length; i++){
			genreData[i] = (int[])movieData.get(i+1)[3];
		}

		System.out.println("entre path for userData");
		fin = br.readLine();
		if(fin.length() == 0) fin = "user.data";
		Map<Integer, Object[]> userData = parser.getUserData(fin);

		int[][] ratingMatrix = new int[userData.size()][movieData.size()];
		int[][] genreComp = new int[19][2];

		System.out.println("entre path for ratingData");
		fin = br.readLine();
		if(fin.length() == 0) fin = "ratings.data";
		parser.fillRatings(ratingMatrix, fin, movieData, userData, genreComp, genreData);

		

		System.out.println("Enter User ID");

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int userid = Integer.parseInt(br.readLine());
		float[][] moviepredict = new float[movieData.size()][2];


		int[][] genreweights = new int[19][4];
		weights.getGenreWeights(genreweights, ratingMatrix, genreData, userid, moviepredict, movieData);

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
			if(((String)movieData.get(recommended[i]+1)[0]).length() != 0)
			System.out.println(movieData.get(recommended[i]+1)[0]);
		}

	}

}