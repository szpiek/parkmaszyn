package EntityBeans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Machine")
@Table(name="Machine")
public class Machine  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 660681201810885116L;
	private String IP;
	private String login;
	private String password;
	private Integer memory;
	private Integer ID;
	private Boolean isBook;
	private Processor processor;
	private OS os;
	
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
		processor=m.processor;
		IP=m.IP;
		login=m.login;
		password=m.password;
		memory=m.memory;
		isBook=m.isBook;
	}
	
	@ManyToOne(
			fetch=FetchType.EAGER,
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        targetEntity = Processor.class
	    )
	@JoinColumn(name="CPU_FK")
	public Processor getProcessor() {
		return processor;
	}

	
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@ManyToOne(
			fetch=FetchType.EAGER,
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        targetEntity = OS.class
	    )
	@JoinColumn(name="OS_FK")
	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public Machine(String system,String arch,String proc,String ipNumber ,String log, String pass, Integer mem, Integer nbits, boolean book)
	{
		IP=ipNumber;
		login=log;
		password=pass;
		memory=mem;
		isBook=book;
	}
	public Machine(OS o,Processor p,String ipNumber ,String log, String pass, Integer mem, Integer nbits, boolean book)
	{
		IP=ipNumber;
		login=log;
		password=pass;
		memory=mem;
		isBook=book;
		this.os = o;
		this.processor = p;
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
