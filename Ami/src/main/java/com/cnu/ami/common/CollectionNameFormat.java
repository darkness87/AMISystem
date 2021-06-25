package com.cnu.ami.common;

public class CollectionNameFormat {

	public String formatYear(int gseq, String year) {

		String collectionName = "CASS" + "_" + gseq + "_" + year + "_" + "RAW" + "_" + "LP";

		return collectionName;
	}

	public String formatDay(int gseq, String day) {

		String collectionName = "CASS" + "_" + gseq + "_" + day.substring(0, 4) + "_" + "RAW" + "_" + "LP";

		return collectionName;
	}

	public String formatDcu(String day) {

		String collectionName = "CASS" + "_" + day.substring(0, 4) + "_" + "DCU" + "_" + "LP";

		return collectionName;
	}

	public String formatMapp() {

		String collectionName = "MAPP" + "_" + "HISTORY";

		return collectionName;
	}

	public String formatFault() {

		String collectionName = "CASS" + "_" + "LP" + "_" + "FAULT";

		return collectionName;
	}

}
