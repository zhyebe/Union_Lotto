package sseqiu.entity;

import java.io.Serializable;

public class Result<Type> implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS_STATUS = 1;
	public static final int FAIL_STATUS = 0;
	private int status;
	private Type data;
	private String msg;
	
	public Result(){}
	
	public Result(int status, Type data, String msg) {
		super();
		this.status = status;
		this.data = data;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Type getData() {
		return data;
	}

	public void setData(Type data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + status;
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
		Result other = (Result) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", data=" + data + ", msg=" + msg
				+ "]";
	}
	
	
	
}
