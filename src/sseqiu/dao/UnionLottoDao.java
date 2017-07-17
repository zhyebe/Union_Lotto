package sseqiu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import org.springframework.web.dao.EntityDao;
import sseqiu.entity.UnionLotto;
import sseqiu.util.StringUtils;
@Repository
public class UnionLottoDao extends EntityDao<UnionLotto>{
	public static final String ADDUNIONLOTTO="insert into union_lotto(id,redball,blueball)values(?,?,?)";
	public static final String FINDALL="select * from union_lotto";
	public static final String FINDBYID="select * from union_lotto where id=?";
	public static final String UPDATEBYID="update union_lotto set redball=?,blueball=? where id=?";
	public static final String FINDMAXID="select * from union_lotto where id=(select max(id) from union_lotto)";
	@Override
	public UnionLotto toEntity(ResultSet resultSet) {
		UnionLotto lotto=new UnionLotto();
		try {
			String redBall=resultSet.getString("redball");
			lotto.setRedBalls(StringUtils.split(redBall));
			lotto.setBlueBall(resultSet.getString("blueball"));
			lotto.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return lotto;
	}

}
