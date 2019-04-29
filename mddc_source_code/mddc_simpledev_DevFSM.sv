module simpledevFSMModel (
					input               clk, rst_n, accessType,
                    input [31:0]        address,
                    input [31:0]        data,
                    input               ready,
                    /*input [31:0]      dataIn,                                                                                                                                                                                  
                    input [2:0]         commandIn,                                                                                                                                                                               
                    output logic        busy,                                                                                                                                                                                    
                    output logic [31:0] dataOut,                                                                                                                                                                                 
                    output logic [7:0]  opermode,                                                                                                                                                                                
                    output logic [2:0]  status,*/
                    output logic [3:0]  feedback
);

	//assign p0 = WRITE%A.CHANNELENABLE.E_CHANNEL3;
	//assign p1 = (A.CHANNELENABLE.E_CHANNEL3 == 1);
	//assign p2 = WRITE%A.CHANNELENABLE.E_CHANNEL0;
	//assign p3 = (A.CHANNELENABLE.E_CHANNEL0 == 1);

	//Register: REGA
	logic [31:0] ARegAddr = 32'd0; 
	logic [31:0] AReg; 
	logic [31:0] ARegTmp;
	//Register: CHANNEL_TIME_SCALE
	logic [31:0] CTSRegAddr = 32'd4; 
	logic [31:0] CTSReg; 
	logic [31:0] CTSRegTmp;

	//State: Global_State, OrthoRegion: O1
	typedef enum                         logic [1:0] {
			SA, 
			SB
	} Global_State_O1_STATEFSM;
	Global_State_O1_STATEFSM Global_State_O1_states;
	

	always_ff @ (posedge clk or negedge rst_n)
     begin
        if (!rst_n)
          begin
		Global_State_O1_states <= SA;
          end
        else
		begin
        	if (fsmState == CHECKING)
               begin
			case(Global_State_O1_states)
			SA: begin
			if (ready && p0 && p1) begin
				Global_State_O1_states <= SB;
			end
			end
			SB: begin
			if (ready && p2 && p3) begin
				Global_State_O1_states <= SA;
			end
			end
			endcase //Global_StateO1states
               end   
        end
	end
endmodule

