package TransfermarktAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class RapidApiExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043a\u043b\u0443\u0431\u0430: ");
        String clubName = scanner.nextLine().trim().replace(" ", "%20"); // Преобразование пробелов для URL

        try {
            // Первый запрос для поиска клуба
            String apiUrl = "https://transfermarket.p.rapidapi.com/search?query=" + clubName + "&domain=de";
            HttpRequest searchRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("x-rapidapi-key", "fccec1ec4cmsha3352b856179ccdp10a71cjsnf0bea677dc56")
                    .header("x-rapidapi-host", "transfermarket.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> searchResponse = HttpClient.newHttpClient().send(searchRequest, HttpResponse.BodyHandlers.ofString());
            String searchResponseBody = searchResponse.body();

            // Поиск информации о клубах в ответе
            Map<Integer, String> clubMap = new LinkedHashMap<>();
            String[] clubsData = searchResponseBody.split("\\{\"id\":\"");
            if (clubsData.length > 1) {
                System.out.println("\u041d\u0430\u0439\u0434\u0435\u043d\u043d\u044b\u0435 \u043a\u043b\u0443\u0431\u044b:");
                for (int i = 1; i < clubsData.length; i++) {
                    String clubSection = clubsData[i];
                    String id = extractValue(clubSection, "", "\"");
                    String name = extractValue(clubSection, "\"name\":\"", "\"");
                    if (!id.isEmpty() && !name.isEmpty()) {
                        clubMap.put(i, id);
                        System.out.println(i + ". " + name);
                    }
                }

                // Считывание выбора пользователя
                System.out.print("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043d\u043e\u043c\u0435\u0440 \u043a\u043b\u0443\u0431\u0430 \u0434\u043b\u044f \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u044f \u043f\u043e\u0434\u0440\u043e\u0431\u043d\u043e\u0439 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u0438: ");
                int clubChoice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                if (clubMap.containsKey(clubChoice)) {
                    String selectedClubId = clubMap.get(clubChoice);

                    // Запрос дополнительной информации
                    String detailsApiUrl = "https://transfermarket.p.rapidapi.com/clubs/get-profile?id=" + selectedClubId + "&domain=de";
                    HttpRequest detailsRequest = HttpRequest.newBuilder()
                            .uri(URI.create(detailsApiUrl))
                            .header("x-rapidapi-key", "fccec1ec4cmsha3352b856179ccdp10a71cjsnf0bea677dc56")
                            .header("x-rapidapi-host", "transfermarket.p.rapidapi.com")
                            .method("GET", HttpRequest.BodyPublishers.noBody())
                            .build();

                    HttpResponse<String> detailsResponse = HttpClient.newHttpClient().send(detailsRequest, HttpResponse.BodyHandlers.ofString());
                    String detailsResponseBody = detailsResponse.body();

                    // Вывод полной информации о клубе
                    System.out.println("\n\u041f\u043e\u043b\u043d\u0430\u044f \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044f \u043e \u043a\u043b\u0443\u0431\u0435:");
                    String fullName = extractValue(detailsResponseBody, "\"fullName\":\"", "\"");
                    String foundingDate = extractValue(detailsResponseBody, "\"founding\":\"", "\"");
                    String city = extractValue(detailsResponseBody, "\"city\":\"", "\"");
                    String stadium = extractValue(detailsResponseBody, "\"stadium\":\"", "\"");
                    String capacity = extractValue(detailsResponseBody, "\"totalCapacity\":\"", "\"");

                    System.out.println("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435: " + fullName);
                    System.out.println("\u0413\u043e\u0434 \u043e\u0441\u043d\u043e\u0432\u0430\u043d\u0438\u044f: " + foundingDate);
                    System.out.println("\u0413\u043e\u0440\u043e\u0434: " + city);
                    System.out.println("\u0421\u0442\u0430\u0434\u0438\u043e\u043d: " + stadium);
                    System.out.println("\u0412\u043c\u0435\u0441\u0442\u0438\u043c\u043e\u0441\u0442\u044c: " + capacity);
                } else {
                    System.out.println("\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0439 \u0432\u044b\u0431\u043e\u0440.");
                }
            } else {
                System.out.println("\u041a\u043b\u0443\u0431\u044b \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static String extractValue(String source, String key, String delimiter) {
        int start = source.indexOf(key) + key.length();
        int end = source.indexOf(delimiter, start);
        return (start > key.length() - 1 && end > start) ? source.substring(start, end) : "\u041d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u043e";
    }
}