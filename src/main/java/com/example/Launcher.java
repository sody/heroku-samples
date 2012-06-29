package com.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Launcher {
	private final String webAppFolder;

	public Launcher(final String webAppFolder) {
		this.webAppFolder = webAppFolder;
	}

	private static boolean isBlank(String s) {
		return s == null || s.trim().length() == 0;
	}

	public void startServer() throws Exception {
		final String webPort = System.getenv("PORT");
		final int port = isBlank(webPort) ? 8080 : Integer.parseInt(webPort);

		final Server server = new Server(port);
		final WebAppContext root = new WebAppContext();
		final SessionHandler sessionHandler = new SessionHandler();

		root.setSessionHandler(sessionHandler);
		root.setContextPath("/");
		root.setDescriptor(webAppFolder + "/WEB-INF/web.xml");
		root.setResourceBase(webAppFolder);
		root.setParentLoaderPriority(true);

		server.setHandler(root);
		server.start();
		server.join();
	}


	public static void main(String[] args) throws Exception {
		final CommandLineParser parser = new PosixParser();

		final Options options = new Options();
		options.addOption("w", "web-app-folder", true, "Root folder of web application");
		options.addOption("h", "help", false, "Show command usage");

		try {
			final CommandLine commandLine = parser.parse(options, args, true);

			if (!commandLine.getArgList().isEmpty()) {
				throw new ParseException("Unexpected command line arguments.");
			}

			if (commandLine.hasOption("help")) {
				showHelp(options);
				System.exit(0);
			}

			final Launcher main = new Launcher(commandLine.getOptionValue("web-app-folder", "src/main/webapp"));
			main.startServer();
		} catch (ParseException ex) {
			System.err.println(ex.getMessage());
			showHelp(options);
			System.exit(-1);
		}
	}

	private static void showHelp(final Options options) {
		new HelpFormatter().printHelp(Launcher.class.getName(), options);
	}
}
