import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);
    private static boolean isDirty = false;

    public static void main(String[] args)
    {
        boolean quit = false;
        String choice;

        while (!quit)
        {
            displayMenu();
            choice = SafeInput.getRegExString(in, "Enter your choice: ", "[OoSsAaDdIiPpMmCcQq]");
            switch (choice.toUpperCase())
            {
                case "O":
                    if (isDirty)
                    {
                        promptSaveChanges();
                    }
                    openFile();
                    break;
                case "S":
                    saveFile();
                    break;
                case "A":
                    addItem();
                    isDirty = true;
                    break;
                case "D":
                    deleteItem();
                    isDirty = true;
                    break;
                case "I":
                    insertItem();
                    isDirty = true;
                    break;
                case "V":
                    viewList();
                    break;
                case "M":
                    moveItem();
                    isDirty = true;
                    break;
                case "C":
                    clearList();
                    isDirty = true;
                    break;
                case "Q":
                    if (isDirty)
                    {
                        promptSaveChanges();
                    }
                    quit = SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu()
    {
        System.out.println("\nMenu:");
        System.out.println("O - Open a file");
        System.out.println("S - Save a file");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View the list");
        System.out.println("M - Move an item in the list");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
        System.out.println();
        viewList();
    }

    private static void openFile()
    {
        System.out.print("Enter the file name to open: ");
        String fileName = in.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            list.clear();
            String line;
            while ((line = reader.readLine()) != null)
            {
                list.add(line);
            }
            System.out.println("File loaded successfully.");
            isDirty = false;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + fileName);
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private static void saveFile()
    {
        System.out.print("Enter the file name to save: ");
        String fileName = in.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for (String item : list)
            {
                writer.write(item);
                writer.newLine();
            }
            System.out.println("File saved successfully.");
            isDirty = false;
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static void addItem()
    {
        String item = SafeInput.getString(in, "Enter the item to add: ");
        list.add(item);
        System.out.println("Item added.");
    }

    private static void deleteItem()
    {
        viewList();
        int index = SafeInput.getRangedInt(in, "Enter the item number to delete: ", 1, list.size()) - 1;
        list.remove(index);
        System.out.println("Item deleted.");
    }

    private static void insertItem()
    {
        viewList();
        int index = SafeInput.getRangedInt(in, "Enter the position to insert the item: ", 1, list.size() + 1) - 1;
        String item = SafeInput.getString(in, "Enter the item to insert: ");
        list.add(index, item);
        System.out.println("Item inserted.");
    }

    private static void viewList()
    {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private static void moveItem()
    {
        viewList();
        int fromIndex = SafeInput.getRangedInt(in, "Enter the item number to move: ", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(in, "Enter the new position for the item: ", 1, list.size()) - 1;

        if (fromIndex != toIndex)
        {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            System.out.println("Item moved.");
        }
        else
        {
            System.out.println("The item is already at the specified position.");
        }
    }

    private static void clearList()
    {
        boolean confirm = SafeInput.getYNConfirm(in, "Are you sure you want to clear the list?");
        if (confirm)
        {
            list.clear();
            System.out.println("List cleared.");
        }
        else
        {
            System.out.println("Clear list operation cancelled.");
        }
    }

    private static void promptSaveChanges() {
        boolean saveChanges = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save them?");
        if (saveChanges)
        {
            saveFile();
        }
    }
}