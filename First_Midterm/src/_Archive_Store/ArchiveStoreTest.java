package _Archive_Store;

import java.util.*;

class NonExistingItemException extends RuntimeException {
    public NonExistingItemException(int id) {
        super("Item with id " + id + " doesn't exist");
    }
}

abstract class Archive {
    private int id;
    private Date dateArchived;

    public int getId() {
        return id;
    }

    public Archive(int id) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public abstract String openArchive(Date date, int id);

    public void setDateArchived(Date d) {
        this.dateArchived = d;
    }
}

class LockedArchive extends Archive {

    private Date dateToOpen;

    public LockedArchive(int id, Date date) {
        super(id);
        this.dateToOpen = date;
    }

    @Override
    public String openArchive(Date date, int id) {
        if(date.before(dateToOpen))
            return "Item " + id + " cannot be opened before " + dateToOpen.toString() + "\n";
        else {
            return "Item " + id + " opened at " + date.toString() + "\n";
        }
    }
}

class SpecialArchive extends Archive {

    private int maxOpen;
    private int noOpened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        noOpened = 0;
    }

    @Override
    public String openArchive(Date date, int id) {
        if(noOpened < maxOpen) {
            noOpened++;
            return "Item " + id + " opened at " + date.toString() + "\n";
        } else {
            return "Item " + id + " cannot be opened more than " + maxOpen + " times\n";
        }
    }
}

class ArchiveStore {

    List<Archive> archiveList;
    StringBuilder finalLog;

    public ArchiveStore() {
        archiveList = new ArrayList<>();
        finalLog = new StringBuilder();
    }

    public void archiveItem(Archive item, Date date) {
        item.setDateArchived(date);
        this.archiveList.add(item);
        finalLog.append("Item " + item.getId() + " archived at " + date.toString() + "\n");
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        Archive item = this.archiveList.stream().filter(i -> i.getId() == id).findFirst().orElseThrow(
                () -> new NonExistingItemException(id));
        finalLog.append(item.openArchive(date, id));
    }

    public String getLog() {
        return finalLog.substring(0, finalLog.length()-1);
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}