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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A Serializable class which sort list of dates according to Calendar object.
 * To sort a list of items use sort() method.
 * @author Ashwin
 */
public class SortingWishList implements Serializable{
	
	/**
	 * serialVersionUID = 14L
	 */
	private static final long serialVersionUID = 14L;


	public ArrayList<Dates> dooots;
	
	
	/**
	 * An AutomicBoolean value used to check the list sorted. 
	 */
	private AtomicBoolean isSorted = new AtomicBoolean(false);
	
	/**
	 * Pass Unsorted list
	 * @param unSorted List of unsorted BirthDayDoot
	 */
	public void setList(ArrayList<Dates> unSorted){
		this.dooots = unSorted;
		this.isSorted.set(false);
	}

	public void sort(){
		ArrayList<Dates> temp = calendarCalculations();
		Collections.sort(temp, new Comparator<Dates>() {
			@Override
			public int compare(Dates lhs, Dates rhs) {
				lhs.getDob().clear(Calendar.MILLISECOND);
				rhs.getDob().clear(Calendar.MILLISECOND);
				return (lhs.getDob().before(rhs.getDob()) ? -1 : 
					(lhs.getDob().compareTo(rhs.getDob())== 0 ? 0 : 1));
			}
		});
		isSorted.set(true);
		setSortedList(temp);
	}
	
	private ArrayList<Dates> calendarCalculations(){
		Calendar calendar = Calendar.getInstance();
		ArrayList<Dates> temp = new ArrayList<Dates>();
		for(Dates dooot : dooots){
			Calendar bCal = dooot.getDob();
			if(CalendarUtils.isInsaneDate(bCal)){
				bCal.set(Calendar.YEAR, CalendarUtils.getNextLeap(CalendarUtils.getCurrentYear()));
				dooot.setDob(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			else{
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear());
				dooot.setDob(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			if(CalendarUtils.isToday(bCal)){
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear());
				dooot.setDob(bCal);
				dooot.setBDay("Today");
			}
			else if(bCal.before(calendar)){
				bCal.set(Calendar.YEAR, CalendarUtils.getCurrentYear() + 1);
				dooot.setDob(bCal);
				dooot.setBDay(CalendarUtils.styleDate(bCal));
			}
			else if(CalendarUtils.isComingWeek(bCal)){
				dooot.setBDay(CalendarUtils.getWeekDay(bCal));
			} 
			if(CalendarUtils.isTomorrow(bCal)){
				dooot.setBDay("Tomorrow");
			}
			temp.add(dooot);
		}
		return temp;
	}
	
	
	public void sortByName(){
		ArrayList<Dates> temp = calendarCalculations();
		Collections.sort(temp, new Comparator<Dates>() {
			@Override
			public int compare(Dates lhs, Dates rhs) {
				return (lhs.getDob().before(rhs.getDob()) ? -1 : 
					(lhs.getDob().compareTo(rhs.getDob())== 0 ? 0 : 1));
			}
		});
	}
	
	/**
	 * Get the list of today Event list.
	 * @return today event list of Dates. Returns empty list if not sorted 
	 */
	public ArrayList<Dates> todayList(){
		ArrayList<Dates> todayDooots = new ArrayList<Dates>();
		if(isSorted.get()){
			for(Dates dooot:dooots){
				if(CalendarUtils.isToday(dooot.getDob())){
					todayDooots.add(dooot);
				}
			}
		}
		else{
			sort();
		}
		return todayDooots;
	}
	
	private void setSortedList(ArrayList<Dates> doots){
		this.dooots = doots;
	}
	
	
	/**
	 * Get the list of tomorrow Event list.
	 * @return tomorrow event list of Dates. Returns empty list if not sorted 
	 */
	public ArrayList<Dates> tomorrowList(){
		ArrayList<Dates> todayDooots = new ArrayList<Dates>();
		if(isSorted.get()){
			for(Dates dooot:dooots){
				if(CalendarUtils.isTomorrow(dooot.getDob())){
					todayDooots.add(dooot);
				}
			}
		}
		else{
			sort();
		}
		return todayDooots;
	}
	
	/**
	 * List after sorting Calendar wise.
	 * @return Sorted list
	 */
	public ArrayList<Dates> getSortedList(){
		if(isSorted.get()){
			return dooots;
		}else{
			sort();
			return dooots;
		}
	}
	
	/**
	 * @return true if list is already sorted.
	 */
	public boolean isListSorted(){
		return isSorted.get();
	}
	
}
