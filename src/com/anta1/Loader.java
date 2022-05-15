package com.anta1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
    public int loaderProduct(ArrayList<Product> list, String path) throws FileNotFoundException {
        String sline;
        String[] strings;
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            sline = scanner.nextLine();
            strings = sline.split(" ");
//            System.out.println(strings[0] +", "+strings[1]+", "+Double.parseDouble(strings[1]));
            list.add(new Product(strings[0], Float.parseFloat(strings[1])));
        }
        scanner.close();
        return 0;
    }
}
