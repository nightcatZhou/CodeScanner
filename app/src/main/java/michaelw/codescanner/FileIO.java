package michaelw.codescanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
/**
 * Created by Michael.W on 6/1/2015.
 */
public class FileIO {
    public FileIO() {
    }
    public void readfile(Context c) {
        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(c.openFileInput("Item.txt")));
            data.clear();
            String s = "";
            while ((s = br.readLine()) != null) //Use BufferedReader to read line.
            {
                data.add(s.split("\t"));                                                        //Split the line to several parts.
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Writes data into the txt file
     */
    public void writefile(Context c) {
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(c.openFileOutput("Item.txt", c.MODE_PRIVATE)));
            for (int i = 0; i < data.size(); i++) {
                String[] temp = data.get(i);
                String s = temp[0];

                for (int j = 1; j < temp.length; j++) //Assemble the data.
                {
                    s += '\t' + temp[j];
                }
                s += '\n';
                bw.write(s);
            }
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Return data.
     * @return contact info
     */
    public ArrayList<String[]> getdata() {
        return data;
    }

    /**
     * Return item index with specific ID.
     * @param ID item ID
     * @return item index
     */
    public int getIndexByID(String ID) {
        for(int i=0;i<data.size();i++)
        {
            if (data.get(i)[0].equals(ID)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Return item information with specific index in array.
     * @param index index in array
     * @return item information
     */
    public String[] getInformationByIndex(int index) {
        return data.get(index);
    }

    /**
     * Add data.
     * @param temp contact data
     */
    public void adddata(String[] temp) {
        data.add(temp);
    }

    /**
     * Delete data.
     * @param i index of data to be deleted
     */
    public void deletedata(int i) {
        data.remove(i);
    }

    /**
     * Modify data.
     * @param i index of data to be modified.
     * @param temp new contact data to replace the old one
     */
    public void modifydata(int i, String[] temp) {
        for(int j=0;j<temp.length;j++)
            data.get(i)[j]=temp[j];
    }
    //Use an array list of string[] to store data.
    private ArrayList<String[]> data = new ArrayList<String[]>();
}
