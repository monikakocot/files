package com.pl.freeAccesToFile;

import java.io.*;

public class Main
{
    public static void main(String[] args)
    {
        Product [] products = new Product[3];

        products [0] = new Product();
        products [1] = new Product(29.0, "Video Kurs Java");
        products [2] = new Product(39.0, "Video Kurs C++", 2008, 11, 21);

        try
        {
            RandomAccessFile RAF = new RandomAccessFile("baza.txt", "rw");

            Product.saveToFile(products , RAF);
            RAF.seek(0); // cofam się do początku textu. A nie gdzie skończyło się zapisywanie

            Product [] towarki = Product.readFromFile(RAF);

            for (int i = 0; i < towarki.length; i++)
            {
                System.out.println(towarki[i].getPrice());
                System.out.println(towarki[i].getName());
                System.out.println(towarki[i].getReleaseDate());
                System.out.println("-----------------------");
            }

/* int n = 2;
           RAF.seek((n-1)*Towar.DLUGOSC_REKORDU);

           Towar a = new Towar();
           a.czytajDane(RAF);

           System.out.println(a);
*/

            try
            {
                Product b = new Product();

                b.readRecord(RAF, 3);

// wystąpi błd to tego poniżej nie wykona

                System.out.println(b);
                System.out.println("lala");
            }
            catch(NoRecord err)
            {
                System.out.println(err.getMessage());
            }
            RAF.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
