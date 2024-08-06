package desafioqa;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

public class PokeApiTest {

    @Test
    public void testGetPokemonPikachu() {
        // Define a base URL
        RestAssured.baseURI = "https://pokeapi.co/api/v2";

        // Faz a requisição para o endpoint /pokemon/pikachu
        Response response = given()
                .when()
                .get("/pokemon/pikachu")
                .then()
                .statusCode(200)  // Verifica se o status code é 200
                .extract()
                .response();

        // Extrai o nome do Pokémon da resposta JSON
        String name = response.jsonPath().getString("name");

        // Verifica se o nome é igual a "pikachu"
        assertEquals("pikachu", name);
    }
}

