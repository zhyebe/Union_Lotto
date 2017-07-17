package sseqiu.aspect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component//扫描到Spring容器
@Aspect//指定为方面组件
public class LoggerBean {
	@Pointcut("within(com.onenote.service..*)")
	public void p1(){}
	
	@AfterThrowing(pointcut="p1()",throwing="e")
	public void execute(Exception e){
		//记录Service异常信息功能(文件记录)
		try {
			//获得项目根目录，并在根目录创建文件夹用于存放日志
			File file=new File(this.getClass().getClassLoader().getResource("").getPath()+"../../logs");
			file.mkdirs();
			File file1=new File(file.getPath()+"/error.log");
			file1.createNewFile();
			FileWriter fw = new FileWriter(file1,true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("发生了异常信息："+e);
			e.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
