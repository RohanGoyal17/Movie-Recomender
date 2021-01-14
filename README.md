# Movie-Recomender

<b>Data</b> 
1) ratings.data -- The full u data set, 100000 ratings by 943 users on 1682 items.
       Each user has rated at least 20 movies. Users and items are
       numbered consecutively from 1. The data is randomly
       ordered. This is a tab separated list of 
	     user id | item id | rating | timestamp. 
       The time stamps are unix seconds since 1/1/1970 UTC  
2) movies.data Information about movies; this is a tab separated
       list of
       movie id | movie title | release date | video release date |
       IMDb URL | unknown | Action | Adventure | Animation |
       Children's | Comedy | Crime | Documentary | Drama | Fantasy |
       Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi |
       Thriller | War | Western |
       The last 19 fields are the genres, a 1 indicates the movie
       is of that genre, a 0 indicates it is not; movies can be in
       several genres at once.
       The movie ids are the ones used in the ratings.data data set.
3) genre.data A list of the movie genres
4) user.data Demographic information about the users; this is a tab
       separated list of
       user id | age | gender | occupation | zip code
       The user ids are the ones used in the u.data data set.
       
<b>Warmup Problems </b>
1) Top Movie By Genre 
2) Top Movie By Year
3) Top Movie By Year & Genre
4) Most watched Movie
5) Most watched Genre
6) Highest rated Genre
7) Most Active User

 <b>Problem Statement</b> <br>
 Given a user id, recommend 5 movies for that user. You should recommend a movie which the user has the highest probability of liking. You must not recommend a movie the user has already seen.

<br>

<b>Part 1</b>  Warm Up Problems <br><br>
Execute data.java, the code will prompt for the required inputs.

```
$ javac parser.java
$ javac data.java
$ java data
```


Genres are always taken as a input in integer (index) <br>
Exception has been handled for one outlying data <br>
If in any part a query/queries are entered for which there is no movie, then the output is blank <br>
if left blank, data fields pickup default value e.g. "movie.data". <br>


<b>Part 2</b> recommendation <br><br>

using correlation between watched movie reviews and new movie to recommend <br>
takes user id as input, prints out the names of 5 recommended movies
```
$ javac parser.java
$ javac weights.java
$ javac recommend.java
$ java recommend
```
