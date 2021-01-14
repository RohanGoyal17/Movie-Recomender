import java.io.*;
import java.util.*;

class weights{
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
}