package com.pl.serializable;

import com.pl.serializable.Product;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/*
Made with Java basic course on  Udemy
 */
public class Main {
    public static void main(String[] args) {

        Product[] product = new Product[3];

        product[0] = new Product();
        product[1] = new Product(29.0, "Video Kurs Java");
        product[2] = new Product(39.0, "Video Kurs C++", 2008, 11, 21);

        try {

            // FOR WRITE OBJECT IN FILE
            ObjectOutputStream outS = new ObjectOutputStream(new FileOutputStream("baza.txt"));
            outS.writeObject(product[1]);   // zapisuje binarnie (dziwne znaczki)
            outS.close();

            // FOR READ OBJEC FROM FILE
            ObjectInputStream inS = new ObjectInputStream(new FileInputStream("baza.txt"));


            //Towar a = (Towar) inS.readObject();
            //System.out.println(a.pobierzNazwe());

            // czytamy obiekt więc możemy go zrzutowac n Towr albo inną klasę np. dziedziczącą po Towar
            Product[] a =  (Product[]) inS.readObject();

            for (int i =0 ; i < a.length; i++)
                System.out.println(a[i].getName());
            inS.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }
    }
}