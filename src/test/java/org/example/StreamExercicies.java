package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.service.InMemoryMovieService;
import org.example.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;

public class StreamExercicies {

    private static Stream<Employee> employeeStream = Arrays.stream(Employee.SOME);

    List<String> list = Arrays.asList(
            "alpha", "bravo", "charlie", "delta", "", "echo", "foxtrot");
    List<String> list1 = Arrays.asList(
            "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");

    private static final String WORD_REGEXP = "[- .:,]+";

    @Test
    public void udemy1() {
        String[] names = new String[]{"Juan", "Pedro", "Jose"};
        Arrays.stream(names).forEach(System.out::println);
    }

    @Test
    public void udemy2() {
        employeeStream
                .filter(employee -> employee.getSalary() >= 2500)
                .map(Employee::getName)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void udemy3() {
        employeeStream.map(Employee::getName)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void udemy4() {
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(5)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void udemy5() {
        final Random random = new Random();
        Supplier<Integer> supp = () -> {
            Integer result = random.nextInt();
            System.out.println("(supplying " + result + ")");
            return result;
        };
        System.out.println("\n Test 1");
        Stream<Integer> randoms = Stream.generate(supp);
        System.out.println("First stream built");
        randoms.filter(n -> n > 0)
                .limit(3)
                .forEach(System.out::println);
        System.out.println("-------------------------");
        Stream<Integer> randoms1 = Stream.generate(supp);
        randoms1.limit(3)
                .filter(n -> n > 0)
                .forEach(System.out::println);
    }

    @Test
    public void udemy6() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Stream<Integer> stream1 = stream.limit(2);
        stream1.forEach(System.out::println);
    }

    @Test
    public void udemy7() {
        Set<Book> emp = Arrays.stream(Book.SOMEBOOKS)
                .collect(Collectors.toSet());
        emp.stream()
                .flatMap(book -> Arrays.stream(book.getWords()))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void udemy8() {
        Stream<String> s = Stream.of("Hello");
        s.forEach(System.out::println);
    }

    @Test
    public void udemy9() {
        final Random random = new Random();
        Stream<Integer> randoms = Stream.generate(random::nextInt);
        randoms.filter(n -> n > 0)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void udemy10() {
        employeeStream
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .takeWhile(e -> e.getSalary() <= 2000)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @Test
    public void udemy11() {
        employeeStream.sorted(
                Comparator.comparingInt(Employee::getSalary)
                        .reversed()
        )
                .limit(10)
                .map(Employee::getName)
                .forEachOrdered(System.out::println);
    }

    @Test
    public void udemy12() {
        Random random = new Random();
        Stream<Integer> randoms = Stream.generate(random::nextInt);
        randoms.peek(System.out::println)
                .filter( n -> n > 0 )
                .distinct()
                .limit( 10 )
                .forEach(System.out::println);
    }

    @Test
    public void udemy13() {
        Boolean result = employeeStream.allMatch(employee -> employee.getName() != null);
        System.out.println(result);

        result = employeeStream.anyMatch(employee -> employee.getName().equals("Mike"));
        System.out.println(result);

        result = employeeStream.noneMatch(employee -> employee.getName().equals("Pepo"));
        System.out.println(result);
    }

    @Test
    public void udemy14() {
        Employee[] employees = employeeStream.filter(employee -> employee.getSalary() < 2000)
                .toArray(Employee[]::new);
        for (Employee object : employees) {
            System.out.println(object);
        }
    }

    @Test
    public void udemy15() {
        System.out.println(employeeStream.filter(employee -> employee.getSalary() < 2000)
                .count());
    }

    @Test
    public void udemy16() {
        Collection<String> collection = employeeStream
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        Optional<String> longest = collection.stream()
                .max(Comparator.comparingInt(String::length));
        System.out.println(longest.orElse("Yeeepa"));
    }

    @Test
    public void udemy17() {
        Collection<String> stringCollection = employeeStream
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        String allNames = stringCollection.stream()
                .reduce("", ( a, b ) -> a + " " + b );
        System.out.println(allNames);
    }

    @Test
    public void udemy18() {
        Collection<String> stringCollection = employeeStream
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        StringBuilder summary = stringCollection.stream().collect(
                () -> new StringBuilder(),
                ( StringBuilder builder, String s ) -> builder.append(s),
                ( StringBuilder builder1, StringBuilder builder2 ) -> builder1.append(builder2)
        );
        System.out.println(summary);

        summary = stringCollection.stream().collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append
        );
        System.out.println(summary);

        String s = stringCollection.stream()
                .collect(Collectors.joining());
        System.out.println(s);
    }

    @Test
    public void udemy19() {
        TreeSet<Employee> treeSet = employeeStream.collect(Collectors.toCollection(
                () -> new TreeSet<>(
                        Comparator.comparingInt(Employee::getSalary)
                )
        ));
        treeSet.forEach(System.out::println);
    }

    @Test
    public void udemy20() {
        Map<String, Integer> salaries = employeeStream.collect(
                Collectors.toMap(Employee::getName,
                        Employee::getSalary)
        );
        System.out.println(salaries);
    }

    @Test
    public void udemy21() {
        Map<Integer, List<Employee>> brackets = employeeStream.collect(
                Collectors.groupingBy(
                        e -> e.getSalary() / 1000
                )
        );
        System.out.println(brackets);
    }

    @Test
    public void udemy22() {
        Map<Boolean, List<Employee>> emp = employeeStream.collect(
                Collectors.partitioningBy(employee -> employee.getSalary() > 2000)
        );
        System.out.println(emp);
    }

    @Test
    public void udemy23() {
        Integer max = IntStream.range(0, 10)
                .max().getAsInt();
        System.out.println(max);
    }

    @Test
    public void udemy24() {
        System.out.println(employeeStream
                .peek(System.out::println)
                .mapToDouble(Employee::getSalary)
                .peek(System.out::println)
                .average()
                .getAsDouble());
    }

    @Test
    public void stringFirstLetterOfEachWord() {
        Predicate<String> predicate = s -> s == null || s.equals("");
        String result = list.stream()
                .filter(predicate.negate())
                .map(s -> s.substring(0, 1))
                .collect(joining());
        Assertions.assertEquals(result, "abcdef");
    }

    @Test
    public void removeOddLengths() {
        Predicate<String> predicate = s -> s.length() > 0 && s.length() % 2 == 0;
        List<String> result = list.stream()
                .filter(predicate)
                .collect(toList());
        Assertions.assertEquals(result.get(0), "echo");
    }

    @Test
    public void transformToUppercase() {
        List<String> strings = list.stream()
                .map(String::toUpperCase)
                .collect(toList());
        Assertions.assertEquals(strings.get(0), "ALPHA");
    }

    @Test
    public void transformMapToString() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 3);
        map.put("b", 2);
        map.put("a", 1);
        String strings = map.entrySet()
                .stream()
                .map(stringIntegerEntry -> stringIntegerEntry.getKey() + "=" + stringIntegerEntry.getValue() + ",")
                .collect(joining());
        System.out.println(strings.substring(0, strings.lastIndexOf(",")));
    }

    @Test
    public void threadThatPrintsNumbersList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        new Thread(() -> {
            list.forEach(n -> System.out.print(n));
        }).start();
    }

    @Test
    public void convertToLowercaseAndPrint() {
        list1.stream()
                .map(String::toLowerCase)
                .collect(toList())
                .forEach(System.out::println);
    }

    @Test
    public void convertToLowercaseAndPrintWithOddLength() {
        Predicate<String> predicate = s -> s.length() % 2 != 0;
        list1.stream()
                .filter(predicate)
                .map(String::toLowerCase)
                .collect(toList())
                .forEach(System.out::println);
    }

    @Test
    public void joinSecondThreeFourElements() {
        IntPredicate predicate = i -> i == 2 || i == 3 || i == 4;
        String s = IntStream.range(0, list1.size())
                .filter(predicate)
                .mapToObj(i -> list1.get(i) + "-")
                .collect(Collectors.joining());
        System.out.println(s.substring(0, s.lastIndexOf("-")));
    }

    @Test
    public void countNumberLines() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get("src/main/SonnetI.txt"), StandardCharsets.UTF_8)) {
            System.out.println(reader.lines()
                    .count());
        }
    }

    @Test
    public void wordsWithNoDuplicates() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get("src/main/SonnetI.txt"), StandardCharsets.UTF_8)) {
            List list = reader.lines()
                    .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                    .distinct()
                    .collect(toList());
            System.out.println(list);
        }
    }

    @Test
    public void wordsWithNoDuplicatesLowercaseAndSorted() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get("src/main/SonnetI.txt"), StandardCharsets.UTF_8)) {
            List list = reader.lines()
                    .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                    .distinct()
                    .map(String::toLowerCase)
                    .sorted()
                    .collect(toList());
            System.out.println(list);
        }
    }

    @Test
    public void wordsWithNoDuplicatesLowercaseAndSortedByLength() throws IOException {
        Comparator<String> comparator = comparing(s -> s.length());
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get("src/main/SonnetI.txt"), StandardCharsets.UTF_8)) {
            List list = reader.lines()
                    .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                    .distinct()
                    .map(String::toLowerCase)
                    .sorted(comparator.reversed())
                    .collect(toList());
            System.out.println(list);
        }
    }

    @Test
    public void averageValue() {
        Integer[] integers = {1, 2, 3, 4, 5};
        Double avg = Stream.of(integers)
                .mapToInt(Integer::intValue)
                .average().orElse(0D);
        Assertions.assertEquals(avg, 3.0);
    }

    @Test
    public void upperCaseList() {
        String[] strings = {"aaa", "bbb", "ccc"};
        List<String> converted = Stream.of(strings)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .collect(toList());
        Assertions.assertEquals(converted.get(0), "AAA");
    }

    @Test
    public void filterStrings() {
        String[] strings = {"bes", "ale", "vino", "asturias", "veo", "ate"};
        Predicate<String> predicate = s -> s.length() == 3 && s.toLowerCase().startsWith("a");
        List<String> startWithA = Stream.of(strings)
                .filter(predicate)
                .peek(System.out::println)
                .collect(toList());
        Assertions.assertEquals(startWithA.get(0), "ale");
    }

    @Test
    public void commaSeparated() {
        Integer[] integers = {1, 2, 3, 4, 5};
        List<String> strings = Stream.of(integers)
                .map(integer -> integer % 2 == 0 ? "e" + integer : "o" + integer)
                .peek(System.out::println)
                .collect(toList());
        Assertions.assertEquals(strings.get(0), "o1");
    }

    @Test
    public void highestPopulatedCityInEachCountry() {
        CountryDao countryDao= InMemoryWorldDao.getInstance();
        List<City> highPopulatedCitiesOfCountries = countryDao.findAllCountries()
                .stream()
                .map( country -> country.getCities().stream().max(comparing(City::getPopulation)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        highPopulatedCitiesOfCountries.forEach(System.out::println);
    }

    @Test
    public void mostPopulatedCityOfEachCountry() {
        CountryDao countryDao = InMemoryWorldDao.getInstance();
//        final Predicate<Map.Entry<String, Optional<City>>> isPresent = entry -> entry.getValue().isPresent();
        final BiConsumer<String, Optional<City>> printEntry =
                (k,v) -> {
                    City city = v.get();
                    System.out.println(k + ": City [ name= " + city.getName() + ", population= " + city.getPopulation() + " ]");
                };
        Collector<City, ?, Map<String, Optional<City>>> groupingHighPopulatedCitiesByContinent = groupingBy(city ->
                countryDao.findCountryByCode(city.getCountryCode()).getContinent(),
                Collectors.maxBy(comparing(City::getPopulation)));
        Map<String, Optional<City>> highPopulatedCitiesByContinent = countryDao.findAllCountries()
                .stream()
                .flatMap(country -> country.getCities().stream())
                .collect(groupingHighPopulatedCitiesByContinent);
        highPopulatedCitiesByContinent.forEach(printEntry);
    }

    @Test
    public void numberOfMoviesOfEachDirector() {
        MovieService movieService = InMemoryMovieService.getInstance();
        Collection<Movie> movies = movieService.findAllMovies();
        Map<String, Long> directorMovieCounts =
                movies.stream()
                        .map(Movie::getDirectors)
                        .flatMap(List::stream)
                        .collect(groupingBy(Director::getName, Collectors.counting()));
        directorMovieCounts.entrySet().forEach(System.out::println);
    }

    @Test
    public void numberOfGenresOfEachDirectorMovies() {
        MovieService movieService = InMemoryMovieService.getInstance();
        Collection<Director> directors = movieService.findAllDirectors();
        Stream<DirectorGenre> stream =
                directors.stream()
                        .flatMap(director -> director.getMovies()
                                .stream()
                                .map(Movie::getGenres)
                                .flatMap(Collection::stream)
                                .map(genre -> new DirectorGenre(director, genre))
                                .collect(toList()).stream()
                        );
        Map<Director, Map<Genre, Long>> directorGenreList =
                stream.collect(
                        groupingBy(
                                DirectorGenre::getKey,
                                groupingBy(DirectorGenre::getValue, Collectors.counting())
                        )
                );
        directorGenreList.forEach(
                (k1,v1) -> {
                    System.out.println(k1.getName());
                    v1.forEach( (k2,v2) -> System.out.println(format("\t%-12s: %2d", k2.getName(), v2)));
                    System.out.println();
                }
        );
    }

    @Test
    public void highestPopulateCapitalCity() {
        CountryDao countryDao = InMemoryWorldDao.getInstance();
        CityDao cityDao = InMemoryWorldDao.getInstance();
        Optional<City> capital = countryDao.findAllCountries()
                .stream()
                .map(Country::getCapital)
                .map(cityDao::findCityById)
                .filter(Objects::nonNull).max(comparing(City::getPopulation));
        capital.ifPresent(System.out::println);
    }

    @Test
    public void highestPopulatedCapitalCityOfEachContinent() {
        CountryDao countryDao = InMemoryWorldDao.getInstance();
        CityDao cityDao = InMemoryWorldDao.getInstance();
        Map<String, Optional<ContinentPopulatedCity>> continentsCapitals = countryDao.findAllCountries()
                .stream()
                .filter(country -> country.getCapital() > 0)
                .map(country -> new ContinentPopulatedCity(country.getContinent(), cityDao.findCityById(country.getCapital())))
                .collect(groupingBy(ContinentPopulatedCity::getKey, Collectors.maxBy(comparing(cpc -> cpc.getValue().getPopulation()))));
        BiConsumer<String, Optional<ContinentPopulatedCity>> print= (k, v) -> {
            Consumer<ContinentPopulatedCity> continentPopulatedCityConsumer = cpc -> System.out.println(cpc.getKey() + ": " + v.get().getValue());
            v.ifPresent(continentPopulatedCityConsumer);
        };
        continentsCapitals.forEach(print);
    }

    @Test
    public void sortCountriesByNumberOfTheirCities() {
        CountryDao countryDao = InMemoryWorldDao.getInstance();
        Comparator<Country> sortByNumOfCities = comparing(country -> country.getCities().size());
        Predicate<Country> countriesHavingNoCities = country -> country.getCities().isEmpty();
        List<Country> countries = countryDao.findAllCountries()
                .stream()
                .filter(countriesHavingNoCities.negate())
                .sorted(sortByNumOfCities.reversed())
                .collect(toList());
        countries.forEach(country -> System.out.println(format("%38s %3d", country.getName(), country.getCities().size())));
    }

    @Test
    public void findMoviesWithGenresOfDramaAndComedy() {
        MovieService movieService = InMemoryMovieService.getInstance();
        Collection<Movie> movies = movieService.findAllMovies();
        Predicate<Movie> drama = movie -> movie.getGenres().stream().anyMatch(genre -> genre.getName().equals("Drama"));
        Predicate<Movie> comedy = movie -> movie.getGenres().stream().anyMatch(genre -> genre.getName().equals("Comedy"));
        Predicate<Movie> havingTwoGenresOnly = movie -> movie.getGenres().size() == 2;
        List<Movie> dramaAndComedyMovies = movies.stream()
                .filter(havingTwoGenresOnly.and(drama.and(comedy)))
                .collect(toList());
        dramaAndComedyMovies.forEach(movie -> System.out.println(format("%-32s: %12s", movie.getTitle(), movie.getGenres().stream().map(Genre::getName).collect(joining(",")))));
    }

    @Test
    public void groupMoviesByTheYear() {
        MovieService movieService = InMemoryMovieService.getInstance();
        Collection<Movie> movies = movieService.findAllMovies();
        Map<Integer, String> moviesByYear = movies.stream().collect(groupingBy(Movie::getYear, mapping(Movie::getTitle, joining(","))));
        moviesByYear.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> System.out.println(format("%4d: %s", entry.getKey(), entry.getValue())));
    }

    @Test
    public void sortCountriesByPopulationDensityIgnoringZeroPopulationCountries() {
        WorldDao worldDao = InMemoryWorldDao.getInstance();
        Collection<Country> countries = worldDao.findAllCountries();
        Comparator<Country> populationDensityComparator = comparingDouble(country -> country.getPopulation() / country.getSurfaceArea());
        Predicate<Country> livesNobody = country -> country.getPopulation() == 0L;
        countries.stream().filter(livesNobody.negate()).sorted(populationDensityComparator.reversed())
                .forEach(System.out::println);
    }

    @Test
    public void serviciosConUnidadesIgual5() {
        Respuesta respuesta = RespuestaDaoImpl.getInstance().getRespuesta();
//        respuesta.setPolizas(null);
//        List<Servicio> servicios = Optional.ofNullable(respuesta.getPolizas())
//                .orElseGet(() -> new ArrayList<>())
//                .stream()
//                .map(poliza -> poliza.getProductos() == null ? new ArrayList<Producto>() : poliza.getProductos())
//                .flatMap(Collection::stream)
//                .map(producto -> producto.getServicios() == null ? new ArrayList<Servicio>() : producto.getServicios())
//                .flatMap(Collection::stream)
//                .filter(servicio -> servicio.getUnidades() == 5)
//                .collect(toList());

        List<Servicio> servicios = Optional.ofNullable(respuesta.getPolizas())
                .orElseGet(() -> new ArrayList<>())
                .stream()
                .map(poliza -> Optional.ofNullable(poliza.getProductos()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .map(producto -> Optional.ofNullable(producto.getServicios()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .filter(servicio -> servicio.getUnidades() == 5)
                .collect(toList());
        Assertions.assertEquals(servicios.size(), 1);
    }

    @Test
    public void sumarUnidadesDeTodosProductos() {
        Integer sum = Optional.ofNullable(RespuestaDaoImpl.getInstance().getRespuesta().getPolizas())
                .orElseGet(() -> new ArrayList<>())
                .stream()
                .map(poliza -> Optional.ofNullable(poliza.getProductos()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .map(producto -> Optional.ofNullable(producto.getServicios()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .reduce(0, (partial, s) -> partial + Optional.ofNullable(s.getUnidades()).orElse(0), Integer::sum);
        System.out.println(sum);
    }

}

