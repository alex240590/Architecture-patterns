import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PersonService implements DateRecordManager{
    private static final Logger logger = LoggerFactory.getLogger(OfficeService.class);

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonConvertor personConvertor;

    public Person findById(Long id) {
        logger.info("CALLED METHOD - findById()");
        return personRepository.findById(id).get();
    }

    public List<Person> findAll() {
        logger.info("CALLED METHOD - findAll()");
        return (List<Person>) personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("CALLED METHOD - create()");
        return personRepository.save(person);
    }

    public PersonResponseDto deleteById(Long id) {
        logger.info("CALLED METHOD - deleteById()");
        Person person = findById(id);
        person.setDeleted(true);
        update(person);

        return personConvertor.convertToPersonResponseDto(person);
    }

    public Person update(Person person) {
        logger.info("CALLED METHOD - update()");
        return personRepository.save(person);
    }

    public Person findByUsername(String username) {
        logger.info("CALLED METHOD - findByUsername()");
        Credentials credentials  = credentialsRepository.findByLogin(username);
        return personRepository.findById(credentials.getId()).get();
    }

    public Set<Project> findActiveProjects(Person person) {
        logger.info("CALLED METHOD - findActiveProjects()");
        return person.getProjects().stream().
                filter(project -> !project.isDeleted()).
                collect(Collectors.toSet());
    }

    public String produceFullName(Person person) {
        logger.info("CALLED METHOD - produceFullName()");
        return person.getRusSurname() + " " + person.getRusName();
    }

    public void addProject(Person person, Project project) {
        logger.info("CALLED METHOD - addProject()");
        person.getProjects().add(project);
    }

    public List<Person> findAllDevs(Person requester) {
        logger.info("CALLED METHOD - findAllDevs()");
        return findAll().stream()
                .filter(person -> !person.equals(requester))
                .filter(person -> !person.isDeleted())
                .filter(person -> !person.getDepartment().getName().equals("Project Management"))
                .filter(person -> !person.getDepartment().getName().equals("Human resources"))
                .collect(Collectors.toList());
    }

    public List<Person> findAllHumanResources() {
        logger.info("CALLED METHOD - findAllHumanResources()");
        return findAll().stream()
                .filter(person -> !person.isDeleted())
                .filter(person -> person.getDepartment().getName().equals("Human resources"))
                .collect(Collectors.toList());
    }

    public List<NameIdResponseDto> findAllHumanResourcesNameIdResponseDto() {
        logger.info("CALLED METHOD - findAllHumanResourcesNameIdResponseDto()");
        return  findAllHumanResources().stream().filter(p -> !p.isDeleted()).map(p -> personConvertor.covertToNameIdResponseDto(p)).collect(Collectors.toList());
    }

    public List<EmployeeNameResponseDto> findAllEmployeeNameResponseDto() {
        logger.info("CALLED METHOD - findAllEmployeeNameResponseDto()");
        return findAll().stream().filter(p -> !p.isDeleted()).map(personConvertor::convertToEmployeeNameResponseDto).collect(Collectors.toList());
    }

    public PersonFullResponseDto findByIdPersonFullResponseDto(Long id) {
        logger.info("CALLED METHOD - findByIdPersonFullResponseDto()");
        Person person = findById(id);
        return  personConvertor.convertToPersonFullResponseDto(person);


    }

    public PersonResponseDto findByIdPersonResponseDto(Long id) {
        logger.info("CALLED METHOD - findByIdPersonResponseDto()");
        Person person = findById(id);
        return  personConvertor.convertToPersonResponseDto(person);
    }

    public PersonResponseDto updateWithId(PersonRequestDto body, Long id) {
        logger.info("CALLED METHOD - updateWithId()");
        Person person = personConvertor.convertToPerson(body);
        person.setDateChange(LocalDate.now());
        person.setId(id);
        update(person);
        return personConvertor.convertToPersonResponseDto(person);
    }

    public PersonResponseDto create(PersonRequestDto body) {
        logger.info("CALLED METHOD - create()");
        Person person = personConvertor.convertToPerson(body);
        person.setDateCreation(LocalDate.now());
        person.setDateChange(LocalDate.now());
        return personConvertor.convertToPersonResponseDto(create(person));
    }

    public List<EmployeesShortInfoResponseDto> findEmployeesShortInfoResponseDto(Integer offset, Integer limit, Person currentPerson) {
        logger.info("CALLED METHOD - findEmployeesShortInfoResponseDto()");
        if(offset != null && limit != null) {
            return findAll().stream().filter(p -> !p.isDeleted()).skip(offset).limit(limit).map(p -> personConvertor.covertToEmployeesShortInfoResponseDto(p, currentPerson)).collect(Collectors.toList());
        } else {
            return findAll().stream().filter(p -> !p.isDeleted()).map(p -> personConvertor.covertToEmployeesShortInfoResponseDto(p, currentPerson)).collect(Collectors.toList());
        }

    }

    public InformationResponseDto findInformation(Person person) {
        logger.info("CALLED METHOD - findInformation()");
        return personConvertor.convertToInformationResponseDto(person);
    }

    public List<MyEmployeesShortInfoResponseDto> findEmployeesShortInfoResponseDtoByPerson(Person person) {
        logger.info("CALLED METHOD - findEmployeesShortInfoResponseDtoByPerson()");
        return findAll().stream().filter(p -> p.getParameterByQualifier("HR").equals(person.getId().toString())).map(p -> personConvertor.covertToMyEmployeesShortInfoResponseDto(p)).collect(Collectors.toList());
    }
}
