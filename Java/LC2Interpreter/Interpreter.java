/* Name:    Thomas LePage twl9586
 * Class:   Interpreter.java
 * Purpose: Runs the interpreter program
 */
 
import java.util.Vector;
import java.io.File;

public class Interpreter
{
	private Memory memory; 
	private Registers registers;
	private Converter converter;
	private InstructionReader instructionReader;
	private Extender extender;
	private Parser parser;
	
	private Vector instructionStack;
	private Vector decodedInstructions;
	private String currentInstruction;
	private int programCounter;
	private int code;
	private int lastResult;
	
/* Function: Interpreter()
 * Purpose:  to set up the memory, registers, and information stacks
 * Input:    a String representing the file name which contains the binary
 *           code
 * Output:   nothing
 */
	public Interpreter( String file )
	{
		// the file which contains the instructions
		File instructionFile = new File( file );
		
		// the file reader to acquire and store the instructions
		instructionReader = new InstructionReader( instructionFile );
		
		// store the instructions in a vector
		instructionStack = instructionReader.getStack();
		
		// the vector which will contain the decoded instructions
		decodedInstructions = new Vector();
		
		programCounter = 0;  // the program counter
		code = -1;           // initialize the function code
		lastResult = 0;      // initialize the last result variable
		
		memory = new Memory();
		registers = new Registers();
		converter = new Converter();
		extender = new Extender();
		parser = new Parser();
		
		registers.clearRegisters();
		
		// set the start location in memory
		int startLocation = converter.binToDec( "0011000000000000" );
	
		//store the instructions in memory
		for( int t = 0; t < instructionStack.size(); t++ )
		{
			memory.setValueAtMemAddress(startLocation + t,
				converter.binToDec( instructionStack.get(t).toString()));
		}
																												
		// set the IP
		memory.setIPIndex(startLocation);
		System.out.println( "Initial register contents after instruction " +			"load.");
		registers.printRegisterContents();
		System.out.println();
	}

/* Function: run()
 * Purpose:  controls the interpreter operation by retrieving the next
 *           instruction, incrementing the IP, and calling the function
 * Input:    nothing
 * Output:   nothing
 */
	public void run()
	{	
		// while HALT is not called
		while( code != 16 )
		{
			// get the current instruction
			currentInstruction = extender.ZEXT(converter.decToBin(
				memory.getValueAtMemAddress(memory.getIPIndex())));
			
			// increment the IP
			memory.setIPIndex(memory.getIPIndex() + 1 );
			
			// parse the instruction and get code
			code = parser.parseString(currentInstruction);
			
			// call the function
			callFunction(code, currentInstruction );
		}
	}

/* Function: callFunction()
 * Purpose:  to take the code from the parser and call the appropriate
 *           function
 * Input:    an integer code representing function, and a String which
 *           represents the instruction to be executed
 * Output:   nothing
 */
	public void callFunction( int code, String instruction )
	{
		// call the appropriate function based on the code from the 
		// instruction parser
		switch( code )
		{
			case 0: 
				ADD( instruction, code );
				break;
			case 1:
				ADD( instruction, code );
				break;
			case 2:
				AND( instruction, code );
				break;
			case 3: 
				AND( instruction, code );
				break;
			case 4:
				BR( instruction );
				break;
			case 5:
				JSR( instruction );
				break;
			case 6:
				JSRR( instruction );
				break;
			case 7:
				LD( instruction );
				break;
			case 8:
				LDI( instruction );
				break;
			case 9:
				LDR( instruction );
				break;
			case 10:
				LEA( instruction );
				break;
			case 11:
				NOT( instruction );
				break;
			case 12: 
				RET();
				break;
			case 13:
				ST( instruction );
				break;
			case 14:
				STI( instruction );
				break;
			case 15: 
				STR( instruction );
				break;
			case 16:
				TRAP( instruction );
				break;
		}
	}
	
/* Function: ADD()
 * Purpose:  If bit<5> == 0, then add the contents of SR1 and SR2. If
 *           bit<5> == 1, then add the contents of SR1 and the sign
 *           extended immediate value.  The result in both cases is
 *           placed in DR and sets the condition codes.  The condition 
 *           codes are modified.
 * Input:    the instruction string and the integer code to determine
 *           which subfunction to call.
 * Output:   nothing
 */
	public void ADD( String instruction, int code )
	{
		String DR = null,
			   SR1 = null,
			   SR2 = null,
			   imm5 = null,
			   decodedString = null;
		
		int value = 0;
		
		// get the subparts of the instruction string
		DR = instruction.substring(4, 7);
		SR1 = instruction.substring(7, 10);
		SR2 = instruction.substring(13, 16);
		imm5 = instruction.substring(11, 16);
		 
		switch( code )
		{
			// if bit<5> is 0
			case 0: 
				
				// add contents of SR1 and SR2
				value = registers.getRegValue(SR1) + registers.getRegValue(SR2);
				
				// store value into DR
				registers.setRegValue(registers.convertRegisterCode(DR), 
					value );
				
				// create decoded string
				decodedString = "ADD    " + "R" + 
					registers.convertRegisterCode(DR) +
					", R" + registers.convertRegisterCode(SR1) + ", R" +
					registers.convertRegisterCode(SR2);
				
				// store decoded instruction
				decodedInstructions.add( decodedString );
				
				// set last result
				lastResult = registers.getRegValue(DR);
				break;
			
			// if bit<5> is 1
			case 1:
				
				// add contents of SR1 and sign extended imm5 value
				value = registers.getRegValue(SR1) + 
					converter.binToDec(extender.SEXT(imm5));
				
				// store value in DR
				registers.setRegValue(registers.convertRegisterCode(DR), value);
			
				// create decoded string
				decodedString = "ADD    " + "R" + 
					registers.convertRegisterCode(DR) +
					", R" + registers.convertRegisterCode(SR1) + ", #" +
					converter.binToDec(extender.SEXT(imm5));
				
				// set last result
				lastResult = registers.getRegValue(DR);
				
				// store decoded instruction
				decodedInstructions.add( decodedString );
				break;
		}
		
		// set the condition codes based on last result
		registers.setConditionCodes(lastResult);
	}
	
/* Function: AND()
 * Purpose:  If bit<5> == 0, then perform the bitwise AND of the contents of
 *           SR1 and SR2.  If bit<5> == 1, the perform the bitwise AND of the
 *           contents of SR1 and the sign extended immediate value.  The
 *           result in both cases is placed in DR and sets the condition
 *           codes.  The condition codes are modified.
 * Input:    the instruction string and the integer code which determines
 *           the subfunction to be called
 * Output:   nothing
 */
	public void AND( String instruction, int code )
	{
		String DR = null,
		   SR1 = null,
		   SR2 = null,
		   imm5 = null,
		   decodedString = null;
	
		int value = 0;
		
		// get the subparts of the instruction string
		DR = instruction.substring(4, 7);
		SR1 = instruction.substring(7, 10);
		SR2 = instruction.substring(13, 16);
		imm5 = instruction.substring(11, 16);
		
		switch( code )
		{
			// if bit<5> is 0
			case 2: 
				
				// bitwise AND the values in SR1 and SR2
				value = registers.getRegValue(SR1) & 
					registers.getRegValue(SR2);
				
				// store value into DR
				registers.setRegValue(registers.convertRegisterCode(DR), 
					value );
		
				// create decoded string
				decodedString = "AND   " + "R" + 
					registers.convertRegisterCode(DR) +
					", R" + registers.convertRegisterCode(SR1) + ", R" +
					registers.convertRegisterCode(SR2);
				
				// set last result
				lastResult = value;
				
				// store decoded string
				decodedInstructions.add( decodedString );
				break;
		
			// if bit<5> is 1
			case 3:
				
				// bitwise AND the values in SR1 and sign extended imm5
				value = registers.getRegValue(SR1) & 
					converter.binToDec(extender.SEXT(imm5));
				
				// store value in DR
				registers.setRegValue(registers.convertRegisterCode(DR), value );
		
				// create decoded string
				decodedString = "AND    " + "R" + 
					registers.convertRegisterCode(DR) +
					", R" + registers.convertRegisterCode(SR1) + ", #" +
					converter.binToDec(extender.SEXT(imm5));
				
				// set last result
				lastResult = value;
				
				// store decoded string
				decodedInstructions.add( decodedString );
				break;
		}
		
		// set the condition codes
		registers.setConditionCodes(lastResult);
	}
	
/* Function: BR()
 * Purpose:  If the condition is true, branch to the specified location on the 
 *           current page. The top 7 bits of the current program counter are 
 *           concatenated with the pgoffset9 field to form the new 16-bit PC 
 *           value, which is written to the PC only if the condition is true. 
 *           The condition codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void BR( String instruction )
	{
		char nC, zC, pC;
		boolean n, z, p, N, Z, P;
		
		// get the page offset from instruction
		String pgOffset = instruction.substring(7, 16);
		
		// convert to get n, z, and p values
		nC = instruction.charAt(4);
		zC = instruction.charAt(5);
		pC = instruction.charAt(6);
		
		if( registers.getN() == 1 ) N = true;
			else N = false;
		if( registers.getZ() == 1 ) Z = true;
			else Z = false;
		if( registers.getP() == 1 ) P = true;
			else P = false;
			
		if( nC == '1' ) n = true;
			else n = false;
		if( zC == '1' ) z = true;
			else z = false;
		if( pC == '1' ) p = true;
			else p = false;
			
		// check current condition
		if((n & N) | (z & Z) | (p & P))
		{
			// create the new IP
			programCounter = 
				converter.binToDec(extender.createOffset(
				extender.ZEXT(
				converter.decToBin(memory.getIPIndex())), pgOffset));
			
			// set the new IP
			memory.setIPIndex( programCounter );
			
			String condition = "";
			
			if(n)condition += "n";
			if(z)condition += "z";
			if(p)condition += "p";
			if(!n && !z && !p)condition = "NOP";
			
			// create decoded string
			String decodedString = "BR" + condition + "    " + 
				converter.decToHex(memory.getIPIndex()); 
	
			// store decoded string
			decodedInstructions.add(decodedString);		
		}
		
	}

/* Function: JSR()
 * Purpose:  Unconditionally jump to the specified location on the current page. 
 *           If the link bit L is set, the PC is saved in R7, allowing a later 
 *           return. The top 7 bits of the current program counter are 
 *           concatenated with the pgoffset9 field to form the new 16-bit PC 
 *           value. The condition codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void JSR( String instruction )
	{
		char L;
		
		// get the page offset from the instruction string
		String pgOffset = instruction.substring(7, 16);
		
		// get the link bit
		L = instruction.charAt(4);
		
		// save the current IP
		if( L == '1' )
		{
			registers.setRegValue(7, memory.getIPIndex() );
		}
		
		// create new IP value
		programCounter = 
			converter.binToDec(extender.createOffset(
			extender.ZEXT(converter.decToBin(memory.getIPIndex())), pgOffset));
		
		// set the new IP values
		memory.setIPIndex( programCounter ); 
		
		String opCode = null;
		
		if( L == '1') opCode = "JSR";
		else opCode = "JMP";
		
		// create decoded string
		String decodedString = opCode + "   " + 
			converter.decToHex(memory.getIPIndex());
			
		// store the decoded instruction
		decodedInstructions.add(decodedString); 
	}

/* Function: JSRR()
 * Purpose:  If the link bit L is set, the PC is saved in R7, allowing a later 
 *           return. The index is zero extended to 16 bits and added to the 
 *           contents of BaseR to form the new PC value. The condition codes 
 *           are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void JSRR( String instruction )
	{
		// get the link bit
		char L = instruction.charAt(4);
		
		// get the index6 value
		String index6 = extender.ZEXT(instruction.substring(10, 16));
		
		// get the base register
		String baseReg = instruction.substring(7,10);
		
		// store the current IP
		if( L == '1' )
		{
			registers.setRegValue( 7, memory.getIPIndex());
		}
		
		// extend the base register
		String extBaseReg = extender.ZEXT( baseReg );
		
		int temp = registers.getRegValue(baseReg);
		int temp2 = converter.binToDec(index6);
		
		// add the contents to form new IP
		temp = temp + temp2;
		
		// create new IP address
		String newPC = extender.ZEXT(converter.decToBin(temp));
		programCounter = converter.binToDec( newPC );
		
		// set new IP address
		memory.setIPIndex( programCounter );
		
		String opCode;
		
		if( L == '1' ) opCode = "JSRR";
		else opCode = "JMPR";
		
		// create decoded string
		String decodedString = opCode + "   R" + 
			registers.convertRegisterCode(baseReg) + ", #" + 
			converter.binToDec(index6);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: LD()
 * Purpose:  Load a register from the specified location on the current page. 
 *           The top 7 bits of the current program counter are concatenated with 
 *           the pgoffset9 field to form a 16-bit memory address. The contents 
 *           of memory at this address is loaded into DR and is used to set the 
 *           condition codes.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void LD( String instruction )
	{
		// get values from instruction string
		String DR = instruction.substring(4, 7);
		String pgOffset = instruction.substring(7, 16);
		
		// get current IP address
		String IPAddress = 
			extender.ZEXT(converter.decToBin(memory.getIPIndex()));
		
		// create offset
		int loadPC = 
			converter.binToDec(extender.createOffset(
			IPAddress, pgOffset ));
		
		// get values at memory location
		int memContents = memory.getValueAtMemAddress(loadPC);
		
		// set value into DR
		registers.setRegValue( registers.convertRegisterCode(DR), memContents );
		
		// set last result
		lastResult = memContents;
		
		// set condition codes
		registers.setConditionCodes(memContents);	
		
		// create decoded string
		String decodedString = "LD   R" + registers.convertRegisterCode(DR) +
			", " + converter.decToHex(loadPC);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}
	
/* Function: LDI()
 * Purpose:  Load a register indirectly from the specified location. The top 7 
 *           bits of the current program counter are concatenated with the 
 *           pgoffset9 field to form a 16-bit memory address. The contents of 
 *           memory at this location is used as the address of the data that is 
 *           loaded into DR. The data loaded into DR sets the condition codes.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void LDI( String instruction )
	{
		// get values out of instruction string
		String DR = instruction.substring(4, 7);
		String pgOffset = instruction.substring(7, 16);
		
		// get IP address
		String IPAddress = 
			extender.ZEXT(converter.decToBin(memory.getIPIndex()));
		
		// create offset
		int loadPC =
			converter.binToDec(extender.createOffset(
			IPAddress, pgOffset ));
		
		// create new address
		int memContentAddress = memory.getValueAtMemAddress(loadPC);
		
		// get actual contents
		int memContents = memory.getValueAtMemAddress(memContentAddress);
		
		// store value in DR
		registers.setRegValue( registers.convertRegisterCode(DR), memContents );
		
		// set last result
		lastResult = memContents;
		
		// set condition codes
		registers.setConditionCodes(memContents);	
		
		// create decoded string
		String decodedString = "LDI   R" + registers.convertRegisterCode(DR)
			+ ", " + converter.decToHex(memContentAddress);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: LDR()
 * Purpose:  Load a register using a base register and index. The index is zero 
 *           extended to 16 bits and added to the contents of BaseR to form a 
 *           memory address. The contents of memory at this address is loaded 
 *           into DR and sets the condition codes. Note that index6 is a 
 *           positive offset.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void LDR( String instruction )
	{
		// get values from instruction string
		String DR = instruction.substring(4, 7);
		String baseReg = instruction.substring(7, 10);
		String index6 = extender.ZEXT(instruction.substring(10, 16));
		
		int temp = registers.getRegValue(baseReg);
		int temp2 = converter.binToDec(index6);
		
		// add the contents to get new IP
		temp = temp + temp2;
		
		// create address string
		String newReg = extender.ZEXT(converter.decToBin(temp));
		
		// get the memory contents at new address
		int memContents = memory.getValueAtMemAddress(converter.binToDec(newReg));
		
		// store value in DR
		registers.setRegValue(registers.convertRegisterCode(DR),
			memContents );
		
		// set last result
		lastResult = memContents;
		
		// set condition codes
		registers.setConditionCodes(lastResult);
		
		// create decoded string
		String decodedString = "LDR    R" + registers.convertRegisterCode(DR) +
			", R" + registers.convertRegisterCode(baseReg) + ", #" + 
			converter.binToDec(index6);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: LEA()
 * Purpose:  The top 7 bits of the current program counter are concatenated with 
 *           the pgoffset9 field to form a 16-bit memory address. This address 
 *           is placed into DR and sets the condition codes.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void LEA( String instruction )
	{
		// get values from instruction string
		String DR = instruction.substring(4, 7);
		String pgOffset = instruction.substring(7, 16);
		
		// create the effective address
		String effectiveAddress = extender.createOffset(
			extender.ZEXT(converter.decToBin(memory.getIPIndex())), 
			pgOffset );
		
		// set last result
		lastResult = converter.binToDec(effectiveAddress);
		
		// store effective address in DR
		registers.setRegValue(DR, effectiveAddress );
		
		// set the condition codes
		registers.setConditionCodes(lastResult);
		
		// create the decoded string
		String decodedString = "LEA    R" + registers.convertRegisterCode(DR) +
			", " + converter.decToHex(converter.binToDec(effectiveAddress));
		
		// store the decoded instructions
		decodedInstructions.add(decodedString);
	}

/* Function: NOT()
 * Purpose:  Perform the bitwise complement operation on the contents of SR and 
 *           place the result in DR. The result sets the condition codes.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void NOT( String instruction )
	{
		// get values from instruction string
		String DR = instruction.substring(4, 7);
		String SR = instruction.substring(7, 10);
		
		// get contents of SR
		int sourceRegContents = registers.getRegValue(SR);
		
		// bitwise NOT SR
		int notSR = ~sourceRegContents;
		
		// store last result
		lastResult = notSR;
		
		// store notSR in DR
		registers.setRegValue(registers.convertRegisterCode(DR), notSR);
		
		// set condtion codes
		registers.setConditionCodes(notSR);
		
		// create the decoded string
		String decodedString = "NOT   R" + 
			registers.convertRegisterCode(DR) + ", R" + 
			registers.convertRegisterCode(SR);
		
		// store the decoded instruction
		decodedInstructions.add(decodedString);
	}

/* Function: RET()
 * Purpose:  Load the PC with the value in R7, thus allowing a return from a 
 *           previous JSR or JSRR instruction. The condition codes are not 
 *           modified.
 * Input:    nothing
 * Output:   nothing
 */
	public void RET()
	{
		// set IP index from R7
		memory.setIPIndex(registers.getRegValue(7));
		
		// create decoded string
		String decodedString = "RET";
		
		// store the decoded string
		decodedInstructions.add(decodedString);
	}
/* Function: ST()
 * Purpose:  Store from the specified register to the specified location on the 
 *           current page. The top 7 bits of the current program counter are 
 *           concatenated with the pgoffset9 field to form a 16-bit memory 
 *           address. The contents of SR are copied into memory at this address. 
 *           The condition codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void ST( String instruction )
	{
		// get values from instruction string
		String SR = instruction.substring(4, 7);
		String pgOffset = instruction.substring(7, 16);
		
		// create offset address
		String storeAddress = extender.createOffset(
			extender.ZEXT(converter.decToBin(memory.getIPIndex())), 
			pgOffset );
		
		// store SR contents into offset address
		memory.setValueAtMemAddress(converter.binToDec(storeAddress),
			registers.getRegValue(SR));
		
		// create decoded string
		String decodedString = "ST     R" + registers.convertRegisterCode(SR) +
			", " + converter.decToHex(converter.binToDec(storeAddress));
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: STI()
 * Purpose:  Store from the specified register to the indirectly specified 
 *           location. The top 7 bits of the current program counter are 
 *           concatenated with the pgoffset9 field to form a 16-bit memory 
 *           address. The contents of memory at this address are used as the 
 *           address where the contents of SR are to be copied. The condition 
 *           codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void STI( String instruction )
	{
		// get values from instruction string
		String SR = instruction.substring(4, 7);
		String pgOffset = instruction.substring(7, 16);
		
		// create offset address
		String loadPC = extender.createOffset(
			extender.ZEXT(converter.decToBin(memory.getIPIndex())),
			pgOffset );
		
		// get memory contents
		int memAddressContents = memory.getValueAtMemAddress(
			converter.binToDec(loadPC));
		
		// convert to string
		String storeAddress = extender.ZEXT(converter.decToBin(
			memAddressContents));
		
		// store SR into memory address
		memory.setValueAtMemAddress(converter.binToDec(storeAddress),
			registers.getRegValue(SR));
		
		// create decoded string
		String decodedString = "STI   R" + registers.convertRegisterCode(SR)
			+ ", " + converter.decToHex(memAddressContents);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: STR()
 * Purpose:  Store from the specified register to the specified location. The 
 * 			 offset is zero extended to 16 bits and added to the contents of 
 *           BaseR to form a memory address. The contents of SR are copied into 
 *           memory at this address. The condition codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void STR( String instruction )
	{
		// get values from instruction string
		String SR = instruction.substring(4, 7);
		String baseReg = instruction.substring(7, 10);
		String index6 = extender.ZEXT(instruction.substring(10, 16));
		
		int temp = registers.getRegValue(baseReg);
		int temp2 = converter.binToDec(index6);

		// add contents to get new address
		temp = temp + temp2;
		String newReg = extender.ZEXT(converter.decToBin(temp));
		
		// store SR contents into new address
		memory.setValueAtMemAddress( converter.binToDec(newReg), 
			registers.getRegValue(SR));
		
		// create decoded string
		String decodedString = "STR   R" + registers.convertRegisterCode(SR) +
			", R" + registers.convertRegisterCode(baseReg) + ", #" + 
			converter.binToDec(index6);
		
		// store decoded string
		decodedInstructions.add(decodedString);
	}

/* Function: TRAP()
 * Purpose:  Execute the system call specified by the trap number. The PC is 
 *           saved in R7, allowing a later return. The trapvect8 field is zero 
 *           extended to 16 bits. The contents of this memory location are 
 *           placed into the PC. The condition codes are not modified.
 * Input:    the instruction string
 * Output:   nothing
 */
	public void TRAP( String instruction )
	{
		// get trap vector
		String trapvect8 = extender.ZEXT(instruction.substring(8, 16));
		
		// store IP index in R7
		registers.setRegValue(7, memory.getIPIndex());
		
		// set value in memory
		memory.setValueAtMemAddress( memory.getIPIndex(), 
			memory.getValueAtMemAddress(converter.binToDec(trapvect8)));
		
		// create decoded string
		String decodedString = "HALT   " + 
			converter.decToHex(converter.binToDec(trapvect8));
		
		// store decoded string
		decodedInstructions.add(decodedString);
		
		// print decoded instructions
		for( int t = 0; t < decodedInstructions.size(); t++ )
		{
			System.out.println(decodedInstructions.get(t).toString());
		}
		
		// print register contents
		registers.printRegisterContents();
		
		// print used memory contents
		memory.printUsedMemContents();
		
		// exit program
		System.exit(0);
	}

/* Function: main()
 * Purpose:  to execute the interpreter
 * Input:    the absolute file name of the binary code
 * Output:   nothing
 */
	public static void main( String args[] )
	{
		Interpreter app = new Interpreter( "C:/testrun.txt" );
		app.run();
	}
}
