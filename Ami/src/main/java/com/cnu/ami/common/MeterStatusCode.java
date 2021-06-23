package com.cnu.ami.common;

import com.cnu.ami.failure.code.dao.document.LpFaultTemp;

public class MeterStatusCode {

	public static String checkCode(LpFaultTemp data, String type, int code) {

		// code
		// 1:과전류,2:정기검침,3:계절변경,4:모뎀 커버,5:터미널 커버,6:Latch ON,7:Latch OFF,8:Latch
		// ERROR,9:Sag / Swell,10:오결선,11:온도,12:DST,
		// 13:자계 감지,14:부하제한 차단,15:현재 Tariff,16:현재 Tariff,17:SR,18:정전,19:시각 변경,20:수동
		// 검침,21:DR,22:배터리 없음,23:전압 결상,24:프로그램 변경,
		// 25:과전압,26:저전압,27:비설정 LP 기록주기,28:비정기검침

		String value = null;

		if (type.equals("EA")) {

			if (code == 1) {
				if (data.getF1() == 1) {
					value = MeterCodeConst.AEType.f1;
				}
			} else if (code == 4) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.AEType.f4;
				}
			} else if (code == 5) {
				if (data.getF5() == 1) {
					value = MeterCodeConst.AEType.f5;
				}
			} else if (code == 6) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.AEType.f6;
				}
			} else if (code == 7) {
				if (data.getF7() == 1) {
					value = MeterCodeConst.AEType.f7;
				}
			} else if (code == 8) {
				if (data.getF8() == 1) {
					value = MeterCodeConst.AEType.f8;
				}
			} else if (code == 9) {
				if (data.getF9() == 1) {
					value = MeterCodeConst.AEType.f9;
				}
			} else if (code == 10) {
				if (data.getF10() == 1) {
					value = MeterCodeConst.AEType.f10;
				}
			} else if (code == 11) {
				if (data.getF11() == 1) {
					value = MeterCodeConst.AEType.f11;
				}
			} else if (code == 12) {
				if (data.getF12() == 1) {
					value = MeterCodeConst.AEType.f12;
				}
			} else if (code == 13) {
				if (data.getF13() == 1) {
					value = MeterCodeConst.AEType.f13;
				}
			} else if (code == 14) {
				if (data.getF14() == 1) {
					value = MeterCodeConst.AEType.f14;
				}
			} else if (code == 15) {
				if (data.getF15() == 1) {
					value = MeterCodeConst.AEType.f15;
				}
			} else if (code == 16) {
				if (data.getF16() == 1) {
					value = MeterCodeConst.AEType.f16;
				}
			} else if (code == 17) {
				if (data.getF17() == 1) {
					value = MeterCodeConst.AEType.f17;
				}
			} else if (code == 18) {
				if (data.getF18() == 1) {
					value = MeterCodeConst.AEType.f18;
				}
			} else if (code == 19) {
				if (data.getF19() == 1) {
					value = MeterCodeConst.AEType.f19;
				}
			} else if (code == 20) {
				if (data.getF20() == 1) {
					value = MeterCodeConst.AEType.f20;
				}
			} else if (code == 21) {
				if (data.getF21() == 1) {
					value = MeterCodeConst.AEType.f21;
				}
			} else if (code == 22) {
				if (data.getF22() == 1) {
					value = MeterCodeConst.AEType.f22;
				}
			} else if (code == 24) {
				if (data.getF24() == 1) {
					value = MeterCodeConst.AEType.f24;
				}
			}

		} else if (type.equals("G")) {

			if (code == 1) {
				if (data.getF1() == 1) {
					value = MeterCodeConst.GType.f1;
				}
			} else if (code == 4) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.GType.f4;
				}
			} else if (code == 5) {
				if (data.getF5() == 1) {
					value = MeterCodeConst.GType.f5;
				}
			} else if (code == 6) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.GType.f6;
				}
			} else if (code == 7) {
				if (data.getF7() == 1) {
					value = MeterCodeConst.GType.f7;
				}
			} else if (code == 8) {
				if (data.getF8() == 1) {
					value = MeterCodeConst.GType.f8;
				}
			} else if (code == 9) {
				if (data.getF9() == 1) {
					value = MeterCodeConst.GType.f9;
				}
			} else if (code == 10) {
				if (data.getF10() == 1) {
					value = MeterCodeConst.GType.f10;
				}
			} else if (code == 11) {
				if (data.getF11() == 1) {
					value = MeterCodeConst.GType.f11;
				}
			} else if (code == 12) {
				if (data.getF12() == 1) {
					value = MeterCodeConst.GType.f12;
				}
			} else if (code == 13) {
				if (data.getF13() == 1) {
					value = MeterCodeConst.GType.f13;
				}
			} else if (code == 14) {
				if (data.getF14() == 1) {
					value = MeterCodeConst.GType.f14;
				}
			} else if (code == 15) {
				if (data.getF15() == 1) {
					value = MeterCodeConst.GType.f15;
				}
			} else if (code == 16) {
				if (data.getF16() == 1) {
					value = MeterCodeConst.GType.f16;
				}
			} else if (code == 17) {
				if (data.getF17() == 1) {
					value = MeterCodeConst.GType.f17;
				}
			} else if (code == 18) {
				if (data.getF18() == 1) {
					value = MeterCodeConst.GType.f18;
				}
			} else if (code == 19) {
				if (data.getF19() == 1) {
					value = MeterCodeConst.GType.f19;
				}
			} else if (code == 20) {
				if (data.getF20() == 1) {
					value = MeterCodeConst.GType.f20;
				}
			} else if (code == 21) {
				if (data.getF21() == 1) {
					value = MeterCodeConst.GType.f21;
				}
			} else if (code == 22) {
				if (data.getF22() == 1) {
					value = MeterCodeConst.GType.f22;
				}
			} else if (code == 23) {
				if (data.getF23() == 1) {
					value = MeterCodeConst.GType.f23;
				}
			} else if (code == 24) {
				if (data.getF24() == 1) {
					value = MeterCodeConst.GType.f24;
				}
			}

		} else if (type.equals("AMIGO")) {

			if (code == 1) {
				if (data.getF1() == 1) {
					value = MeterCodeConst.AmigoType.f1;
				}
			} else if (code == 2) {
				if (data.getF2() == 1) {
					value = MeterCodeConst.AmigoType.f2;
				}
			} else if (code == 3) {
				if (data.getF3() == 1) {
					value = MeterCodeConst.AmigoType.f3;
				}
			} else if (code == 25) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.AmigoType.f4;
				}
			} else if (code == 5) {
				if (data.getF5() == 1) {
					value = MeterCodeConst.AmigoType.f5;
				}
			} else if (code == 6) {
				if (data.getF4() == 1) {
					value = MeterCodeConst.AmigoType.f6;
				}
			} else if (code == 7) {
				if (data.getF7() == 1) {
					value = MeterCodeConst.AmigoType.f7;
				}
			} else if (code == 8) {
				if (data.getF8() == 1) {
					value = MeterCodeConst.AmigoType.f8;
				}
			} else if (code == 9) {
				if (data.getF9() == 1) {
					value = MeterCodeConst.AmigoType.f9;
				}
			} else if (code == 10) {
				if (data.getF10() == 1) {
					value = MeterCodeConst.AmigoType.f10;
				}
			} else if (code == 11) {
				if (data.getF11() == 1) {
					value = MeterCodeConst.AmigoType.f11;
				}
			} else if (code == 26) {
				if (data.getF12() == 1) {
					value = MeterCodeConst.AmigoType.f12;
				}
			} else if (code == 13) {
				if (data.getF13() == 1) {
					value = MeterCodeConst.AmigoType.f13;
				}
			} else if (code == 14) {
				if (data.getF14() == 1) {
					value = MeterCodeConst.AmigoType.f14;
				}
			} else if (code == 15) {
				if (data.getF15() == 1) {
					value = MeterCodeConst.AmigoType.f15;
				}
			} else if (code == 16) {
				if (data.getF16() == 1) {
					value = MeterCodeConst.AmigoType.f16;
				}
			} else if (code == 27) {
				if (data.getF17() == 1) {
					value = MeterCodeConst.AmigoType.f17;
				}
			} else if (code == 18) {
				if (data.getF18() == 1) {
					value = MeterCodeConst.AmigoType.f18;
				}
			} else if (code == 19) {
				if (data.getF19() == 1) {
					value = MeterCodeConst.AmigoType.f19;
				}
			} else if (code == 28) {
				if (data.getF20() == 1) {
					value = MeterCodeConst.AmigoType.f20;
				}
			} else if (code == 22) {
				if (data.getF22() == 1) {
					value = MeterCodeConst.AmigoType.f22;
				}
			} else if (code == 24) {
				if (data.getF24() == 1) {
					value = MeterCodeConst.AmigoType.f24;
				}
			}

		} else if (type.equals("E")) {
		} else if (type.equals("S")) {
		} else {
		}

		return value;
	}

	public static String meterCheckCode(String type, long code) {

		String value = null;

		if (type.equals("EA")) {

			if (code == 0x800000) {
				value = MeterCodeConst.AEType.f1;
			} else if (code == 0x100000) {
				value = MeterCodeConst.AEType.f4;
			} else if (code == 0x080000) {
				value = MeterCodeConst.AEType.f5;
			} else if (code == 0x040000) {
				value = MeterCodeConst.AEType.f6;
			} else if (code == 0x020000) {
				value = MeterCodeConst.AEType.f7;
			} else if (code == 0x010000) {
				value = MeterCodeConst.AEType.f8;
			} else if (code == 0x008000) {
				value = MeterCodeConst.AEType.f9;
			} else if (code == 0x004000) {
				value = MeterCodeConst.AEType.f10;
			} else if (code == 0x002000) {
				value = MeterCodeConst.AEType.f11;
			} else if (code == 0x001000) {
				value = MeterCodeConst.AEType.f12;
			} else if (code == 0x000800) {
				value = MeterCodeConst.AEType.f13;
			} else if (code == 0x000400) {
				value = MeterCodeConst.AEType.f14;
			} else if (code == 0x000200) {
				value = MeterCodeConst.AEType.f15;
			} else if (code == 0x000100) {
				value = MeterCodeConst.AEType.f16;
			} else if (code == 0x000080) {
				value = MeterCodeConst.AEType.f17;
			} else if (code == 0x000040) {
				value = MeterCodeConst.AEType.f18;
			} else if (code == 0x000020) {
				value = MeterCodeConst.AEType.f19;
			} else if (code == 0x000010) {
				value = MeterCodeConst.AEType.f20;
			} else if (code == 0x000008) {
				value = MeterCodeConst.AEType.f21;
			} else if (code == 0x000004) {
				value = MeterCodeConst.AEType.f22;
			} else if (code == 0x000001) {
				value = MeterCodeConst.AEType.f24;
			}

		} else if (type.equals("G")) {

			if (code == 0x800000) {
				value = MeterCodeConst.GType.f1;
			} else if (code == 0x100000) {
				value = MeterCodeConst.GType.f4;
			} else if (code == 0x080000) {
				value = MeterCodeConst.GType.f5;
			} else if (code == 0x040000) {
				value = MeterCodeConst.GType.f6;
			} else if (code == 0x020000) {
				value = MeterCodeConst.GType.f7;
			} else if (code == 0x010000) {
				value = MeterCodeConst.GType.f8;
			} else if (code == 0x008000) {
				value = MeterCodeConst.GType.f9;
			} else if (code == 0x004000) {
				value = MeterCodeConst.GType.f10;
			} else if (code == 0x002000) {
				value = MeterCodeConst.GType.f11;
			} else if (code == 0x001000) {
				value = MeterCodeConst.GType.f12;
			} else if (code == 0x000800) {
				value = MeterCodeConst.GType.f13;
			} else if (code == 0x000400) {
				value = MeterCodeConst.GType.f14;
			} else if (code == 0x000200) {
				value = MeterCodeConst.GType.f15;
			} else if (code == 0x000100) {
				value = MeterCodeConst.GType.f16;
			} else if (code == 0x000080) {
				value = MeterCodeConst.GType.f17;
			} else if (code == 0x000040) {
				value = MeterCodeConst.GType.f18;
			} else if (code == 0x000020) {
				value = MeterCodeConst.GType.f19;
			} else if (code == 0x000010) {
				value = MeterCodeConst.GType.f20;
			} else if (code == 0x000008) {
				value = MeterCodeConst.GType.f21;
			} else if (code == 0x000004) {
				value = MeterCodeConst.GType.f22;
			} else if (code == 0x000002) {
				value = MeterCodeConst.GType.f23;
			} else if (code == 0x000001) {
				value = MeterCodeConst.GType.f24;
			}

		} else if (type.equals("AMIGO")) {

			if (code == 0x800000) {
				value = MeterCodeConst.AmigoType.f1;
			} else if (code == 0x400000) {
				value = MeterCodeConst.AmigoType.f2;
			} else if (code == 0x200000) {
				value = MeterCodeConst.AmigoType.f3;
			} else if (code == 0x100000) {
				value = MeterCodeConst.AmigoType.f4;
			} else if (code == 0x080000) {
				value = MeterCodeConst.AmigoType.f5;
			} else if (code == 0x040000) {
				value = MeterCodeConst.AmigoType.f6;
			} else if (code == 0x020000) {
				value = MeterCodeConst.AmigoType.f7;
			} else if (code == 0x010000) {
				value = MeterCodeConst.AmigoType.f8;
			} else if (code == 0x008000) {
				value = MeterCodeConst.AmigoType.f9;
			} else if (code == 0x004000) {
				value = MeterCodeConst.AmigoType.f10;
			} else if (code == 0x002000) {
				value = MeterCodeConst.AmigoType.f11;
			} else if (code == 0x001000) {
				value = MeterCodeConst.AmigoType.f12;
			} else if (code == 0x000800) {
				value = MeterCodeConst.AmigoType.f13;
			} else if (code == 0x000400) {
				value = MeterCodeConst.AmigoType.f14;
			} else if (code == 0x000200) {
				value = MeterCodeConst.AmigoType.f15;
			} else if (code == 0x000100) {
				value = MeterCodeConst.AmigoType.f16;
			} else if (code == 0x000080) {
				value = MeterCodeConst.AmigoType.f17;
			} else if (code == 0x000040) {
				value = MeterCodeConst.AmigoType.f18;
			} else if (code == 0x000020) {
				value = MeterCodeConst.AmigoType.f19;
			} else if (code == 0x000010) {
				value = MeterCodeConst.AmigoType.f20;
			} else if (code == 0x000004) {
				value = MeterCodeConst.AmigoType.f22;
			} else if (code == 0x000001) {
				value = MeterCodeConst.AmigoType.f24;
			}

		} else if (type.equals("E")) {
		} else if (type.equals("S")) {
		} else {
		}

		return value;
	}

}
