package EntityBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name="getAllEmployes", query="FROM Emploee e WHERE admin=0")
@Table(name="Emploee")
public class Emploee implements Serializable{
        /**
	 * 
	 */
	private static final long serialVersionUID = 938499949640813684L;
		Integer ID;
        private String firstName;
        private String lastName;
        private String email;
        private String department;
        Collection<Rezerwation> rezerwation;
        private Integer phone;
        private String manager;
        private String password;
        private boolean admin;
        
        public Emploee(){}
        
        public Emploee(String fName,String lName, String mail,String depart,Integer telephone,String boss,String pass,boolean adm)
        {
        	firstName=fName;
        	lastName=lName;
        	email=mail;
        	department=depart;
        	phone=telephone;
        	manager=boss;
        	password=pass;
        	admin=adm;
        }
        
        @OneToMany(
    	        cascade = {CascadeType.REMOVE},
    	        mappedBy = "emploee",
    	        fetch=FetchType.EAGER,
    	        targetEntity = Rezerwation.class
    	    )
        public Collection<Rezerwation> getRezerwation() {
        		if(rezerwation==null) rezerwation=new ArrayList<Rezerwation>();
                return rezerwation;
        }
        public void setRezerwation(Collection<Rezerwation> rezerwation) {
                this.rezerwation = rezerwation;
        }
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        public Integer getID() {
                return ID;
        }
        public void setID(Integer iD) {
                ID = iD;
        }
        public String getFirstName() {
                return firstName;
        }
        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }
        public String getLastName() {
                return lastName;
        }
        public void setLastName(String lastName) {
                this.lastName = lastName;
        }
        public void setPassword(String pass) 
        {
        	this.password = pass;
        }
        
        public String getPassword()
        {
        	return this.password;
        }
        
        @Column(unique=true)
        public String getEmail() {
                return email;
        }
        public void setEmail(String email) {
                this.email = email;
        }
        public String getDepartment() {
                return department;
        }
        public void setDepartment(String department) {
                this.department = department;
        }
        public Integer getPhone() {
                return phone;
        }
        public void setPhone(Integer phone) {
                this.phone = phone;
        }
        public String getManager() {
                return manager;
        }
        public void setManager(String manager) {
                this.manager = manager;
        }
        
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Emploee))
                return false;
            Emploee e=(Emploee)o;
            if (e.ID == ID)
                return true;
            return false;
        }
        
        public int hashCode() {
            return ID;
        }

        public String toString()
        {
        	return firstName+" "+lastName+" "+email+" "+department+" "+manager;
        }

		public void setAdmin(boolean admin) {
			this.admin = admin;
		}

		public boolean isAdmin() {
			return admin;
		}
}
