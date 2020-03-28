/*
 * Copyright (C) 2013 km innozol IT solutions Pvt Ltd <http://innozol.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rishi.family;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class SortListService {
	
	private static final String TAG = "SortListService";
	private static Context mContext;
	private static ArrayList<Dates> mDooots;
	private static SortCallback mCallback;
	
	public static void sortList(Context context,ArrayList<Dates> dooots, SortCallback callback){
		mContext = context;
		mDooots = dooots;
		mCallback = callback;
		new SortAsynchronous().execute();
	}
	
	private static class SortAsynchronous extends AsyncTask<Void, Void, ArrayList<Dates>>{
		@Override
		protected ArrayList<Dates> doInBackground(Void... params) {
			Log.d(TAG, "doInBackGround");
			SortingWishList list = new SortingWishList();
			list.setList(mDooots);
			list.sort();
			//SortedListFile.saveToFile(mContext, list);
			return list.getSortedList();
		}
		
		@Override
		protected void onPostExecute(ArrayList<Dates> result) {
			super.onPostExecute(result);
			mCallback.onSort(result);
		}
	}
	
	public interface SortCallback{
		public void onSort(ArrayList<Dates> dooots);
	}
	
}
