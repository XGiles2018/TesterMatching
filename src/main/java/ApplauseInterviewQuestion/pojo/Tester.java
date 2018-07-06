package ApplauseInterviewQuestion.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Tester {
	@Id
	@Column
	private Long id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String country;
	
	@Transient
	private int bugCount;
	

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable( name = "tester_has_device", joinColumns = @JoinColumn(name="device_id"), inverseJoinColumns = @JoinColumn(name = "tester_id"))
	private Set<Device> devices = new HashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getBugCount() {
		return bugCount;
	}

	public void setBugCount(int bugCount) {
		this.bugCount = bugCount;
	}

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
