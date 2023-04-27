Command-line/GUI prompt for a URL of a IMDb image results page and (optional) a folder name to download the images to. Utility will then download each image to user directory Downloads/FOLDERNAME.
---

An IMDb image results page looks like [this](http://www.imdb.com/title/tt1951264/mediaindex?refine=still_frame&ref_=ttmi_ref_sf).   
For each thumbnail in each page of results, the program gets the image's link, like [this](https://m.media-amazon.com/images/M/MV5BMTQ5NjYyOTY3N15BMl5BanBnXkFtZTgwMzE3NzY2MDE@._V1_UY100_CR25,0,100,100_AL_.jpg).   
Then fixes that URL so that it has the correct URL to the full-size image, like [this](https://m.media-amazon.com/images/M/MV5BMTQ5NjYyOTY3N15BMl5BanBnXkFtZTgwMzE3NzY2MDE@._V1_.jpg).   
After all the URLs have been fetched, the program downloads each image.   

**GUI version (Executable JAR):**   
Java source code and Eclipse project: [Github Repo](https://github.com/pizzaboy314/imdb_stills_grabber_gui)   
JAR file: [link](https://github.com/pizzaboy314/imdb_stills_grabber_gui/blob/master/HD_IMDB_Image_Downloader.jar?raw=true) (note: requres Java Runtime: [JRE](https://www.java.com/en/download/))   

**Command line version:**   
Java source code and Eclipse project: [Github Repo](https://github.com/pizzaboy314/imdb_stills_grabber)   
To run...   
**Windows:** Run the .bat file in the "final" directory. You can also make a shortcut to the .bat file and add the icon in the src directory.   
**Linux/Mac:** Open terminal and cd to the "final" directory. Run command -> java GetStillsFromUrls
