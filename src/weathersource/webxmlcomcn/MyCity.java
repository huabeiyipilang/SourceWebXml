package weathersource.webxmlcomcn;

import cn.kli.weather.engine.City;

public class MyCity extends City {
	//be used to query weather
	public String parent;

	MyCity(){
		
	}
	
	MyCity(City city){
		name = city.name;
		id = city.id;
		weathers = city.weathers;
	}
	
}
