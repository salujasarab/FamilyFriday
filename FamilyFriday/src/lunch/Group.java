package lunch;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private List<String> members;
	
	public Group() {
		members = new ArrayList<String>();
	}
	
	public List<String> getMembers() {
		return members;
	}
	
	public void addMember(String name) {
		members.add(name);
		
	}

}
