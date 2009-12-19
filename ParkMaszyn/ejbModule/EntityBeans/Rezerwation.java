package EntityBeans;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Rezerwation {
	Integer ID;
	Date createDate;
	Date returnDate;
	@Column(nullable=false)
	Emploee emploee;
	@Column(nullable=false)
	Collection<Machine> machine;
	Boolean isBook;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="rezerwation")
	public Collection<Machine> getMachine() {
		return machine;
	}
	public void setMachine(Collection<Machine> machine) {
		this.machine = machine;
	}
	@ManyToOne(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@JoinColumn(name="emploeeID")
	public Emploee getEmploee() {
		return emploee;
	}
	public void setEmploee(Emploee emploee) {
		this.emploee = emploee;
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
	
	
}
