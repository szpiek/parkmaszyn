package EntityBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.swing.text.Utilities;
import Utilities.*;

@Entity(name="Machine")
@Table(name="Machine")
public class Machine  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 660681201810885116L;
	private String os;
	private String architecture;
	private String processor;
	private String IP;
	private String login;
	private String password;
	private Integer memory;
	private Integer bits;
	private Integer ID;
	private Boolean isBook;
	

	
	public Boolean getIsBook() {
		return isBook;
	}

	public void setIsBook(Boolean isBook) {
		this.isBook = isBook;
	}
	
	public Machine(){}
	
	public void copyFromMachine(Machine m)
	{
		os=m.os;
		architecture=m.architecture;
		processor=m.processor;
		IP=m.IP;
		login=m.login;
		password=m.password;
		memory=m.memory;
		bits=m.bits;
		isBook=m.isBook;
	}
	
	public Machine(String system,String arch,String proc,String ipNumber ,String log, String pass, Integer mem, Integer nbits, boolean book)
	{
		os=system;
		architecture=arch;
		processor=proc;
		IP=ipNumber;
		login=log;
		password=pass;
		memory=mem;
		bits=nbits;
		isBook=book;
	}
	
	public String getOs() {
		return os;
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
	
    @Override
    public String toString() {
    return "Machine ID="+ID;
    }
    
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Machine))
            return false;
        Machine m=(Machine)o;
        if (m.ID == ID)
            return true;
        return false;
    }
    
    public int hashCode() {
        return ID;
    }
    
}
