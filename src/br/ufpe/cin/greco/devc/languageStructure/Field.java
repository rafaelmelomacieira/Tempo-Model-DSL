package br.ufpe.cin.greco.devc.languageStructure;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.TreeMap;

import br.ufpe.cin.greco.devc.languageStructure.exception.NoRegisterFieldException;
import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.FieldType;


public class Field {
	private FieldType type;
	private HashMap<String, Field> fields = new HashMap<String, Field>();
	private TreeMap	<Integer,Field> sortedFields = new TreeMap<Integer,Field>();
	private Integer offset;
	private AccessType accessType;
	private String name;
	private Integer begin;
	private Integer end;
	protected boolean root = false;
	protected boolean leaf = true;
	private Field father;
	
	public Field getFather() {
		return father;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public HashMap<String, Field> getFields() {
		return fields;
	}
	
	public void setFields(HashMap<String, Field> fields) {
		this.leaf = false;
		this.fields = fields;
		for (String fieldName : this.fields.keySet()) {
			sortedFields.put(this.fields.get(fieldName).getBegin(), this.fields.get(fieldName));
		}
	}
	
	public boolean isRoot() {
		return root;
	}
	
	public Integer getOffset() {
		return offset;
	}
	
	private Integer resolveAddress(String value){
		if (value.matches("[0-9]+")){
			return Integer.parseInt(value,10);
		}else if (value.matches("[0][x][0-9abcdefABCDEF]+")){
			return Integer.parseInt(value.substring(2, value.length()),16);
		}else if (value.matches("[b][01]+")){
			return Integer.parseInt(value.substring(1, value.length()),2);
		}
		return null;
	}
	
	public Field(String name, String offset, AccessType type, FieldType fieldType, String start, String end) {
		this.name = name;
		if (fieldType == FieldType.RESERVED) this.name += this.hashCode();
		this.offset = resolveAddress(offset);
		this.type = fieldType;
		this.begin = resolveAddress(start);
		this.end = resolveAddress(end);
		this.accessType = type;
	}
	
	public Field(String name, String offset, AccessType type, FieldType fieldType) {
		this.name = name;
		if (fieldType == FieldType.RESERVED) this.name += this.hashCode();
		this.offset = resolveAddress(offset);
		this.type = fieldType;
		this.begin = -1;
		this.end = -1;
		this.accessType = type;
	}
	
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	public void setBegin(Integer start) {
		this.begin = start;
	}
	
	public Integer getSize(){
		if (this.begin == -1){
			return 0;
		}else{
			return this.begin - this.end + 1;
		}
	}
	
	/*public Field(String name, FieldType type, int fieldSize, int currentRegSize) {
		this.name = name;
		this.type = type;
		this.size = fieldSize;
		this.start = currentRegSize;
		this.end = this.start + this.size - 1;
	}*/
	
	public String getFieldSignature(String formatName) throws NoRegisterFieldException{
		return "GET_" + formatName.toUpperCase() + "_" + this.getName().toUpperCase();
	}
	
	public String getFieldCode() throws NoRegisterFieldException{
		double mask = (Math.pow(2, this.getSize()) - 1 ) * Math.pow(2,this.getBegin());
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("0");
		return "(reg & " + df.format(mask) + ") >> " + this.getBegin();
		//return "a";
	}

	public String getName() {
		return name;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public FieldType getType() {
		return type;
	}
	
	public void buildFields(TDCCheckerGenerator tdevcChecker){
		Field currentField; 
		for (String fields : this.getFields().keySet()) {
			currentField = this.getFields().get(fields); 
			if (currentField.getFields().isEmpty()){
				//System.out.println(currentField.getName());
			}else{
				//System.out.println("sub - " + currentField.getName());
				currentField.buildFields(tdevcChecker);
			}
		}
	}
		
	public Integer checkIntegrity(){
		Field currentField;
		Integer first = -1, last = -1, size = 0;
		if (this.begin == -1){
			for (String fieldName : this.fields.keySet()) {
				currentField = this.fields.get(fieldName);
				size += currentField.checkIntegrity();
				if (currentField.getBegin() > first || first == -1){
					first = currentField.getBegin();
				}
				if (currentField.getEnd() < last || last == -1){
					last = currentField.getEnd();
				}
				if (currentField.checkIntegrity() == -1){
					System.err.println("Field Bad Integrity - " + fieldName);
				}
			}
			if ((first-last+1) == size) {
				this.begin = first;
				this.end = last;
				return size;
			}else{
				System.err.println("Bad Integrity - " + this.getName());
				System.err.println("Begin: " + first);
				System.err.println("End:   " + last);
				System.err.println("Size:  " + size);
				return -1;
			}
		}else{
			return this.getSize();
		}
	}
	
	
	
	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setFather(Field father) {
		this.father = father;
	}

	@Override
	public String toString() {
		return "[" +  (getSize()>1?(getEnd() + ":"):"") + getBegin() + "] " + (getType()==FieldType.RESERVED?"RESERVED":getName()) + "[" + getAccessType().getAlias() + "]";
	}

	public TreeMap<Integer, Field> getSortedFields() {
		return sortedFields;
	}
	
	
}

