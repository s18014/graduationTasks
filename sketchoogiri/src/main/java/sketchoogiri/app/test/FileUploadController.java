package sketchoogiri.app.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

	@GetMapping("")
	public String uploadForm(Model model) {
		return "upload";
	}

	@PostMapping("")
	public String upload(@RequestParam("file") MultipartFile multipartFile, Model model) {
		File uploadFile = mkfile(multipartFile);
		try {
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);
			uploadFileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("filepath", uploadFile.getPath().substring(1));
		return uploadForm(model);
	}
	
    private File mkfile(MultipartFile mFile){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String datetime = now.format(formatter);
        String dir = "./images";
        String fileName = mFile.getOriginalFilename();
        String extention = fileName.substring(fileName.lastIndexOf("."));
        File uploadFile = new File(dir, datetime + extention);
        // 既に存在する場合はプレフィックスをつける
        int prefix = 0;
        while(uploadFile.exists()){
            prefix++;
            uploadFile =
                    new File(dir, datetime + "-" + String.valueOf(prefix) + extention);
        }
        System.out.println(new File(dir).mkdir());
        return uploadFile;
    }
}
