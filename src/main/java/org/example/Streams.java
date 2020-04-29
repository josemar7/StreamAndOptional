package org.example;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    private static Stream<Employee> employeeStream = Arrays.stream(Employee.SOME);

    public static void main(String[] args) {
//        String[] names = new String[]{"Juan", "Pedro", "Jose"};
//        Arrays.stream(names).forEach(System.out::println);
//        System.out.println("-------------------------");
//        main2();
//        System.out.println("-------------------------");
//        main3();
//        System.out.println("-------------------------");
//        main4();
//        System.out.println("-------------------------");
//        main5();
//        System.out.println("-------------------------");
//        main6();
//        System.out.println("-------------------------");
//        main7();
//        System.out.println("-------------------------");
//        main8();
//        System.out.println("-------------------------");
//        main9();
//        System.out.println("-------------------------");
//        main10();
//        System.out.println("-------------------------");
//        main11();
//        System.out.println("-------------------------");
//        main12();
//        System.out.println("-------------------------");
//        main13();
//        System.out.println("-------------------------");
//        main14();
//        System.out.println("-------------------------");
//        main15();
//        System.out.println("-------------------------");
//        main16();
//        System.out.println("-------------------------");
//        main17();
//        System.out.println("-------------------------");
//        main18();
//        System.out.println("-------------------------");
//        main19();
//        System.out.println("-------------------------");
//        main20();
//        System.out.println("-------------------------");
//        main21();
//        System.out.println("-------------------------");
//        main22();
//        System.out.println("-------------------------");
//        main23();
        System.out.println("-------------------------");
        main24();
    }

    public static void main2() {
        employeeStream
                .filter(employee -> employee.getSalary() >= 2500)
                .map(Employee::getName)
                .sorted()
                .forEach(System.out::println);
    }

    public static void main3() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        stream.forEach(System.out::println);
        System.out.println("-------------------------");
        employeeStream.map(Employee::getName)
                .sorted()
                .forEach(System.out::println);

    }

    public static void main4() {
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(5)
                .sorted()
                .forEach(System.out::println);
    }

    public static void main5() {
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

    public static void main6() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        Stream<Integer> stream1 = stream.limit(2);
        stream1.forEach(System.out::println);
    }

    public static void main7() {
        Set<Book> emp = Arrays.stream(Book.SOMEBOOKS)
                .collect(Collectors.toSet());
        emp.stream()
                .flatMap(book -> Arrays.stream(book.getWords()))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    public static void main8() {
        Stream<String> s = Stream.of("Hello");
        s.forEach(System.out::println);
    }

    public static void main9() {
        final Random random = new Random();
        Stream<Integer> randoms = Stream.generate(random::nextInt);
        randoms.filter(n -> n > 0)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }

    public static void main10() {
        employeeStream
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .takeWhile(e -> e.getSalary() <= 2000)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static void main11() {
        employeeStream.sorted(
                Comparator.comparingInt(Employee::getSalary)
                .reversed()
        )
                .limit(10)
                .map(Employee::getName)
                .forEachOrdered(System.out::println);
    }

    public static void main12() {
        Random random = new Random();
        Stream<Integer> randoms = Stream.generate(random::nextInt);
        randoms.peek(System.out::println)
                .filter( n -> n > 0 )
                .distinct()
                .limit( 10 )
                .forEach(System.out::println);
    }

    public static void main13() {
        Boolean result = employeeStream.allMatch(employee -> employee.getName() != null);
        System.out.println(result);

        result = employeeStream.anyMatch(employee -> employee.getName().equals("Mike"));
        System.out.println(result);

        result = employeeStream.noneMatch(employee -> employee.getName().equals("Pepo"));
        System.out.println(result);
    }

    public static void main14() {
        Object[] objects = employeeStream.filter(employee -> employee.getSalary() < 2000)
                .toArray();
        for (Object object : objects) {
            System.out.println(object);
        }

        Employee[] employees = employeeStream.filter(employee -> employee.getSalary() < 2000)
                .toArray(Employee[]::new);
        for (Employee object : employees) {
            System.out.println(object);
        }
    }

    public static void main15() {
        System.out.println(employeeStream.filter(employee -> employee.getSalary() < 2000).count());
    }

    public static void main16() {
        Collection<String> collection = employeeStream
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        Optional<String> longest = collection.stream()
                .max(Comparator.comparingInt(String::length));
        System.out.println(longest.orElse("Yeeepa"));
    }

    public static void main17() {
        Collection<String> stringCollection = employeeStream
                .map(Employee::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        String allNames = stringCollection.stream()
                .reduce("", ( a, b ) -> a + " " + b );
        System.out.println(allNames);
    }

    public static void main18() {
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

    public static void main19() {
        TreeSet<Employee> treeSet = employeeStream.collect(Collectors.toCollection(
                () -> new TreeSet<>(
                        Comparator.comparingInt(Employee::getSalary)
                )
        ));
        treeSet.forEach(System.out::println);
    }

    public static void main20() {
        Map<String, Integer> salaries = employeeStream.collect(
                Collectors.toMap(Employee::getName,
                        Employee::getSalary)
        );
        System.out.println(salaries);
    }

    public static void main21() {
        Map<Integer, List<Employee>> brackets = employeeStream.collect(
                Collectors.groupingBy(
                        e -> e.getSalary() / 1000
                )
        );
        System.out.println(brackets);
    }

    public static void main22() {
        Map<Boolean, List<Employee>> emp = employeeStream.collect(
                Collectors.partitioningBy(employee -> employee.getSalary() > 2000)
        );
        System.out.println(emp);
    }

    public static void main23() {
        Integer max = IntStream.range(0, 10)
                .max().getAsInt();
        System.out.println(max);
    }

    public static void main24() {
        System.out.println(employeeStream
                .peek(System.out::println)
                .mapToDouble(Employee::getSalary)
                .peek(System.out::println)
                .average()
                .getAsDouble());
    }

//    public static void main25() {
//        return polizas.stream()
//                .map(o -> o.getProductos() != null ? o.getProductos() : new ArrayList<ProductoVO>())
//                .flatMap(Collection::stream)
//                .map(o -> o.getServicios() != null ? o.getServicios() : new ArrayList<ServicioVO>())
//                .flatMap(Collection::stream)
//                .filter(servicioVO ->
//                        disponibilidad.equals(Disponibilidad.DISPONIBLE) ? servicioVO.getDisponible() != null : servicioVO.getConsumido() != null)
//                .reduce(0, (subtotal, element) -> subtotal +
//                        (disponibilidad.equals(Disponibilidad.DISPONIBLE) ? element.getDisponible() : element.getConsumido()), Integer::sum);
//    }


}
