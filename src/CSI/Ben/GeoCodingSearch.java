package CSI.Ben;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
public class GeoCodingSearch extends Thread {
	private static final Logger logger = LogManager.getLogger(GeoLocationAuto.class);
	List<String> dataList ; 
	String apiKey ; 
	public boolean changeLogfile = false;
    public GeoCodingSearch(List<String> dataList,String apiKey) {
    	super();
    	this.dataList = dataList;
    	this.apiKey = apiKey;
    }
    public GeoCodingSearch(){
    	super();
    }
    
    public void run() { // overwrite Thread's run()
    	if(dataList ==null){
    		return ;
    	}
        for (int i=0; i < dataList.size(); i++) {
            try {
//            	System.out.println(apiKey+"sleep: "+dataList.get(i));
            	String[] tmp = dataList.get(i).split(",");
            	
            	try {
//            		System.out.println(tmp[tmp.length-1]+" ## "+apiKey);
					searchLatLng(dataList.get(i),addressDeal(tmp[tmp.length-1]),apiKey);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                sleep(3000);
            } catch (InterruptedException e) {
            	logger.debug("sleepError",e);
            }
        }
//        System.out.println("hello:"+changeLogfile);
        if(changeLogfile){
//        	System.out.println("hello?");
//        	System.out.println(new File("/data/log/sbi/appSearch/addrSearch.log").exists());
//        	System.out.println(new File("/data/log/sbi/appSearch/addrSearch.log").renameTo(new File("hello.txt")));
//        	new File("/data/log/sbi/appSearch/addrSearch.log").renameTo(new File("/data/log/sbi/appSearch/addrSearch.log."+new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date())));
//        	new File("/data/log/sbi/appSearch/addrSearch_notFound.log").renameTo(new File("/data/log/sbi/appSearch/addrSearch_notFound.log."+new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date())));
        }
    }
    
    
    public static String searchLatLng(String oriString, String address,String apiKey) throws IOException{
    	String url = "https://maps.googleapis.com/maps/api/geocode/json?key="+apiKey+"&address="+URLEncoder.encode(address, "UTF-8");
    	
    	String jsonObj = null;
    	String latlngStr ;
    	try{
    		jsonObj = getConnection(url);
    		JSONObject jsonArray = new JSONObject(jsonObj);
//        	System.out.print(jsonArray.getString("status"));
        	
        	if("OK".equals(jsonArray.getString("status"))){
//        		System.out.println("inOk?");
        		latlngStr = jsonArray.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat")
        	    		+","
        	    		+jsonArray.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        		
        		writeToFile(oriString+","+latlngStr);
        	}else if("OVER_QUERY_LIMIT".equals(jsonArray.getString("status"))){
        		latlngStr = "0,0";
        		
        		LogManager.getLogger("anal").debug(address+",0,0 : "+jsonArray.getString("status")+" key: "+apiKey);
        	}else if("ZERO_RESULTS".equals(jsonArray.getString("status"))){
        		writeToFile(oriString+",0,0");
        	}else{
        		latlngStr = "0,0";
        		LogManager.getLogger("anal").debug(address+",0,0 : "+jsonArray.getString("status")+" key: "+apiKey);
        	}
    	}catch(Exception e){
    		latlngStr = "0,0";
    		LogManager.getLogger("anal").debug(address+",0,0 : InternetError!! key: "+apiKey);
    	}
    	
    	
//    	System.out.println(oriString+","+latlngStr);
//    	writeToFile(oriString+","+latlngStr);
//    	"+api_key+"&address="+encodeURI(address_rest_20170622[addr_i].replace("?","").replace("?","").replace("?","").replace("○","零").replace("○","零").replace("○","零").replace("○","零").replace("○","零").replace("○","零").replace(/..里/,'').split("號")[0]+"號")+"&sensor=true";
//		writeToFile(oriString + "," + searchLatLng (thisLine, addressDeal( strList[strList.length-1] ) ));
		return null;
	}
	
	public static String getConnection(String url) throws IOException{
//		URL obj = ;
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return (response.toString());
	}
	
	public static void writeToFile(String output){
		logger.debug(output);
	}
	
	public static String addressDeal(String address_ori){
		String address_dest=address_ori;
		address_dest = address_dest
				.replaceAll("一", "1").replaceAll("二", "2").replaceAll("三", "3").replaceAll("四", "4").replaceAll("五", "5")
				.replaceAll("六", "6").replaceAll("七", "7").replaceAll("八", "8").replaceAll("九", "9")
				.replaceAll("十","1").replaceAll("廿","2")
				.replaceAll("\\?", "").replaceAll("○","零").replaceAll("零","0").replaceAll("..里","").replaceAll("\\d+鄰","");
		address_dest = address_dest.split("號")[0]+"號";
		return address_dest;
	}
}