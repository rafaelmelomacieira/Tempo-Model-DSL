module ${name}FSMModel (
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

<#list hfsmProp?keys as prop>
	//assign ${prop} = ${hfsmProp[prop]};
</#list>

<#list registers?keys as regs>
	//Register: ${registers[regs].name}
	logic [31:0] ${regs}RegAddr = 32'd${registers[regs].offset}; 
	logic [31:0] ${regs}Reg; 
	logic [31:0] ${regs}RegTmp;
</#list>

<@genStates sonInst=globalState/>

	always_ff @ (posedge clk or negedge rst_n)
     begin
        if (!rst_n)
          begin
			<@genStatesReset sonInst=globalState/>
          end
        else
		begin
        	if (fsmState == CHECKING)
               begin
               	  <@genStatesProc sonInst=globalState/>
               end   
        end
	end
endmodule

<#macro genStates sonInst>
	<#list (sonInst.myOrthoRegions)?keys as ortho>
		<#list (sonInst.myOrthoRegions[ortho].regionStates)?keys>
	//State: ${sonInst.name}, OrthoRegion: ${sonInst.myOrthoRegions[ortho].regionName}
	typedef enum                         logic [${sonInst.myOrthoRegions[ortho].getBitSize()}:0] {
		    <#items as mySon> 
			${sonInst.myOrthoRegions[ortho].regionStates[mySon].name}<#sep>, </#sep>
			</#items>
	} ${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_STATEFSM;
	${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_STATEFSM ${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_states;
	
		</#list>
		<#list (sonInst.sons)?keys as mySon>
			<@genStates sonInst=sonInst.sons[mySon]/>
		</#list>
	</#list>
</#macro>
<#macro genStatesProc sonInst>
	<#list (sonInst.myOrthoRegions)?keys as ortho>
		<#list (sonInst.myOrthoRegions[ortho].regionStates)?keys>
			case(${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_states)
		    <#items as mySon> 
			${sonInst.myOrthoRegions[ortho].regionStates[mySon].name}: begin
			<#list sonInst.myOrthoRegions[ortho].regionStates[mySon].exitPoints as exitp>
			if (ready && ${exitp}) begin
				${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_states <= ${exitp.targetState.name};
			end
			</#list>
			<@genStatesProc sonInst=sonInst.myOrthoRegions[ortho].regionStates[mySon]/>
			end
			</#items>
			endcase //${sonInst.name}${sonInst.myOrthoRegions[ortho].regionName}states
		</#list>
	</#list>
</#macro>
<#macro genStatesReset sonInst>
	<#list (sonInst.myOrthoRegions)?keys as ortho>
		<#list (sonInst.myOrthoRegions[ortho].regionStates)?keys>
		${sonInst.name}_${sonInst.myOrthoRegions[ortho].regionName}_states <= ${sonInst.myOrthoRegions[ortho].regionInitialState.name};
		   <#items as mySon> 
			<@genStatesReset sonInst=sonInst.myOrthoRegions[ortho].regionStates[mySon]/>
			</#items>
		</#list>
	</#list>
</#macro>