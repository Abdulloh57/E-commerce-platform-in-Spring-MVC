package library.uz.controller;

import library.uz.servise.FileServise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileServise fileServise;
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         Model model) throws Exception {

        String fileName = fileServise.uploadFile(file);

        model.addAttribute("imageUrl", "/images/" + fileName);

        return "image";
    }

    @GetMapping
    public String imagePath(){
        return "image";
    }

    @GetMapping("/v1/{imageUrl}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageUrl") String imageUrl) throws Exception {
        byte[] content =fileServise.getImage(imageUrl);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(content);
    }
}
