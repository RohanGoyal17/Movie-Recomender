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

<b>Part 1</b>  Warm Up Problems <br><br>
Execute data.java, the code will prompt for the required inputs.

```
$ javac data.java
$ java data
```

Wrong inputs will crash the code (GIGO ;) ) <br>
Files need to be in the same directory as data.java <br>
Genres are always taken as a input in integer (index) <br>
Exception has been handled for one outlying data entry in movies.data (line :267) <br>
If in any part a query/queries are entered for which there is no movie, then the output is blank <br> 
