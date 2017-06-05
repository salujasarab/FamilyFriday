package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lunch.Generator;
import lunch.Group;

public class TestLunch {

	static String[] arr = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
	
	public List<String> getList() {
		
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		
		return list;
	}
	
	public boolean test() {
		List<String> memberList = getList();
		Generator gen = new Generator();
		List<Group> groups = gen.generate(memberList);
		
		Set<String> memberSet = new HashSet<>(Arrays.asList(arr));
		
		for(Group group: groups) {
			
			List<String> members = group.getMembers();
			
			for(int i=0; i<members.size(); i++) {
				if(!memberSet.contains(members.get(i)))
					return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		
		TestLunch testLunch = new TestLunch();
		if(testLunch.test())
			System.out.println("All members were assigned groups");
		else
			System.out.println("Test Failed. Some members were not assigned groups");		
	}

}
