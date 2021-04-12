package com.cnu.ami.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Result Data return
 * 
 * @author sookwon
 */
@Getter
@Setter
public class ResultDataVO<T> {

	private boolean result;
	private T returnData;

}
