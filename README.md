# VoD App

This is a BackEnd part of the VoD app, created for the purpose of:
* Finding movies and tv shows from the local database which is updated daily from an TMDB website 
* Finding and displaying posters for media via OMDb website
* Searching movies and tv shows by keyword or ratings
* Displaying movie and tv show details about their popularity, average rating, etc.
* Creating your own account, updating or deleting it
* Authenticating account via email address and password
* Displaying watchlist, rents or buys for current account
* Adding, removing movies, tv shows to watchlist
* Posibility to rent or buy movies and/or tv shows

To run this application:
1. Have both FrontEnd and BackEnd downloaded locally.
  - FrontEnd: https://github.com/C0nn0rJ0hn/vod_frontend
  - BackEnd: https://github.com/C0nn0rJ0hn/vod-backend
2. Have a local MySQL database server running, with the following schema:
  - Database name: vod_db
  - username: vod_user
  - password: vod_password
3. First, run BackEnd by starting VodBackendApplication.
  - Side note: if You want to fill database with data, then go to DataMigrationScheduler and change @Scheduled annotation. Currently database is updated daily on midnight.
4. Second, run FrontEnd by starting VodApplication.java (src).
5. You can then access the frontend website on http://localhost:8081/.
 
## UNIT TESTS:
Current line coverage of the project is 81%, with class coverage reaching 87%. 