package weathersource.webxmlcomcn;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import cn.kli.weather.engine.City;

public class DataProxy {
	//prefs
	private final static String SOURCE = "webxml";
	private final static String KEY_HAS_INITED = "has_inited";
	
	private static DataProxy sInstance;
	private CityDbHelper mDbHelper;
	private Context mContext;
	
	private DataProxy(Context context){
		mContext = context;
		mDbHelper = new CityDbHelper(mContext);
		
		//create database
		mDbHelper.getReadableDatabase();

		//copy database if exists
		if(!getDataPrepared()){
			boolean res = Utils.copyDatabaseFile(mContext, true, CityDbHelper.DB_NAME);
			setDataPrepared(res);
		}
	}
	
	public static DataProxy getInstance(Context context){
		if(sInstance == null){
			sInstance = new DataProxy(context);
		}
		return sInstance;
	}
	
	
	public void setDataPrepared(boolean prepared){
		SharedPreferences prefs = mContext.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(KEY_HAS_INITED, prepared);
		editor.commit();
		if(prepared){
			mDbHelper.close();
			mDbHelper = null;
			mDbHelper = new CityDbHelper(mContext);
		}
	}
	
	public boolean getDataPrepared(){
		SharedPreferences prefs = mContext.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
		boolean res = prefs.getBoolean(KEY_HAS_INITED, false);
		return res;
	}

	public void addCity(MyCity city){
		mDbHelper.addCity(city);
	}
	
	public MyCity getCityByIndex(String index){
		return mDbHelper.getCityByIndex(index);
	}

	public List<City> getCityList(City city){
		return mDbHelper.getCityList(city);
	}
	
}
