package sseqiu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sseqiu.entity.UnionLotto;

public class StringUtils {
	/*要抓取数据的网址*/
	private static final String WEB_URL="http://zx.500.com/ssq/";
	public static final int LENGTH=6;
	public static List<String> split(String str){
		List<String> redBalls=new ArrayList<String>();
		String[] newStrs=new String[LENGTH];
		newStrs=str.split(",");
		for(String newStr:newStrs){
			redBalls.add(newStr);
		}
		return redBalls;
	}
	
	public static String listToString(List<String> list, char separator) {  
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) { 
			sb.append(list.get(i));   
			if (i < list.size() - 1) { 
				sb.append(separator); 
				}   
			}    
		return sb.toString();
		
	}
	
	public static UnionLotto getDataUnion() throws IOException{
		Document doc=Jsoup.connect(WEB_URL).timeout(10000).get();
		Element element=doc.getElementById("kj_opencode");
		Element elementId=doc.getElementById("kj_expect");
		Elements elementIds=elementId.children();
		Elements redballElements=element.getElementsByClass("redball");
		Elements blueballElements=element.getElementsByClass("blueball");
		List<String> redBalls=new ArrayList<String>();
		for(Element e:redballElements){
			redBalls.add(e.text());
		}
		String blueBall=blueballElements.get(0).text();
		int id=Integer.parseInt(elementIds.get(0).val());
		UnionLotto lotto=new UnionLotto();
		lotto.setId(id);
		lotto.setRedBalls(redBalls);
		lotto.setBlueBall(blueBall);
		System.out.println(lotto);
		return lotto;
	}
}

