package wf.garnier.nativedemo.starwars;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({ StarWarsController.SwapiCharacter.class, StarWarsController.SwapiResponse.class })
class StarWarsConfiguration {

}
