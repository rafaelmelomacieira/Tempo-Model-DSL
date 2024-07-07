package doublem.tempo.dsl.type;

public enum AccessType {
	READ,
	WRITE,
	READ_WRITE,
	VOID;
	
	public String getAlias(){
		switch (this) {
		case READ:
			return "RO";
		case WRITE:
			return "WO";
		/*case BULKREAD:
			return "Bulk Read";
		case BULKWRITE:
			return "Bulk Write";
		case NNWRITEUNTIL:
			return "Not Null Write until the Number of paramenters";
		case NNREADUNTIL:
			return "Not Null Read until the Number of paramenters";
		case WRITEUNTIL:
			return "Nullable Write until the Number of paramenters";
		case READUNTIL:
			return "Nullable Read until the Number of paramenters";*/
		case READ_WRITE:
			return "RW";
		case VOID:
			return "--";
		default:
			return "--";
		}
	}
	
	public String getName(){
		switch (this) {
		case READ:
			return "Read only";
		case WRITE:
			return "Write only";
		/*case BULKREAD:
			return "Bulk Read";
		case BULKWRITE:
			return "Bulk Write";
		case NNWRITEUNTIL:
			return "Not Null Write until the Number of paramenters";
		case NNREADUNTIL:
			return "Not Null Read until the Number of paramenters";
		case WRITEUNTIL:
			return "Nullable Write until the Number of paramenters";
		case READUNTIL:
			return "Nullable Read until the Number of paramenters";*/
		case READ_WRITE:
			return "Read/Write";
		case VOID:
			return "Void";
		default:
			return " - ";
		}
	}
}
