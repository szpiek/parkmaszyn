package EntityBeans;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Machine")
public class Machine {
	
	private String os;
	private String architecture;
	private String processor;
	private String IP;
	private String login;
	private String password;
	private Integer memory;
	private Integer bits;
	private Integer ID;
	Collection<Rezerwation> rezerwation;
	
	public String getOs() {
		return os;
	}
	@ManyToMany(fetch=FetchType.LAZY)
	public Collection<Rezerwation> getRezerwation() {
		return rezerwation;
	}
	public void setRezerwation(Collection<Rezerwation> rezerwation) {
		this.rezerwation = rezerwation;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getArchitecture() {
		return architecture;
	}
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getMemory() {
		return memory;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public Integer getBits() {
		return bits;
	}
	public void setBits(Integer bits) {
		this.bits = bits;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	
}
