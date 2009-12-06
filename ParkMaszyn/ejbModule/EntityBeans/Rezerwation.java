package EntityBeans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Rezerwation")
public class Rezerwation {
	Integer ID;
	Date createDate;
	Date returnDate;
	Integer emploeeID;
	Boolean isBook;
	
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
	public Integer getEmploeeID() {
		return emploeeID;
	}
	public void setEmploeeID(Integer emploeeID) {
		this.emploeeID = emploeeID;
	}
	public Boolean getIsBook() {
		return isBook;
	}
	public void setIsBook(Boolean isBook) {
		this.isBook = isBook;
	}
	
	
}
