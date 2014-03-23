package classes;

public class FileURL {
	private String url;
	private String filename;
	
	public FileURL(String url){
		this.url = url;
		this.filename = url.replaceAll("http://ia.media-imdb.com/images/M/", "");
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	
}

