package com.cnu.ami.common;

public class CollectionNameFormat {

	public String formatMonth(int gseq, String month) {

		String collectionName = "CASS" + "_" + gseq + "_" + month + "_" + "RAW" + "_" + "LP";

		return collectionName;
	}

	public String formatDay(int gseq, String day) {

		String collectionName = "CASS" + "_" + gseq + "_" + day.substring(0, 4) + "_" + "RAW" + "_" + "LP";

		return collectionName;
	}

}
