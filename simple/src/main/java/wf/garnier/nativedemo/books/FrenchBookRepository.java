package wf.garnier.nativedemo.books;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class FrenchBookRepository implements BookRepository {

	private final Map<String, Book> books = new HashMap<>();

	public FrenchBookRepository() {
		//@formatter:off
		books.put(
				"ravage",
				new Book(
						"Ravage",
						"René Barjavel",
						"Roman d'anticipation",
						"""
						Dans une société mature, un jour l'électricité disparaît et plus aucune machine ne peut fonctionner. 
						Les habitants, anéantis par la soudaineté de la catastrophe, sombrent dans le chaos, 
						privés d'eau courante, de lumière et de moyens de déplacement...
						""",
						LocalDate.of(1943, Month.FEBRUARY, 27),
						"ravage"
				)
		);
		books.put(
				"la-planete-des-singes",
				new Book(
						"La Planète des Singes",
						"Pierre Boulle",
						"Science-fiction",
						"""
						Le roman raconte l’histoire de trois hommes qui explorent une planète lointaine similaire à la
						Terre, où les grands singes sont les espèces dominantes et intelligentes, alors que l'humanité
						est réduite à l’état animal. Le narrateur, Ulysse Mérou, est capturé par les singes et se
						retrouve enfermé dans un laboratoire. Prouvant son intelligence aux singes, il aide ensuite les
						scientifiques simiens à découvrir les origines de leur civilisation.
						""",
						LocalDate.of(1963, Month.JANUARY, 1),
						"la-planete-des-singes"
				)
		);
		//@formatter:on
	}

	@Override
	public Book findBookBySlug(String slug) {
		return books.get(slug);
	}

	@Override
	public Collection<Book> findAll() {
		return books.values();
	}

}
