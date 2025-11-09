package spellchecker;

import java.util.Set;

import io.javalin.Javalin;

public class SpellServer {
	public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("/");
		}).start(7777);

		app.get("/spell-check", ctx -> {
			Set<String> words = SpellChecker.check(ctx.queryParam("word"));
			if (words == null)
				ctx.json("Word spelled correct.");
			else if (words.isEmpty()) {
				ctx.json("Word not found; no corrections are available.");
			} else
				ctx.json(words);

		});
	}
}
