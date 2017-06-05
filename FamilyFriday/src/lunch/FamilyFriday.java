package lunch;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FamilyFriday {
	
	public static String fileName = "buddies.txt";
	
	public void parseArgs(Options options, String[] args) {
		
	try{	
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		
		if(cmd.hasOption("add")) {
		    System.out.println("Adding");
		    String memberName = cmd.getOptionValue("add");
		    addMembers(memberName);
		    
		}
		else if(cmd.hasOption("generate")) {
			System.out.println("Generating");
			List<String> memberList = getMembersFromFile(fileName);
			Generator generator = new Generator();
			List<Group> groups = generator.generate(memberList);
			
			printGroups(groups);
		}
		else if(cmd.hasOption("help")) {
			System.out.println("Use -add option to add members and -generate to generate lunch buddies");
		}
		
	}catch(ParseException e) {
		System.out.println("Parse Exception");
	}
		
}
	
	public void printGroups(List<Group> groups) {
		
		int index = 1;
		for(Group group: groups) {
			System.out.println("-------------------------------------");
			System.out.println("Group Number: " + index++);
			
			List<String> members = group.getMembers();
			
			for(int i=0; i<members.size(); i++) {
				System.out.println(members.get(i));
			}
		}
	}

	public boolean checkFileExist(String filePath) {
	
		Path path = Paths.get(filePath);
		return Files.isRegularFile(path);
	}
	
	public void addMembers(String memberName) {
		
		File f = new File(fileName); 
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;

		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			Files.write(Paths.get(fileName), memberName.getBytes(), StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null && fileWriter != null) {

				try {
					bufferedWriter.close();
					fileWriter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
	
	public List<String> getMembersFromFile(String fileName) {
		
		BufferedReader br = null;
		List<String> memberList = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(fileName)) ;
		    String line;
		    while ((line = br.readLine()) != null) {
		       
		    	memberList.add(line);
		    }
		    
		    br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return memberList;
	}
	
	public static void main(String[] args)  {
		
		// create Options object
		Options options = new Options();

		options.addOption("add", true, "add member");
		options.addOption("help", false, "Help");
		options.addOption("generate", false, "Lunch Buddies list");
		
		FamilyFriday ff = new FamilyFriday();
		ff.parseArgs(options, args);
		

	}

}
