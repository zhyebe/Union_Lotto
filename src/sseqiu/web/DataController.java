package sseqiu.web;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sseqiu.entity.UnionLotto;
import sseqiu.util.StringUtils;

@Controller
@RequestMapping("/data")
public class DataController {
	
	@RequestMapping("/getWebData.do")
	@ResponseBody
	public UnionLotto getWebHtml(){
		return getDataUnion();
	}
	
	public UnionLotto getDataUnion(){
		try {
			return StringUtils.getDataUnion();
		} catch (IOException e) {
			e.printStackTrace();
			return getDataUnion();
		}
		
	}
}
