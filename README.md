# Code Challenge
A small cinema, which only plays movies from the Fast & Furious franchise, is looking to develop a mobile/web app for their users.

## Architecture
Spring boot app designed with endpoints for getting and maintaining a schedule for movie times. 
Only the current 8 Fast and Furious movies are available.
Dates on the format "yyyy-MM-dd" are used for looking up times for a specific day.
Movies parameters are 1 through 8.
Endpoints to retrieve movie info from IMDB are also available.
Data is stored on demand to local file system in resources for a lightweight structure. No SQL DB needed.

## Local Run

    ./gradlew bootRun

App will be running at `http://localhost:9000/` 
Example on where to get showtimes:

    localhost:9000/getshowtimes?date=2020-12-22&movie=1
    
## TODO in more updates
Switch endpoints to using POST instead of GET where data is being saved
Finish inner parts of tests from created stubs.
Create a proper persistence layer with something from [persistence-with-spring](https://www.baeldung.com/persistence-with-spring-series)
Allow for multiple input types on endpoints.
Set default input for date to the current day
Allow for other movie series to be added
Create a frontend view to easily interact with endpoints.
Crunch review data together and output in a nice way
Add inventory for movie ticket availability
Add endpoints for simulating a purchase to reduce inventory
