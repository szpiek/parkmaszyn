package EntityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="OS")
@Table(name="OS")
public class OS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5510157132036561769L;
	Integer ID;
	String name;
	String version;
	String patch;
	Integer bits;
	
	
	public Integer getBits() {
		return bits;
	}

	public void setBits(Integer bits) {
		this.bits = bits;
	}

	public OS()
	{
	}
	
	public OS(String name,String version,String patch, Integer bits)
	{
		this.bits=bits;
		this.name=name;
		this.patch=patch;
		this.version=version;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPatch() {
		return patch;
	}
	public void setPatch(String patch) {
		this.patch = patch;
	}
	
	
}
