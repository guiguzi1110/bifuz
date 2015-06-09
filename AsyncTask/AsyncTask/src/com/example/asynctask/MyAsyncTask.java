package com.example.asynctask;
import java.io.File;
import org.acra.*;
import org.acra.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
public class MyAsyncTask extends AsyncTask<String, Void, String> {

	private Context context;
	private PrintWriter printWriter;

	public MyAsyncTask(Context context) {
        this.context = context;
    }
	
	
	@Override
	
	protected String doInBackground(String... params) {
		String type;
		
		boolean hasPermission =context.getPackageManager().checkPermission(
			    Manifest.permission.READ_LOGS, context.getPackageName())
			        == PackageManager.PERMISSION_GRANTED;
		
//		new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
//			    @Override
//			    public void onAppNotResponding(ANRError error) {
//			        // Handle the error. For example, log it to HockeyApp:
//			        Log.d("ERROR", error.getMessage());
//			    }
//			}).start();
//		
		
		
//		Toast.makeText(context, "Permission is" + String.valueOf(hasPermission) , Toast.LENGTH_SHORT).show();
		
		
		
		if (params.length>0){
			type=params[0];
			Intent i=new Intent();
			switch (type) {
			  case "broadcast":
				   if (params.length==4){
					  	i.setComponent(new ComponentName(params[1], params[2]));
					  	final String command = params[3];
					  	final Intent myint=i;
					  	Thread thread = new Thread() {
					  	    @Override
					  	    public void run() {
					  	    	try {
					  	    		Log.d("BIFUZ_BROADCAST ",command + "\n " );
									context.sendBroadcast(myint);
								 }
								catch(Exception e) {
								    Log.e("Fuzz exception:", e.toString());
								}
								catch(Error e2) {
								    Log.e("Fuzz error:", e2.toString());
								}
					  	    }
					  	};
					  	thread.start();
						try {
							thread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return type+ "_" + params[1]  ;
				   }
				  return "Bad Args";
			  case "intent":
				  if (params.length==11){
					  	i.setComponent(new ComponentName(params[1], params[2]));
					  	i.setAction(params[3]);
							i.addCategory(params[4]);
							int f=Integer.parseInt(params[5].substring(3),16);
							i.addFlags(f);
							i.setData(Uri.parse(params[6]));
							String extra_type = params[7];
							if (extra_type.equals("boolean")){
								i.putExtra(params[8], Boolean.parseBoolean(params[9]));
							}
							else {
								i.putExtra(params[8], Integer.parseInt(params[9]));
							}
							final String command = params[10];
							final Intent myint=i;
							Thread thread = new Thread() {
						  	    @Override
						  	    public void run() {
						  	    	try {
						  	    		Log.d("BIFUZ_INTENT ",command + "\n " );
						  	    		context.startActivity(myint);
									 }
									catch(Exception e) {
									    Log.e("Fuzz exception:", e.toString());									    
									}
									catch(Error e2) {
									    Log.e("Fuzz error:", e2.toString());									   
									}
						  	    }
							};
							thread.start();
							try {
								thread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						return  type+ "_" + params[1] ;
				   }
				  return "Bad Args";
			  default:
				  return "Bad Args";
			}
		
				
			}
			else return "Bad Args";
		
		
	}
	
	@Override
    protected void onPostExecute(String result) {
		String filename;
		if (result.contains("Error"))
			filename="logcat_testError" +".txt";
		else filename="logcat_"+result +".txt";
		
//		filename="logcat_MyTest2.txt";
		File outputFile = new File(Environment.getExternalStorageDirectory(),filename);
		String filePath = outputFile.getAbsolutePath();
		
		
		
//		
//		try {
//		    outputFile.createNewFile(); 
//		    String cmd = "shell pm grant org.test.bifuz android.permission.READ_LOGS";
//		    Runtime.getRuntime().exec(cmd);
//		} catch (IOException e) {
//		    // TODO Auto-generated catch block
//		    e.printStackTrace();
//		}
//		
		
		try {
		    outputFile.createNewFile(); 
		    String cmd = "logcat -d -f "+outputFile.getAbsolutePath();
		    Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		try {
//			Process process1 = Runtime.getRuntime().exec("logcat -c");
//			Process process = Runtime.getRuntime().exec("logcat -d ");
//		      BufferedReader bufferedReader = new BufferedReader(
//		      new InputStreamReader(process.getInputStream()));
//		                       
//		      StringBuilder log=new StringBuilder();
//		      String line;
//		      while ((line = bufferedReader.readLine()) != null) {
//		        log.append(line);
//		      }
//		      
//		      Toast.makeText(context, "Fuzz " + log.toString(), Toast.LENGTH_SHORT).show();
//		      printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
//		      printWriter.append(log.toString());
////		      printWriter.println(log.toString());
//		      
//		      printWriter.close();
		} catch (Exception e) {
					 System.out.println("Exception " + e);
			}

		
    }

}
