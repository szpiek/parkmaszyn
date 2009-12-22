package EntityBeans;

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

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

@Entity(name="Machine")
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
	
	@ManyToMany(
        targetEntity=Rezerwation.class,
        fetch=FetchType.LAZY,
        cascade={}
    )
    @JoinTable(
        name="MACHINE_REZERWATION",
        joinColumns=@JoinColumn(name="MACH_ID"),
        inverseJoinColumns=@JoinColumn(name="REZ_ID")
    )
	public Collection<Rezerwation> getRezerwation() {
		if(rezerwation==null) rezerwation=new ArrayList<Rezerwation>();
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
