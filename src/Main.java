import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        File pathFile = new File("D://Games/GunRunner/savegames");
        pathFile.mkdirs();

        String path1 = "D://Games/GunRunner/savegames/save1.dat";
        String path2 = "D://Games/GunRunner/savegames/save2.dat";
        String path3 = "D://Games/GunRunner/savegames/save3.dat";

        GameProgress gameProgress1 = new GameProgress(80, 20, 3, 20.5);
        GameProgress gameProgress2 = new GameProgress(90, 50, 4, 50.5);
        GameProgress gameProgress3 = new GameProgress(100, 80, 5, 80);

        saveGame(path1, gameProgress1);
        saveGame(path2, gameProgress2);
        saveGame(path3, gameProgress3);

        String zipPath = "D://Games/GunRunner/savegames/zip.zip";
        ArrayList<String> list = new ArrayList<>();
        list.add(path1);
        list.add(path2);
        list.add(path3);

        zipFiles(zipPath, list);
        deleteFiles(list);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, ArrayList<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipPath))) {
            for (String path : list) {
                File file = new File(path);
                try {
                    FileInputStream fis = new FileInputStream(path);
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    fis.close();
                    zout.write(buffer);
                    zout.closeEntry();
                 } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFiles(ArrayList<String> list) {
        for (String path : list) {
            File file = new File(path);
            file.delete();
        }
    }
}