package CSI.Ben;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class GeoLocationAuto {
//	total = 2500 *60
//	https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDc2oSzYl-UJ6brhxL3-BoNPvl3nbjNogk&address=%E5%8F%B0%E5%8C%97%E5%B7%BF%E5%85%89%E5%BE%A9%E5%8D%97%E8%B7%AF100%E8%99%9F&sensor=false
	private static final Logger logger = LogManager.getLogger(GeoLocationAuto.class);
	static int useAmount = 2400;
//	static int useAmount = 600;
	static List<String> allApiKey = Arrays.asList(
		"AIzaSyDc2oSzYl-UJ6brhxL3-BoNPvl3nbjNogk","AIzaSyBW8il92lnY-OExDq0i96yTyX47u2AbJwo","AIzaSyAgKIoOcrMYYXNWkfqzPU-Q_TGSbRUYjlI",
		"AIzaSyDdcK3WRL1YWAVnxjvY4zIBOo55nQlQIB8","AIzaSyBtULNchG1u7yW-mjqGuQpyk8caN9hUZ5w","AIzaSyDgMEDXeEMhKUTvSHk16LPjaTPzHbUqGZc",
		"AIzaSyBN7PT_EZthT269xBDu0hDZRUeF0ejwN6Q","AIzaSyDRLosPqV8tDB-MF9junhYZNY_sqelq6Ac","AIzaSyD9mdtmbwecbum6UxzN95WaqE5X5w2mJmo",
		"AIzaSyDxMwdHSAOu1EX7g956_fgfu4BVLOqWrOY","AIzaSyBC4hUHQU5Vl4bIIligqvui0bJO57N5dTM","AIzaSyBwmLSJgguatMRayDV9mxOOmNqe9iNVCoo",
		"AIzaSyDEmNANk1de-xssF5LE0ydJZA7iX33VZaU","AIzaSyBzu50QSksJLP3T9uDbvtiSQv07_HAL1Vw","AIzaSyAUsixaZHU963SWVapfDz_gSd6NFiv83zE",
		"AIzaSyCXReRl759MVd8Qu8535_dep6s1Pa1wgJw","AIzaSyB9ta1XyxMRNmgWFWjtHM_px6uaqVsphgI","AIzaSyD2HVodPgl8B6nMO7dEkpOfBgmxPZMWmy4",
		"AIzaSyCCba840R44yvznDTMouSyvdVpz5Z3ZI90","AIzaSyB-6fPSSjQab8LNkcAG8cM-0UyJJRr71sM","AIzaSyDOLgJD1B6Kggyhd_pFd-uqSicwB7-QrOE",
		"AIzaSyAKAYnrb6Ru6lFpEvxse4wsFYTlXbgHggA","AIzaSyAmtQKJ684Qrj8jhHp_KStYw3TYbZfW2SE","AIzaSyBzIyZzw_cNkWDV-ZNVh6HrOYARz2d7IEk",
		"AIzaSyDu9qLpw-J9RPSQzs14CBZkLwWCCoBc69A","AIzaSyApL0FSbOebC8gQ1xH8YExPG96SQ1x3AiE","AIzaSyAA8HDFb4P6LbL_iWkJGcZKnNFjXuQX8Xs",
		"AIzaSyBgfC4pegNOjzJsyyDTqy31BQiYAcJlrKM","AIzaSyBwyQy7E1U4QGC0yQTDcpQm41Cr0sXVZ2U","AIzaSyClDDSCECP18Yg4dZzGOBkafaXxQ3Zp4FM",
		"AIzaSyAc0jMzr6s7ZH9jVbtqQioupPVqE6Jq5WI","AIzaSyAXcR8d_yvVYB0XkeEYwerroCzFyJZBKvE","AIzaSyAIei6WPOXHEFtPmYAG9E7JIKWZraFD3Ik",
		"AIzaSyCebqEmmXxl-7I5wAlQK6Zw2hEi7hB5ScU","AIzaSyA8jWb-0eAtIJeY_Fea8pfnPRn-uehU0wk","AIzaSyBALDUr7UsyXAqjTOo5BRz1mo6tNPCteog",
		"AIzaSyAKZ9TvLxEVfQMyMtj92VCr29ANUepx000","AIzaSyCW_2y57KFirUOraQcxYsfGDONXzaizP8E","AIzaSyD5x_d_lqykOol8uV6BXe-dYl9Oa19B86Q",
		"AIzaSyAOYDN9qMW9wdowvPtuAJxXb8k5nAo6pu4","AIzaSyC4XnJtVzo70MKP_ccYfZBTI8SiS2jqt5s","AIzaSyDRHdaIAlcDHWIo7dbbTZblkVQlW-TVqEc",
		"AIzaSyCerqMwkFQBeWgMR_08sBGsPcY_WQqmgxk","AIzaSyCpYPWqgh14hz-zWxaa3SK1bUi-osK0bkk","AIzaSyA6x9kv9wBtV9uufI_4YNXZpNgwWn9bNj8",
		"AIzaSyDCWGjRLxMTetFGtreoCWvJuOwGcObS_-8","AIzaSyCkf_RLlFG-WXTurGgeEa-N5SH1mo0Ki3I","AIzaSyDwK4gGrIRr_hGqztB5gdFdPaX0NnFfOSE",
		"AIzaSyA_2mmh0xrcnEeHpD63NLBkl8jvCy046bQ","AIzaSyCoDLprz5zglrAU5dOpqf5ZD49WiGJSG_A","AIzaSyDMGsbIA0bvn1D81Pimdk7o72zq_XiSBgs",
		"AIzaSyC0o-S5gQDA4HSK19SFFSSwp68j5Of3gAc","AIzaSyAepx9kB4fMPFdbRvuWT0mIQ3p_IlbP8K8","AIzaSyB9v4rkoegJB3-vOq8yxNr54JqA92XP2sg",
		"AIzaSyD04YnoAX3c2rZAn45oPH7QMPjXiZGleHA","AIzaSyDRh9XxWeY5HiXhhrm_2QtHHiZkpIujoXs","AIzaSyAQkZ_ZEdN9zJzTHP9RtI1sD_D0nnwWx4A",
		"AIzaSyCSvCF7GBzTA_iG3k_1iYGe6BDTvrKGNfw","AIzaSyCLnD3NAa4e5vyEKSnievAZHBS-Qpg6GVg","AIzaSyDrVtWYL_4JBVC4NRz2lVin9HAdpMw0K4A"
	);
	public static void main(String[] args){
//		Thread t2 = new GeoCodingSearch(Arrays.asList("789","012"),"AIzaSyAepx9kB4fMPFdbRvuWT0mIQ3p_IlbP8K8");
//		t2.start();
//		int ii=0;
//		LogManager.getLogger("anal").debug("2334");
//		logger.debug("桃園市平鎮區2鄰山子頂99號".replaceAll("\\d+",""));
//		System.out.println("桃園市平鎮區2鄰山子頂99號".replaceAll("\\d+鄰","")+"Over");
//		
//		
//		if(ii==0){return ;}
//		String sysString = GeoLocationAuto.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
//		String projectPath = sysString.split(sysString.split("/")[sysString.split("/").length - 1])[0];
		String sysString = GeoLocationAuto.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
		String projectPath = sysString.split(sysString.split("/")[sysString.split("/").length - 1])[0];
		String file_name;
		if(args.length==0){
			file_name = projectPath+ "/combine.log";
		}else{
			file_name = projectPath+"/"+args[0];
		}
		if(!(new File(file_name).exists())){
			System.out.println("file not found: "+file_name);
			return ;
		}
//		String file_name = projectPath+"/company_register_addr_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".csv";
//		String file_name = "D:/workspace/addrSearch/test.csv";
//		String dest_name = "D:/workspace/addrSearch/20171129_output.csv";
//		List<String> allAddress = new ArrayList<String>();
//		new File(dest_name).delete();
		
		String thisLine = null;
		
		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file_name), "UTF-8"));
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest_name, true), StandardCharsets.UTF_8));
			
