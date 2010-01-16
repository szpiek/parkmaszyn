package EntityBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import Utilities.FlexToJavaConverter;

@Entity
@NamedQuery(name="getRezByUser", query="FROM Rezerwation r WHERE EMPLOYEE_FK=:userId")
@Table(name="Rezerwation")
public class Rezerwation  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824088400077308476L;
	Integer ID;
	Date createDate;
	Date returnDate;
	Set<Machine> machine;
	Emploee emploee;
	Boolean isBook;
	String need;
	
	public void fixForFlex()
	{
		//machine=(Collection<Machine>)(FlexToJavaConverter.convertMchineArray( FlexToJavaConverter.convertFromPersistentBag(machine) ));
	}
	
	public Rezerwation(){}
	
	public Rezerwation(Date created, Date returned, Boolean book)
	{
		createDate=created;
		returnDate=returned;
		isBook=book;
	}
	
	@ManyToOne(
			fetch=FetchType.EAGER,
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
	        fetch=FetchType.EAGER
	    )
	@JoinTable(
			name="REZ_MACH",
			joinColumns=@JoinColumn(name="REZ_ID"),
			inverseJoinColumns=@JoinColumn(name="MACH_ID")
	)
	public Set<Machine> getMachine() {
		if(machine==null) machine=new HashSet<Machine>();
		return machine;
	}
	public void setMachine(Set<Machine> machine) {
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
	public void setNeed(String n)
	{
		this.need = n;
	}
	public String getNeed()
	{
		return this.need;
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
