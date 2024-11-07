import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args)
    {
        boolean quit = false;
        String choice;

        while (!quit)
        {
            displayMenu();
            choice = SafeInput.getRegExString(in, "Enter your choice: ", "[AaDdIiPpQq]");
            switch (choice.toUpperCase())
            {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "P":
                    printList();
                    break;
                case "Q":
                    quit = getYNConfirm("Are you sure you want to quit? (Y/N)");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu()
    {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("P - Print the list");
        System.out.println("Q - Quit the program");
        System.out.println();
        printList();
    }

    private static void addItem()
    {
        String item = SafeInput.getString(in, "Enter the item to add: ");
        list.add(item);
        System.out.println("Item added.");
    }

    private static void deleteItem()
    {
        printList();
        int index = SafeInput.getRangedInt(in, "Enter the item number to delete: ", 1, list.size()) - 1;
        list.remove(index);
        System.out.println("Item deleted.");
    }

    private static void insertItem()
    {
        printList();
        int index = SafeInput.getRangedInt(in, "Enter the position to insert the item: ", 1, list.size() + 1) - 1;
        String item = SafeInput.getString(in, "Enter the item to insert: ");
        list.add(index, item);
        System.out.println("Item inserted.");
    }

    private static void printList()
    {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private static boolean getYNConfirm(String prompt)
    {
        String response = SafeInput.getRegExString(in, prompt, "[YyNn]");
        return response.equalsIgnoreCase("Y");
    }
}
