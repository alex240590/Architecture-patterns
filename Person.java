import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@Data
@Entity
public class Person extends ParametersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(allocationSize = 1, name = "person_generator")
    private Long id;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 2, max = 30)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Size(min = 2, max = 30)
    @NotBlank
    private String surname;

    @Column(nullable = false)
    private String rusName;

    @Column(nullable = false)
    private String rusSurname;

    @Column
    private String photo_url;

    @Column(nullable = false)
    @Past
    private LocalDate dateBirth;

    @Column(nullable = false)
    private String skype;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String emergencyPhoneNumber;

    @PastOrPresent
    private LocalDate dateCreation;

    @PastOrPresent
    private LocalDate dateChange;

    private boolean isDeleted;

    @Pattern(regexp = JSON_PATTERN, message = "Parameter value is not matches Json regex pattern.")
    private String parameters;

    @OneToMany(mappedBy = "person")
    private Set<Request> requests;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_role",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "persons_projects",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (isDeleted != person.isDeleted) return false;
        if (!email.equals(person.email)) return false;
        if (!name.equals(person.name)) return false;
        if (!surname.equals(person.surname)) return false;
        if (!rusName.equals(person.rusName)) return false;
        if (!rusSurname.equals(person.rusSurname)) return false;
        if (!dateBirth.equals(person.dateBirth)) return false;
        if (!skype.equals(person.skype)) return false;
        if (!phoneNumber.equals(person.phoneNumber)) return false;
        if (!emergencyPhoneNumber.equals(person.emergencyPhoneNumber)) return false;
        if (dateCreation != null ? !dateCreation.equals(person.dateCreation) : person.dateCreation != null)
            return false;
        if (dateChange != null ? !dateChange.equals(person.dateChange) : person.dateChange != null) return false;
        return parameters.equals(person.parameters);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + rusName.hashCode();
        result = 31 * result + rusSurname.hashCode();
        result = 31 * result + dateBirth.hashCode();
        result = 31 * result + skype.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + emergencyPhoneNumber.hashCode();
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (dateChange != null ? dateChange.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + parameters.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", rusName='").append(rusName).append('\'');
        sb.append(", rusSurname='").append(rusSurname).append('\'');
        sb.append(", dateBirth=").append(dateBirth);
        sb.append(", skype='").append(skype).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", emergencyPhoneNumber='").append(emergencyPhoneNumber).append('\'');
        sb.append(", dateCreation=").append(dateCreation);
        sb.append(", dateChange=").append(dateChange);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", parameters='").append(parameters).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
