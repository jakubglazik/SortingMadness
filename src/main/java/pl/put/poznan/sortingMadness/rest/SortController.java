package pl.put.poznan.sortingMadness.rest;

import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/sort")
public class SortController {

    @PostMapping
    public List<Integer> Sort(@RequestBody SortRequest request) {
        List<Integer> data = request.getData();

        data.sort(Integer::compareTo);

        return data;
    }
}
