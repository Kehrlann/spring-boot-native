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
		books.put(
				"vingt-mille-lieues-sous-les-mers",
				new Book(
						"Vingt Mille Lieues sous les mers",
						"Jules Vernes",
						"Roman d'aventures",
						"""
						Vingt Mille Lieues sous les mers est un roman d'aventures de Jules Verne, paru en 1869-1870. Il
						relate le voyage de trois naufragés capturés par le capitaine Nemo, mystérieux inventeur qui
						parcourt les fonds des mers à bord du Nautilus, un sous-marin très en avance sur les
						technologies de l'époque.
						""",
						LocalDate.of(1869, Month.JANUARY, 1),
						"vingt-mille-lieues-sous-les-mers"
				)
		);
		books.put(
				"la-zone-du-dehors",
				new Book(
						"La Zone du Dehors",
						"Alain Damasio",
						"Roman d'anticipation",
						"""
						Il s'agit d'un roman d’anticipation qui s’intéresse aux sociétés de contrôle sous le modèle
						démocratique. On y suit la vie « molle » des habitants de Cerclon, société démocratique (ou
						pseudo-démocratique) installée sur un satellite imaginaire de Saturne en 20841. La société de
						Cerclon est caractérisée par le clastre : tous les deux ans, tous les citoyens se réunissent
						pour « classer » leurs compatriotes selon leur comportement, leur efficacité au travail, etc. De
						l'issue du clastre, dépendent le nom (composé d'un code de lettres) de l'individu (en
						développant l'idée de « dividu » n'existant que dans son rapport à la masse) et sa place dans le
						système. Les habitants de Cerclon se surveillent donc mutuellement et doivent, sous peine de
						déclassement, rester dans la « norme ». Le quotidien de cette société se voit secoué par les
						actions subversives de la « Volte », groupuscule contestataire qui ira jusqu’au bout de sa
						« volution ».
						""",
						LocalDate.of(1999, Month.JANUARY, 1),
						"la-zone-du-dehors"
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
