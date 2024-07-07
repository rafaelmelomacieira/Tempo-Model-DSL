package doublem.tempo.dsl;

import doublem.tempo.dsl.type.DelayLimitType;
import doublem.tempo.dsl.type.DelayType;

public class Delay {
	private DelayType type;
	private Integer value;
	private DelayLimitType limit;
	
	public Delay(String value, DelayType type, DelayLimitType limit) {
		this.type = type;
		this.value = Integer.parseInt(value);
		this.limit = limit;
	}
	
	public DelayType getType() {
		return type;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public DelayLimitType getLimit() {
		return limit;
	}
	
	public Integer getAbsoluteValue(){
		return this.value * type.getToSecond();
	}
	
}
