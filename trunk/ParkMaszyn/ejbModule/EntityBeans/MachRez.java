package EntityBeans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MachRez")

public class MachRez {

	Integer ID;
	Integer MachID;
	Integer RezID;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getMachID() {
		return MachID;
	}
	public void setMachID(Integer machID) {
		MachID = machID;
	}
	public Integer getRezID() {
		return RezID;
	}
	public void setRezID(Integer rezID) {
		RezID = rezID;
	}
	
}
