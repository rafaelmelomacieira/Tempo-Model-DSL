module alterauartFSMModel (
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

	//assign p0 = READ%status.RRDY;
	//assign p1 = (status.RRDY == 1);
	//assign p2 = READ%rxdata;
	//assign p3 = READ%status.TRDY;
	//assign p4 = (status.TRDY == 1);
	//assign p5 = WRITE%txdata;

	//Register: RXReg
	logic [31:0] rxdataRegAddr = 32'd0; 
	logic [31:0] rxdataReg; 
	logic [31:0] rxdataRegTmp;
	//Register: TXReg
	logic [31:0] txdataRegAddr = 32'd4; 
	logic [31:0] txdataReg; 
	logic [31:0] txdataRegTmp;
	//Register: DivisorReg
	logic [31:0] divisorRegAddr = 32'd16; 
	logic [31:0] divisorReg; 
	logic [31:0] divisorRegTmp;
	//Register: ControlReg
	logic [31:0] controlRegAddr = 32'd12; 
	logic [31:0] controlReg; 
	logic [31:0] controlRegTmp;
	//Register: StatusReg
	logic [31:0] statusRegAddr = 32'd8; 
	logic [31:0] statusReg; 
	logic [31:0] statusRegTmp;

	//State: Global_State, OrthoRegion: tx
	typedef enum                         logic [1:0] {
			TX_IDLE, 
			TX_PRONTO
	} Global_State_tx_STATEFSM;
	Global_State_tx_STATEFSM Global_State_tx_states;
	
	//State: Global_State, OrthoRegion: rx
	typedef enum                         logic [1:0] {
			RX_PRONTO, 
			RX_IDLE
	} Global_State_rx_STATEFSM;
	Global_State_rx_STATEFSM Global_State_rx_states;
	

	always_ff @ (posedge clk or negedge rst_n)
     begin
        if (!rst_n)
          begin
		Global_State_tx_states <= TX_IDLE;
		Global_State_rx_states <= RX_IDLE;
          end
        else
		begin
        	if (fsmState == CHECKING)
               begin
			case(Global_State_tx_states)
			TX_IDLE: begin
			if (ready && p3 && p4) begin
				Global_State_tx_states <= TX_PRONTO;
			end
			end
			TX_PRONTO: begin
			if (ready && p5) begin
				Global_State_tx_states <= TX_IDLE;
			end
			end
			endcase //Global_Statetxstates
			case(Global_State_rx_states)
			RX_PRONTO: begin
			if (ready && p2) begin
				Global_State_rx_states <= RX_IDLE;
			end
			end
			RX_IDLE: begin
			if (ready && p0 && p1) begin
				Global_State_rx_states <= RX_PRONTO;
			end
			end
			endcase //Global_Staterxstates
               end   
        end
	end
endmodule

