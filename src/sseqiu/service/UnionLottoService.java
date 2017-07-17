package sseqiu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.idao.IDao;

import sseqiu.dao.UnionLottoDao;
import sseqiu.entity.Result;
import sseqiu.entity.UnionLotto;
import sseqiu.util.StringUtils;

@Service
public class UnionLottoService {
	@Resource
	IDao<UnionLotto> unionDao;
	private static final int PERIODS=11;//前10期的数据
	public Result<UnionLotto> addUnionLotto(Integer id,String redball, String blueball) throws Exception {
		Result<UnionLotto> result=new Result<UnionLotto>();
		if(id!=null){
			List<UnionLotto> lottos=unionDao.findByAny(new Object[]{id}, UnionLottoDao.FINDBYID);
			if(lottos!=null){
				unionDao.modify(new Object[]{redball,blueball,id}, UnionLottoDao.UPDATEBYID);
				result.setData(null);
				result.setMsg("数据更新成功！");
				result.setStatus(Result.FAIL_STATUS);
				return result;
			}
		}
		Object[] objs=new Object[3];
		objs[0]=id;
		objs[1]=redball;
		objs[2]=blueball;
		try {
			unionDao.add(objs, UnionLottoDao.ADDUNIONLOTTO);
			result.setData(null);
			result.setMsg("添加成功！");
			result.setStatus(Result.SUCCESS_STATUS);
		} catch (Exception e) {
			result.setData(null);
			result.setMsg("添加失败！");
			result.setStatus(Result.FAIL_STATUS);
			e.printStackTrace();
		}
		return result;
	}

	public Result<List<UnionLotto>> findUnionLottos() {
		Result<List<UnionLotto>> result=new Result<List<UnionLotto>>();
		List<UnionLotto> lottos=new ArrayList<UnionLotto>();
		try {
			lottos=unionDao.findAll(UnionLottoDao.FINDALL);
			result.setData(lottos);
			result.setMsg("数据成功返回！");
			result.setStatus(Result.SUCCESS_STATUS);
		} catch (Exception e) {
			result.setData(null);
			result.setMsg("没有查询到任何数据！");
			result.setStatus(Result.FAIL_STATUS);
			e.printStackTrace();
		}
		return result;
	}

	public Result<List<String>> randomUnionLotto() {
		Result<List<String>> result=new Result<List<String>>();
		List<UnionLotto> lottos=new ArrayList<UnionLotto>();
		Set<String> newRedBalls=new HashSet<String>();
		List<String> newRedBals=new ArrayList<String>();
		List<String> redBalls=new ArrayList<String>();
		List<String> blueBalls=new ArrayList<String>();
		List<String> list=new ArrayList<String>();
		StringBuilder builder=new StringBuilder();
		try {
			List<UnionLotto> lottosAll = unionDao.findAll(UnionLottoDao.FINDALL);
			List<UnionLotto> maxLotto=unionDao.findByAny(null, UnionLottoDao.FINDMAXID);
			int periods = PERIODS;
			if(lottosAll.size() < PERIODS){
				periods  = lottosAll.size();
			}
			for(int i=0;i<periods;i++){
				lottos.addAll(unionDao.findByAny(new Object[]{(maxLotto.get(0).getId()-i)}, UnionLottoDao.FINDBYID));
			}
			for(UnionLotto unionLotto:lottos){
				redBalls.addAll(unionLotto.getRedBalls());
				blueBalls.add(unionLotto.getBlueBall());
			}
			Random random=new Random();
			for(int i=0;i<redBalls.size();i++){
				newRedBalls.add(redBalls.get(random.nextInt(redBalls.size())));
				if(newRedBalls.size()==StringUtils.LENGTH){
					break;
				}
			}
			
			String newBlueBall=blueBalls.get(random.nextInt(blueBalls.size()));
			
			for(String ball:newRedBalls){
				newRedBals.add(ball);
			}
			Collections.sort(newRedBals, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return Integer.parseInt(o1)-Integer.parseInt(o2);
				}
			});
			
			for(int i=0;i<newRedBals.size();i++){
				builder.append(newRedBals.get(i));
				if(i<newRedBals.size()-1){
					builder.append(",");
				}
			}
			
			list.add(builder.toString());
			list.add(newBlueBall);
			result.setData(list);
			result.setMsg("数据成功返回！");
			result.setStatus(Result.SUCCESS_STATUS);
		} catch (Exception e) {
			result.setData(null);
			result.setMsg("没有查询到任何数据！");
			result.setStatus(Result.FAIL_STATUS);
			e.printStackTrace();
		}
		return result;
	}
	
}
