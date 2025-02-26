package it.unibo.tesi.chorol;


import it.unibo.tesi.chorol.utils.OutputSettings;
import it.unibo.tesi.chorol.visitor.flow.FlowController;
import jolie.lang.parse.ParserException;
import jolie.lang.parse.module.ModuleException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Paths;

public class Application {
	public static void main(String[] args) throws ParserException, IOException, ModuleException {
		Options options = new Options();
		options.addOption("T", "full-type", false, "Mostrare composizione tipi ricorsivi");
		options.addOption("S", "stdlib", false, "Includere anche i servizi della stdlib di Jolie");
		options.addOption("i", "input", true, "Path del servizio");
		options.addOption("o", "output", true, "Path delle viste");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("", options);
			System.exit(1);
			return;
		}

		if (cmd.hasOption("full-type") || cmd.hasOption("T")) OutputSettings.setFullType(true);
		if (cmd.hasOption("S") || cmd.hasOption("stdlib")) OutputSettings.setSaveStdLib(true);
		String input = cmd.hasOption("i") ? cmd.getOptionValue("i") : cmd.hasOption("input") ? cmd.getOptionValue("input") : "/home/kekko/Studio/tesi/chorol/src/main/resources/examples/IfThenElse/ifChain.ol";
		String output = cmd.hasOption("o") ? cmd.getOptionValue("o") : cmd.hasOption("output") ? cmd.getOptionValue("output") : "./generated";
		FlowController flowController = new FlowController(Paths.get(input), Paths.get(output));
		flowController.save();
	}
}
