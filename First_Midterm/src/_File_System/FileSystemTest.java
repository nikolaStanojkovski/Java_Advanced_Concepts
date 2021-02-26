package _File_System;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

interface IFile {
    String getFileName();
    long getFileSize();
    String getFileInfo();
    void sortBySize();
    long findLargestFile();
}

class File implements IFile {

    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public long getFileSize() {
        return this.size;
    }

    @Override
    public String getFileInfo() {
        return String.format("File name: %10s File size: %10d\n", this.name, this.size);
    }

    @Override
    public void sortBySize() {

    }

    @Override
    public long findLargestFile() {
        return this.size;
    }
}

class FileNameExistsException extends RuntimeException {
    public FileNameExistsException(String name, String folder) {
        super("There is already a file named " + name + " in the folder " + folder);
    }
}

class Folder implements IFile {

    private String name;
    private List<IFile> filesList;

    public Folder(String name) {
        this.name = name;
        filesList = new ArrayList<>();
    }

    public void addFile(IFile file) {
        for(IFile f : filesList)
            if(f.getFileName().equals(file.getFileName()))
                throw new FileNameExistsException(file.getFileName(), this.name);

        filesList.add(file);
    }

    public List<IFile> getFilesList() {
        return filesList;
    }

    @Override
    public String getFileName() {
        return this.name;
    }

    @Override
    public long getFileSize() {
        return this.filesList.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(printFolder(this, ""));
        return RecursiveToString("\t", filesList, sb);
    }

    private String printFolder(IFile f, String tab) {
        return String.format("%sFolder name: %10s Folder size: %10s\n",
                tab, f.getFileName(), f.getFileSize());
    }

    private String RecursiveToString(String tabulator, List<IFile> files, StringBuilder sb) {
        for(IFile f : files) {
            if(f instanceof Folder) {
                RecursiveToString(tabulator + "\t", ((Folder) f).getFilesList(),
                        sb.append(printFolder(f, tabulator)));
            } else {
                sb.append(tabulator + f.getFileInfo());
            }
        }
        return sb.toString();
    }

    @Override
    public void sortBySize() {
        sortFiles(this.filesList);
    }

    private void sortFiles(List<IFile> files) {
        files.sort(Comparator.comparing(IFile::getFileSize));
        for(IFile f : files) {
            if(f instanceof Folder)
                sortFiles(((Folder) f).getFilesList());
        }
    }

    @Override
    public long findLargestFile() {
        return findLargest(0, filesList);
    }

    private long findLargest(long max, List<IFile> files) {
        for(IFile f : files) {
            if(f instanceof Folder)
                max = findLargest(max, ((Folder) f).getFilesList());
            else {
                if(f.getFileSize() > max)
                    max = f.getFileSize();
            }
        }
        return max;
    }
}

class FileSystem {
    private final Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }

    public void addFile(IFile file) {
        rootDirectory.addFile(file);
    }

    public long findLargestFile() {
        return rootDirectory.findLargestFile();
    }

    public void sortBySize() {
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo();
    }
}

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());

    }
}