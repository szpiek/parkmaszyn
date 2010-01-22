package EntityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Processor")
@Table(name="Processor")
public class Processor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3022258858199045155L;

	public Integer getClock() {
		return clock;
	}

	public void setClock(Integer clock) {
		this.clock = clock;
	}
	Integer Id;
	Integer clock;
	String name;
	Integer bits;
	String architecture;
	Integer cores;
	
	public Integer getCores() {
		return cores;
	}

	public void setCores(Integer cores) {
		this.cores = cores;
	}

	public Processor(){
	}
	
	public Processor(String name, Integer bits, String architecture, Integer clock, Integer cores)
	{
		this.name=name;
		this.bits=bits;
		this.architecture=architecture;
		this.clock=clock;
		this.cores=cores;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBits() {
		return bits;
	}
	public void setBits(Integer bits) {
		this.bits = bits;
	}
	public String getArchitecture() {
		return architecture;
	}
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
}
