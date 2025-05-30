package ed.lab.ed1final.controller; //Kevin Palencia

import ed.lab.ed1final.trie.Trie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trie")
public class TrieController {

    private final Trie trie = new Trie();

    @PostMapping("/{word}")
    public ResponseEntity<Void> insert(@PathVariable String word) {
        if (!word.matches("^[a-z]+$")) {
            return ResponseEntity.badRequest().build();
        }
        trie.insert(word);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{word}/count")
    public ResponseEntity<WordCountResponse> countWordsEqualTo(@PathVariable String word) {
        int count = trie.countWordsEqualTo(word);
        return ResponseEntity.ok(new WordCountResponse(word, count));
    }

    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<PrefixCountResponse> countWordsStartingWith(@PathVariable String prefix) {
        int count = trie.countWordsStartingWith(prefix);
        return ResponseEntity.ok(new PrefixCountResponse(prefix, count)); // CAMBIO: campo debe llamarse 'prefix'
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> erase(@PathVariable String word) {
        if (trie.countWordsEqualTo(word) == 0) {
            return ResponseEntity.badRequest().build(); // CAMBIO: validar si existe antes de borrar
        }
        trie.erase(word);
        return ResponseEntity.noContent().build();
    }

    private record WordCountResponse(String word, int wordsEqualTo) {}

    private record PrefixCountResponse(String prefix, int wordsStartingWith) {} // CAMBIO: 'prefix' en vez de 'word'
}