//			int i=0,j=0;
//			List<String> dataList = new ArrayList<String>();
			
//			while ((thisLine = br.readLine()) != null) {
//				allAddress.add(thisLine);
//				bw.write(thisLine+"\n");
//				if(i<allApiKey.size()){
//					if(j < useAmount){
//						
//					}
//				}
			int jjk=0;
//			System.out.println(jjk);
				for(int i=0;i < allApiKey.size();i++){
					List<String> dataList =new ArrayList<String>();
					for(int j=0; j < useAmount ; j++){
						jjk++;
						thisLine = br.readLine();
//						System.out.println(j+" ## "+useAmount+" ## "+thisLine);
						if(thisLine==null){
							break;
						}
//						dataList = new ArrayList<String>();
						dataList.add(thisLine);
					}
//					System.out.println(new Gson().toJson(dataList));
					if(i == allApiKey.size() || thisLine==null){
						GeoCodingSearch geoSearchService = new GeoCodingSearch(dataList,allApiKey.get(i));
						geoSearchService.changeLogfile = true;
						geoSearchService.start();
					}else{
						new GeoCodingSearch(dataList,allApiKey.get(i)).start();
					}
					if(thisLine==null){
						break;
					}
				}
				System.out.println("data Amount: "+--jjk);
//				String[] strList = thisLine.split(",");
//				searchLatLng(thisLine, addressDeal( strList[strList.length-1] ) );
//			}
			br.close();
			new File(file_name).renameTo(new File(file_name+".over."+new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date())));
//			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Convert Over");
		
		return ;
	}
	
}
