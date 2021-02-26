package _Cover_Page;

import java.util.*;
import java.util.stream.Collectors;

class Category {
    private String nameCategory;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    @Override
    public boolean equals(Object o) {
        return nameCategory.equals(((Category)o).getNameCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCategory);
    }
}

abstract class NewsItem {
    protected String title;
    protected Date datePublish;
    protected Category category;

    public NewsItem(String title, Date datePublish, Category category) {
        this.title = title;
        this.datePublish = datePublish;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    protected int parseDate() {
        return (int)((System.currentTimeMillis() - datePublish.getTime())/60000);
    }

    public abstract String getTeaser();
}

class TextNewsItem extends NewsItem {

    private String textOfNews;

    public TextNewsItem(String title, Date datePublish, Category category, String textOfNews) {
        super(title, datePublish, category);
        this.textOfNews = textOfNews;
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        for(char c : textOfNews.toCharArray())
            if(sb.toString().length() < 80)
                sb.append(c);
        return sb.toString();
    }

    @Override
    public String getTeaser() {
        return String.format("%s\n%d\n%s\n",
                super.title, super.parseDate(),
                getText());
    }
}

class MediaNewsItem extends NewsItem {

    private String urlLocation;
    private int noViews;

    public MediaNewsItem(String title, Date datePublish, Category category, String url, int news) {
        super(title, datePublish, category);
        this.noViews = news;
        this.urlLocation = url;
    }

    @Override
    public String getTeaser() {
        return String.format("%s\n%d\n%s\n%d\n",
                super.title, super.parseDate(),
                this.urlLocation, this.noViews);
    }
}

class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Category " + name + " was not found");
    }
}

class FrontPage {
    private List<NewsItem> newsItemList;
    private Category[] categories;
    public FrontPage(Category[] categories) {
        this.categories = categories;
        newsItemList = new ArrayList<>();
    }

    public void addNewsItem(NewsItem tni) {
        newsItemList.add(tni);
    }

    public List<NewsItem> listByCategory(Category category) {
        return newsItemList.stream().filter(i -> i.getCategory().equals(category)).
                collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if(Arrays.stream(categories).filter(i -> i.equals(
                new Category(category))).count() == 0) {
            throw new CategoryNotFoundException(category);
        }

        return listByCategory(new Category(category));
    }

    @Override
    public String toString() {
        return newsItemList.stream().map(line -> line.getTeaser()).collect(Collectors.joining());
    }
}

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }

        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
