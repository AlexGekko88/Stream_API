import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }


        long qtyOfUnderage = persons.stream()
                .filter(person -> person.getAge() < 18) // поиск несовершеннолетних, младше 18 лет.
                .count();


        List<String> conscripts = persons.stream()  // призывники ( MAN  от 18 - 27 лет)
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());


        Comparator<Person> comparator = (person1, person2) -> person1.getFamily().compareTo(person2.getFamily());
        List<Person> workableHighEducated = persons.stream()       // потенциально работоспособные люди с высшим образованием  Woman от 18-60, и до 65 лет. Man
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 65)
                .filter(person -> !(person.getSex() == Sex.WOMAN && person.getAge() >= 60))
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
