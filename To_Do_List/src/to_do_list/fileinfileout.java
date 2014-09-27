package to_do_list;

import static java.lang.System.out;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.FileInputStream;
import java.lang.reflect.Type;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.ContextWrapper.*;
import android.content.Context;
import android.widget.Toast;
//pass the list context to this class -done
public class fileinfileout
{
	private static Context List;
	static String file_save_name = "save.sav";
	static ArrayList<todoitems> itemlist; 
	String status;
	public static void setContext(Context ctx)
	{
		List = ctx;
    }
	static void settype(ArrayList<todoitems> provided	)
	{
		itemlist = provided;
	}
	static void getstatus(String command)
	{
		if(command == "save")
		{
			file_save_name = "save.sav";
		}
		else if(command == "archive")
		{
			file_save_name = "archive.sav";
		}
	}
	
	public static void filein()
	{
		try 
		{
			FileInputStream fis =  List.openFileInput(file_save_name);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			Type listype = new TypeToken<ArrayList<todoitems>>() {}.getType();
			if(file_save_name=="save.sav")
			{
				da_items.Global = gson.fromJson(in,listype);
			}
			else
			{
				da_items.Global_archived = gson.fromJson(in,listype);
			}
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			File yourFile = new File(file_save_name);
			try {
				yourFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void saveInFile() 
	{
		try 
		{
			FileOutputStream fos = List.openFileOutput(file_save_name,Context.MODE_PRIVATE);
			OutputStreamWriter os = new OutputStreamWriter(fos);
			Gson gsonout = new Gson();
			if(da_items.Global.size()<1)
			{
				out.println("WARNING: WIPING SAVEFILE");
			}
			if(file_save_name=="save.sav")
			{
				gsonout.toJson(da_items.Global,os);
			}
			else
			{
				gsonout.toJson(da_items.Global_archived,os);
			}
			
			os.flush();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			File yourFile = new File(file_save_name);
			try {
				yourFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



}