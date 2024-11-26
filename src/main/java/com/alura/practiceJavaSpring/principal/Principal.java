package com.alura.practiceJavaSpring.principal;

import com.alura.practiceJavaSpring.model.BookData;
import com.alura.practiceJavaSpring.model.Data;
import com.alura.practiceJavaSpring.service.APIService;
import com.alura.practiceJavaSpring.service.DataConverter;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private APIService apiService = new APIService();
    private DataConverter dataConverter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu for interacting with book data.
     */
    public void showMenu() {
        var json = apiService.fetchData(URL_BASE);
        var data = dataConverter.getData(json, Data.class);

        int choice = 0;
        while (choice != 5) {
            System.out.println("*************** Menu ************");
            System.out.println("1. Top 10 most downloaded books");
            System.out.println("2. Search for a book by title");
            System.out.println("3. Search for authors by start year");
            System.out.println("4. Display statistics");
            System.out.println("5. Exit");

            System.out.println("Enter your choice:");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Top 10 of books most downloaded");
                    getMostDowloaded(data);
                    break;
                case 2:
                    System.out.println("Please enter the title of the book you want to search for:");
                    var bookTitle = scanner.nextLine();
                    findBook(bookTitle);
                    break;
                case 3:
                    System.out.println("Please enter the year from which you want to search for authors:");
                    var date = scanner.nextLine();
                    findAuthorsByYear(date);
                    break;
                case 4:
                    getStatistics(data);
                    break;
                case 5:
                    System.out.println("Exiting the menu. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    /**
     * Retrieves and displays the top 10 most downloaded books.
     * @param data The data containing book information.
     */
    private static void getMostDowloaded(Data data) {
        data.results().stream()
                .sorted(Comparator.comparing(BookData::numberOfDownloads).reversed())
                .limit(10)
                .map(t -> t.title().toUpperCase())
                .forEach(System.out::println);
    }

    /**
     * Finds authors based on the given starting year.
     * @param date The starting year for author search.
     */
    private void findAuthorsByYear(String date) {
        String json;
        json = apiService.fetchData(URL_BASE + "?author_year_start=" + date);
        var foundAuthors = dataConverter.getData(json, Data.class);
        System.out.println("Displaying authors based on the year entered:");
        System.out.println(foundAuthors);
    }

    /**
     * Finds a book by its title.
     * @param bookTitle The title of the book to search for.
     */
    private void findBook(String bookTitle) {
        String json;
        json = apiService.fetchData(URL_BASE + "?search=" + bookTitle.replace(" ", "+"));
        var searchData = dataConverter.getData(json, Data.class);
        Optional<BookData> foundBook = searchData.results().stream()
                .filter(l -> l.title().toUpperCase().contains(bookTitle.toUpperCase()))
                .findFirst();
        if (foundBook.isPresent()) {
            System.out.println("The found book is: ");
            System.out.println(foundBook);
        } else {
            System.out.println("The book was not found.");
        }
    }

    /**
     * Displays statistics based on book data.
     * @param data The data containing book information.
     */
    private static void getStatistics(Data data) {
        DoubleSummaryStatistics statistics = data.results().stream()
                .filter(d -> d.numberOfDownloads() > 0)
                .collect(Collectors.summarizingDouble(BookData::numberOfDownloads));
        System.out.println("*************** Statistics ************");
        System.out.println("Average number of downloads: " + statistics.getAverage());
        System.out.println("Maximum number of downloads: " + statistics.getMax());
        System.out.println("Minimum number of downloads: " + statistics.getMin());
        System.out.println("Number of records evaluated for calculating the statistics: " + statistics.getCount());
    }
}