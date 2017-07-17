package sseqiu.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class UnionLotto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private List<String> redBalls;
	private String blueBall;
	
	public UnionLotto(){}

	public UnionLotto(int id, List<String> redBalls, String blueBall) {
		super();
		this.id = id;
		this.redBalls = redBalls;
		this.blueBall = blueBall;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getRedBalls() {
		return redBalls;
	}

	public void setRedBalls(List<String> redBalls) {
		this.redBalls = redBalls;
	}

	public String getBlueBall() {
		return blueBall;
	}

	public void setBlueBall(String blueBall) {
		this.blueBall = blueBall;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blueBall == null) ? 0 : blueBall.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((redBalls == null) ? 0 : redBalls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnionLotto other = (UnionLotto) obj;
		if (blueBall == null) {
			if (other.blueBall != null)
				return false;
		} else if (!blueBall.equals(other.blueBall))
			return false;
		if (id != other.id)
			return false;
		if (redBalls == null) {
			if (other.redBalls != null)
				return false;
		} else if (!redBalls.equals(other.redBalls))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnionLotto [id=" + id + ", redBalls=" + redBalls
				+ ", blueBall=" + blueBall + "]";
	}

	
	
	
	
}
