package br.ufsc.tcc.gamifyEngine.compositeKeys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RuleAttributeKey implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	private int user;
	
	@Column(name = "attribute_id", nullable = false)
	private int attribute;
	
	public RuleAttributeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public RuleAttributeKey(int user, int attribute) {
		this.user = user;
		this.attribute = attribute;
	}
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}	
	
//	@Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((user == null) ? 0 : user.hashCode());
//        result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
//        return result;
//    }
//	
//	@Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        RuleAttributeKey other = (RuleAttributeKey) obj;
//        if (user == null) {
//            if (other.user != null)
//                return false;
//        } else if (!user.equals(other.user))
//            return false;
//        if (attribute == null) {
//            if (other.attribute != null)
//                return false;
//        } else if (!attribute.equals(other.attribute))
//            return false;
//        return true;
//    }
}
