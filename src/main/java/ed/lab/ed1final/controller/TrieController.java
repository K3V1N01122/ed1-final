package ed.lab.ed1final.controller;

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
        WordCountResponse response = new WordCountResponse(word, count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{prefix}/prefix")
    public ResponseEntity<PrefixCountResponse> countWordsStartingWith(@PathVariable String prefix) {
        int count = trie.countWordsStartingWith(prefix);
        PrefixCountResponse response = new PrefixCountResponse(prefix, count);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<Void> erase(@PathVariable String word) {
        trie.erase(word);
        return ResponseEntity.noContent().build();
    }

    // DTO internos para las respuestas
    private record WordCountResponse(String word, int wordsEqualTo) {}
    private record PrefixCountResponse(String word, int wordsStartingWith) {}
}

