package com.cnu.ami.common;

/**
 * 시간 값 체크 코드
 * @author sookwon
 *
 */
public class TimeCode {

	public static String checkTime(int idx) throws Exception {

		String hour = "00";
		String time = "00";

		if (idx == 0 || idx == 4 || idx == 8 || idx == 12 || idx == 16 || idx == 20 || idx == 24 || idx == 28
				|| idx == 32 || idx == 36 || idx == 40 || idx == 44 || idx == 48 || idx == 52 || idx == 56 || idx == 60
				|| idx == 64 || idx == 68 || idx == 72 || idx == 76 || idx == 80 || idx == 84 || idx == 88 || idx == 92
				|| idx == 96) {
			time = "00";
		} else if (idx == 1 || idx == 5 || idx == 9 || idx == 13 || idx == 17 || idx == 21 || idx == 25 || idx == 29
				|| idx == 33 || idx == 37 || idx == 41 || idx == 45 || idx == 49 || idx == 53 || idx == 57 || idx == 61
				|| idx == 65 || idx == 69 || idx == 73 || idx == 77 || idx == 81 || idx == 85 || idx == 89
				|| idx == 93) {
			time = "15";
		} else if (idx == 2 || idx == 6 || idx == 10 || idx == 14 || idx == 18 || idx == 22 || idx == 26 || idx == 30
				|| idx == 34 || idx == 38 || idx == 42 || idx == 46 || idx == 50 || idx == 54 || idx == 58 || idx == 62
				|| idx == 66 || idx == 70 || idx == 74 || idx == 78 || idx == 82 || idx == 86 || idx == 90
				|| idx == 94) {
			time = "30";
		} else if (idx == 3 || idx == 7 || idx == 11 || idx == 15 || idx == 19 || idx == 23 || idx == 27 || idx == 31
				|| idx == 35 || idx == 39 || idx == 43 || idx == 47 || idx == 51 || idx == 55 || idx == 59 || idx == 63
				|| idx == 67 || idx == 71 || idx == 75 || idx == 79 || idx == 83 || idx == 87 || idx == 91
				|| idx == 95) {
			time = "45";
		}

		if (idx >= 0 && idx <= 3) {
			hour = "00";
		} else if (idx >= 4 && idx <= 7) {
			hour = "01";
		} else if (idx >= 8 && idx <= 11) {
			hour = "02";
		} else if (idx >= 12 && idx <= 15) {
			hour = "03";
		} else if (idx >= 16 && idx <= 19) {
			hour = "04";
		} else if (idx >= 20 && idx <= 23) {
			hour = "05";
		} else if (idx >= 24 && idx <= 27) {
			hour = "06";
		} else if (idx >= 28 && idx <= 31) {
			hour = "07";
		} else if (idx >= 32 && idx <= 35) {
			hour = "08";
		} else if (idx >= 36 && idx <= 39) {
			hour = "09";
		} else if (idx >= 40 && idx <= 43) {
			hour = "10";
		} else if (idx >= 44 && idx <= 47) {
			hour = "11";
		} else if (idx >= 48 && idx <= 51) {
			hour = "12";
		} else if (idx >= 52 && idx <= 55) {
			hour = "13";
		} else if (idx >= 56 && idx <= 59) {
			hour = "14";
		} else if (idx >= 60 && idx <= 63) {
			hour = "15";
		} else if (idx >= 64 && idx <= 67) {
			hour = "16";
		} else if (idx >= 68 && idx <= 71) {
			hour = "17";
		} else if (idx >= 72 && idx <= 75) {
			hour = "18";
		} else if (idx >= 76 && idx <= 79) {
			hour = "19";
		} else if (idx >= 80 && idx <= 83) {
			hour = "20";
		} else if (idx >= 84 && idx <= 87) {
			hour = "21";
		} else if (idx >= 88 && idx <= 91) {
			hour = "22";
		} else if (idx >= 92 && idx <= 95) {
			hour = "23";
		}

		return hour + ":" + time;
	}
}
