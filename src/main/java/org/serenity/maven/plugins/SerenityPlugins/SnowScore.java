package org.serenity.maven.plugins.SerenityPlugins;

import java.util.ArrayList;

public class SnowScore {
	public ArrayList<String> contributor;
	public int count;
	
	public SnowScore(){
		contributor = new ArrayList<String>();
		count = 0;
	}
	
	public void addHit(String hitter){
		if(!contributor.contains(hitter)){
			contributor.add(hitter);
		}
		count++;
	}
}
