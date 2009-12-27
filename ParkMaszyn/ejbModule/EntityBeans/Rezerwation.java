package EntityBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Rezerwation")
public class Rezerwation  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824088400077308476L;
	Integer ID;
	Date createDate;
	Date returnDate;
	Collection<Machine> machine;
	Emploee emploee;
	Boolean isBook;
	
	public Rezerwation(){}
	
	public Rezerwation(Date created, Date returned, Boolean book)
	{
		createDate=created;
		returnDate=returned;
		isBook=book;
	}
	
	
	@ManyToOne(
			fetch=FetchType.LAZY,
	        cascade = {CascadeType.MERGE},
	        targetEntity = Emploee.class
	    )
	@JoinColumn(name="EMPLOYEE_FK")
	public Emploee getEmploee() {
		return emploee;
	}
	public void setEmploee(Emploee emploee) {
		this.emploee = emploee;
	}
	@ManyToMany(
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        fetch=FetchType.LAZY,
	        mappedBy = "rezerwation",
	        targetEntity = Machine.class
	    )
	public Collection<Machine> getMachine() {
		if(machine==null) machine=new ArrayList<Machine>();
		return machine;
	}
	public void setMachine(Collection<Machine> machine) {
		this.machine = machine;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Boolean getIsBook() {
		return isBook;
	}
	public void setIsBook(Boolean isBook) {
		this.isBook = isBook;
	}
	
    @Override
    public String toString() {
    return "Rezerwation ID="+ID;
    }
    
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Rezerwation))
            return false;
        Rezerwation r=(Rezerwation)o;
        if (r.ID == ID)
            return true;
        return false;
    }
    
    public int hashCode() {
        return ID;
    }
}
