package sseqiu.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sseqiu.entity.Result;
import sseqiu.entity.UnionLotto;
import sseqiu.service.UnionLottoService;

@Controller
@RequestMapping("/union")
public class UnionLottoController {
	@Resource
	UnionLottoService service;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Result<UnionLotto> addUnionLotto(Integer id,String redball,String blueball) throws Exception{
		return service.addUnionLotto(id,redball,blueball);
	}
	
	@RequestMapping("/findAll.do")
	@ResponseBody
	public Result<List<UnionLotto>> findUnionLottos(){
		return service.findUnionLottos();
	}
	
	@RequestMapping("/randomUnionLotto.do")
	@ResponseBody
	public Result<List<String>> randomUnionLotto(){
		return service.randomUnionLotto();
	}
}
