import java.io.*;
import java.util.*;



class data{

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

		System.out.println("1.) Best movie by genre, Enter genre index (refer documents)");
			
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

		System.out.println("2.) Best movie by year, Enter year");
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

		System.out.println("3.) Best movie by year and genre, Enter year");
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

		// most watched movie
			for(int i =1; i < movieData.size()+1; i++){
				if((Integer)movieData.get(i)[5]> flag){
					flag = (Integer)movieData.get(i)[5];
					output = (String)movieData.get(i)[0];
				}
			}

		System.out.println("4.) Most watched movie is  " + output);
			flag = 0;
			output = "";
			int outputi = 0;
			int outputib = 0;

		//most watched genre	, highest rated genre
			float flagb = 1;
			for(int i =0; i< 19; i++){
				if(genreComp[i][0] > flag){
					flag = genreComp[i][0];
					outputi = i; 
				}
				if((float)genreComp[i][1]/genreComp[i][0] > flagb){
					flagb = (float)genreComp[i][1]/genreComp[i][0];
					outputib = i; 
				}
			}
		System.out.println("5.) Most watched genre is: " + outputi);
		System.out.println("6.) Highest rated genre is: " + outputib);

		// most active user
			flag = 0;
			outputi = 0;
			for(int i =1; i < userData.size()+1; i++){
				if((Integer)userData.get(i)[4]> flag){
					flag = (Integer)userData.get(i)[4];
					outputi = i;
				}
			}
		System.out.println("7.) Most active user is ID number: " + outputi);
	}

}