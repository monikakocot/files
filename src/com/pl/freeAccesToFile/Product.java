package com.pl.freeAccesToFile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Product {

    public static final int NAME_LENGHT = 30;
    public static final int RECORD_LENGHT = (Character.SIZE * NAME_LENGHT + Double.SIZE + 3 * Integer.SIZE)/8;
    private double price; //8 bajtow
    private String name; //NAME_LENGHT * 2 bajtów
    private Date releaseDate; //4 bajty + 4 + 4 = 12 bajtow RAZEM 80

    public Product()
    {
        this.price = 0.0;
        this.name = " ";
        this.releaseDate = new GregorianCalendar().getTime();
    }
    public Product(double price, String name)
    {
        this();
        this.price = price;
        this.name = name;
    }
    public Product(double price, String name, int rok, int m, int dz)
    {
        this(price,name);
        GregorianCalendar calendar = new GregorianCalendar(rok, m-1, dz);
        this.releaseDate = calendar.getTime();
    }

// save everything

    public static void saveToFile(Product[] towar, DataOutput outS) throws IOException
    {
        for (int i = 0; i < towar.length; i++)
            towar[i].saveData(outS);
    }

// read everything
    public static Product[] readFromFile(RandomAccessFile RAF) throws IOException
    {
        int ilRekordow = (int) (RAF.length()/Product.RECORD_LENGHT );
// Lenght of all bajts divided by Lenght of record = number of records
        Product[] products = new Product[ilRekordow];

        for (int i = 0; i < ilRekordow; i++)
        {
            products[i] = new Product();
            products[i].readData(RAF);
        }

        return products;
    }

// UPGRATE - single saving

    public void saveData(DataOutput outS) throws IOException
    {
        outS.writeDouble(this.price);

        StringBuffer stringB = new StringBuffer(Product.NAME_LENGHT );
        stringB.append(this.name);
        stringB.setLength(Product.NAME_LENGHT );

        outS.writeChars(stringB.toString());

        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.releaseDate);

        outS.writeInt(kalendarz.get(Calendar.YEAR));
        outS.writeInt((kalendarz.get(Calendar.MONTH)+1));
        outS.writeInt(kalendarz.get(Calendar.DAY_OF_MONTH));
    }

// UPGRATE - single reading

    public void readData(DataInput inS) throws IOException
    {
        this.price = inS.readDouble();

        StringBuffer tString = new StringBuffer(Product.NAME_LENGHT);

        for (int i = 0; i < Product.NAME_LENGHT; i++)
        {
            char tCh = inS.readChar();

            if (tCh != '\0')
                tString.append(tCh);
        }

        this.name = tString.toString();

        int rok = inS.readInt();
        int m = inS.readInt();
        int dz = inS.readInt();

        GregorianCalendar calendar = new GregorianCalendar(rok,m-1,dz);
        this.releaseDate = calendar.getTime();
    }
// UPGRATE - reading singe record

    public void readRecord(RandomAccessFile RAF, int n) throws IOException, NoRecord
    {
        if (n <= RAF.length()/ Product.RECORD_LENGHT)
        {
            RAF.seek((n-1)*Product.RECORD_LENGHT);
            this.readData(RAF);
        }
        else
            throw new NoRecord("There is no recors");
    }


    public void setReleaseDate(int r, int m, int dz)
    {
        GregorianCalendar calendar = new GregorianCalendar(r, m-1, dz);
        this.releaseDate = calendar.getTime();
    }
    public void setReleaseDate(Date data)
    {
        this.releaseDate = data;
    }
    @Override
    public String toString()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(this.releaseDate );
        return this.price+" zł; nazwa: "+this.name+" "+calendar.get(Calendar.YEAR)+" rok "+(calendar.get(Calendar.MONTH)+1)+" m "+calendar.get(Calendar.DAY_OF_MONTH)+" dz ";
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

