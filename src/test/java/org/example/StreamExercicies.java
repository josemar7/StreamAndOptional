package org.example;

import org.example.dao.CityDao;
import org.example.dao.CountryDao;
import org.example.dao.InMemoryWorldDao;
import org.example.dao.WorldDao;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;

public class StreamExercicies {

    List<String> list = Arrays.asList(
            "alpha", "bravo", "charlie", "delta", "", "echo", "foxtrot");
    List<String> list1 = Arrays.asList(
            "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");

    private static final String WORD_REGEXP = "[- .:,]+";

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
            list.forEach(n -> System.out.println(n));
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
        final Predicate<Map.Entry<String, Optional<City>>> isPresent = entry -> entry.getValue().isPresent();
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

}


class DirectorGenre implements Map.Entry<Director, Genre> {
    private Director director;
    private Genre genre;

    public DirectorGenre(Director director, Genre genre) {
        this.director = director;
        this.genre = genre;
    }

    @Override
    public Director getKey() {
        return director;
    }

    @Override
    public Genre getValue() {
        return genre;
    }

    @Override
    public Genre setValue(Genre genre) {
        this.genre = genre;
        return genre;
    }
}

class ContinentPopulatedCity implements Map.Entry<String, City> {
    private String continent;
    private City city;

    public ContinentPopulatedCity(String continent, City city) {
        this.continent = continent;
        this.city = city;
    }

    @Override    public String getKey() {
        return continent;
    }

    @Override    public City getValue() {
        return city;
    }

    @Override    public City setValue(City city) {
        this.city = city;
        return city;
    }

}