package com.pl.serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Product implements Serializable
{
    public static final int NAME_LENGHT = 30;
    public static final int RECORD_LENGHT = (Character.SIZE * NAME_LENGHT + Double.SIZE + 3 * Integer.SIZE)/8;
    private double price; //8 bajtow
    private String name; //DLUGOSC_NAZWY * 2 bajtów
    private Date releaseDate; //4 bajty + 4 + 4 = 12 bajtow RAZEM 80

    private transient String password = "secret"; //ulotny it will be not pl.  WE will get null on output

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
    public Product(double price, String nazwa, int rok, int m, int dz)
    {
        this(price, nazwa);
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        this.releaseDate = kalendarz.getTime();
    }


//checking password in readObject method

    private void readObject(ObjectInputStream inS) throws IOException, ClassNotFoundException
    {
        inS.defaultReadObject();
        if (password != null)
            if (!password.equals("secret"))
                throw new IOException("WRONG DATA");

        System.out.println("I am from new readobject method");


    }
    private void writeObject(ObjectOutputStream outS) throws IOException
    {
        outS.defaultWriteObject();
    }


// GETTERY SETTERY TOSTRING

    public void setReleaseDate(int r, int m, int dz)
    {
        GregorianCalendar kalendarz = new GregorianCalendar(r, m-1, dz);
        this.releaseDate = kalendarz.getTime();
    }
    public void setReleaseDate(Date data)
    {
        this.releaseDate = data;
    }

    @Override
    public String toString()
    {
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.releaseDate);
        return this.price+" zł; nazwa: "+this.name+" "+kalendarz.get(Calendar.YEAR)+" rok "+(kalendarz.get(Calendar.MONTH)+1)+" m "+kalendarz.get(Calendar.DAY_OF_MONTH)+" dz ";
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

    public Date getReleaseDate() {
        return releaseDate;
    }
}

