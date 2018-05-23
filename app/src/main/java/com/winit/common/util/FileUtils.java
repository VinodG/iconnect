package com.winit.common.util;

import android.content.Context;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Base64;
import android.util.Log;

import com.winit.common.constant.AppConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils
{
	public interface DownloadListner {
		void onProgress(int count);
		void onComplete();
		void onError(String error);
	}

	public static void deleteDir(File file){
		if(file.exists()) {
			String[] entries = file.list();
			for (String s : entries) {
				File currentFile = new File(file.getPath(), s);
				if (currentFile.isDirectory())
					deleteDir(currentFile);
				currentFile.delete();
			}
		}
	}

	public static synchronized boolean downloadZip(Object responseDO,String location, String fileName,int readBytes, DownloadListner downloadListener){
		try {
			ZipInputStream zipInputStream = new ZipInputStream(null);
			ZipEntry zipEntry;
			File file = new File(location);
			if(!file.exists()) {
				file.mkdirs();
			}else{
				deleteDir(file);
				file.delete();
				file.mkdirs();
			}
			if(!StringUtils.isEmpty(fileName)) {
				File sqlite = new File(location, fileName);
				if (sqlite.exists())
					sqlite.delete();
			}
			byte[] buffer = new byte[1024];
			while ((zipEntry = zipInputStream.getNextEntry())!=null) {
				if(zipEntry.isDirectory()){
					File f = new File(location , zipEntry.getName());
					if(!f.isDirectory()){
						f.mkdirs();
					}
				}else{
					fileName = !StringUtils.isEmpty(fileName)?fileName:zipEntry.getName();
					FileOutputStream fout = new FileOutputStream(new File(location, fileName));
					int len;
					while ((len = zipInputStream.read(buffer)) != -1){
						readBytes += len;
//						if (downloadListener != null)
//							downloadListener.onProgress(((readBytes/responseDO.contentLength)*100));
						fout.write(buffer, 0, len);
					}
					fout.close();
					zipInputStream.closeEntry();
					fileName = "";
				}
			}
			zipInputStream.close();
			if (downloadListener != null)
				downloadListener.onComplete();
			return true;
		}
		catch(Exception e){
			Log.e("Decompress", "unzip", e);
			if (downloadListener != null)
				downloadListener.onError("");
		}
		return false;
	}

	public static String  getFileNameFromPath(String filePath){
		String fileName = null;
		try
		{
			fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return fileName;
	}

	public static String SaveInputStreamAsFile(Context ctx,String SdcardPath, String source, String fileName) {
		try {
			File myFile = new File(SdcardPath,"Themes.xml");

			myFile.createNewFile();

			FileOutputStream fOut = new FileOutputStream(myFile);

			OutputStreamWriter myOutWriter =

					new OutputStreamWriter(fOut);

			myOutWriter.append(source);

			myOutWriter.close();

			fOut.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static void inputStream2File(InputStream inputStream, String fileName,String SdcardPath) {
		try
		{
			File themeFile = new File(SdcardPath);
			if(!themeFile.exists())
			{
				new File(SdcardPath).mkdirs();
			}
			File file =new File(SdcardPath + fileName);
			if(file.exists())
			{
				file.delete();
			}

			BufferedInputStream bis = new BufferedInputStream(inputStream);
			FileOutputStream fos = new FileOutputStream(SdcardPath+fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte byt[] = new byte[1024];
			int noBytes;
			while((noBytes=bis.read(byt)) != -1)
				bos.write(byt,0,noBytes);
			bos.flush();
			bos.close();
			fos.close();
			bis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static InputStream getFileFromSDcard(String SDcardpath, String fileName){
		InputStream is = null;
		try
		{
			File myFile = new File(SDcardpath,fileName);
			if(!myFile.exists())
			{
				myFile.createNewFile();
			}
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
//				txtData.setText(aBuffer);
			is = new ByteArrayInputStream(aBuffer.getBytes());
			myReader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return is;
	}

	public static void saveObjAsFile(String fileName, Object obj){
		synchronized (obj) {
			ObjectOutputStream oos = null;
			try {
				File file = new File(AppConstants.PRIVATE_CACHE_DIR_PATH);
				if(!file.exists())
					file.mkdirs();
				File fileObj = new File(AppConstants.PRIVATE_CACHE_DIR_PATH,fileName+".ser");
				if(fileObj.exists())
					fileObj.delete();
				fileObj.createNewFile();
				oos = new ObjectOutputStream(new FileOutputStream(fileObj));
				oos.writeObject(obj);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static Object readObjAsFile(String fileName){
			ObjectInputStream ois = null;
			try {
				File fileObj = new File(AppConstants.PRIVATE_CACHE_DIR_PATH, fileName+".ser");
				if (fileObj.exists()){
					ois = new ObjectInputStream(new FileInputStream(fileObj));
					return ois.readObject();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return null;
	}

	public static void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	private static void acquireWifi(Context context, PowerManager.WakeLock mWifiLock){
		mWifiLock.acquire();
		Log.e("acquire", "DONE");
	}

	public static String encodeImage(String path){
		try {
			File file = new File(path);
			if(file.isFile()) {
				FileInputStream imageInFile = new FileInputStream(new File(path));
				byte imageData[] = new byte[(int) file.length()];
				imageInFile.read(imageData);
				return Base64.encodeToString(imageData, Base64.NO_WRAP);
			}else
				return path;
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return  "";
	}

	public static File getOutputImageFile(String folder){

		File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory()+"/NAPCO/"+folder);

		if (!captureImagesStorageDir.exists())
		{
			if (!captureImagesStorageDir.mkdirs())
			{
				Log.d("NAPCO", "Oops! Failed create ");
				return null;
			}
		}

		String timestamp = System.currentTimeMillis()+"";
		File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
				+ "CAPTURE_" + timestamp + ".jpg");


		return imageFile;
	}

	public static File getOutputAudioFile(String folder){

		File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory()+"/ALOKOZAY/"+folder);

		if (!captureImagesStorageDir.exists())
		{
			if (!captureImagesStorageDir.mkdirs())
			{
				Log.d("ALOKOZAY", "Oops! Failed create ");
				return null;
			}
		}

		String timestamp = System.currentTimeMillis()+"";
		File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
				+ "CAPTURE_" + timestamp + ".mp3");


		return imageFile;
	}

	public static File getOutputVideoFile(String folder){

		File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory()+"/ALOKOZAY/"+folder);

		if (!captureImagesStorageDir.exists())
		{
			if (!captureImagesStorageDir.mkdirs())
			{
				Log.d("ALOKOZAY", "Oops! Failed create ");
				return null;
			}
		}

		String timestamp = System.currentTimeMillis()+"";
		File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
				+ "CAPTURE_" + timestamp + ".mp4");


		return imageFile;
	}

	public static File getApkFilePath(String folder){

		File captureImagesStorageDir = new File(Environment.getExternalStorageDirectory()+"/ALOKOZAY/"+folder);

		if (!captureImagesStorageDir.exists())
		{
			if (!captureImagesStorageDir.mkdirs())
			{
				Log.d("ALOKOZAY", "Oops! Failed create ");
				return null;
			}
		}

		String timestamp = System.currentTimeMillis()+"";
		File imageFile = new File(captureImagesStorageDir.getPath() + File.separator
				+ "ALSEER_SFA_" + timestamp + ".apk");


		return imageFile;
	}

}
