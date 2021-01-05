package vn.metech.common;

import java.util.HashMap;
import java.util.Map;

public enum Command {

	DEV("DevRequest"),
	PROD("ProdRequest"),
	TPC("TPCRequest")
	//
	;

	String name;

	Command(String name) {
		this.name = name;
	}

	static Map<String, Command> commands = new HashMap<>();

	static {
		for (Command command : values()) {
			commands.put(command.name, command);
		}
	}

	public static Command of(String name) {
		Command command = commands.get(name);

		return command == null ? PROD : command;
	}

}
