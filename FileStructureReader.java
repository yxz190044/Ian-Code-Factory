import java.io.File;

public class FileStructureReader {
    public static void main(String[] args) {
        String directoryPath = "/Users/ianmessaoudene/Desktop/javafiles/com/mashape";
        
        File directory = new File(directoryPath);
        
        if (directory.exists() && directory.isDirectory()) {
            printDirectoryStructure(directory, 0);
        } else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }
    }
    
    private static void printDirectoryStructure(File directory, int level) {
        File[] files = directory.listFiles();
        
        for (File file : files) {
            
        String fileName = file.getName();
            if (fileName.equals(".DS_Store")) {
                continue;
            }
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            if (file.isDirectory()) {
                System.out.print("/");
            }
            System.out.println(fileName);
            
            if (file.isDirectory()) {
                printDirectoryStructure(file, level + 1);
            }
        }
    }
}
