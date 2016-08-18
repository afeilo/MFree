/*
 * Copyright (C) 2014 Saravan Pantham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiefei.openmusicplayer.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Singleton class that provides access to common objects
 * and methods used in the application.
 *
 * @author Saravan Pantham
 */
public class Common{

	/**
	 * Used to downsample a bitmap that's been downloaded from the internet.
	 */
	public Bitmap getDownsampledBitmap(Context ctx, URL url, int targetWidth, int targetHeight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options outDimens = getBitmapDimensions(url);

			int sampleSize = calculateSampleSize(outDimens.outWidth, outDimens.outHeight, targetWidth, targetHeight);

			bitmap = downsampleBitmap(url, sampleSize);

		} catch (Exception e) {
			//handle the exception(s)
		}

		return bitmap;
	}

	/**
	 * Retrieves the image dimensions of the input file.
	 *
	 * @param url Url of the input file.
	 * @return A BitmapFactory.Options object with the output image dimensions.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public BitmapFactory.Options getBitmapDimensions(URL url) throws FileNotFoundException, IOException {
		BitmapFactory.Options outDimens = new BitmapFactory.Options();
		outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)

		InputStream is = url.openStream();
		// if Options requested only the size will be returned
		BitmapFactory.decodeStream(is, null, outDimens);
		is.close();

		return outDimens;
	}

//	/**
//	 * Resamples a resource image to avoid OOM errors.
//	 *
//	 * @param resID     Resource ID of the image to be downsampled.
//	 * @param reqWidth  Width of output image.
//	 * @param reqHeight Height of output image.
//	 * @return A bitmap of the resampled image.
//	 */
//	public Bitmap decodeSampledBitmapFromResource(int resID, int reqWidth, int reqHeight) {
//
//		final BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//		options.inJustDecodeBounds = false;
//		options.inPurgeable = true;
//
//		return BitmapFactory.decodeResource(mContext.getResources(), resID, options);
//	}

	/**
	 * Resamples the specified input image file to avoid OOM errors.
	 *
	 * @param inputFile Input file to be downsampled
	 * @param reqWidth  Width of the output file.
	 * @param reqHeight Height of the output file.
	 * @return The downsampled bitmap.
	 */
	public Bitmap decodeSampledBitmapFromFile(File inputFile, int reqWidth, int reqHeight) {

		InputStream is = null;
		try {

			try {
				is = new FileInputStream(inputFile);
			} catch (Exception e) {
				//Return a null bitmap if there's an error reading the file.
				return null;
			}

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);

			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			options.inJustDecodeBounds = false;
			options.inPurgeable = true;

			try {
				is = new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				//Return a null bitmap if there's an error reading the file.
				return null;
			}

			return BitmapFactory.decodeStream(is, null, options);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Calculates the sample size for the resampling process.
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return The sample size.
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	/**
	 * Calculates the sample size for the resampling process.
	 *
	 * @return The sample size.
	 */
	public int calculateSampleSize(int width, int height, int targetWidth, int targetHeight) {
		float bitmapWidth = width;
		float bitmapHeight = height;

		int bitmapResolution = (int) (bitmapWidth * bitmapHeight);
		int targetResolution = targetWidth * targetHeight;

		int sampleSize = 1;

		if (targetResolution == 0) {
			return sampleSize;
		}

		for (int i = 1; (bitmapResolution / i) > targetResolution; i *= 2) {
			sampleSize = i;
		}

		return sampleSize;
	}

	/**
	 * @param url
	 * @param sampleSize
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Bitmap downsampleBitmap(URL url, int sampleSize) throws FileNotFoundException, IOException {
		Bitmap resizedBitmap;
		BitmapFactory.Options outBitmap = new BitmapFactory.Options();
		outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
		outBitmap.inSampleSize = sampleSize;

		InputStream is = url.openStream();
		resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
		is.close();

		return resizedBitmap;
	}

	/*
     * Returns the status bar height for the current layout configuration.
     */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}

		return result;
	}

	/*
     * Returns the navigation bar height for the current layout configuration.
     */
	public static int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			return resources.getDimensionPixelSize(resourceId);
		}

		return 0;
	}

	/**
	 * Returns the view container for the ActionBar.
	 *
	 * @return
	 */
	public static View getActionBarView(Activity activity) {
		Window window = activity.getWindow();
		View view = window.getDecorView();
		int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");

		return view.findViewById(resId);
	}

}