package classes;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class GetStillsFromUrls {

	public static void main(String[] args) {
		List<FileURL> downloadURLS = new ArrayList<FileURL>();
		boolean loop = true;
		String downloadLoc = System.getProperty("user.home") + File.separator + "Downloads";
		String url = "";
		String folder = "";
		String input = "";
		
		System.out.println("High resolution images will be saved in: " + downloadLoc);
		while(loop){
			downloadLoc = System.getProperty("user.home") + File.separator + "Downloads";
			
			url = (String) JOptionPane.showInputDialog(null, "Enter the URL from an IMDB image result page:",
					"Provide URL", JOptionPane.PLAIN_MESSAGE, null, null, null);
			while(url == null || url.equals("")){
				url = (String) JOptionPane.showInputDialog(null, "Invalid URL. Enter a valid URL from an IMDB image result page:",
						"Provide URL", JOptionPane.PLAIN_MESSAGE, null, null, null);
			}
			
			folder = (String) JOptionPane.showInputDialog(null, "Enter a new folder name to download to (default is 'IMDB'):",
					"Provide Folder Name", JOptionPane.PLAIN_MESSAGE, null, null, null);
			
			if(folder.equals("") || folder == null){
				downloadLoc += File.separator + "IMDB";
			} else {
				downloadLoc += File.separator + folder;
			}
			File path = new File(downloadLoc);
			if(!path.exists())
				path.mkdirs();
			
			downloadURLS = parseHTML(url);
			
			downloadStills(downloadURLS, downloadLoc);
			System.out.println("Finished.");
			
			input = (String) JOptionPane.showInputDialog(null, "Enter another URL (Y/N)?",
					"New Download?", JOptionPane.PLAIN_MESSAGE, null, null, null);
			if(input == null || input.equals("") || input.toUpperCase().equals("N")){
				loop = false;
			}
		}
	}
	
	public static List<String> readFile(String filename) {
		List<String> urls = new ArrayList<String>();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));
			
	        String line = br.readLine();

	        while (line != null) {
	            urls.add(line);
	            line = br.readLine();
	        }
	        br.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	    return urls;
	}
	
	public static List<FileURL> parseHTML(String input){
		List<FileURL> downloadURLS = new ArrayList<FileURL>();
		boolean grabJPG = false;
		String url = input;
		
		while(url != null){
			String nextURL = null;
			try {
				URL source = null;
				boolean valid = true;
				try {
					source = new URL(url);
				} catch (MalformedURLException e){
					valid = false;
				}
				while(valid == false){
					valid = true;
					url = (String) JOptionPane.showInputDialog(null, "Malformed URL format. Are you sure you copied the entire URL?\n" + "Try again:",
							"Provide URL", JOptionPane.PLAIN_MESSAGE, null, null, null);
					try {
						source = new URL(url);
					} catch (MalformedURLException e){
						valid = false;
					}
				}
				
				BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));
				
				String inputLine = in.readLine();
				while (inputLine != null){
					if(inputLine.contains("class=\"prevnext\"") && inputLine.contains("Next")){
						String tmp = inputLine.replaceAll("<a href=\"", "");
						tmp = tmp.replaceAll("\" class=\"prevnext\" >Next&nbsp;&raquo;</a>", "");
						nextURL = "http://www.imdb.com" + tmp.trim();
					}
					if(inputLine.contains("media_index_thumbnail_grid")){
						grabJPG = true;
					}
					if(grabJPG && inputLine.contains("src=")){
						String tmp = inputLine.replaceAll("(src=|\")", "");
						tmp = tmp.replaceAll("V1_.+", "V1_.jpg");	//change small images to big
						FileURL furl = new FileURL(tmp);
						downloadURLS.add(furl);
					}
					if(grabJPG && inputLine.contains("</div>")){
						grabJPG = false;
						inputLine = null;
					} else {
						inputLine = in.readLine();
					}
				}
				
				in.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = nextURL;
		}
		
		
		return downloadURLS;
	}
	public static void downloadStills(List<FileURL> downloadURLS, String downloadLoc){
		JFrame frame = new JFrame("Downloading Images...");
		frame.setPreferredSize(new Dimension(210,80));
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(200,40));
		label.setHorizontalAlignment(JLabel.CENTER);
		frame.add(label);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		for(FileURL downloadURL : downloadURLS){
			label.setText("Saving image " + (downloadURLS.indexOf(downloadURL)+1) + " out of " + downloadURLS.size());
			try {
				URL url = new URL(downloadURL.getUrl());
				InputStream in = url.openStream();
				File dest = new File(downloadLoc + File.separator + downloadURL.getFilename());
				if(!dest.exists()){
					Files.copy(in, Paths.get(downloadLoc + File.separator + downloadURL.getFilename()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Finished!", "Finished", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
	}
	

}
