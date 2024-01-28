import java.io.*;
import java.util.*;

public class PhoneBookManager {
    private static final String PHONEBOOK_FILE = "phonebook.dat";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Contact> phoneBook = loadPhoneBook();

        boolean running = true;
        while (running) {
            System.out.println("Phone Book Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Display Phone Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addContact(phoneBook);
                    break;
                case 2:
                    deleteContact(phoneBook);
                    break;
                case 3:
                    displayPhoneBook(phoneBook);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        savePhoneBook(phoneBook);
    }

    private static List<Contact> loadPhoneBook() {
        List<Contact> phoneBook = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PHONEBOOK_FILE))) {
            phoneBook = (List<Contact>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file doesn't exist or error occurred during deserialization
        }
        return phoneBook;
    }

    private static void savePhoneBook(List<Contact> phoneBook) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PHONEBOOK_FILE))) {
            outputStream.writeObject(phoneBook);
        } catch (IOException e) {
            System.out.println("Error occurred while saving phone book.");
        }
    }

    private static void addContact(List<Contact> phoneBook) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        Contact contact = new Contact(name, phoneNumber);
        phoneBook.add(contact);
        System.out.println("Contact added successfully.");
    }

    private static void deleteContact(List<Contact> phoneBook) {
        System.out.print("Enter name to delete: ");
        String name = scanner.nextLine();
        boolean contactDeleted = false;
        Iterator<Contact> iterator = phoneBook.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                contactDeleted = true;
                break;
            }
        }
        if (contactDeleted) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void displayPhoneBook(List<Contact> phoneBook) {
        if (phoneBook.isEmpty()) {
            System.out.println("Phone book is empty.");
        } else {
            System.out.println("Phone Book:");
            for (Contact contact : phoneBook) {
                System.out.println(contact);
            }
        }
    }

    private static class Contact implements Serializable {
        private String name;
        private String phoneNumber;

        public Contact(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Phone: " + phoneNumber;
        }
    }
}
