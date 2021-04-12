package com.cnu.ami.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDataVO<T> {

	private boolean result;
	private T returnData;

}
