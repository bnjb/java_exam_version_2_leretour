import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Scanner fileIn; //input file connection
		PrintWriter fileOut; //HTML file connection
		String filenameIn; //original file's name ahans.txt
		String filenameOut; //new HTML file's name ahans.html
		int dotIndex; //position of . in filename
		String line = null; // a line from the input file
		Agent agent = new Agent();
		String password = "";
		String passwordVerify = "";

		
		// 1. ask user for a file name (or file path)
		
		System.out.println("Nom du fichier :");
		filenameIn = scanner.nextLine(); // = aahans
		filenameIn = filenameIn + ".txt";// . txt
		String picture = filenameIn.substring(0, filenameIn.length() - 4); //ahans
		picture = picture + ".png"; // ahans.png
		// 2. check if file exists
		
		try {
			
			//3. rename .txt as .html 
			InputStream input = Main.class.getClassLoader().getResourceAsStream("data/" + filenameIn);

			fileIn = new Scanner(input);
			dotIndex = filenameIn.lastIndexOf(".");
			if(dotIndex == -1) {
				filenameOut = filenameIn + ".html";				
			}
			else {
				filenameOut = filenameIn.substring(0,dotIndex) + ".html";
			}

//			File fileHtml = new File();
			InputStream inputHtml = Main.class.getClassLoader().getResourceAsStream("html/" + filenameOut);

			fileOut = new PrintWriter(inputHtml.toString());
			
			// 4. determine if file is empty
			
			try {
				line 	= fileIn.nextLine(); //ahans.txt
				agent.nom = line;
				int count = 0;
				while(fileIn.hasNextLine()) {
					line = fileIn.nextLine();
					if (count == 0) {
						agent.prenom = line;
					}
					if (count == 2) {
						password = line;
					}
					if (count >= 4 && count <= 15) {
						agent.materiel.add(line);
					}
					count ++;		
				}
				
				fileIn.close();
//				fileIn = new Scanner(file);
			}
			catch(NoSuchElementException e) {
				System.out.println("Erreur: "+e.getMessage());
			}
			if(line==null) {
				System.out.println("Le fichier n'existe pas :(");
			}
			else {
				// 5. read each line and insert necessary <tags>
				fileOut.println("<!DOCTYPE html>");
				fileOut.println("<html>");
				fileOut.println("<head>");
				fileOut.println("<link rel=\'stylesheet\' href='C:/Users/Utilisateur/Desktop/IDP/Spring/mspr_java/bin/css/style.css'>");
				fileOut.println("</head>");
				fileOut.println("<body>");
				fileOut.println("<div class = 'topnav'>");
				fileOut.println("<a href='C:/Users/Utilisateur/Desktop/IDP/Spring/mspr_javaindex.html'>Accueil</a>");
				fileOut.println("<div class='image'>"); //IMAGE
				fileOut.println("<img src='C:/Users/Utilisateur/Desktop/IDP/Spring/mspr_java/bin/data/gosecuri.png' width=\"70\" height=\"50\">");
				fileOut.println("</div>");

				fileOut.println("</div>");

				fileOut.println("<div class='titre'>"); //TITRE
				fileOut.println("<h1 class='salut'>");
				fileOut.println(agent.nom + " " + agent.prenom);
				fileOut.println("</h1>");
				fileOut.println("</div>");
				
				fileOut.println("<div class='image'>"); //IMAGE
				fileOut.println("<img src='C:/Users/Utilisateur/Desktop/IDP/Spring/mspr_java/bin/data/" + picture + "'>");
				fileOut.println("</div>");

				
				fileOut.println("<div class='mat'>");
				for (String mat : agent.materiel) { //MATERIEL	
					fileOut.println("<br>");
					fileOut.println("<div class ='item'>");
					fileOut.println("<input type='checkbox' id='horns' name='horns' checked disabled='true'");
					fileOut.println("<label for='horns'>" + mat + "</label>");
					fileOut.println("</div>");

					  
				}
				fileOut.println("</div>");
				fileOut.println("</body>");
				fileOut.println("</html>");
				
			}
			fileIn.close();
			fileOut.close();
			
			File fileToOpen = new File(Main.class.getClassLoader().getResource("html/" + filenameOut).getFile());
						
			try {
				System.out.println("Mot de passe :");
				passwordVerify = scanner.nextLine();
				if (passwordVerify.equals(password)) {
					System.out.println("Fichier HTML en cours de génération :)");
					Desktop.getDesktop().browse(fileToOpen.toURI());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e + "Mauvais login");
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e + "Fichier non trouvé");
		} finally {
			scanner.close();
		}

	}

}
