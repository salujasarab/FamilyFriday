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
import java.io.IOException;
import java.io.Writer;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FamilyFriday {

	public static String fileName = "buddies.txt";

	/**
	 * 
	 * @param options
	 * @param args - Command line arguments
	 * 
	 * This method parses the command line args and options and takes specific action.
	 */

	public void parseArgs(Options options, String[] args) {

		try{	
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse( options, args);

			// If the add option was specified on the command line
			if(cmd.hasOption("add")) {
				System.out.println("Adding Member");
				String memberName = cmd.getOptionValue("add");
				addMembers(memberName);

			}

			// If the generate option was specified on the command line
			else if(cmd.hasOption("generate")) {
				System.out.println("Generating Lunch Buddies list");
				List<String> memberList = getMembersFromFile(fileName);
				Generator generator = new Generator();
				List<Group> groups = generator.generate(memberList);

				printGroups(groups);
			}
			else if(cmd.hasOption("help")) {
				System.out.println("Use '-add' option to add members and '-generate' to generate lunch buddies");
			}
			else
				System.out.println("Please use 'help' to get list of options to specify");

		}catch(ParseException e) {
			System.out.println("Parse Exception");
		}
	}

	/**
	 * 
	 * @param groups - Group list
	 * 
	 * This methods receives a list of lunch buddy groups and prints it.
	 */
	public void printGroups(List<Group> groups) {

		int index = 1;
		for(Group group: groups) {
			System.out.println();
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

	/**
	 * 
	 * @param memberName
	 * 
	 * This method adds the specified member to the lunch group list
	 */
	public void addMembers(String memberName) {

		File f = new File(fileName); 
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;

		// Check if the file exists, if not create it
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Appending member to the file
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

	/**
	 * 
	 * @param fileName
	 * @return list of members read from the specified file
	 * 
	 * This methods reads member names from the specified file and return it as a list of string
	 */
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

		// Adding options to the command line cli
		options.addOption("add", true, "add member");
		options.addOption("help", false, "Help");
		options.addOption("generate", false, "Lunch Buddies list");

		FamilyFriday ff = new FamilyFriday();
		ff.parseArgs(options, args);
	}
}
