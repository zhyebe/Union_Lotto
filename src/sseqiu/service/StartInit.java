package sseqiu.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;




import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.idao.IDao;

import sseqiu.dao.UnionLottoDao;
import sseqiu.entity.Result;
import sseqiu.entity.UnionLotto;
import sseqiu.util.StringUtils;

@Service
public class StartInit implements ApplicationListener<ContextRefreshedEvent>{

	@Resource
	IDao<UnionLotto> unionDao;
	private static final int HOUR = 21;
	private static final int MINUTE = 50;
	private static final int SECOND = 0;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			System.out.println("初始化任务...");
			System.out.println("启动定时器...");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, HOUR);//24Hours
			cal.set(Calendar.MINUTE, MINUTE);
			cal.set(Calendar.SECOND, SECOND);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String year = sdf.format(new Date()).substring(0, 2);
					System.out.println("当前系统时间为："+sdf.format(new Date()));
					addDataUnionLotto(year);
				}
			}, cal.getTime(), 24*60*60*1000);
		}
	}
	public void addDataUnionLotto(String year){
		UnionLotto unionLotto = null;
		try {
			unionLotto = StringUtils.getDataUnion();
			System.out.println("[UNIONLOTTO]=====:"+unionLotto);
			Result<UnionLotto> result = addUnionLotto(Integer.parseInt(year+unionLotto.getId()), StringUtils.listToString(unionLotto.getRedBalls(), ','), unionLotto.getBlueBall());
			System.out.println("[RESULT]=====:"+result);
		} catch (IOException e) {
			addDataUnionLotto(year);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
}
