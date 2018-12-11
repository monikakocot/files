package com.pl.zipper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper extends JFrame
{
    public FileZipper()
    {
        this.setTitle("Zipper");
        this.setBounds(275, 300, 250, 250);
        this.setJMenuBar(menuBar);

        JMenu menuFile = menuBar.add(new JMenu("Plik"));

        Action actionAdd = new Action("Add", "Add new file to archiwum", "ctrl D", new ImageIcon("dodaj.png"));
        Action actionDelete = new Action("Delete", "Delete marked file/files from archiwum", "ctrl U", new ImageIcon("usun.png"));
        Action actionZip = new Action("Zip", "Zip", "ctrl Z");

        JMenuItem menuOpen=  menuFile.add(actionAdd);
        JMenuItem menuDelete =  menuFile.add(actionDelete);
        JMenuItem menuZip =  menuFile.add(actionZip);

        bAdd = new JButton(actionAdd);
        bDel = new JButton(actionDelete);
        bZip = new JButton(actionZip);
        JScrollPane scrollek = new JScrollPane(list);

        list.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout layout = new GroupLayout(this.getContentPane());

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(scrollek, 100, 150, Short.MAX_VALUE)
                        .addContainerGap(0, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup().addComponent(bAdd).addComponent(bDel).addComponent(bZip)

                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(scrollek, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup().addComponent(bAdd).addComponent(bDel).addGap(5, 40, Short.MAX_VALUE).addComponent(bZip))
        );

        this.getContentPane().setLayout(layout);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
    }
    private DefaultListModel modelList = new DefaultListModel()
    {
        @Override
        public void addElement(Object obj)
        {
            list.add(obj);
            super.addElement(((File)obj).getName());
        }
        @Override
        public Object get(int index)
        {
            return list.get(index);
        }

        @Override
        public Object remove(int index)
        {
            list.remove(index);
            return super.remove(index);
        }
        ArrayList list = new ArrayList();
    };
    private JList list = new JList(modelList);
    private JButton bAdd;
    private JButton bDel;
    private JButton bZip;
    private JMenuBar menuBar = new JMenuBar();
    private JFileChooser chooser = new JFileChooser();
    public static void main(String[] args)
    {
        new FileZipper().setVisible(true);
    }

    private class Action extends AbstractAction
    {
        public Action(String name, String desc, String acceleratorKey)
        {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SHORT_DESCRIPTION, desc);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));
        }
        public Action(String name, String desc, String acceleratorKey, Icon icon)
        {
            this(name,desc,acceleratorKey);
            this.putValue(Action.SMALL_ICON, icon);
        }

        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Add"))
                addFilesToArchiwum();
            else if (e.getActionCommand().equals("Delete"))
                deleteFilesFromArchiwum();
            else if (e.getActionCommand().equals("Zip"))
            createZipArchiwum();
        }

        private void addFilesToArchiwum()
        {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setMultiSelectionEnabled(true);

            int tmp = chooser.showDialog(rootPane, "Add to archiwum");

            if (tmp == JFileChooser.APPROVE_OPTION)
            {
                File[] paths = chooser.getSelectedFiles();

                for (int i = 0; i < paths.length; i++)
                    if(!isFileReapeted(paths[i].getPath()))
                        modelList.addElement(paths[i]);

            }
        }
        private boolean isFileReapeted(String testFile)
        {
            for (int i = 0; i < modelList.getSize(); i++)
                if (((File)modelList.get(i)).getPath().equals(testFile))
                    return true;

            return false;
        }
        private void deleteFilesFromArchiwum()
        {
            int[] tmp = list.getSelectedIndices();

            for (int i = 0; i < tmp.length; i++)
                modelList.remove(tmp[i]-i);
        }


        private void createZipArchiwum()
        {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
           chooser.setSelectedFile(new File(System.getProperty("user.dir")+File.separator+"myname.zip"));
            int tmp = chooser.showDialog(rootPane, "Make Zip");

            if (tmp == JFileChooser.APPROVE_OPTION)
            {
                byte tmpData[] = new byte[BUFFOR];
                try
                {
                    ZipOutputStream zOutS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile()),
                            BUFFOR));

                    for (int i = 0; i < modelList.getSize(); i++)
                    {
                        if (!((File)modelList.get(i)).isDirectory())
                            zip(zOutS, (File)modelList.get(i), tmpData, ((File)modelList.get(i)).getPath());
                        else
                        {
                            outputPaths((File)modelList.get(i));

                            for (int j = 0; j < pathsList.size(); j++)
                                zip(zOutS, (File)pathsList.get(j), tmpData, ((File)modelList.get(i)).getPath());

                            pathsList.removeAll(pathsList);
                        }

                    }

                    zOutS.close();
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        private void zip (ZipOutputStream zOutS, File filePath, byte[] tmpData, String basePath) throws IOException
        {
            BufferedInputStream inS = new BufferedInputStream(new FileInputStream(filePath), BUFFOR);

            zOutS.putNextEntry(new ZipEntry(filePath.getPath().substring(basePath.lastIndexOf(File.separator)+1)));

            int counter;
            while ((counter = inS.read(tmpData, 0, BUFFOR)) != -1)
                zOutS.write(tmpData, 0, counter);


            zOutS.closeEntry();

            inS.close();
        }
        public static final int BUFFOR = 1024;

        private void outputPaths(File pathName)
        {
            String[] namesOfFilesAndDirectories = pathName.list();

            for (int i = 0; i < namesOfFilesAndDirectories.length; i++)
            {
                File p = new File(pathName.getPath(), namesOfFilesAndDirectories[i]);

                if (p.isFile())
                    pathsList.add(p);

                if (p.isDirectory())
                    outputPaths((new File(p.getPath())));

            }
        }

        ArrayList pathsList = new ArrayList();
    }

}
