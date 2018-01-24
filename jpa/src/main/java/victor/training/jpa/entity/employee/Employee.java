package victor.training.jpa.entity.employee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue("EMPLOYEE")
@NamedQueries({
@NamedQuery(
	name="Employee_countByName", 
	query="SELECT count(e) FROM Employee e WHERE UPPER(e.name) = UPPER(:name)"
), 
@NamedQuery(
	name="Employee_getWithProjects", 
	query="SELECT e FROM Employee e LEFT JOIN FETCH e.projects WHERE e.id = :id"
)})
public class Employee  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;

	@ManyToOne
	@JoinColumn(name = "SITE_ID")
	private Site site;

	//@ManyToOne // TODO mark lazy ? // INITIAL
	@ManyToOne(fetch = FetchType.LAZY) // SOLUTION
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@OneToOne(mappedBy = "employee", cascade=CascadeType.ALL) // SOLUTION
	// @OneToOne(mappedBy = "employee") // INITIAL
	private EmployeeDetails details;

	@ManyToMany(mappedBy = "employees")
	private List<Project> projects = new ArrayList<>();
	
	//@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL) // INITIAL
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval=true) // SOLUTION
	private List<EmployeePhone> phones = new ArrayList<>();

	@ElementCollection
	@CollectionTable
	private Set<Address> addresses = new HashSet<>();

//	private String role; // INITIAL
	// SOLUTION (
	@ManyToOne 
	@JoinColumn
	private EmployeeRole role;
	// SOLUTION )
	
	@Version // SOLUTION
	private long version;
	
	public Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public EmployeeDetails getDetails() {
		return details;
	}

	public void setDetails(EmployeeDetails details) {
		this.details = details;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public long getVersion() {
		return version;
	}

	public List<EmployeePhone> getPhones() {
		return phones;
	}

	public void setPhones(List<EmployeePhone> phones) {
		this.phones = phones;
	}




	public Set<Address> getAddresses() {
		return addresses;
	}



	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void setVersion(long version) {
		this.version = version;
	}
 
}
