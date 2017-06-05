package lunch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {
	private static int MAX_GRP = 5;
	private static int MIN_GRP = 3;
	
	private FridaySeed fridaySeed;
	private long seed;
	
	public Generator() {
		fridaySeed = new FridaySeed();
		seed = fridaySeed.getSeed();
	}
	
	private int getRandomNumber() {
		Random rand = new Random(seed);
		return rand.nextInt(MAX_GRP - MIN_GRP) + MIN_GRP;
	}
	
	private void shuffle(List<String> list) {
		
		//long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
	}
	
	public List<Group> generate(List<String> list) {
		
		shuffle(list);

		List<Group> groupList = new ArrayList<Group>();
		
		int i = 0;
		
		while(i < list.size()) {
			int memleft = list.size() - i;
			int grpSize = memleft;
			
			if(memleft > 10) {
				grpSize = getRandomNumber();
				
			} else if(memleft > 5 && memleft <= 10) {
				grpSize = memleft/2;
			} 
			
			Group group = new Group();
			for(int k = 0; k < grpSize; k++) {
				
				group.addMember(list.get(i+k));
			}
			
			groupList.add(group);
			i = i+grpSize;
		}
		return groupList;
	}

}
