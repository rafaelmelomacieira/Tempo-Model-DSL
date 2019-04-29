module sdcardFSMModel (
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

	//assign p0 = READ%NSR.LINKST;
	//assign p1 = (NSR.LINKST == 1);
	//assign p2 = (NSR.LINKST == 0);
	//assign p3 = (ISR.IOMODE == 0);
	//assign p4 = (ISR.IOMODE == 1);
	//assign p5 = WRITE%GPR.PHYPD;
	//assign p6 = (GPR.PHYPD == 0);
	//assign p7 = WRITE%BMCR.POWER_DN;
	//assign p8 = (BMCR.POWER_DN == 0);
	//assign p9 = WRITE%BMCR.SPEEDSEL;
	//assign p30 = READ%MRCMD;
	//assign p10 = (BMCR.SPEEDSEL == 0);
	//assign p32 = (MRCMD == 0x00);
	//assign p31 = (MRCMD == 0x01);
	//assign p12 = (GPR.PHYPD == 1);
	//assign p34 = (MRCMDX != 0x00);
	//assign p11 = (BMCR.POWER_DN == 1);
	//assign p33 = READ%MRCMDX;
	//assign p14 = (NCR.RST == 1);
	//assign p36 = (MRCMD == RXNOERROR);
	//assign p13 = WRITE%NCR.RST;
	//assign p35 = (MRCMDX != 0x01);
	//assign p16 = WRITE%MDRALR;
	//assign p38 = (MRCMDX == 0x01);
	//assign p15 = WRITE%MWCMDR;
	//assign p37 = (pkgcounter < rxlen);
	//assign p18 = WRITE%TCR.TXREQ;
	//assign p17 = WRITE%MDRAHR;
	//assign p39 = (pkgcounter == rxlen);
	//assign p19 = (TCR.TXREQ == 1);
	//assign p41 = (MRCMD != 0x00);
	//assign p40 = (MRCMDX == 0x00);
	//assign p21 = WRITE%CHIPR;
	//assign p20 = (TCR.TXREQ == 0);
	//assign p23 = (CHIPR == 32);
	//assign p22 = (CHIPR == RXNOERROR);
	//assign p25 = (NSR.TX1END == 1);
	//assign p24 = READ%NSR.TX1END;
	//assign p27 = (NSR.TX2END == 1);
	//assign p26 = READ%NSR.TX2END;
	//assign p29 = (NSR == (t1 - 1));
	//assign p28 = READ%NSR;

	//Register: wakeUpControlReg
	logic [31:0] WCRRegAddr = 32'd15; 
	logic [31:0] WCRReg; 
	logic [31:0] WCRRegTmp;
	//Register: generalPurposeReg
	logic [31:0] GPRRegAddr = 32'd31; 
	logic [31:0] GPRReg; 
	logic [31:0] GPRRegTmp;
	//Register: transmitCheckSumControlReg
	logic [31:0] TCSCRRegAddr = 32'd49; 
	logic [31:0] TCSCRReg; 
	logic [31:0] TCSCRRegTmp;
	//Register: multicastAddressReg1
	logic [31:0] MAR1RegAddr = 32'd23; 
	logic [31:0] MAR1Reg; 
	logic [31:0] MAR1RegTmp;
	//Register: multicastAddressReg0
	logic [31:0] MAR0RegAddr = 32'd22; 
	logic [31:0] MAR0Reg; 
	logic [31:0] MAR0RegTmp;
	//Register: multicastAddressReg5
	logic [31:0] MAR5RegAddr = 32'd27; 
	logic [31:0] MAR5Reg; 
	logic [31:0] MAR5RegTmp;
	//Register: multicastAddressReg4
	logic [31:0] MAR4RegAddr = 32'd26; 
	logic [31:0] MAR4Reg; 
	logic [31:0] MAR4RegTmp;
	//Register: multicastAddressReg3
	logic [31:0] MAR3RegAddr = 32'd25; 
	logic [31:0] MAR3Reg; 
	logic [31:0] MAR3RegTmp;
	//Register: multicastAddressReg2
	logic [31:0] MAR2RegAddr = 32'd24; 
	logic [31:0] MAR2Reg; 
	logic [31:0] MAR2RegTmp;
	//Register: multicastAddressReg7
	logic [31:0] MAR7RegAddr = 32'd29; 
	logic [31:0] MAR7Reg; 
	logic [31:0] MAR7RegTmp;
	//Register: multicastAddressReg6
	logic [31:0] MAR6RegAddr = 32'd28; 
	logic [31:0] MAR6Reg; 
	logic [31:0] MAR6RegTmp;
	//Register: memoryDataWriteCommandWithoutAddressIncrementReg
	logic [31:0] MWCMDXRRegAddr = 32'd246; 
	logic [31:0] MWCMDXRReg; 
	logic [31:0] MWCMDXRRegTmp;
	//Register: receiveCheckSumControlStatusReg
	logic [31:0] RCSCSRRegAddr = 32'd50; 
	logic [31:0] RCSCSRReg; 
	logic [31:0] RCSCSRRegTmp;
	//Register: memoryDataPre_FetchReadCommandWithoutAddressIncrementReg
	logic [31:0] MRCMDXRegAddr = 32'd240; 
	logic [31:0] MRCMDXReg; 
	logic [31:0] MRCMDXRegTmp;
	//Register: networkStatusReg
	logic [31:0] NSRRegAddr = 32'd0; 
	logic [31:0] NSRReg; 
	logic [31:0] NSRRegTmp;
	//Register: interruptMaskReg
	logic [31:0] IMRRegAddr = 32'd255; 
	logic [31:0] IMRReg; 
	logic [31:0] IMRRegTmp;
	//Register: rxStatusReg
	logic [31:0] RSRRegAddr = 32'd6; 
	logic [31:0] RSRReg; 
	logic [31:0] RSRRegTmp;
	//Register: rxtxFlowControlReg
	logic [31:0] FCRRegAddr = 32'd10; 
	logic [31:0] FCRReg; 
	logic [31:0] FCRRegTmp;
	//Register: memoryDataReadCommandWithAddressIncrementRegII
	logic [31:0] MRCMDRegAddr = 32'd242; 
	logic [31:0] MRCMDReg; 
	logic [31:0] MRCMDRegTmp;
	//Register: generalPurposeControlReg
	logic [31:0] GPCRRegAddr = 32'd30; 
	logic [31:0] GPCRReg; 
	logic [31:0] GPCRRegTmp;
	//Register: networkControlReg
	logic [31:0] NCRRegAddr = 32'd1; 
	logic [31:0] NCRReg; 
	logic [31:0] NCRRegTmp;
	//Register: EEPROM_PHYAddressReg
	logic [31:0] EPARRegAddr = 32'd12; 
	logic [31:0] EPARReg; 
	logic [31:0] EPARRegTmp;
	//Register: earlyTransmitControl_StatusReg
	logic [31:0] ETXCSRRegAddr = 32'd48; 
	logic [31:0] ETXCSRReg; 
	logic [31:0] ETXCSRRegTmp;
	//Register: rxControlReg
	logic [31:0] RCRRegAddr = 32'd5; 
	logic [31:0] RCRReg; 
	logic [31:0] RCRRegTmp;
	//Register: txControlReg
	logic [31:0] TCRRegAddr = 32'd2; 
	logic [31:0] TCRReg; 
	logic [31:0] TCRRegTmp;
	//Register: txPacketLengthLowReg
	logic [31:0] MDRALRRegAddr = 32'd252; 
	logic [31:0] MDRALRReg; 
	logic [31:0] MDRALRRegTmp;
	//Register: txSRAMReadPointerAddress
	logic [31:0] TRPARegAddr = 32'd34; 
	logic [31:0] TRPAReg; 
	logic [31:0] TRPARegTmp;
	//Register: receiveOverflowCounterReg
	logic [31:0] ROCRRegAddr = 32'd7; 
	logic [31:0] ROCRReg; 
	logic [31:0] ROCRRegTmp;
	//Register: memoryDataRead_addressRegister
	logic [31:0] MRRRegAddr = 32'd244; 
	logic [31:0] MRRReg; 
	logic [31:0] MRRRegTmp;
	//Register: rxSRAMWritePointerAddressByte
	logic [31:0] RWPARegAddr = 32'd36; 
	logic [31:0] RWPAReg; 
	logic [31:0] RWPARegTmp;
	//Register: txStatusRegI
	logic [31:0] TSRIRegAddr = 32'd3; 
	logic [31:0] TSRIReg; 
	logic [31:0] TSRIRegTmp;
	//Register: backPressureThresholdReg
	logic [31:0] BPTRRegAddr = 32'd8; 
	logic [31:0] BPTRReg; 
	logic [31:0] BPTRRegTmp;
	//Register: txStatusRegII
	logic [31:0] TSRIIRegAddr = 32'd4; 
	logic [31:0] TSRIIReg; 
	logic [31:0] TSRIIRegTmp;
	//Register: CHIPRevision
	logic [31:0] CHIPRRegAddr = 32'd44; 
	logic [31:0] CHIPRReg; 
	logic [31:0] CHIPRRegTmp;
	//Register: indexReg
	logic [31:0] INDEXREGRegAddr = 32'd0; 
	logic [31:0] INDEXREGReg; 
	logic [31:0] INDEXREGRegTmp;
	//Register: EEPROM_PHYDataReg
	logic [31:0] EPDRRegAddr = 32'd13; 
	logic [31:0] EPDRReg; 
	logic [31:0] EPDRRegTmp;
	//Register: txPacketLengthHighReg
	logic [31:0] MDRAHRRegAddr = 32'd253; 
	logic [31:0] MDRAHRReg; 
	logic [31:0] MDRAHRRegTmp;
	//Register: dataReg
	logic [31:0] DATAREGRegAddr = 32'd4; 
	logic [31:0] DATAREGReg; 
	logic [31:0] DATAREGRegTmp;
	//Register: flowControlThresholdReg
	logic [31:0] FCTRRegAddr = 32'd9; 
	logic [31:0] FCTRReg; 
	logic [31:0] FCTRRegTmp;
	//Register: BasicModeControl
	logic [31:0] BMCRRegAddr = 32'd0; 
	logic [31:0] BMCRReg; 
	logic [31:0] BMCRRegTmp;
	//Register: txControlRegII
	logic [31:0] TCR2RegAddr = 32'd45; 
	logic [31:0] TCR2Reg; 
	logic [31:0] TCR2RegTmp;
	//Register: interruptStatusReg
	logic [31:0] ISRRegAddr = 32'd254; 
	logic [31:0] ISRReg; 
	logic [31:0] ISRRegTmp;
	//Register: memoryDataWrite_addressRegs
	logic [31:0] MWRRegAddr = 32'd250; 
	logic [31:0] MWRReg; 
	logic [31:0] MWRRegTmp;
	//Register: productID
	logic [31:0] PIDRegAddr = 32'd42; 
	logic [31:0] PIDReg; 
	logic [31:0] PIDRegTmp;
	//Register: physicalAddressRegister0
	logic [31:0] PAR0RegAddr = 32'd16; 
	logic [31:0] PAR0Reg; 
	logic [31:0] PAR0RegTmp;
	//Register: memoryDataWriteCommandWithAddressIncrementReg
	logic [31:0] MWCMDRRegAddr = 32'd248; 
	logic [31:0] MWCMDRReg; 
	logic [31:0] MWCMDRRegTmp;
	//Register: specialModeControlReg
	logic [31:0] SMCRRegAddr = 32'd47; 
	logic [31:0] SMCRReg; 
	logic [31:0] SMCRRegTmp;
	//Register: memoryDataReadCommandWithAddressIncrementRegI
	logic [31:0] MRCMDX1RegAddr = 32'd241; 
	logic [31:0] MRCMDX1Reg; 
	logic [31:0] MRCMDX1RegTmp;
	//Register: vendorID
	logic [31:0] VIDRegAddr = 32'd40; 
	logic [31:0] VIDReg; 
	logic [31:0] VIDRegTmp;
	//Register: physicalAddressRegister5
	logic [31:0] PAR5RegAddr = 32'd21; 
	logic [31:0] PAR5Reg; 
	logic [31:0] PAR5RegTmp;
	//Register: physicalAddressRegister2
	logic [31:0] PAR2RegAddr = 32'd18; 
	logic [31:0] PAR2Reg; 
	logic [31:0] PAR2RegTmp;
	//Register: physicalAddressRegister1
	logic [31:0] PAR1RegAddr = 32'd17; 
	logic [31:0] PAR1Reg; 
	logic [31:0] PAR1RegTmp;
	//Register: physicalAddressRegister4
	logic [31:0] PAR4RegAddr = 32'd20; 
	logic [31:0] PAR4Reg; 
	logic [31:0] PAR4RegTmp;
	//Register: physicalAddressRegister3
	logic [31:0] PAR3RegAddr = 32'd19; 
	logic [31:0] PAR3Reg; 
	logic [31:0] PAR3RegTmp;
	//Register: EEPROM_PHYControlReg
	logic [31:0] EPCRRegAddr = 32'd11; 
	logic [31:0] EPCRReg; 
	logic [31:0] EPCRRegTmp;
	//Register: operationTestControlReg
	logic [31:0] OCRRegAddr = 32'd46; 
	logic [31:0] OCRReg; 
	logic [31:0] OCRRegTmp;

	//State: Global_State, OrthoRegion: linkState
	typedef enum                         logic [1:0] {
			DOWN, 
			UP
	} Global_State_linkState_STATEFSM;
	Global_State_linkState_STATEFSM Global_State_linkState_states;
	
	//State: PHYUP, OrthoRegion: receive
	typedef enum                         logic [3:0] {
			RXPKGLOWLEN, 
			RXPKGIDLE, 
			RXPKGPAYLOAD, 
			RXPKGHIGHLEN, 
			RXPKGHEADERERR, 
			RXPKGSTATUS
	} PHYUP_receive_STATEFSM;
	PHYUP_receive_STATEFSM PHYUP_receive_states;
	
	//State: PHYUP, OrthoRegion: transmissionConfig
	typedef enum                         logic [2:0] {
			TXWRPKGLEN, 
			TXWRPKGIDLE, 
			TXWRPKGWR
	} PHYUP_transmissionConfig_STATEFSM;
	PHYUP_transmissionConfig_STATEFSM PHYUP_transmissionConfig_states;
	
	//State: PHYUP, OrthoRegion: transmissionExec
	typedef enum                         logic [1:0] {
			TXSDPKGSDING, 
			TXSDPKGIDLE
	} PHYUP_transmissionExec_STATEFSM;
	PHYUP_transmissionExec_STATEFSM PHYUP_transmissionExec_states;
	
	//State: Global_State, OrthoRegion: PHYLayerOperation
	typedef enum                         logic [2:0] {
			PHYDN, 
			PHYUP, 
			SWRST
	} Global_State_PHYLayerOperation_STATEFSM;
	Global_State_PHYLayerOperation_STATEFSM Global_State_PHYLayerOperation_states;
	
	//State: PHYUP, OrthoRegion: receive
	typedef enum                         logic [3:0] {
			RXPKGLOWLEN, 
			RXPKGIDLE, 
			RXPKGPAYLOAD, 
			RXPKGHIGHLEN, 
			RXPKGHEADERERR, 
			RXPKGSTATUS
	} PHYUP_receive_STATEFSM;
	PHYUP_receive_STATEFSM PHYUP_receive_states;
	
	//State: PHYUP, OrthoRegion: transmissionConfig
	typedef enum                         logic [2:0] {
			TXWRPKGLEN, 
			TXWRPKGIDLE, 
			TXWRPKGWR
	} PHYUP_transmissionConfig_STATEFSM;
	PHYUP_transmissionConfig_STATEFSM PHYUP_transmissionConfig_states;
	
	//State: PHYUP, OrthoRegion: transmissionExec
	typedef enum                         logic [1:0] {
			TXSDPKGSDING, 
			TXSDPKGIDLE
	} PHYUP_transmissionExec_STATEFSM;
	PHYUP_transmissionExec_STATEFSM PHYUP_transmissionExec_states;
	
	//State: Global_State, OrthoRegion: ethOperationMode
	typedef enum                         logic [2:0] {
			OPER8BITS, 
			OPER16BITS, 
			UNDEF_OPER_MODE
	} Global_State_ethOperationMode_STATEFSM;
	Global_State_ethOperationMode_STATEFSM Global_State_ethOperationMode_states;
	
	//State: PHYUP, OrthoRegion: receive
	typedef enum                         logic [3:0] {
			RXPKGLOWLEN, 
			RXPKGIDLE, 
			RXPKGPAYLOAD, 
			RXPKGHIGHLEN, 
			RXPKGHEADERERR, 
			RXPKGSTATUS
	} PHYUP_receive_STATEFSM;
	PHYUP_receive_STATEFSM PHYUP_receive_states;
	
	//State: PHYUP, OrthoRegion: transmissionConfig
	typedef enum                         logic [2:0] {
			TXWRPKGLEN, 
			TXWRPKGIDLE, 
			TXWRPKGWR
	} PHYUP_transmissionConfig_STATEFSM;
	PHYUP_transmissionConfig_STATEFSM PHYUP_transmissionConfig_states;
	
	//State: PHYUP, OrthoRegion: transmissionExec
	typedef enum                         logic [1:0] {
			TXSDPKGSDING, 
			TXSDPKGIDLE
	} PHYUP_transmissionExec_STATEFSM;
	PHYUP_transmissionExec_STATEFSM PHYUP_transmissionExec_states;
	

	always_ff @ (posedge clk or negedge rst_n)
     begin
        if (!rst_n)
          begin
		Global_State_linkState_states <= DOWN;
		Global_State_PHYLayerOperation_states <= PHYDN;
		PHYUP_receive_states <= RXPKGIDLE;
		PHYUP_transmissionConfig_states <= TXWRPKGIDLE;
		PHYUP_transmissionExec_states <= TXSDPKGIDLE;
		Global_State_ethOperationMode_states <= UNDEF_OPER_MODE;
          end
        else
		begin
        	if (fsmState == CHECKING)
               begin
			case(Global_State_linkState_states)
			DOWN: begin
			if (ready && p0 && p1) begin
				Global_State_linkState_states <= UP;
			end
			end
			UP: begin
			if (ready && p0 && p2) begin
				Global_State_linkState_states <= DOWN;
			end
			end
			endcase //Global_StatelinkStatestates
			case(Global_State_PHYLayerOperation_states)
			PHYDN: begin
			if (ready && p13 && p14) begin
				Global_State_PHYLayerOperation_states <= SWRST;
			end
			if (ready && p5 && p6) begin
				Global_State_PHYLayerOperation_states <= PHYUP;
			end
			if (ready && p7 && p8) begin
				Global_State_PHYLayerOperation_states <= PHYUP;
			end
			if (ready && p9 && p10) begin
				Global_State_PHYLayerOperation_states <= SWRST;
			end
			end
			PHYUP: begin
			if (ready && p7 && p11) begin
				Global_State_PHYLayerOperation_states <= PHYDN;
			end
			if (ready && p13 && p14) begin
				Global_State_PHYLayerOperation_states <= SWRST;
			end
			if (ready && p5 && p12) begin
				Global_State_PHYLayerOperation_states <= PHYDN;
			end
			case(PHYUP_receive_states)
			RXPKGLOWLEN: begin
			if (ready && p30) begin
				PHYUP_receive_states <= RXPKGHIGHLEN;
			end
			end
			RXPKGIDLE: begin
			if (ready && p30 && p32) begin
				PHYUP_receive_states <= RXPKGIDLE;
			end
			if (ready && p33 && p34 && p33 && p35) begin
				PHYUP_receive_states <= RXPKGHEADERERR;
			end
			if (ready && p30 && p31) begin
				PHYUP_receive_states <= RXPKGSTATUS;
			end
			end
			RXPKGPAYLOAD: begin
			if (ready && (p30 && p41 && p33 && p35) && p39) begin
				PHYUP_receive_states <= RXPKGHEADERERR;
			end
			if (ready && p33 && p37) begin
				PHYUP_receive_states <= RXPKGPAYLOAD;
			end
			if (ready && (p33 && p38 || p30 && p31) && p39) begin
				PHYUP_receive_states <= RXPKGSTATUS;
			end
			if (ready && p30 && p37) begin
				PHYUP_receive_states <= RXPKGPAYLOAD;
			end
			if (ready && (p33 && p40 || p30 && p32) && p39) begin
				PHYUP_receive_states <= RXPKGIDLE;
			end
			end
			RXPKGHIGHLEN: begin
			if (ready && p30) begin
				PHYUP_receive_states <= RXPKGPAYLOAD;
			end
			end
			RXPKGHEADERERR: begin
			if (ready && p33 || p30) begin
				PHYUP_receive_states <= RXPKGHEADERERR;
			end
			end
			RXPKGSTATUS: begin
			if (ready && p30 && p36) begin
				PHYUP_receive_states <= RXPKGLOWLEN;
			end
			end
			endcase //PHYUPreceivestates
			case(PHYUP_transmissionConfig_states)
			TXWRPKGLEN: begin
			if (ready && p18 && p19) begin
				PHYUP_transmissionConfig_states <= TXWRPKGIDLE;
			end
			end
			TXWRPKGIDLE: begin
			if (ready && p15) begin
				PHYUP_transmissionConfig_states <= TXWRPKGWR;
			end
			end
			TXWRPKGWR: begin
			if (ready && p16 || p17) begin
				PHYUP_transmissionConfig_states <= TXWRPKGLEN;
			end
			end
			endcase //PHYUPtransmissionConfigstates
			case(PHYUP_transmissionExec_states)
			TXSDPKGSDING: begin
			if (ready && p28 && p29) begin
				PHYUP_transmissionExec_states <= TXSDPKGIDLE;
			end
			if (ready && p24 && p25 || p26 && p27) begin
				PHYUP_transmissionExec_states <= TXSDPKGSDING;
			end
			end
			TXSDPKGIDLE: begin
			if (ready && p18 && p19) begin
				PHYUP_transmissionExec_states <= TXSDPKGSDING;
			end
			if (ready && p18 && p20) begin
				PHYUP_transmissionExec_states <= TXSDPKGSDING;
			end
			if (ready && p21 && p22) begin
				PHYUP_transmissionExec_states <= TXSDPKGSDING;
			end
			if (ready && p21 && p23) begin
				PHYUP_transmissionExec_states <= TXSDPKGSDING;
			end
			end
			endcase //PHYUPtransmissionExecstates
			end
			SWRST: begin
			if (ready && p5 && p12) begin
				Global_State_PHYLayerOperation_states <= PHYDN;
			end
			if (ready && p7 && p11) begin
				Global_State_PHYLayerOperation_states <= PHYDN;
			end
			end
			endcase //Global_StatePHYLayerOperationstates
			case(Global_State_ethOperationMode_states)
			OPER8BITS: begin
			if (ready && p3) begin
				Global_State_ethOperationMode_states <= OPER16BITS;
			end
			end
			OPER16BITS: begin
			if (ready && p4) begin
				Global_State_ethOperationMode_states <= OPER8BITS;
			end
			end
			UNDEF_OPER_MODE: begin
			if (ready && p3) begin
				Global_State_ethOperationMode_states <= OPER16BITS;
			end
			if (ready && p4) begin
				Global_State_ethOperationMode_states <= OPER8BITS;
			end
			end
			endcase //Global_StateethOperationModestates
               end   
        end
	end
endmodule

